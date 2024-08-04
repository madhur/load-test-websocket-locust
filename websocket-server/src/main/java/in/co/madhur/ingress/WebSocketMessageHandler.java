package in.co.madhur.ingress;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.co.madhur.MessageFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.Executor;

@Service
public class WebSocketMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketMessageHandler.class);

    @Autowired
    private Executor heartbeatTaskExecutor;

    @Autowired
    private ObjectMapper objectMapper;

    public void handleHeartBeatMessages(Session session) {
        heartbeatTaskExecutor.execute(() -> {
            try {
                session.getRemote().sendString(objectMapper.writeValueAsString(MessageFactory.getHeartBeatResp()));
            } catch (IOException e) {
                logger.error("Error sending heartbeat response to session {}", session, e);
            }
        });

    }
}
