package iot.service;

import iot.dto.SensorDto;
import iot.domain.User;
import iot.repository.UserRepository;
import iot.util.StatusStandard;
import iot.util.UserUtil;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static iot.util.StatusStandard.*;

@Service
public class SensorService {
    private KafkaTemplate <String, SensorDto> kafkaTemplate;
    private UserRepository userRepository;
    private StatusStandard statusStandard;
    private NotificationService notificationService;
    private UserUtil userUtil;

    public SensorService(UserRepository userRepository, UserUtil userUtil){
        this.userRepository = userRepository;
        this.userUtil = userUtil;
    }

    @Transactional
    public void updateStatus(SensorDto sensorDto, User user){
        user.setPrevStatus(user.getStatus());
        int overNum = userUtil.calculateValue(sensorDto, userUtil.judgeAge(user));
        user.setStatus(overNum);
        userRepository.save(user);
    }
    public void processSensorData(SensorDto sensorDto) {
        User user = userUtil.findUser("John");
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // 비즈니스 로직 수행
        updateStatus(sensorDto, user);
        notificationService.sendRealTimeNotification(user);

        // Kafka 응답 전송
        kafkaTemplate.send("ResponseTopic", sensorDto);
    }
}
