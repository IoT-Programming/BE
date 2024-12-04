package iot.service;

import iot.domain.User;
import iot.dto.ResponseDto;
import iot.dto.SensorDto;
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

    @Autowired
    private final SensorWebSocketHandler sensorWebSocketHandler;

    public NotificationService(SensorWebSocketHandler sensorWebSocketHandler) {
        this.sensorWebSocketHandler = sensorWebSocketHandler;
    }

    public void sendRealTimeNotification(User user, SensorDto sensorDto) {
        CompletableFuture.runAsync(() -> {
            try {
                ResponseDto.ResponseDtoBuilder responseDtoBuilder= ResponseDto.builder()
                        .BPM(sensorDto.getBPM())
                        .bodyTemp(sensorDto.getBodyTemp())
                        .airTemp(sensorDto.getAirTemp())
                        .lan(sensorDto.getLan())
                        .lon(sensorDto.getLon());
//                String message = "pulse: " + sensorDto.getPulse()
//                        + "bodyTemp: " + sensorDto.getBodyTemp()
//                        + "airTemp: " + sensorDto.getAirTemp()
//                        + "lan: " + sensorDto.getLan()
//                        + "lon: " + sensorDto.getLon();
                if(user.getStatus() != user.getPrevStatus()){
//                    message += "message: " + user.getName() + "님의 상태가 " + user.getPrevStatus() + "에서 " + user.getStatus() + "로 변경되었습니다."
//                            + "연락처: " + user.getPhone();
                    responseDtoBuilder.message(user.getName() + "님의 상태가 " + user.getPrevStatus() + "단계에서 " + user.getStatus() + "단계로 변경되었습니다.")
                            .phone(user.getPhone());
                }
                ResponseDto responseDto = responseDtoBuilder.build();
                //sensorWebSocketHandler.broadcastMessage(message);
                sensorWebSocketHandler.broadcastMessage(responseDto);
                System.out.println("WebSocket Notification Sent.");
            } catch (Exception e) {
                System.err.println("Error in async notification: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

}
