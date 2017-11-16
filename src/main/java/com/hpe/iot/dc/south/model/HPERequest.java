/*
* Copyright 2015 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: IOT
* Module:  IOT
* Package: com.hpe.iot.dc.south.model
* Source:  Mqttrequest.java
* Author:  P M, Shakir
* Organization: HP 
 * Revision: 1.0
* Date: Mar 7, 2016
* Contents:
*-----------------------------------------------------------------------------
*/

package com.hpe.iot.dc.south.model;

import javax.json.JsonObject;

/**
 * @author pmshak
 *
 */
public class HPERequest {
	
	String deviceAddress;
	String sensorDataType;//Mapped to container in IOT
	JsonObject sensorData;
	String requestId;
	String requestBody;
	HPERequestType requestType;

	public String getMacAddress() {
		return deviceAddress;
	}

	public void setMacAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	public JsonObject getSensorData() {
		return sensorData;
	}

	public void setSensorData(JsonObject sensorData) {
		this.sensorData = sensorData;
	}

	public HPERequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(HPERequestType requestType) {
		this.requestType = requestType;
	}

	public String getSensorDataType() {
		return sensorDataType;
	}

	public void setSensorDataType(String sensorDataType) {
		this.sensorDataType = sensorDataType;
	}
	

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return "RaspiRequest [deviceAddress=" + deviceAddress + ", sensorData="
				+ sensorData + ", requestType=" + requestType + "]";
	}

}
