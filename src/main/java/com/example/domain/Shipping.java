package com.example.domain;

/**
 * 配送状況を表すドメインクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class Shipping {

	/** 配送状況ID */
	private Integer shippingId;
	/** 配送状況 */
	private String shippingStatus;

	public Shipping() {
	}

	public Shipping(Integer shippingId, String shippingStatus) {
		super();
		this.shippingId = shippingId;
		this.shippingStatus = shippingStatus;
	}

	@Override
	public String toString() {
		return "Shipping [shippingId=" + shippingId + ", shippingStatus=" + shippingStatus + "]";
	}

	public Integer getShippingId() {
		return shippingId;
	}

	public void setShippingId(Integer shippingId) {
		this.shippingId = shippingId;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}
}
