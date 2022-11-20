package com.test.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.test.service.ExchangeRateService;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{

	//Autowiring variables from application.properties
	@Autowired
	Environment env;

	//Defining the method declared in ExchangeRateService interface to get exchange rate
	@Override
	public String getExchangeRate() {
		//checking the mode
		if(env.getProperty("mode")!=null) {
			switch (env.getProperty("mode").toLowerCase()) {

			case "development":
				return "1.1";

			case "production":
				return this.getDynamicRate(); //calling private method to get exchange rate
			default:
				return "Wrong mode defined";
			}
		}else {
			return "Environment mode cannot be null";
		}
	}

	//Method to call the external API to fetch the exchange rate in production mode
	private String getDynamicRate() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://www.floatrates.com/daily/EUR.json");

		ResponseEntity<Map> rates = restTemplate.exchange(builder.toUriString().replace("%20", " "), HttpMethod.GET, new HttpEntity(headers),Map.class);
		if(rates.getBody()!=null && rates.getBody().get("usd")!=null) {
			Map m=(Map) rates.getBody().get("usd");
			return m.get("rate").toString();
		}
		return "USD rate not found";
	}

}
