from locust import TaskSet, task
from util import config_util, definitions
from dataproviders.test_data_provider import get_user_id
print(definitions.get_project_path())
project_config = config_util.get_value_from_config(definitions.get_project_path() + '/../config.ini')
print(project_config)
websocket_host = project_config.get('WEBSOCKET_SERVER', 'host')

class WebSocketScenario(TaskSet):

    @task
    def test_websocket(self):
        user_id = get_user_id();
        print(user_id)
        if user_id is not None:
            ws = self.client.connect(websocket_host, user_id)
            ws.run_forever()