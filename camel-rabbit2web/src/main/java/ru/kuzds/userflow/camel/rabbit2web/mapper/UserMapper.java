package ru.kuzds.userflow.camel.rabbit2web.mapper;

import org.springframework.stereotype.Component;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.User;

@Component
public class UserMapper {

    public SaveUserRequest toSaveUserRequest(User user) {
        SaveUserRequest request = new SaveUserRequest();
        request.setUser(user);
        return request;
    }
}
