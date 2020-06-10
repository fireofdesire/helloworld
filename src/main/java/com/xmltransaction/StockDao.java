package com.xmltransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StockDao 
{
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	//结账操作:
	//1.查商品价格
	public Integer getGoodsPriceById(String goodsId)
	{
		String sql = "select goodsPrice from transaction_goods where goodsId=?";
		return jdbcTemplate.queryForObject(sql, Integer.class, goodsId);
	}
	
	//2.扣钱 
	public void updateBalance(String userId,String goodsId)
	{
		Integer price = getGoodsPriceById(goodsId);
		String sql = "update transaction_user set balance = balance-? where userId = ?";
		jdbcTemplate.update(sql,price,userId);
	}
	
	//3.减库存
	public void updateGoodsStockById(String goodsId)
	{
		String sql = "update transaction_stock set stock = stock-1 where goodsId=?";
		jdbcTemplate.update(sql,goodsId);
	}
	
	//改商品价格
	public void updateGoodsPriceById(String goodsId,int goodsPrice)
	{
		String sql = "update transaction_goods set goodsPrice = ? where goodsId = ?";
		jdbcTemplate.update(sql,goodsPrice,goodsId);
	}
	
}
