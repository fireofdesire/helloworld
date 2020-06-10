package com.xmltransaction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService 
{
	@Autowired
    StockDao stockDao;	
	//结账
	public void checkout(String userId,String goodsId) throws FileNotFoundException
	{
		//线程休眠3秒，事务超时自动回滚
		/*
		 * try { Thread.sleep(3000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		*/
		
		//减库存
		stockDao.updateGoodsStockById(goodsId);
		//减账户余额
		stockDao.updateBalance(userId, goodsId);
		System.out.println("结账成功");
		
		//让运行时异常发生时，事务从默认回滚变为默认不回滚
		//int x = 1/0;
		
		//让编译时异常发生时，事务从不默认回滚变为默认回滚
		//new FileInputStream("D://sss.aa");
	}
	
	//改价格，设置事务传播行为
	public void ChangePrice(String goodsId,int goodsPrice)
	{
		stockDao.updateGoodsPriceById(goodsId, goodsPrice);
		int x = 1/0;		
	}
	
	//大事务
	public void checkOutAndChangePrice(String userId,String goodsId,int goodsPrice)
	{
		ApplicationContext aoc = new ClassPathXmlApplicationContext("xmlTransaction.xml");
		StockService service = aoc.getBean(StockService.class);
		try 
		{
			service.checkout(userId, goodsId);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service.ChangePrice(goodsId, goodsPrice);
	}
}
