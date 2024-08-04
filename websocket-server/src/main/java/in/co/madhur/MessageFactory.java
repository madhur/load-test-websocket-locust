package in.co.madhur;

import in.co.madhur.dto.WebSocketBaseMessage;

public class MessageFactory {

    public static WebSocketBaseMessage getHeartBeatResp() {
        WebSocketBaseMessage hbResponse = new WebSocketBaseMessage();
        hbResponse.setType("heartbeatresp");
        return hbResponse;
    }
}
