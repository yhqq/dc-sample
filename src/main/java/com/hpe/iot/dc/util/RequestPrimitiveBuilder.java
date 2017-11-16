package com.hpe.iot.dc.util;

import java.math.BigInteger;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hpe.iot.m2m.common.ContentInstance;
import com.hpe.iot.m2m.common.PrimitiveContent;
import com.hpe.iot.m2m.common.RequestPrimitive;
import com.hpe.iot.m2m.common.ResponseTypeInfo;

@Service
public class RequestPrimitiveBuilder {

	private String to = "";
	private String from = "";
	private Object content = "";
	
	public RequestPrimitiveBuilder to(String to) {
		this.to = to;
		return this;
	}
	
	public RequestPrimitiveBuilder from(String from) {
		this.from = from;
		return this;
	}
	
	public RequestPrimitiveBuilder content(Object content) {
		this.content = content;
		return this;
	}
	
	public RequestPrimitive build() {
		RequestPrimitive requestPrimitive = new RequestPrimitive();

		// Create(1), Retrieve(2), Update(3), Delete(4), Notify (5);
		// Mandatory
		requestPrimitive.setOperation(BigInteger.valueOf(1L));

		// Targeted resource. Can be either key:value or Relative path.
		// Egs: MACADDRESS:11:22:33:44/co2 or iniqueuIdentifier of the container
		// or complete path like /HPE_IOT/{aename}/containerName
		// Mandatory
		// TODO: 変更する
		requestPrimitive.setTo(this.to);
		
		// Device/Gateway/resource/application Id.
		/// Egs: MACADDRESS:11:22:33:44 or iniqueuIdentifier of the sensor or
		// complete path like /HPE_IOT/{aename}
		// Mandotory
		// requestPrimitive.setFrom("1000000");
		// TODO: みなおし
		requestPrimitive.setFrom(this.from);

		// Unique ID for each request
		// Mandatory
		requestPrimitive.setRequestIdentifier(UUID.randomUUID().toString());

		
		// oneM2M resource Type. ContentInstance(4), mgmt command(12) etc.
		// Mandatory
		requestPrimitive.setResourceType(BigInteger.valueOf(4L));

		// Set Sensor Payload in the content
		// TODO: みなおし
		PrimitiveContent content = new PrimitiveContent();
		ContentInstance ci = new ContentInstance();
		ci.setResourceType(requestPrimitive.getResourceType());
		ci.setContentInfo("text/plain:0");
		ci.setContent(this.content);
		content.getAny().add(ci);
		requestPrimitive.setPrimitiveContent(content);
		
		// Asynch(2), Synch(3)
		// Posting sensor data as Asycronously. This is the likely w ay to post
		// Sensor data
		// Mandatory
		// requestPrimitive.setResponseType(BigInteger.valueOf(2L));
		// TODO: みなおし
		ResponseTypeInfo resp = new ResponseTypeInfo();
		resp.setResponseTypeValue(BigInteger.valueOf(2L));

		// If ResponseType==2, Notification URL for status update. Keep null if
		// NA
		// This is most likely NULL when sensor post data. Because SENSORS does
		// not expect a response back
		// Optional
		resp.getNotificationURI().add("http://example.com/notificationEndpoint");
		requestPrimitive.setResponseType(resp);

		return requestPrimitive;
	}

}
