package in.co.madhur.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.co.madhur.ingress.WebSocketMessageHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SystemFactory {

    @Getter
    private static SystemFactory instance;

    @Getter
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Getter
    private WebSocketMessageHandler webSocketMessageHandler;

    private SystemFactory() {

    }
    @PostConstruct
    public void init() {
        instance = this;
    }
}
