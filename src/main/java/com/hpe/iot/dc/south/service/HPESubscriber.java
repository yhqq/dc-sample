/*
* Copyright 2015 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: IOT
* Module:  IOT
* Package: com.hpe.iot.dc.south.bound
* Source:  MqttSubscriber.java
* Author:  P M, Shakir
* Organization: HP 
 * Revision: 1.0
* Date: Mar 7, 2016
* Contents:
*-----------------------------------------------------------------------------
*/

package com.hpe.iot.dc.south.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpe.iot.dc.service.AbstractDC;
import com.hpe.iot.dc.service.DcSubscriber;
import com.hpe.iot.dc.service.ObjectConverter;
import com.hpe.iot.dc.south.model.HPERequest;
import com.hpe.iot.dc.south.model.HPEResponse;
import com.hpe.iot.m2m.common.RequestPrimitive;
import com.hpe.iot.m2m.common.ResponsePrimitive;

/**
 * @author pmshak
 *
 */
@Service
public class HPESubscriber implements DcSubscriber<HPERequest, HPEResponse>{
	
	@Autowired
	AbstractDC abstractDC;
	
	@Autowired
	ObjectConverter objectConverter;

	@Override
	public HPEResponse receiveRequestFromDevice(HPERequest request) {
		
		RequestPrimitive requestPrimitive = objectConverter.RequestToRequestprimitive(request);
		
		ResponsePrimitive responsePrimitive = abstractDC.sendRequestToIOT(requestPrimitive);
		
		return (HPEResponse) objectConverter.ResponseprimitiveResponse(responsePrimitive);
	}


	@Override
	public void receiveResponseFromDevice(HPEResponse response) {
		// TODO Auto-generated method stub
		
	}

}
