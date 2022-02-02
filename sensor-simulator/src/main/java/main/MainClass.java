package main;

import sensorSimulator.SensorSimulator;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class MainClass {
    public static void main(String[] args) throws IOException, URISyntaxException, NoSuchAlgorithmException,
            KeyManagementException {
        SensorSimulator sensorSimulator = new SensorSimulator(5L);
        sensorSimulator.start();
        SensorSimulator sensorSimulator1 = new SensorSimulator(12L);
        sensorSimulator1.start();
    }
}
