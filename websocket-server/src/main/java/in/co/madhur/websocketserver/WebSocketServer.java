package in.co.madhur.websocketserver;

import in.co.madhur.ingress.WebSocketChannelHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WebSocketServer {

    private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    private Server server;
    private boolean isRunning;

    @PostConstruct
    public void initialize() {
        logger.debug("Initializing WebSocketServerExtension");
        QueuedThreadPool threadPool = new QueuedThreadPool(100);
        server = new Server(threadPool);
        server.manage(threadPool);
        server.setStopTimeout(5*1000);
        start("0.0.0.0", 8080);
    }

    private boolean start(String host, int port) {
        try {
            ServerConnector socketConnector = new ServerConnector(server);
            socketConnector.setPort(port);
            socketConnector.setHost(host);
            server.setConnectors(new Connector[] { socketConnector });
            disableServerVersionHeader();
            WebSocketHandler webSocketHandler = new WebSocketHandler() {

                @Override
                public void configure(WebSocketServletFactory webSocketServletFactory) {
                    webSocketServletFactory.register(WebSocketChannelHandler.class);
                    webSocketServletFactory.getExtensionFactory().unregister("permessage-deflate");
                }
            };

            server.setHandler(webSocketHandler);
            server.start();
            isRunning = server.isStarted();
            logger.info("Websocket Jetty server started @- {}/{}", host, port);

        } catch (Exception e) {
            logger.error("Unable to start the server on {}/{}", host, port, e);
            isRunning = false;
        }
        return isRunning;
    }

    private void disableServerVersionHeader() {
        for (Connector y : server.getConnectors()) {
            y.getConnectionFactories().stream().filter(cf -> cf instanceof HttpConnectionFactory).forEach(
                    cf -> ((HttpConnectionFactory) cf).getHttpConfiguration().setSendServerVersion(false));
        }
    }

    public boolean stop() {
        try {
            server.stop();
        } catch (Exception e) {
            logger.error("Unable to stop server");
        }
        isRunning = !server.isStopped();
        return !isRunning;
    }

    public boolean isRunning() {
        return isRunning;
    }

}
