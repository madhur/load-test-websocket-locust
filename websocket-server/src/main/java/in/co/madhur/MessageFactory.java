package in.co.madhur;

import in.co.madhur.dto.WebSocketBaseMessage;

public class MessageFactory {

    public static WebSocketBaseMessage getHeartBeatResp(int userId, long publishTimestamp) {
        WebSocketBaseMessage hbResponse = new WebSocketBaseMessage();
        hbResponse.setType("heartbeatresp");
        hbResponse.setUserId(userId);
        hbResponse.setClientPublishTimestamp(publishTimestamp);
        hbResponse.setServerDelay(System.currentTimeMillis() - publishTimestamp);
        return hbResponse;
    }
}
