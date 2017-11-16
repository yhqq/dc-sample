/*
* Copyright 2015 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: IOT
* Module:  IOT
* Package: com.hpe.iot.dc.south.model
* Source:  MqttResponse.java
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
public class HPEResponse {
	
	String responseStatus;
	String requestId;
	JsonObject sensorData;
	
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public JsonObject getSensorData() {
		return sensorData;
	}
	public void setSensorData(JsonObject sensorData) {
		this.sensorData = sensorData;
	}
	@Override
	public String toString() {
		return "RaspiResponse [responseStatus=" + responseStatus
				+ ", requestId=" + requestId + ", sensorData=" + sensorData
				+ "]";
	}
	
	

}
