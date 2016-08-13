package com.packt.webstore.service;

import java.util.List;

import com.packt.webstore.domain.OrderInfo;

public interface InfoService {

	
	List<OrderInfo> getAllOrdersInfo();
}
