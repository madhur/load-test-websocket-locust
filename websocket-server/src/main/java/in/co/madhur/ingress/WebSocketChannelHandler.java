package in.co.madhur.ingress;

import in.co.madhur.config.SystemFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;

import java.io.IOException;

@WebSocket
public class WebSocketChannelHandler {

    private String connectionId;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelHandler.class);


    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        try {
            logger.info("Inside onConnect for session {}", session);
        } catch (Exception e) {
            logger.error("Exception in onConnect for session {} connectionId={} due to {}", session,
                    connectionId, e);
        }
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        try {
            processMessage(session, message);
        } catch (Exception e) {
            logger.error("Exception in onMessage for session {} message {} due to {}", session, message,
                    e);
        }
    }

    private void processMessage(Session session, String message) {
        JSONObject jsonMessage = new JSONObject(message);
        String messageType = jsonMessage.optString("type").toLowerCase();
        processEventsBasedOnMessageType(session, messageType, jsonMessage);
    }

    private void processEventsBasedOnMessageType(Session session, String messageType, JSONObject jsonMessage) {
        switch (messageType) {
            case "heartbeatreq":
                SystemFactory.getInstance().getWebSocketMessageHandler().handleHeartBeatMessages(session, jsonMessage);
                break;
            default:
                logger.info("Unknown message type received : {}", messageType);
        }
    }



    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        try {
            try {
                logger.info("Inside onClose for session {} status {} reason {}", session, status, reason);

            } catch (Exception e) {
                logger.error(
                        "Exception while calling remove session lua. Session : {}, status {}, Reason {}  due to {}",
                        session, status, reason, e);
            }
        } catch (Exception e) {
            logger.error("Exception in onClose for session {} status {} reason {} due to {}", session, status,
                    reason, e);
        }
    }

    @OnWebSocketError
    public void onWebSocketError(Session session, Throwable e) {
        logger.error("Inside onWebSocketError for session {} due to {}", session,
               e);
    }
}
