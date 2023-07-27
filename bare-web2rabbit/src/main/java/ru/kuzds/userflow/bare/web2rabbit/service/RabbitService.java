package ru.kuzds.userflow.bare.web2rabbit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.kuzds.userflow.userservice.User;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitService {

    public final static String EXCHANGE_NAME = "user.flow.cluster.exchange";
    public final static String ROUTING_KEY = "user.flow.cluster.queue";

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void send(User user) {

        byte[] body;
        try {
            body = objectMapper.writeValueAsBytes(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        rabbitTemplate.send(EXCHANGE_NAME, ROUTING_KEY, new Message(body));
    }
}
