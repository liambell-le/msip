package com.example.msip.service;

import com.example.msip.data.MessageBody;
import com.example.msip.message.DefaultMessageAdapter;
import com.example.msip.message.IMessageAdapter;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumptionService {

    IMessageAdapter adapter = new DefaultMessageAdapter();

    public String testMessageQueue(MessageBody body) {
        return adapter.translateMessageBody();
    }

}
