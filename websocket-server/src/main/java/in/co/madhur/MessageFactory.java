package in.co.madhur;

import in.co.madhur.dto.WebSocketBaseMessage;

public class MessageFactory {

    public static WebSocketBaseMessage getHeartBeatResp(int userId, long serverDelay) {
        WebSocketBaseMessage hbResponse = new WebSocketBaseMessage();
        hbResponse.setType("heartbeatresp");
        hbResponse.setUserId(userId);
        hbResponse.setServerDelay(serverDelay);
        return hbResponse;
    }
}
