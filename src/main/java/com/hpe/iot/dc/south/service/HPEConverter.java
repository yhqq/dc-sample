/*
* Copyright 2015 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: IOT
* Module:  IOT
* Package: com.hpe.iot.dc.service
* Source:  ConverterImpl.java
* Author:  P M, Shakir
* Organization: HP
 * Revision: 1.0
* Date: Mar 7, 2016
* Contents:
*-----------------------------------------------------------------------------
*/

package com.hpe.iot.dc.south.service;

import java.math.BigInteger;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hpe.iot.dc.service.ObjectConverter;
import com.hpe.iot.dc.south.model.HPERequest;
import com.hpe.iot.dc.south.model.HPERequestType;
import com.hpe.iot.dc.south.model.HPEResponse;
import com.hpe.iot.m2m.common.ContentInstance;
import com.hpe.iot.m2m.common.MgmtCmd;
import com.hpe.iot.m2m.common.PrimitiveContent;
import com.hpe.iot.m2m.common.RequestPrimitive;
import com.hpe.iot.m2m.common.ResponsePrimitive;
import com.hpe.iot.m2m.common.ResponseTypeInfo;

/**
 * @author pmshak
 *
 */
@Service
public class HPEConverter implements ObjectConverter<HPERequest, HPEResponse> {

//	private static String DEVICE_UNIQUE_IDENTIFIER="MACADDRESS";
	private static String DEVICE_UNIQUE_IDENTIFIER="deviceAddress";

	final static Logger logger = Logger.getLogger(HPEConverter.class);

	/* (non-Javadoc)
	 * @see com.hpe.iot.dc.service.ObjectConverter#from(java.lang.Object)
	 */
	@Override
	public RequestPrimitive RequestToRequestprimitive(HPERequest requestobject) {

		RequestPrimitive requestPrimitive = new RequestPrimitive();
		ResponseTypeInfo resp=new ResponseTypeInfo();

		//Create(1), Retrieve(2), Update(3), Delete(4), Notify (5);
		//Mandatory
		if(requestobject.getRequestType().equals(HPERequestType.POST_SENSOR_DATA))
			requestPrimitive.setOperation(BigInteger.valueOf(1L));

		//oneM2M resource Type. ContentInstance(4), mgmt command(12) etc.
		//Mandatory
		requestPrimitive.setResourceType(BigInteger.valueOf(4L));

		//Targeted resource. Can be either key:value or Relative path.
		//Egs: MACADDRESS:11:22:33:44/co2 or iniqueuIdentifier of the container or complete path like /HPE_IOT/{aename}/containerName
		//Mandatory
		requestPrimitive.setTo(DEVICE_UNIQUE_IDENTIFIER+":"+
		requestobject.getMacAddress()+"/"+requestobject.getSensorDataType());

		//Unique ID for each request
		//Mandatory
		requestPrimitive.setRequestIdentifier(UUID.randomUUID().toString());

		//Asynch(2), Synch(3)
		//Posting sensor data as Asycronously. This is the likely w	ay to post Sensor data
		//Mandatory
		//requestPrimitive.setResponseType(BigInteger.valueOf(2L));
		resp.setResponseTypeValue(BigInteger.valueOf(2L));

		//If ResponseType==2, Notification URL for status update. Keep null if NA
		//This is most likely NULL when sensor post data. Because SENSORS does not expect a response back
		//Optional
		resp.getNotificationURI().add("http://example.com/notificationEndpoint");
		requestPrimitive.setResponseType(resp);

		//Device/Gateway/resource/application Id.
		///Egs: MACADDRESS:11:22:33:44 or iniqueuIdentifier of the sensor or complete path like /HPE_IOT/{aename}
		//Mandotory
//		requestPrimitive.setFrom("1000000");
		requestPrimitive.setFrom(DEVICE_UNIQUE_IDENTIFIER+":"+ requestobject.getMacAddress());

		//Device/Gateway/resource/application name
		//Egs : Container Name for create container, or ae name for create ae. When posting data, it has less significance(Mention like "Sensor Data").
		//Optional. Name of the resource
		//requestPrimitive.setName(requestobject.getMacAddress());

		//Set Sensor Payload in the content
		if(requestobject.getSensorData() != null){
			PrimitiveContent content = new PrimitiveContent();
			ContentInstance ci = new ContentInstance();
			ci.setContentInfo("text/plain:0");
			ci.setContentSize(BigInteger.valueOf(50L));
			ci.setContent(requestobject.getSensorData().toString());
			content.getAny().add(ci);
			requestPrimitive.setPrimitiveContent(content);

		}


		return requestPrimitive;
	}

	/* (non-Javadoc)
	 * @see com.hpe.iot.dc.service.ObjectConverter#from(com.hpe.iot.m2m.common.RequestPrimitive)
	 */
	@Override
	public HPERequest RequestprimitiveToRequest(RequestPrimitive requestPrimitive) {

		HPERequest request = new HPERequest();

		//pointOfAccess/{containerName}
		request.setMacAddress(requestPrimitive.getTo());

		request.setRequestId(requestPrimitive.getRequestIdentifier());

		if(requestPrimitive.getOperation()==BigInteger.valueOf(2L))//GET Request
			request.setRequestType(HPERequestType.GET_SENSOR_DATA);
		if(requestPrimitive.getOperation()==BigInteger.valueOf(5L)){//NOTF Request
			request.setRequestType(HPERequestType.SENSOR_NOTF);
			//request.setRequestBody(requestPrimitive.getContent().getAny());
		}

		if(requestPrimitive.getOperation().equals(BigInteger.valueOf(3L))){//Mgmt Request
			request.setRequestType(HPERequestType.POST_SENSOR_DATA);

			MgmtCmd mgmtCmd = (MgmtCmd) requestPrimitive.getPrimitiveContent().getAny().get(0);
			//logger.info("mgmtCmd.name= " + mgmtCmd.getName());

			JsonObject json = Json.createObjectBuilder()
					.add("parentID", mgmtCmd.getParentID())
					.add("resourceID", mgmtCmd.getResourceID())
					.add("resourceType", mgmtCmd.getResourceType())
//					.add("name", mgmtCmd.getName())
					.add("cmdType", mgmtCmd.getCmdType())
					.add("execMode", mgmtCmd.getExecMode())
					.add("execNumber", mgmtCmd.getExecNumber())
					.add("execTarget", mgmtCmd.getExecTarget())
					.add("execDelay", mgmtCmd.getExecDelay().toString())
//					.add("execFrequency", mgmtCmd.getExecFrequency().toString())
//					.add("expirationTime", mgmtCmd.getExpirationTime())
//					.add("description", mgmtCmd.getDescription())
					.build();

//			logger.info("json= " + json.toString());

			request.setSensorData(json);
		}

		return request;
	}

	/* (non-Javadoc)
	 * @see com.hpe.iot.dc.service.ObjectConverter#to(java.lang.Object)
	 */
	@Override
	public ResponsePrimitive ResponseToResponseprimitive(HPEResponse response) {

		ResponsePrimitive responsePrimitive = new ResponsePrimitive();

		//Response status code as per oneM2M.
		//Refer 6.6.3 of TS-0004_Service_Layer_Core_Protocol_Specification-V_1_0_1
		if(response.getResponseStatus().equalsIgnoreCase("SUCCESS"))
			responsePrimitive.setResponseStatusCode(BigInteger.valueOf(2000));
		//else TODO

		//Request unique Identifier
		responsePrimitive.setRequestIdentifier(response.getRequestId());

		if(response.getSensorData() != null){
			PrimitiveContent content = new PrimitiveContent();
			ContentInstance ci = new ContentInstance();
			ci.setContentInfo("text/plain:0");
			ci.setContentSize(BigInteger.valueOf(50L));
			ci.setContent(response.getSensorData().toString());
			content.getAny().add(ci);
			responsePrimitive.setPrimitiveContent(content);
		}

		//Device/Gateway/resource/application name
		//{devicename}/{containerName}
		//responsePrimitive.setTo(value);

		//{devicename}
		//Optional
		//responsePrimitive.setFrom(value);

		return responsePrimitive;
	}

	/* (non-Javadoc)
	 * @see com.hpe.iot.dc.service.ObjectConverter#to(com.hpe.iot.m2m.common.ResponsePrimitive)
	 */
	@Override
	public HPEResponse ResponseprimitiveResponse(ResponsePrimitive responsePrimitive) {
		// TODO Auto-generated method stub
		return null;
	}


}
