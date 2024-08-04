### Locust script to load test websocket server


```shell
locust -f controller/websocket_controller.py --host http://localhost:8080
```

The websocket server is specified in `config.ini` file.