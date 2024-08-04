package in.co.madhur.dto;

import lombok.Data;

@Data
public class WebSocketBaseMessage {
    private String type;
    private int userId;
    private long serverDelay;
    private long clientPublishTimestamp;
}
