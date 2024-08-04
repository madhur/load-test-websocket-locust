package in.co.madhur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = "in.co.madhur")
public class WebsocketServerApp {

    public static void main(String[] args) {
        System.out.println("Staring application..................");
        SpringApplication.run(WebsocketServerApp.class, args);
    }
}
