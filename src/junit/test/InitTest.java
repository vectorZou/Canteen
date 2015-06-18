package junit.test;


import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zzb.com.domain.FoodType;
import zzb.com.service.FoodTypeService;

public class InitTest {
	private static EntityManager entityManager;
	private static FoodTypeService fts;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 entityManager = Persistence.createEntityManagerFactory("zzb").createEntityManager();
		 ApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/beans.xml");
		 fts = (FoodTypeService) ac.getBean("foodTypeService");
		 
	}

	@Test
	public void initJPA() {
         entityManager.getTransaction().begin();
		 entityManager.persist(new FoodType("蔬菜2","吃了对身体有好处"));
		 entityManager.getTransaction().commit();
	}
	
	@Test
	public void initSpring(){
		for(int i=1;i<50;i++){
			fts.save(new FoodType("肉类2"+i,""+i));
		}
	}

}
