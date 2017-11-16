/*
* Copyright 2015 Hewlett-Packard Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: IOT
* Module:  IOT
* Package: com.hpe.iot.nip.util
* Source:  RestConfigurations.java
* Author:  P M, Shakir
* Organization: HP
 * Revision: 1.0
* Date: Jan 14, 2016
* Contents:
*-----------------------------------------------------------------------------
*/

package com.hpe.iot.dc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author pmshak
 *
 */
@Configuration
public class RestConfigurations {

	@Bean(name = "restTemplate")
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate;
    }

	public HttpComponentsClientHttpRequestFactory getHttpClientFactory(){
		HttpComponentsClientHttpRequestFactory httpClientFactory = new HttpComponentsClientHttpRequestFactory();
		return httpClientFactory;
	}

}
