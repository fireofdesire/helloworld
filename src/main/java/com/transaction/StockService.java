package com.transaction;

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
	/*
	事务细节：isolation-Isolation:事务的隔离级别
	       propagation-Propagation:事务的传播行为
	       
	       rollbackFor-Class[]:哪些异常事务需要回滚(让不回滚的异常回滚)
	       noRollbackFor-Class[]:哪些异常事务不需要回滚(让会默认回滚的异常发生时不再回滚)
	       
	       rollbackForClassName-String[](全类名):哪些异常事务需要回滚
	       noRollbackForClassName-String[](全类名):哪些异常事务不需要回滚
	       
	       readOnly-boolean:设置事务为只读事务，可以优化事务，默认为false
	       timeout-int（以秒为单位）:设置事务超时，事务一旦超时就自动回滚
	       
	                     事务的回滚：事务发生运行时异常，默认回滚
	                                             事务发生编译时异常，默认不回滚
	       ArithmeticException:除零异常
	                     异常细节:异常一旦被try-catch，外界就感受不到这个异常了,要用异常进行某些测试时，最好把异常抛出去。
	       
	                     隔离级别细节:
	                     隔离级别-会出现的问题
	                    未提交读-脏读、不可重复读、幻读：事务读取了其它事务还没提交的数据
	                    提交读-不可重复读、幻读：同一次事务先后读取同一条数据，但是在两次读取的间隔之间，有其它事务修改了这条数据，就会导致两次读取的结果不一致
	                    可重复读(实际上mysql的可重复读可以避免所有问题)-幻读:事务读取了表的数据，其它事务在表中插入了新的数据，于是事务再次读取表时，发现多了一些数据
	                    事务的并发修改在数据库底层实现了自动排队。
	                    有事务的Service组件注册到ioc容器中后，获取实例创建的是Service的代理对象
	                    
	                    事务的传播行为细节:在事务嵌套使用情况下，设置内部事务是否和大事务共享一个事务(使用同一条连接)
	                    Requires_New:我和其他人不是一条船上的蚂蚱，他们翻船了与我无关，他们回滚我不回滚，实质就是直接使用新的connection。
	                    Required:大家都是一条船上的蚂蚱，要死一起死，要回滚就一起回滚，实质就是将之前事务使用connection传递给现在的事务使用。
	                                                           事务传播行为设置后，必须使用ioc容器来注入Service实例来调用方法才会有效，因为使用ioc来获取到的Service的对象实际上是Service的代理对象，而直接调用Service的方法时使用的是本类对象的方法。
	                                                           事务嵌套使用时，无论什么地方出异常，已经执行的Requires_New事务必定成功执行。
	                    Required事务的属性继承于大事务，不能自己更改了，而Requires_New事务可以自己调整属性。
	*/
	
	//结账
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ,timeout = 3,readOnly = false,noRollbackFor = {ArithmeticException.class},rollbackFor = {FileNotFoundException.class})
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
	@Transactional(propagation = Propagation.REQUIRED)
	public void ChangePrice(String goodsId,int goodsPrice)
	{
		stockDao.updateGoodsPriceById(goodsId, goodsPrice);
		int x = 1/0;		
	}
	
	//大事务
	@Transactional
	public void checkOutAndChangePrice(String userId,String goodsId,int goodsPrice)
	{
		ApplicationContext aoc = new ClassPathXmlApplicationContext("announceTransaction.xml");
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
