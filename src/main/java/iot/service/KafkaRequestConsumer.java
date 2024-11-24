package iot.service;

import iot.domain.User;
import iot.dto.SensorDto;
import iot.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaRequestConsumer {

    private final SensorService sensorService;
    private final UserUtil userUtil;
    private final NotificationService notificationService;
    private final KafkaTemplate<String, SensorDto> kafkaTemplate;


    @KafkaListener(topics = "RequestTopic", groupId = "sensor-group")
    public void consume(SensorDto sensorDto) {
        User user = userUtil.findUser("John");
        sensorService.updateStatus(sensorDto, user);
        notificationService.sendRealTimeNotification("Notification for " + user.getName());

        kafkaTemplate.send("ResponseTopic", sensorDto); // SensorDto를 응답으로 전송
    }
}