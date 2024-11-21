package iot.service;

import iot.domain.Notification;
import iot.handler.SensorWebSocketHandler;
import iot.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final SensorWebSocketHandler sensorWebSocketHandler;

    public NotificationService(SensorWebSocketHandler sensorWebSocketHandler) {
        this.sensorWebSocketHandler = sensorWebSocketHandler;
    }

    public void sendRealTimeNotification(String message) {
        sensorWebSocketHandler.sendNotification(message);
    }
}