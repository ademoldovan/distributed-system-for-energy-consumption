package com.ds.Assignment_1.consumer;

import com.ds.Assignment_1.constants.NotificationEndpoints;
import com.ds.Assignment_1.dto.Message;
import com.ds.Assignment_1.dto.Notification;
import com.ds.Assignment_1.model.Client;
import com.ds.Assignment_1.model.Consumption;
import com.ds.Assignment_1.model.Device;
import com.ds.Assignment_1.model.Sensor;
import com.ds.Assignment_1.repository.ClientRepository;
import com.ds.Assignment_1.repository.ConsumptionRepository;
import com.ds.Assignment_1.repository.DeviceRepository;
import com.ds.Assignment_1.repository.SensorRepository;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final static String QUEUE_NAME="queue";

    @Autowired
    private ConsumptionRepository consumptionRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SimpMessagingTemplate template;

    private Message previousValue;

    public MessageConsumer(){}

//    @Scheduled(fixedRate = 10000)
//    public void listener() throws Exception{
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUri("amqps://gawcxobq:Std3s-pi1MOeB9V-5zQdwvWIXXeeiZl6@fox.rmq.cloudamqp.com/gawcxobq");
//        Connection connection = null;
//        try {
//            connection = factory.newConnection();
//        } catch (IOException | TimeoutException e) {
//            System.exit(0) ;
//        }
//        Channel channel = connection.createChannel();
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String consumption = new String(delivery.getBody(), "UTF-8");
//            System.out.println(" [x] Received '" + consumption + "'");
//            JSONParser parser = new JSONParser();
//            JSONObject json = null;
//            try {
//                json = (JSONObject) parser.parse(consumption);
//             } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Date timestamp = new Date();
//            Message message = new Message(timestamp, Long.parseLong(json.get("sensorId").toString()), Double.parseDouble(json.get("measurementValue").toString()));
//            Sensor sensor = sensorRepository.findSensorById(Long.parseLong(json.get("sensorId").toString()));
//            Client client = clientRepository.findClientById(sensor.getDevice().getClient().getId());
//            Consumption cons = new Consumption(timestamp, Double.parseDouble(json.get("measurementValue").toString()), sensor);
//            if (previousValue != null) {
//                double powerPeak = (message.getMeasurementValue() - previousValue.getMeasurementValue());
//                if (powerPeak > sensor.getMaximum_value()) {
//                    Notification notification = new Notification(sensor.getId(), client.getUser().getUsername());
//                    this.template.convertAndSend(NotificationEndpoints.NOTIFICATION, notification);
//                }
//            }
//            consumptionRepository.save(cons);
//            previousValue = message;
//        };
//        channel.basicConsume(QUEUE_NAME, true, deliverCallback,
//                consumerTag -> { });
//        connection.close();
//    }

}
