package com.ds.Assignment_1.rpc;

import com.ds.Assignment_1.service.ConsumptionService;
import com.ds.Assignment_1.service.DeviceService;
import com.ds.Assignment_1.service.SensorService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.tools.jsonrpc.JsonRpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SmartAppliance {

    private static final String QUEUE = "json-rpc-queue";

    DeviceService deviceService;
    SensorService sensorService;
    ConsumptionService consumptionService;

    @Autowired
    public SmartAppliance(DeviceService deviceService, SensorService sensorService, ConsumptionService consumptionService)
    {
        this.deviceService = deviceService;
        this.sensorService = sensorService;
        this.consumptionService = consumptionService;
    }

    @Scheduled(fixedRate = 10)
    public void listener() throws Exception{
        String uri = System.getenv("CLOUDAMQP_URL");
        if (uri == null) uri = "amqps://xufooike:In7DY07BTC2O1X0cY0Legd2bZzkC0FfU@hornet.rmq.cloudamqp.com/xufooike";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        Channel channel = factory.newConnection().createChannel();

        channel.queueDeclare(QUEUE, false, false, false, null);

        // Create JSON-RPC server providing remote methods defined in Service
        JsonRpcServer server = new JsonRpcServer(channel,
                QUEUE,
                ServiceD.class,
                new ServiceImp(deviceService, sensorService, consumptionService));

        // Start listening for RPC requests from client
        server.mainloop();
    }
}
