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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hpe.iot.dc.service.DeviceController;
import com.hpe.iot.dc.util.RequestPrimitiveBuilder;
import com.hpe.iot.m2m.common.RequestPrimitive;
import com.hpe.iot.m2m.common.ResponsePrimitive;

/**
 * @author pmshak
 *
 */
@RestController
@RequestMapping("/") // http://nipIP:port/xxxxdcの後の"/"
public class APIController {

	private final static Logger logger = LoggerFactory.getLogger(APIController.class);

	@Autowired
	DeviceController dc;
	
	@Autowired
	RequestPrimitiveBuilder requestPrimitiveBuilder;

	@RequestMapping(value = "/sensor_data", method = RequestMethod.POST)
	public ResponseEntity<ResponsePrimitive> postSensorData(@RequestBody String body) {

		//JsonObject sensorData = Json.createObjectBuilder().add("Temperature", body).build();

		RequestPrimitive request = requestPrimitiveBuilder.to("deviceAddress:123/default").content(body).build(); 
		ResponsePrimitive response = dc.sendRequestToIOT(request);

		return ResponseEntity.accepted().body(response);
	}

}