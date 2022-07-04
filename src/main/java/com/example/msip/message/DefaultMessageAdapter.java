package com.example.msip.message;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import lombok.extern.slf4j.Slf4j;

import javax.jms.*;

@Slf4j
public class DefaultMessageAdapter implements IMessageAdapter{

    @Override
    public String translateMessageBody() {
        return sendJMSMessageToIBMMQServer();
    }

    public String sendJMSMessageToIBMMQServer() {
        // These are all JDK Native imports
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        Destination tempDestination = null;
        MessageProducer producer = null;
        MessageConsumer consumer = null;

        try {
            MQQueueConnectionFactory connectionFactory = new MQQueueConnectionFactory();

            //Host name will reflect the host ip of the IBM Websphere server
            connectionFactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, "localhost");
            //Port will probably be 1414 but I'd ask
            connectionFactory.setIntProperty(WMQConstants.WMQ_PORT, 1414);
            //Need to get the channel name if it's not default
            connectionFactory.setStringProperty(WMQConstants.WMQ_CHANNEL, "SYSTEM.DEF.SVRCONN");
            connectionFactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            //Get the QUEUE MANAGER NAME. PROBABLY WONT BE QM1
            connectionFactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, "QM1");
            connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);


            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("queue:///Q1");
            producer = session.createProducer(destination);

            //This is just showing how to send a message.
            long uniqueNumber = System.currentTimeMillis() % 1000;
            TextMessage message = session.createTextMessage("SimpleRequestor: Your lucky number yesterday was " + uniqueNumber);
            connection.start();
            producer.send(message);
            return "THIS WORKED";
        } catch (JMSException e) {
            log.warn(String.valueOf(e.getLinkedException()));
        }
        return "THIS DID NOT WORK";
    }
}
