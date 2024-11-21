package iot.service;

import iot.dto.SensorDto;
import iot.domain.User;
import iot.util.StatusStandard;
import iot.util.UserUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static iot.util.StatusStandard.*;

@Service
public class SensorService {
    private StatusStandard statusStandard;
    private UserUtil userUtil;

    public SensorService(UserUtil userUtil){
        this.userUtil = userUtil;
    }

    @Transactional
    public void updateStatus(SensorDto sensorDto, User user){
        int overNum = userUtil.calculateValue(sensorDto, userUtil.judgeAge(user));
        userUtil.switchStatus(user, overNum);
    }
}
