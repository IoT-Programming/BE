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

    @KafkaListener(topics = "ResponseTopic", groupId = "response-group")
    public void consumeResponse(ConsumerRecord<String, SensorDto> record) {
        String correlationId = record.key();

        if (correlationId == null) {
            System.err.println("Received message with null key");
            return; // 키가 null인 메시지를 무시하거나 별도로 처리
        }

        SynchronousQueue<SensorDto> queue = responseMap.get(correlationId);
        if (queue != null) {
            queue.offer(record.value());
            responseMap.remove(correlationId);
        } else {
            System.err.println("No matching queue found for correlationId: " + correlationId);
        }
    }

}