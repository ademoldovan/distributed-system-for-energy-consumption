package controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.tools.jsonrpc.JsonRpcClient;
import com.rabbitmq.tools.jsonrpc.JsonRpcException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.BaselineDto;
import model.EnergyConsumptionDto;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class Controller extends Application implements Initializable {

    private static final String QUEUE = "json-rpc-queue";
    public LineChart<String,Integer> baselineChart;
    public CategoryAxis hours;
    public NumberAxis average;
    public TextField numberOfDays;
    public Text date;
    public Label time;
    public TextField deviceId;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File("D:\\FACULTATE\\AN 4\\SEM I\\SD\\client-side-application\\src\\main\\java\\view\\sample.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setTitle("Client application");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfDays.setText("7");
        deviceId.setText("6");

        baselineChart.getData().clear();
        XYChart.Series series6 = new XYChart.Series();
        for(int i=0; i<24; i++){
            series6.getData().add(new XYChart.Data(String.valueOf(i), 0));
        }
        //Setting the data to Line chart
        baselineChart.getData().add(series6);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = new Date();
        date.setText(formatter.format(date1));

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            time.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    public EnergyConsumptionDto parseToEnergyConsumptionDto(Object obj){
        String message = obj.toString();
        message = message.replace("{","");
        message = message.replace("}","");
        message = message.replace(",","");
        String[] parts = message.split(" ");
        int hour = Integer.parseInt(parts[0].split("=")[1]);
        int day = Integer.parseInt(parts[1].split("=")[1]);
        double energy = Double.parseDouble( parts[2].split("=")[1]);
        return  new EnergyConsumptionDto(day, hour, energy);
    }

    public void viewHistoricalEnergy(MouseEvent mouseEvent) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException, JsonRpcException {
        // Establish connection to RabbitMQ server
        String uri = System.getenv("CLOUDAMQP_URL");
        if (uri == null) uri = "amqps://xufooike:In7DY07BTC2O1X0cY0Legd2bZzkC0FfU@hornet.rmq.cloudamqp.com/xufooike";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // Create JSON-RPC client
        JsonRpcClient client = new JsonRpcClient(channel, "", QUEUE);
        // Call one of the remote methods provided by the JSON-RPC server
        String method = "getHistoricalEnergyConsumption";
        int nrOfDays = Integer.parseInt(numberOfDays.getText());
        int device_id = Integer.parseInt(deviceId.getText());
        Integer[] arguments = {device_id, nrOfDays};
        ArrayList<EnergyConsumptionDto> result = (ArrayList<EnergyConsumptionDto>) client.call(method, arguments);

        ArrayList<ArrayList<EnergyConsumptionDto>> chartData = new ArrayList<>();
        for(int i=0; i<nrOfDays; i++){
            chartData.add(new ArrayList<EnergyConsumptionDto>());
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentDay = calendar.get(Calendar.DATE);

        for(int i=0; i<result.size();i++)
        {
            EnergyConsumptionDto value = parseToEnergyConsumptionDto(result.get(i));
            ArrayList<EnergyConsumptionDto> arrayList = chartData.get( (nrOfDays - (currentDay - value.getDay() + 1)) );
            arrayList.add(value);
            chartData.set((nrOfDays - (currentDay - value.getDay() + 1)), arrayList);
        }
        baselineChart.getYAxis().setLabel("energy");
        baselineChart.getData().clear();
        for(int i=0; i<nrOfDays; i++){
            XYChart.Series series = new XYChart.Series();
            series.setName(String.valueOf((currentDay - nrOfDays + i + 1)));
            for(EnergyConsumptionDto e: chartData.get(i)){
                series.getData().add(new XYChart.Data(String.valueOf(e.getHour()), e.getEnergy()));
            }
            baselineChart.getData().add(series);
        }
        client.close();
        channel.close();
        connection.close();
    }

    public BaselineDto parseToBaselineDto(Object obj){
        String message = obj.toString();
        message = message.replace("{","");
        message = message.replace("}","");
        message = message.replace(",","");
        String[] parts = message.split(" ");
        double average = Double.parseDouble( parts[0].split("=")[1]);
        int hour = Integer.parseInt(parts[1].split("=")[1]);
        return  new BaselineDto(hour,average);
    }

    public void viewBaseline(MouseEvent mouseEvent) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, JsonRpcException {
        // Establish connection to RabbitMQ server
        String uri = System.getenv("CLOUDAMQP_URL");
        if (uri == null) uri = "amqps://xufooike:In7DY07BTC2O1X0cY0Legd2bZzkC0FfU@hornet.rmq.cloudamqp.com/xufooike";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // Create JSON-RPC client
        JsonRpcClient client = new JsonRpcClient(channel, "", QUEUE);
        // Call one of the remote methods provided by the JSON-RPC server
        String method = "getBaseline";
        int device_id = Integer.parseInt(deviceId.getText());
        Integer[] arguments = {device_id};
        ArrayList<BaselineDto> result = (ArrayList<BaselineDto>) client.call(method, arguments);
        //Prepare XYChart.Series objects by setting data
        baselineChart.getYAxis().setLabel("average");
        baselineChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        series.setName("baseline");
        for(int i=0; i<24; i++){
            BaselineDto baselineDto = parseToBaselineDto(result.get(i));
            series.getData().add(new XYChart.Data(String.valueOf(baselineDto.getHour()), baselineDto.getAverage()));
        }
        //Setting the data to Line chart
        baselineChart.getData().add(series);
        // Note:
        // Call client.getServiceDescription() to get a ServiceDescription that
        // describes all the remote methods that the JSON-RPC server provides.
        client.close();
        channel.close();
        connection.close();

    }
}
