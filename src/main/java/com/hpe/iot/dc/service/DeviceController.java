/*
* Copyright 2015 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: IOT
* Module:  IOT
* Package: com.hpe.iot.mqtt.dc.service
* Source:  MqttAdapter.java
* Author:  P M, Shakir
* Organization: HP 
 * Revision: 1.0
* Date: Jan 30, 2016
* Contents:
*-----------------------------------------------------------------------------
*/

package com.hpe.iot.dc.service;

import com.hpe.iot.m2m.common.RequestPrimitive;
import com.hpe.iot.m2m.common.ResponsePrimitive;

/**
 * @author pmshak
 *
 */
public interface DeviceController {
	
	/**
	 * @param requestPrimitive
	 * @return
	 * 
	 * Request Primitive received from IoT platform. 
	 * Convert the requestPrimitive to protocol specific requet and send,
	 * 
	 */
	public ResponsePrimitive sendRequestToDevice(RequestPrimitive requestPrimitive);

	/**
	 * @param ResponsePrimitive
	 * Convert the response from Device/Gateway to responsePrimitive and send to IoT platform 
	 * 
	 */
	public void sendResponseToIOT(ResponsePrimitive responsePrimitive);

	/**
	 * @param requestPrimitive
	 * @return
	 * 
	 * Any request from coming from Device/gateway. 
	 * Convert to requestPrimitive and sending to IoT platform for processing
	 * 
	 */
	public ResponsePrimitive sendRequestToIOT(RequestPrimitive requestPrimitive);

	/**
	 * @param ResponsePrimitive
	 * This is the response from IoT platform for sendNotification
	 * 
	 */
	public void sendResponseToDevice(ResponsePrimitive responsePrimitive);

}
