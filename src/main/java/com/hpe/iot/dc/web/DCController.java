/*
* Copyright 2015 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: IOT
* Module:  IOT
* Package: com.hpe.iot.dc.web
* Source:  DCController.java
* Author:  P M, Shakir
* Organization: HP
 * Revision: 1.0
* Date: Mar 24, 2016
* Contents:
*-----------------------------------------------------------------------------
*/

package com.hpe.iot.dc.web;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hpe.iot.dc.service.AbstractDCContext;
import com.hpe.iot.dc.south.model.HPERequest;
import com.hpe.iot.dc.south.model.HPERequestType;
import com.hpe.iot.dc.south.service.HPESubscriber;

/**
 * @author pmshak
 *
 */
@RestController
@RequestMapping("/") // http://nipIP:port/xxxxdcの後の"/"
public class DCController {


	@Autowired
	AbstractDCContext dcContext;

	@Autowired
	HPESubscriber hpeSubscriber;


//  deviceからのデータを受け取った後の処理

	final static Logger logger = Logger.getLogger(DCController.class);

	//Sample Implementation
	//Sample Device Posting sensor Data to IoT Platform


	  @RequestMapping(value = "/post/sensorData", method = RequestMethod.POST)
	  public void postSensorData( HttpServletResponse res, @RequestHeader("X-M2M-RI") String riHeader, @RequestBody String body, @RequestHeader("deviceAddress") String deviceAddress){

	  HPERequest hpeRequest = new HPERequest();

	  JsonObject sensorData = Json.createObjectBuilder().add("Temperature", body).build();

	  hpeRequest.setMacAddress(deviceAddress);
	  hpeRequest.setSensorData(sensorData);
	  hpeRequest.setSensorDataType("default");
	  hpeRequest.setRequestType(HPERequestType.POST_SENSOR_DATA);
	  hpeRequest.setRequestId(riHeader);

	  hpeSubscriber.receiveRequestFromDevice(hpeRequest);

	  res.setStatus(HttpServletResponse.SC_ACCEPTED);
	}


}