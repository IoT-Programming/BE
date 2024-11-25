package iot.controller;

import iot.domain.User;
import iot.dto.SensorDto;
import iot.service.KafkaRequestProducer;
import iot.service.NotificationService;
import iot.service.SensorService;
import iot.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor")
@RequiredArgsConstructor
public class SensorController {

    @Autowired
    private SensorService sensorService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserUtil userUtil;

    private final KafkaRequestProducer producer;
    private final KafkaTemplate<String, SensorDto> kafkaTemplate;

    // GET 요청 처리
    @PostMapping("/post")
    public ResponseEntity<SensorDto> handlePostRequest(@RequestBody SensorDto sensorDto) {
        kafkaTemplate.send("RequestTopic", "correlationId-123", sensorDto)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Message sent successfully: " + result.getProducerRecord());
                    } else {
                        System.err.println("Message sending failed: " + ex.getMessage());
                    }
                });

        return ResponseEntity.ok().body(sensorDto);
    }
    @PostMapping("/post/kafkaless")
    public ResponseEntity<SensorDto> handleGetRequestKafkaless(
            @RequestBody SensorDto sensorDto) {
        User user = userUtil.findUser("John");
        sensorService.updateStatus(sensorDto, user);
        notificationService.sendRealTimeNotification(user);
        return ResponseEntity.ok().body(sensorDto);
    }

}
