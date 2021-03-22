package com.cnebula.example.mybatis.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 地址
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

	/**
	 * 国家
	 */
	private String state;
	
	/**
	 * 省
	 */
	private String province;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 街道
	 */
	private String street;
	
	/**
	 * 邮编
	 */
	private String zipCode;
}
