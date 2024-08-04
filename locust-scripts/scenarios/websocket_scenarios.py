from locust import TaskSet
import definitions
from util import config_util
from dataproviders.user_id_provider import get_user_id
project_config = config_util.get_value_from_config(definitions.get_project_path() + '/config.ini')
websocket_host = project_config.get('WEBSOCKET', 'HOST')

class WebSocketScenario(TaskSet):

    def test_websocket(self):
        user_id = get_user_id();

        if user_id is None:
            ws = self.client.connect(websocket_host, user_id)
            ws.run_forever()