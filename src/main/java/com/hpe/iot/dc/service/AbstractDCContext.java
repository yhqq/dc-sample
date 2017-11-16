package com.hpe.iot.dc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AbstractDCContext {

	@Value("${dc_name}")
	private String dc_name="ActilityPoCDC";

	@Value("${dc_version}")
	private String dc_version="1.0";


	public String getDc_name() {
		return dc_name;
	}

	public void setDc_name(String dc_name) {
		this.dc_name = dc_name;
	}

	public String getDc_version() {
		return dc_version;
	}

	public void setDc_version(String dc_version) {
		this.dc_version = dc_version;
	}

}
