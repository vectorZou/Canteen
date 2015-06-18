package junit.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zzb.com.service.FoodTypeService;
import zzb.com.web.utils.TypeTree;

public class TypeTreeTest {
	private static FoodTypeService fts;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 ApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/beans.xml");
		 fts = (FoodTypeService) ac.getBean("foodTypeService");
		 
	}
	
	@Test
	public void test() {
		TypeTree tt = new TypeTree(fts);
		System.out.println(tt.getTreeMap());
	}

}
