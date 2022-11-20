package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.service.ExchangeRateService;

@RestController
public class ExchangeRate {
	
	@Autowired
	ExchangeRateService exchangeRateService;

	//API to get exchange rate
	@RequestMapping(value="/exchange/rate", method = RequestMethod.GET)
	public String exchangeRate() {
		return exchangeRateService.getExchangeRate(); // returning exchange rate
	}
	
}
