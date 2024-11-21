package iot.controller;

import iot.domain.User;
import iot.dto.SensorDto;
import iot.service.NotificationService;
import iot.service.SensorService;
import iot.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor")
// INTENTION: 추후 모든 센서 데이터를 하나의 객체에 담아 한 번에 전송하는 방식을 염두에 두고 제작.
public class SensorController {

    @Autowired
    private SensorService sensorService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserUtil userUtil;

    // GET 요청 처리
    @PostMapping("/post")
    public ResponseEntity<SensorDto> handleGetRequest(
            @RequestBody SensorDto sensorDto) {
        User user = userUtil.findUser("John");
        sensorService.updateStatus(sensorDto, user);
        notificationService.sendRealTimeNotification("John");
        return ResponseEntity.ok().body(sensorDto);
    }

}
