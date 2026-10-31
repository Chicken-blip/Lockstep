package ca.ACOII.lockstep.engine;

public class DisableSensorAction implements Action {
    String sensorId;
    int duration;
    public DisableSensorAction(String sensorId, int duration) {
        this.sensorId = sensorId;
        this.duration = duration;
    }
    @Override
    public int getDuration() {
        return duration;
    }

    public String getSensorId() {
        return sensorId;
    }

    @Override
    public String getDescription() {
        return "Disabled door motion sensor " + sensorId;
    }
}
