package iot.service;

import iot.dto.SensorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaRequestProducer {

    private final KafkaTemplate<String, SensorDto> kafkaTemplate;

    public void sendRequest(String correlationId, SensorDto sensorDto) {
        kafkaTemplate.send("RequestTopic", correlationId, sensorDto);
    }
}