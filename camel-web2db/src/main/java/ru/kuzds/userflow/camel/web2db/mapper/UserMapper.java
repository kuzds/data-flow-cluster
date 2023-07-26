package ru.kuzds.userflow.camel.web2db.mapper;

import org.springframework.stereotype.Component;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.SaveUserResponse;

@Component
public class UserMapper {

    public SaveUserResponse toSaveUserResponse(SaveUserRequest request) {
        SaveUserResponse response = new SaveUserResponse();
        response.setUser(request.getUser());
        return response;
    }
}

