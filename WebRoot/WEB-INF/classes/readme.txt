1、搭建网站开发环境
	1、1采用技术和 导入开发包
	   struts2.0 + spring + jpa
	   	导入对应的开发包
	   	1.1.1 导入jdbc的jar包（jdbcsqlserver2008）
	   		msbase.jar、mssqlserver.jar、msutil.jar、sqljdbc4.jar
	   	1.1.2 导入jpa包
	   		antrl-2.7.6.jar、commons-collections-3.1.jar、commons-logging.jar、dom4j-1.6.1.jar、ehcahe-1.2.3.jar、
	   		ejb3-persistence.jar、hibernate3.jar、hibernate-annotations.jar、hibernate-cglib-repack-2.1_3.jar、
	   		hibernate-commons-annotations.jar、hibernate-entitymanager.jar、javasist-3.4.GA.jar、jta-1.1.jar、
	   		log4j.jar、slf4j-api-1.5.2.jar、slf-log4j12.jar
	   	1.1.3 导入spring+DBCP的jar包
	   		aspectjrt.jar、aspectjweaver.jar、c3p0-0.9.1.2.jar、cglib-nodep-2.1_3.jar、common-annotations.jar、
	   		commons-logging.jar、log4j-1.2.15.jar、spring.jar
	   		commons-dbcp-1.4.jar、commons-pool-1.6.jar、commons-collections-3.2.1.jar
	   	1.1.4 导入struts2.0的jar包
	   		commons-collections-3.2.jar、commons-fileupload-1.2.1.jar、struts2-spring-plugin-2.1.8.jar、struts2-core-2.1.8.jar
	   		xwork-core-2.1.6.jar
	   	1.1.5 导入json所必须的包
	   		commons-beanutils-1.8.3.jar、commons-lang-2.3.jar、ezmorph-1.0.6.jar、json-lib-2.4-jdk15.jar
2、创建相应的package
	在src中：
		zzb.com.domain
		zzb.com.base 
		zzb.com.service
		zzb.com.service.impl
		zzb.com.web.control.filter
		zzb.com.web.control.action
		zzb.com.web.control.action.infoaction
		zzb.com.web.utils
		juint.test
		META-INF
	在WebRoot中
		WEB-INF/page(主要是存放jsp页面)
3、对应于不同的功能实现，向不同的包内创建class类和interface接口
	3.1、配置JPA文件，即persistence.xml文件，并创建相应的类来测试是否配置正确
	3.2、配置spring，即完成spring+JPA的组装，并创建相应的类来测试是否配置正确
	3.3、配置Struts, 即完成Struts+Spring+JPA的组装，并创建相应的类来测试是否配置正确
4、采用循序渐进法，来完成整个软件的设计工作。
	4.1、 首先完成食堂管理系统的框架的搭建，选择合适的框架对以后功能和样式的排布有很大帮助
	4.2、完成基本信息维护模块的构建
		4.2.1、食品类型管理页面，也即食品类型的CRUD以及类型树的生成。
		4.2.2、食品基本信息管理，也即食品的CRUD
		4.2.3  食品供应商信息管理，也就供应商的CRUD
		4.2.4 用户管理  ，也即用户的CRUD
	
	
	
	
	