package iot.service;

import iot.domain.User;
import iot.handler.SensorWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {

    private final SensorWebSocketHandler sensorWebSocketHandler;

    public NotificationService(SensorWebSocketHandler sensorWebSocketHandler) {
        this.sensorWebSocketHandler = sensorWebSocketHandler;
    }

    public void sendRealTimeNotification(User user) {
        CompletableFuture.runAsync(() -> {
            try {
                String message = user.getName() + "님의 상태가 " + user.getPrevStatus() + "에서 " + user.getStatus() + "로 변경되었습니다."
                        + "연락처: " + user.getPhone();
                System.out.println("Sending WebSocket Notification: " + message);
                sensorWebSocketHandler.sendNotification(message);
                System.out.println("WebSocket Notification Sent.");
            } catch (Exception e) {
                System.err.println("Error in async notification: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

}
