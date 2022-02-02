package com.ds.Assignment_1.rpc;

import com.ds.Assignment_1.dto.BaselineDto;
import com.ds.Assignment_1.dto.EnergyConsumptionDto;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public interface ServiceD {
    ArrayList<EnergyConsumptionDto> getHistoricalEnergyConsumption(int deviceId, int numberOfDays);
    ArrayList<BaselineDto> getBaseline(int deviceId);
}
