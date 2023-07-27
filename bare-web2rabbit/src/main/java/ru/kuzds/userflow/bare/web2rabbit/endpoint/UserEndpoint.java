package ru.kuzds.userflow.bare.web2rabbit.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kuzds.userflow.bare.web2rabbit.service.RabbitService;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.SaveUserResponse;
import ru.kuzds.userflow.userservice.User;
import ru.kuzds.userflow.userservice.UserServicePortType;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserEndpoint implements UserServicePortType {


    private final RabbitService rabbitService;

    @Override
    public SaveUserResponse saveUser(SaveUserRequest request) {
        User user = request.getUser();
        log.info("Received user using SOAP: id={} email={}", user.getId(), user.getEmail());

        user.setId(ThreadLocalRandom.current().nextInt(0, 10000));
        SaveUserResponse saveUserResponse = new SaveUserResponse();
        saveUserResponse.setUser(user);

        rabbitService.send(user);

        return saveUserResponse;
    }
}
