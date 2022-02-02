package com.ds.Assignment_1.rpc;

import com.ds.Assignment_1.dto.BaselineDto;
import com.ds.Assignment_1.dto.EnergyConsumptionDto;
import com.ds.Assignment_1.dto.ReadConsumptionsDto;
import com.ds.Assignment_1.dto.ReadDeviceDto;
import com.ds.Assignment_1.service.ConsumptionService;
import com.ds.Assignment_1.service.DeviceService;
import com.ds.Assignment_1.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ServiceImp implements ServiceD {

    DeviceService deviceService;
    SensorService sensorService;
    ConsumptionService consumptionService;

    @Autowired
    public ServiceImp(DeviceService deviceService, SensorService sensorService,ConsumptionService consumptionService) {
        this.deviceService = deviceService;
        this.sensorService = sensorService;
        this.consumptionService = consumptionService;
    }

    @Override
    public ArrayList<EnergyConsumptionDto> getHistoricalEnergyConsumption(int deviceId, int numberOfDays) {
        ReadDeviceDto device = deviceService.getDeviceById((long) deviceId);
        List<ReadConsumptionsDto> readConsumptionsDtos = consumptionService.getConsumptionsBySensor(device.getSensor_id());
        ArrayList<EnergyConsumptionDto> energyConsumptionDtos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int day = calendar.get(Calendar.DATE);
        for(ReadConsumptionsDto i: readConsumptionsDtos){
            Date date = i.getTimestamp();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);
            if(calendar1.get(Calendar.DAY_OF_MONTH) >= (day - numberOfDays + 1) && calendar1.get(Calendar.DAY_OF_MONTH) <= day){
                energyConsumptionDtos.add(new EnergyConsumptionDto(calendar1.get(Calendar.DAY_OF_MONTH), i.getTimestamp().getHours(), i.getEnergy_consumption()));
            }
        }
        return energyConsumptionDtos;
    }

    @Override
    public ArrayList<BaselineDto> getBaseline(int deviceId) {
        ReadDeviceDto device = deviceService.getDeviceById((long) deviceId);
        List<ReadConsumptionsDto> readConsumptionsDtos = consumptionService.getConsumptionsBySensor(device.getSensor_id());
        ArrayList<EnergyConsumptionDto> energyConsumptionDtos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int day = calendar.get(Calendar.DATE);
        ArrayList<BaselineDto> baseline = new ArrayList<>();
        for(int i=0; i<24; i++){
            baseline.add(new BaselineDto(i,0));
        }
        BaselineDto aux;
        for(ReadConsumptionsDto c: readConsumptionsDtos){
            Date date = c.getTimestamp();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);
            if(calendar1.get(Calendar.DAY_OF_MONTH) <= day && calendar1.get(Calendar.DAY_OF_MONTH) >= day-7){
                BaselineDto baselineDto = new BaselineDto(c.getTimestamp().getHours(), c.getEnergy_consumption());
                aux = baseline.get(c.getTimestamp().getHours());
                baseline.set(c.getTimestamp().getHours(), new BaselineDto(aux.getHour(), (aux.getAverage()+baselineDto.getAverage())/2 ));
            }
        }
        return baseline;
    }
}
