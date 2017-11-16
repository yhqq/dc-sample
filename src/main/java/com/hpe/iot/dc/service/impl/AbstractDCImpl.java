package com.hpe.iot.dc.service.impl;

import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpe.iot.dc.service.AbstractDC;
import com.hpe.iot.dc.service.DcPublisher;
import com.hpe.iot.dc.service.ObjectConverter;
import com.hpe.iot.m2m.common.RequestPrimitive;
import com.hpe.iot.m2m.common.ResponsePrimitive;
import com.hpe.iot.proxy.util.RequestResponseHandler;

/**
 * @author pmshak
 *
 */
@Service("abstractDC")
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class AbstractDCImpl implements AbstractDC{

	final static Logger logger = Logger.getLogger(AbstractDCImpl.class);

	@Autowired
	RequestResponseHandler requestResponseHandler;

	@Autowired
	ObjectConverter objectConverter;

	@Autowired
	DcPublisher dcPublisher;

	/* (non-Javadoc)
	 * @see com.hpe.iot.mqtt.dc.service.MqttAdapter#processRequest(com.hpe.iot.m2m.common.RequestPrimitive)
	 */
	@Override
	public ResponsePrimitive sendRequestToDevice(RequestPrimitive requestPrimitive) {

		//TODO Job of DC developer if complex DC

		if (logger.isInfoEnabled()) {
			logger.info(String.format("RequestId [%s] ::  Recieved request --> sendRequestToDevice", requestPrimitive.getRequestIdentifier()));
		}

		Object request =  objectConverter.RequestprimitiveToRequest(requestPrimitive);

		if (logger.isInfoEnabled()) {
			logger.info(String.format("RequestId [%s] ::  Posting request to device --> sendRequestToDevice", requestPrimitive.getRequestIdentifier()));
		}

		Object response = dcPublisher.sendRequestToDevice(request);

		if (logger.isInfoEnabled()) {
			logger.info(String.format("RequestId [%s] ::  Recieved response from Device --> sendRequestToDevice", requestPrimitive.getRequestIdentifier()));
		}

		ResponsePrimitive responsePrimitive =  objectConverter.ResponseToResponseprimitive(response);

		if (logger.isInfoEnabled()) {
			logger.info(String.format("RequestId [%s] ::  Sending response to IoT platform --> sendRequestToDevice", requestPrimitive.getRequestIdentifier()));
		}

		//For Asynch
		if(requestPrimitive.getResponseType().getResponseTypeValue() == BigInteger.valueOf(2l))
			this.sendResponseToIOT(responsePrimitive);

		//For Synch
		return responsePrimitive;
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
		// TODO Auto-generated method stub
		//This is not used as part of 1.2 GA. No responses are sent back to device through DC
		Object response = objectConverter.ResponseprimitiveResponse(responsePrimitive);
		dcPublisher.sendResponseToDevice(response);
	}

}
