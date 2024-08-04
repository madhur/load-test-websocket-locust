
from locust import HttpUser, constant_pacing, User
from client.websocket_client import WebSocketUser
from scenarios.websocket_scenarios import WebSocketScenario

class WebSocketController(User):
    tasks = [WebSocketScenario]
    wait_time = constant_pacing(1)
    
    def __init__(self, *args, **kwargs):
        super(WebSocketController, self).__init__(*args, **kwargs)
        self.client = WebSocketUser()

      