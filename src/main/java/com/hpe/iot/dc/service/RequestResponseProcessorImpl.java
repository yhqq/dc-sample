package com.hpe.iot.dc.service;

import java.io.IOException;
import java.math.BigInteger;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpe.iot.dc.util.ResourceMapper;
import com.hpe.iot.m2m.common.RequestPrimitive;
import com.hpe.iot.m2m.common.ResponsePrimitive;
import com.hpe.iot.nononem2m.session.SessionRequest;
import com.hpe.iot.nononem2m.session.SessionResponse;
import com.hpe.iot.proxy.util.RequestResponseHandler;

/**
 * @author pmshak
 *
 */
@Service("requestResponseHandler")
public class RequestResponseProcessorImpl extends RequestResponseHandler {

	@Autowired
	DeviceController abstractDC;

	 /*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.hpe.iot.proxy.util.RequestResponseHandler#recieveRequest(com.hpe.iot.m2m.common.RequestPrimitive)
	 */
	@Override
	public ResponsePrimitive recieveRequest(RequestPrimitive requestPrimitive) {

		ResponsePrimitive res = this.abstractDC.sendRequestToDevice(requestPrimitive);
		try {
			ResourceMapper.convertToOneM2mResource(res);
		} catch (IOException e) {
			this.log.warn("Error in parsing Request " + e.getMessage());
		}
		return res;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.hpe.iot.proxy.util.RequestResponseHandler#sendResponse(com.hpe.iot.m2m.common.ResponsePrimitive)
	 */
	@Override
	public void sendResponse(ResponsePrimitive responsePrimitive) {
		try {
			ResourceMapper.convertToOneM2mResource(responsePrimitive);
		} catch (IOException e1) {
			this.log.warn("Error in parsing Request " + e1.getMessage());
		}
		String message = this.xmlConverter.getResponsePrimitiveToXML(responsePrimitive);
		try {
			this.messagePublisher.sendMessage(message, this.responseQueue);
		} catch (JMSException e) {
			this.log.warn(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.hpe.iot.proxy.util.RequestResponseHandler#sendRequest(com.hpe.iot.m2m.common.RequestPrimitive)
	 */
	@Override
	public ResponsePrimitive sendRequest(RequestPrimitive requestPrimitive) {
		this.log.debug("recieved Request Primitive :: " + requestPrimitive.toString());


		ResponsePrimitive response = new ResponsePrimitive();
		try {
			ResourceMapper.convertToOneM2mResource(requestPrimitive);
		} catch (IOException e1) {
			response.setResponseStatusCode(BigInteger.valueOf(4000L));
			response.setRequestIdentifier(requestPrimitive.getRequestIdentifier());
			this.log.warn("Error in parsing Request " + e1.getMessage());
		}
		String message = this.xmlConverter.getRequestPrimitiveToXML(requestPrimitive);
		if (this.log.isDebugEnabled()) {
			this.log.debug("Request Primitive converted to XML :: " + message);
		}
		int responseType = requestPrimitive.getResponseType().getResponseTypeValue().intValue();
		if (responseType == 3) {
			String res = this.httpPublisher.sendMessage(message, this.endpoint);
			response = this.xmlConverter.xmlToResponsePrimitive(res);
		} else if (responseType == 2) {
			try {
				if (this.log.isDebugEnabled()) {
					this.log.debug("Publishing request to ->" + this.requestQueue);
				}
				this.messagePublisher.sendMessage(message, this.requestQueue);
				response.setResponseStatusCode(BigInteger.valueOf(1000L));
				response.setRequestIdentifier(requestPrimitive.getRequestIdentifier());
			} catch (JMSException e) {
				response.setResponseStatusCode(BigInteger.valueOf(4008L));
				response.setRequestIdentifier(requestPrimitive.getRequestIdentifier());
				this.log.warn("Error in posting to Queue " + e.getMessage());
			}
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.hpe.iot.proxy.util.RequestResponseHandler#recieveResponse(com.hpe.iot.m2m.common.ResponsePrimitive)
	 */
	@Override
	public void recieveResponse(ResponsePrimitive responsePrimitive) {
		abstractDC.sendResponseToDevice(responsePrimitive);
	}

	@Override
	public void recieveSessionRequest(SessionRequest arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recieveSessionResponse(SessionResponse arg0) {
		// TODO Auto-generated method stub
		
	}

}
