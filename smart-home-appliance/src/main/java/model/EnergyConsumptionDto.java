package model;

public class EnergyConsumptionDto {
    private int day;
    private int hour;
    private Double energy;

    public EnergyConsumptionDto(int day, int hour, Double energy) {
        this.day = day;
        this.hour = hour;
        this.energy = energy;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }
}
