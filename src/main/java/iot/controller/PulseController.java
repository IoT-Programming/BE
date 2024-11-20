package iot.controller;

import iot.domain.User;
import iot.util.UserUtil;
import iot.service.PulseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/pulse")
public class PulseController {
    @Autowired
    private PulseServiceImpl pulseService;
    @Autowired
    private UserUtil userUtil;

    // GET 요청 처리
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> handleGetRequest(
            @RequestParam(value = "value", required = false) Double value) {
        if (value == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Missing 'value' parameter"
            ));
        }
        User user = userUtil.findUser("John");
        pulseService.updateStatus(value, user);

        // 응답 생성
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "receivedValue", value,
                "timestamp", System.currentTimeMillis()
        ));
    }

    // POST 요청 처리
    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> handlePostRequest(
            @RequestBody Map<String, Object> payload) {
        if (!payload.containsKey("value")) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Missing 'value' key in payload"
            ));
        }

        // 로직 처리 (예: 센서 데이터 처리)
        System.out.println("Received payload: " + payload);

        // 응답 생성
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "receivedPayload", payload,
                "timestamp", System.currentTimeMillis()
        ));
    }
}
