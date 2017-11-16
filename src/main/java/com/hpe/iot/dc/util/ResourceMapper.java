package com.hpe.iot.dc.util;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpe.iot.m2m.common.ContentInstance;
import com.hpe.iot.m2m.common.PrimitiveContent;
import com.hpe.iot.m2m.common.RequestPrimitive;
import com.hpe.iot.m2m.common.ResponsePrimitive;

public class ResourceMapper {
	final static Logger logger = Logger.getLogger(ResourceMapper.class);

	public static final ObjectMapper objectMapper = new ObjectMapper();

	public static RequestPrimitive convertToOneM2mResource(RequestPrimitive requestPrimitive)
			throws JsonParseException, JsonMappingException, IOException {
		if (requestPrimitive.getPrimitiveContent() == null) {
			return requestPrimitive;
		}
		String content = "";
		List<Object> contentList = requestPrimitive.getPrimitiveContent().getAny();
		if (!CollectionUtils.isEmpty(contentList)) {
			Object contentObj = contentList.get(0);
			if ((contentObj != null) && ((contentObj instanceof String))) {
				content = contentObj.toString();
			} else {
				return requestPrimitive;
			}
		}
		int resourceType = requestPrimitive.getResourceType().intValue();
		if (resourceType == 4) {

			logger.info("CONTENT: " + content);
			ContentInstance ci = (ContentInstance) objectMapper.readValue(content, ContentInstance.class);
			// ContentInstance ci =
			// (ContentInstance)objectMapper.readValue("abc",
			// ContentInstance.class);
			logger.info("ci: " + ci);
			PrimitiveContent primtContent = new PrimitiveContent();
			primtContent.getAny().add(ci);
			requestPrimitive.setPrimitiveContent(primtContent);
			logger.info("primtcontent: " + primtContent);
		}
		return requestPrimitive;
	}

	public static ResponsePrimitive convertToOneM2mResource(ResponsePrimitive responsePrimitive)
			throws JsonParseException, JsonMappingException, IOException {
		if (responsePrimitive.getPrimitiveContent() == null) {
			return responsePrimitive;
		}
		String content = "";
		List<Object> contentList = responsePrimitive.getPrimitiveContent().getAny();
		if (!CollectionUtils.isEmpty(contentList)) {
			Object contentObj = contentList.get(0);
			if ((contentObj != null) && ((contentObj instanceof String))) {
				content = contentObj.toString();
			} else {
				return responsePrimitive;
			}
		}
		ContentInstance ci = (ContentInstance) objectMapper.readValue(content, ContentInstance.class);
		PrimitiveContent primtContent = new PrimitiveContent();
		primtContent.getAny().add(ci);
		responsePrimitive.setPrimitiveContent(primtContent);

		return responsePrimitive;
	}
}
