package com.transaction;

public class Stock 
{
    private String goodsId;
    private int stock;
	@Override
	public String toString() {
		return "Stock [goodsId=" + goodsId + ", stock=" + stock + "]";
	}
	public Stock() {
		super();
	}
	public Stock(String goodsId, int stock) {
		super();
		this.goodsId = goodsId;
		this.stock = stock;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
