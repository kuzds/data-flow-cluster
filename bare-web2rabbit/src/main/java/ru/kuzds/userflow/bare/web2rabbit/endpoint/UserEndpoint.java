package ru.kuzds.userflow.bare.web2rabbit.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kuzds.userflow.bare.web2rabbit.mapper.UserMapper;
import ru.kuzds.userflow.bare.web2rabbit.service.RabbitService;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.SaveUserResponse;
import ru.kuzds.userflow.userservice.User;
import ru.kuzds.userflow.userservice.UserServicePortType;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserEndpoint implements UserServicePortType {


    private final RabbitService rabbitService;
    private final UserMapper userMapper;

    @Override
    public SaveUserResponse saveUser(SaveUserRequest request) {
        User user = request.getUser();
        log.info("Sending {}", userMapper.toString(user));

        rabbitService.send(user);

        return userMapper.toSaveUserResponse(request);
    }
}
