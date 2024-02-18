package com.eTechub.springbootrabbitmq.controller;


import com.eTechub.springbootrabbitmq.dto.User;
import com.eTechub.springbootrabbitmq.publisher.RabbitMQJsonProducer;
import com.eTechub.springbootrabbitmq.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class JsonMessageController {

    private RabbitMQJsonProducer jsonProducer;

    public JsonMessageController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestBody User user) {
      jsonProducer.sendJsonMessage(user);
      return ResponseEntity.ok("Json message sent to RabbitMQ....");
    }
}
