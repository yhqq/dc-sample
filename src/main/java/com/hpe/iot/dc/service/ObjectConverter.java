/*
* Copyright 2015 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: IOT
* Module:  IOT
* Package: com.hpe.iot.dc.service
* Source:  ObjectConverter.java
* Author:  P M, Shakir
* Organization: HP 
 * Revision: 1.0
* Date: Mar 7, 2016
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
public interface ObjectConverter<Req, Res> {
	
	/**
	 * @param requestobject
	 * @return
	 */
	public RequestPrimitive RequestToRequestprimitive(Req requestobject) ;
	
	/**
	 * @param requestPrimitive
	 * @return
	 */
	public Req RequestprimitiveToRequest(RequestPrimitive requestPrimitive) ;

	/**
	 * @param response
	 * @return
	 */
	public ResponsePrimitive ResponseToResponseprimitive(Res response) ;
	
	/**
	 * @param responsePrimitive
	 * @return
	 */
	public Res ResponseprimitiveResponse(ResponsePrimitive responsePrimitive) ;

}
