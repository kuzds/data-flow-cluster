package ru.kuzds.userflow.bare.db2web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kuzds.userflow.bare.db2web.mapper.UserMapper;
import ru.kuzds.userflow.bare.db2web.repository.NewUserRepository;
import ru.kuzds.userflow.bare.db2web.repository.entity.NewUser;
import ru.kuzds.userflow.bare.db2web.rest.client.UserRestClient;
import ru.kuzds.userflow.userservice.SaveUserRequest;
import ru.kuzds.userflow.userservice.TransferType;
import ru.kuzds.userflow.userservice.UserServicePortType;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabasePollingService {

    private final NewUserRepository repository;
    private final UserServicePortType userServiceProxy;
    private final UserRestClient userRestClient;
    private final UserMapper userMapper;

    @Scheduled(fixedRate = 3000)
    public void pollDatabase() {

        List<NewUser> users = repository.findAll();
        log.info("Retrieved from database: {}", users);

        for (NewUser user : users) {
            SaveUserRequest request = userMapper.toDto(user);
            String userString = userMapper.toString(request.getUser());
            TransferType transferType = user.getTransferType();

            try {
                if (transferType == TransferType.REST) {
                    userRestClient.saveUser(request);
                } else if (transferType == TransferType.SOAP) {
                    userServiceProxy.saveUser(request);
                } else {
                    log.warn("User discarded due to incorrect transferType. Use 'REST' or 'SOAP' values");
                    continue;
                }
            } catch (Throwable e) {
                log.error("Could not send {}", userString);
                continue;
            }

            log.info("Sent {}", userString);
//            repository.delete(user);
        }
    }
}
