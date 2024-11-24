package iot.service;

import iot.dto.SensorDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

@Service
public class KafkaResponseConsumer {

    private final ConcurrentHashMap<String, SynchronousQueue<SensorDto>> responseMap = new ConcurrentHashMap<>();

    public SynchronousQueue<SensorDto> registerResponseQueue(String correlationId) {
        SynchronousQueue<SensorDto> queue = new SynchronousQueue<>();
        responseMap.put(correlationId, queue);
        return queue;
    }

    @KafkaListener(topics = "ResponseTopic", groupId = "response-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumeResponse(ConsumerRecord<String, SensorDto> record) {
        String correlationId = record.key();
        SensorDto responseData = record.value();

        SynchronousQueue<SensorDto> queue = responseMap.get(correlationId);
        if (queue != null) {
            queue.offer(responseData); // 응답 전달
            responseMap.remove(correlationId); // 매핑 제거
        } else {
            System.err.println("No matching queue found for correlationId: " + correlationId);
        }
    }
}
