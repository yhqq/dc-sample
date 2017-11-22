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
