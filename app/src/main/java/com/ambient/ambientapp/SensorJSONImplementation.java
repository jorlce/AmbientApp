package com.ambient.ambientapp;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


public class SensorJSONImplementation implements SensorJSON {
	
	private ObjectMapper mapper;
	
	public SensorJSONImplementation() {
		System.out.println("Create object SensorJSON");
	}
	
	public SensorData findBySensorLabel(String unSensorJson) {
		ObjectMapper mapper = new ObjectMapper();
		SensorData unSensor = null;
		try {
			unSensor = mapper.readValue(unSensorJson, SensorData.class);
			System.out.println("1. Convert JSON to SensorData:");
	    	System.out.println(unSensor.getSensorlabel());
	    	System.out.println(" - ");
	    	System.out.println(unSensor.getLatitud());
	    	System.out.println(" - ");
	    	System.out.println(unSensor.getLongitud());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return unSensor;
	}
	
	//Creates a new sensor in the database
	public String addSensorData(SensorData unsensor) {
		ObjectMapper mapper = new ObjectMapper();
		String unSensorJson = "";
		try {
			unSensorJson = mapper.writeValueAsString(unsensor);
			System.out.println("1. Convert SensorData to JSON:");
	    	System.out.println(unSensorJson);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return unSensorJson;
	}
	
	//Returns the measure data of the sensor from Json sent by the service
/*	public Medidor findSensorMeasure(String measureJson) {
		ObjectMapper mapper = new ObjectMapper();
		Medidor unMeasure = null;
		try {
			//unMeasure = measureRepository.findTopByOrderByTimelecturaDesc(idUnse);
			unMeasure = mapper.readValue(measureJson, Medidor.class);
			//jsonSensor = mapper.writerWithView(ViewsJson.Normal.class).writeValueAsString(unMeasure);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return unMeasure;
	}*/
	
	public List<SensorData> listaSensores(String arraySensorJson) {
		Log.d("Jdao","Dentro de Jdao");
		Log.d("Jdao",arraySensorJson);
		ObjectMapper mapper = new ObjectMapper();
		List<SensorData> listaSensores = null;

		try {
			TypeReference<List<SensorData>> mapType = new TypeReference<List<SensorData>>() {};

			Log.d("Jdao","Antes del mapping");
			listaSensores = mapper.readValue(arraySensorJson, mapType);
			Log.d("Jdao","Despues del mapping");
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			Log.getStackTraceString(e);
		} catch (IOException e) {
			Log.getStackTraceString(e);
		}
		if (listaSensores.size()>0) {
			Log.d("Jdao", "Lista tiene objetos");
		}
		return listaSensores;
	}
	
	//Returns the measure data of the sensor from Json sent by the service
	public Medidor findSensorMeasure(String JsonMeasure ){
		
		ObjectMapper mapper = new ObjectMapper();
		Medidor unMedidor = new Medidor();
		SensorData unSensor = new SensorData();
		
		try {
			JsonNode root = mapper.readTree(JsonMeasure);
			
			    // Get measure values
				unMedidor.setTimelectura(root.path("timelectura").longValue());
				unMedidor.setTemperatura(root.path("temperatura").floatValue()); 
				unMedidor.setHumedad(root.path("humedad").floatValue()); 
				unMedidor.setNivelCO(root.path("nivelCO").floatValue()); 
				unMedidor.setNivelCO2(root.path("nivelCO2").floatValue()); 
				unMedidor.setNivelMetano(root.path("metano").floatValue()); 
				
				
				//System.out.println("id : ");

				// Get Sensor values
				JsonNode sensorNode = root.path("sensorID");
				if (sensorNode.isMissingNode()) {
					// if "name" node is missing
				} else {
					unMedidor.setSensorlabel(sensorNode.path("sensorlabel").textValue());
					unSensor.setId(unMedidor.getSensorlabel());
					unSensor.setLatitud(sensorNode.path("latitud").floatValue());
					unSensor.setLongitud(sensorNode.path("longitud").floatValue());
				}
			
		}catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return unMedidor;
	}
}
