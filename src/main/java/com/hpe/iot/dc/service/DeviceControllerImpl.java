package com.hpe.iot.dc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpe.iot.dc.service.DeviceController;
import com.hpe.iot.m2m.common.RequestPrimitive;
import com.hpe.iot.m2m.common.ResponsePrimitive;
import com.hpe.iot.proxy.util.RequestResponseHandler;

/**
 * @author pmshak
 *
 */
@Service
public class DeviceControllerImpl implements DeviceController{

	@Autowired
	private RequestResponseHandler requestResponseHandler;

	/* (non-Javadoc)
	 * @see com.hpe.iot.mqtt.dc.service.MqttAdapter#processRequest(com.hpe.iot.m2m.common.RequestPrimitive)
	 */
	@Override
	public ResponsePrimitive sendRequestToDevice(RequestPrimitive requestPrimitive) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hpe.iot.mqtt.dc.service.MqttAdapter#sendResponse(com.hpe.iot.m2m.common.ResponsePrimitive)
	 */
	@Override
	public void sendResponseToIOT(ResponsePrimitive responsePrimitive) {
		requestResponseHandler.sendResponse(responsePrimitive);
	}

	/* (non-Javadoc)
	 * @see com.hpe.iot.mqtt.dc.service.MqttAdapter#sendNotification(com.hpe.iot.m2m.common.RequestPrimitive)
	 */
	@Override
	public ResponsePrimitive sendRequestToIOT(RequestPrimitive requestPrimitive) {
		return requestResponseHandler.sendRequest(requestPrimitive);
	}

	/* (non-Javadoc)
	 * @see com.hpe.iot.mqtt.dc.service.MqttAdapter#recieveNotificationResponse(com.hpe.iot.m2m.common.ResponsePrimitive)
	 */
	@Override
	public void sendResponseToDevice(ResponsePrimitive responsePrimitive) {
	}

}
