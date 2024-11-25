package iot.service;

import iot.dto.SensorDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaRequestProducer {

    private final KafkaTemplate<String, SensorDto> kafkaTemplate;

    public void sendRequest(String correlationId, SensorDto sensorDto) {
        kafkaTemplate.send("RequestTopic", correlationId, sensorDto);
    }
}