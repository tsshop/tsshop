package com.shop.tsshop.web.service;


import java.math.BigDecimal;

public interface TransportManagerService {
	/**
	 * 计算运费信息
	 * @param allMoney                   总金额
	 * @param purchaseQuantity           数量
	 * @param deliveryTemplateId         模版 ID
	 * @param cityId                     城市 ID
	 * @return                           统一返回
	 */
	BigDecimal calculateTransfee(BigDecimal allMoney, Integer purchaseQuantity, Long deliveryTemplateId, Long cityId);
}
