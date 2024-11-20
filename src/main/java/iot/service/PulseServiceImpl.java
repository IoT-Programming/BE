package iot.service;

import iot.repository.UserRepository;
import iot.domain.User;
import iot.util.UserUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PulseServiceImpl implements PulseService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserUtil userUtil;

    @Transactional
    public void updateStatus(Double bpmValue, User user){
        if (bpmValue > 81.0){
            userUtil.switchStatus(user);
            System.out.println(user.getName() + "님의 상태가 바뀌었습니다 : " + user.getStatus());
        }
    }
}
