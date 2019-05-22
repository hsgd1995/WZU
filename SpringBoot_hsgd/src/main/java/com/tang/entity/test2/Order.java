package com.tang.entity.test2;

/**
 * 订单（数据源2）
 * @author Administrator
 * @date   2019年5月21日
 */
public class Order {
	private Integer id;
	private Integer userId;
	private String orderName;

	public Order() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", orderName=" + orderName + "]";
	}

}
