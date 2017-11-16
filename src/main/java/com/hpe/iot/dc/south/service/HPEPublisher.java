/*
* Copyright 2015 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: IOT
* Module:  IOT
* Package: com.hpe.iot.dc.south.bound
* Source:  MqttPublisher.java
* Author:  P M, Shakir
* Organization: HP 
 * Revision: 1.0
* Date: Mar 7, 2016
* Contents:
*-----------------------------------------------------------------------------
*/

package com.hpe.iot.dc.south.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.json.JsonValue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hpe.iot.dc.service.DcPublisher;
import com.hpe.iot.dc.south.model.HPERequest;
import com.hpe.iot.dc.south.model.HPEResponse;
import com.hpe.iot.proxy.util.GenericObjectXMLConverter;

/**
 * @author pmshak
 *
 */
@Service
public class HPEPublisher implements DcPublisher<HPERequest, HPEResponse>{

	final static Logger logger = Logger.getLogger(HPEPublisher.class);
	
	@Value("${deviceAddress.mapping.filepath}")
	String filePath;
	
	/* (non-Javadoc)
	 * @see com.hpe.iot.dc.service.DcPublisher#sendRequest(java.lang.Object)
	 */
	@Override
	public HPEResponse sendRequestToDevice(HPERequest request) {
		// Code to invoke Protocol specific clients for getting device details. 
		// Now sample code to simulate the response.
		
		logger.info("request: " + request.toString());
//		logger.info("requestContent: " + request.getSensorData());
		
        try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			
			JsonValue targetVal = request.getSensorData().get("execTarget");
			String target = targetVal.toString().replaceAll("\"", "");
			
			String line;
            while ((line = br.readLine()) != null) {
            	
            	String[] rows = line.split(",");
            	
            	if( rows[0].startsWith("#") || ! rows[0].equals(target) ) {
            		continue;
            	} else if ( rows[0].equals(target) ) {
            		
            		if ( "GET".equals(rows[2]) ){
            			executeGet(rows[1], request.getSensorData().toString());
            		} else if ( "POST".equals(rows[2]) ){
            			executePost(rows[1], request.getSensorData().toString());
            		}
            	}
            	
            }
            br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		HPEResponse response = new HPEResponse();
		response.setRequestId(request.getRequestId());
		response.setResponseStatus("SUCCESS");
		response.setSensorData(null);
		
//		String sensorData = "{\"ACC\": \"10;11;30\", \"BAT\":\"81\",\"TEMP\":\"27\",\"CO2\":\"411\",\"VOC\":\"47\",\"HUMA\":\"69\",\"LUX\": \"103\",\"LPG\":\"410\",\"GAS_UNIT\":\"PPM\",\"TS\":\"2014-11-03T10:58:00.148+05:30\"}";
//		JsonReader jsonReader = Json.createReader(new StringReader((String) sensorData));	
//		JsonObject json = jsonReader.readObject();	
//		jsonReader.close();
//		
//		response.setSensorData(json);
		
		return response;
	}

	/* (non-Javadoc)
	 * @see com.hpe.iot.dc.service.DcPublisher#sendResponse(java.lang.Object)
	 */
	@Override
	public void sendResponseToDevice(HPEResponse response) {
		// TODO Auto-generated method stub
		//This is not used as part of 1.2 GA. No responses are sent back to device through DC
	}
	
	
	
	private static void executeGet(String urlStr, String payload) {
		logger.info("===== HTTP GET Start =====");
		try {
			URL url = new URL(urlStr + "?payload=" + payload);

			HttpURLConnection connection = null;

			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(),
							StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(isr)) {
						
						String line;
						while ((line = reader.readLine()) != null) {
							logger.info(line);
						}
					}
				}
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("===== HTTP GET End =====");
	}

	
	private static void executePost(String urlStr, String payload) {
		logger.info("===== HTTP POST Start =====");
		try {
			URL url = new URL(urlStr);

			HttpURLConnection connection = null;

			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");

				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
				writer.write(payload);
				writer.flush();

				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(),
							StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(isr)) {
						
						String line;
						while ((line = reader.readLine()) != null) {
							logger.info(line);
						}
					}
				}
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("===== HTTP POST End =====");
	}

}
