package zzb.com.service;

import zzb.com.base.DAO;

public interface FoodTypeService extends DAO{
	/***
	 * 获取子类型的个数
	 * @param clazz
	 * @param whereSql
	 * @param params
	 * @return
	 */
	public <T> int count(Class<T> clazz,String whereSql,Object[] params);
}
