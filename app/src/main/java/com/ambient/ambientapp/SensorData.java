package com.ambient.ambientapp;

import com.fasterxml.jackson.annotation.JsonProperty;

//Bean for table sensor_id
public class SensorData {

	@JsonProperty("sensorlabel")
	protected String sensorlabel;

	@JsonProperty("latitud")
	protected float latitud;

	@JsonProperty("longitud")
	protected float longitud;

	@JsonProperty("frecuencia")
	protected int frecuencia;

	public String getSensorlabel() {
		return sensorlabel;
	}

	public void setId(String sensorlabel) {
		this.sensorlabel = sensorlabel;
	}

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}



	
	



}
