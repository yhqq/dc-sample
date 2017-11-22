package com.hpe.iot.dc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hpe.iot.dc.service.DeviceController;
import com.hpe.iot.dc.util.RequestPrimitiveBuilder;
import com.hpe.iot.m2m.common.RequestPrimitive;
import com.hpe.iot.m2m.common.ResponsePrimitive;

@RestController
public class APIController {

	@Autowired
	DeviceController dc;

	@RequestMapping(value = "/upload_data", method = RequestMethod.POST)
	public ResponseEntity<ResponsePrimitive> postSensorData(@RequestBody String body, @RequestHeader("DeviceID") String deviceId) {

		RequestPrimitive request = new RequestPrimitiveBuilder()
				.targetDeviceId(deviceId)
				.sensorData(body)
				.build();
		
		// TODO: ResponsePrimitiveの内容を精査して実際のレスポンスに返す内容を検討する
		ResponsePrimitive response = dc.sendRequestToIOT(request);

		// TODO: 処理結果に応じて場合わけする
		return ResponseEntity.accepted().body(response);
	}

}