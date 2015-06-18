package junit.test;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zzb.com.domain.Food;
import zzb.com.domain.FoodType;
import zzb.com.service.FoodService;


public class FoodTest {
	private static FoodService fs;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 ApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/beans.xml");
		 fs = (FoodService) ac.getBean("foodService");
		 
	}

	@Test
	public void save() {
		Food food = new Food("鱼香肉丝","23.5","a.pgn");
		FoodType type = new FoodType();
		type.setID(61);
		food.setType(type);
		food.setStockTime(new Date().toString());
		food.setStore(12);
		food.setSupplier("邹志兵");
		fs.save(food);
	}
	
	@Test
	public void delete(){
		fs.delete(Food.class, 20);
	}
}
