package Iot.sensor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class SensorController {

    @PostMapping("/sensor/{cmd}")
    public void handleData(@PathVariable String cmd, @RequestBody String data) {

        // 여기서 db에 넘어온 값들 저장

    }
}

