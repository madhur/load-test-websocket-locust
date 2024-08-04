
from locust import HttpUser, constant_pacing
from client.websocket_client import WebSocketClient

class WebSocketController(HttpUser):
    def __init__(self, *args, **kwargs):
        super(WebSocketController).__init__(*args, **kwargs)
        self.client = WebSocketClient()