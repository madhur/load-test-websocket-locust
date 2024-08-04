import websocket
import time
import gevent
import ujson
import logging
import json
from locust import events

heartbeatreq = '{"type":"heartbeatreq","userId":1}'


class WebSocketUser(object):
    def __init__(self):
        self.ws = None

    def connect(self, url, user_id):
        start_time = time.time()
        try:
            print("Connecting to ws", url, user_id)
            self.set_user_id(user_id)
            self.ws = websocket.WebSocketApp(url)
            self.ws.keep_running = False
            self.ws.on_open = self.on_open
            self.ws.on_message = self.on_message
            self.ws.on_error = self.on_error
            self.ws.on_close = self.on_close
        except websocket.WebSocketTimeoutException as e:
            total_time = int((time.time() - start_time) * 1000)
            events.request.fire(request_type="web_socket", name='ws', response_time=total_time,
                                        exception=e)
        else:
            total_time = int((time.time() - start_time) * 1000)
            events.request.fire(request_type="web_socket", name='ws', response_time=total_time,
                                        response_length=0)
            
        return self.ws

    def on_stop(self):
        self.ws.close()

    def on_message(self, app, arg):
        print("On message--" , arg)

    def on_open(self, arg):
        print('Ws connection opened for userId=%s', self.get_user_id(), arg)
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
            heartbeatreq_json_string = json.dumps(heartbeatreq_json_data) + "\n"
            print("Sending heartbeat", heartbeatreq_json_string)
            self.ws.send(heartbeatreq_json_string)
            time.sleep(0.5)

    def get_user_id(self):
        return self.user_id

    def set_user_id(self, user_id):
        self.user_id = user_id

    def on_error(self, error, arg):
        print("Error--", arg)

    def on_close(self, app, close_msg, arg):
        print('CLOSED userId', self.get_user_id())
