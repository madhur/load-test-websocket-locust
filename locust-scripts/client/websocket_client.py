import websocket
import time
import gevent
import ujson
import json
from locust import events
from util import config_util, definitions

project_config = config_util.get_value_from_config(definitions.get_project_path() + '/../config.ini')
websocket_host = project_config.get('WEBSOCKET_SERVER', 'host')
heartbeat_interval = project_config.get('WEBSOCKET_SERVER', 'heartbeat_interval_secs')
heartbeatreq = '{"type":"heartbeatreq","userId":1}'


class WebSocketUser(object):
    def __init__(self):
        self.ws = None

    def connect(self, url, user_id):
        start_time = time.time()
        try:
            self.set_user_id(user_id)
            self.ws = websocket.WebSocketApp(url)
            self.ws.keep_running = False
            self.ws.on_open = self.on_open
            self.ws.on_message = self.on_message
            self.ws.on_error = self.on_error
            self.ws.on_close = self.on_close
        except websocket.WebSocketTimeoutException as e:
            total_time = int((time.time() - start_time) * 1000)
            events.request.fire(request_type="web_socket", name='ws_connect', response_time=total_time,
                                        exception=e)
        else:
            total_time = int((time.time() - start_time) * 1000)
            events.request.fire(request_type="web_socket", name='ws_connect', response_time=total_time,
                                        response_length=0)
            
        return self.ws

    def on_stop(self):
        self.ws.close()

    def on_message(self, app, msg):
        msg_json = json.loads(msg)
        if msg_json['type'] == 'heartbeatresp':
            serverDelay = msg_json['serverDelay']
            events.request.fire(request_type="web_socket", name='heartbeatres', response_time=int(serverDelay),
                                        response_length=0)

    def on_open(self, app):
        #print('Ws connection opened for userId=%s', self.get_user_id())
        self.set_heartbeat_gevent(gevent.spawn(self.heartbeat))


    def on_ping(self):
        self.ws.ping()

    def on_pong(self):
        self.ws.pong()

    def set_heartbeat_gevent(self, heartbeat_gevent):
        self.heartbeat_gevent = heartbeat_gevent

    def heartbeat(self):
        while True:
            heartbeatreq_json_data = ujson.loads(heartbeatreq)
            heartbeatreq_json_data['userId'] = self.get_user_id()
            heartbeatreq_json_data['publishTimestamp'] = int(time.time()*1000)
            heartbeatreq_json_string = json.dumps(heartbeatreq_json_data) + "\n"
            self.ws.send(heartbeatreq_json_string)
            time.sleep(int(heartbeat_interval))

    def get_user_id(self):
        return self.user_id

    def set_user_id(self, user_id):
        self.user_id = user_id

    def on_error(self, error, arg):
        print("Error--", arg)

    def on_close(self, app, close_msg, arg):
        #print('CLOSED userId', self.get_user_id())
        pass
