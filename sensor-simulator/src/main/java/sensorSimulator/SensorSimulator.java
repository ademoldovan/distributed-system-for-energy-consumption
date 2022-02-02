package sensorSimulator;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.tools.json.JSONWriter;
import model.Message;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

public class SensorSimulator {
    private final Long sensorId;
    private final static String QUEUE_NAME="queue";

    public SensorSimulator(Long sensorId){
        this.sensorId = sensorId;
    }

    public void start() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException {

        Timer timer = new Timer("Timer");
        BufferedReader reader = new BufferedReader(
                new FileReader("D:\\FACULTATE\\AN 4\\SEM I\\SD\\Assignment_2\\sensor.csv"));
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://gawcxobq:Std3s-pi1MOeB9V-5zQdwvWIXXeeiZl6@fox.rmq.cloudamqp.com/gawcxobq");
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            System.out.println("!!!EROARE DE CONECTARE LA RABBITMQ!!!");
            System.exit(0) ;
        }
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        TimerTask task = new TimerTask() {
            public void run() {
                String line = null;
                try {
                    line = reader.readLine();
                    if (line == null)
                        timer.cancel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Date date = new Date();
                assert line != null;
                Message message = new Message(
                        (new Timestamp(date.getTime())).toString(), sensorId, Double.parseDouble(line));

                JSONWriter rabbitmqJson = new JSONWriter();
                String jsonMessage = rabbitmqJson.write(message);

                try {
                    channel.basicPublish("", QUEUE_NAME, null, jsonMessage.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(" [x] Sent '" + jsonMessage + "'");
            }
        };
        timer.schedule(task, 0, 10000);
    }
}
