package com.ambient.ambientapp;

import java.util.List;

public interface SensorJSON {

	public SensorData findBySensorLabel(String unSensorJson);
	public String addSensorData(SensorData unSensor);
	public Medidor findSensorMeasure(String measureJson);
	public List<SensorData> listaSensores(String arraySensorJson);
	
}
