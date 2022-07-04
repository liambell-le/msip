package com.example.msip.controller;

import com.example.msip.data.MessageBody;
import com.example.msip.service.MessageConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageConsumptionRestController {

    @Autowired
    MessageConsumptionService service;

    @PostMapping
    public String consumeMessageBody(@RequestBody MessageBody messageBody) {
        return service.testMessageQueue(messageBody);
    }
}
