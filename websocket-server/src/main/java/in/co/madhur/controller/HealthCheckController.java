package in.co.madhur.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    private final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    @GetMapping(value = "/shallow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> shallowHealthCheck() {
        try {
            logger.info("Starting shallow health check");
            logger.info("Shallow health check completed successfully");
            return ResponseEntity.ok("Shallow health check completed");
        } catch (Exception e) {
            logger.error("Shallow health check failed because of {}",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Shallow health check failed");
        }
    }



}
