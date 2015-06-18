package zzb.com.base;

import java.util.LinkedHashMap;

public interface DAO {
	/***
	 * 根据主键查找对应的对象
	 * @param clazz 查找对象的Class
	 * @param obj 主键
	 * @return
	 */
	public <T> T findById(Class<T> clazz,Object obj);
	/***
	 * 保存对象
	 * @param obj
	 */
	public void save(Object obj);
	/***
	 * 更新对象
	 * @param obj
	 */
	public void update(Object obj);
	/***
	 * 删除实体对象
	 * @param clazz 实体对象的class类型
	 * @param id 实体对象的主键id 
	 */
	public <T> void delete(Class<T> clazz,Object id);
	/***
	 * 删除实体对象
	 * @param clazz 实体对象的class类型
	 * @param ids 实体对象的主键id数组 
	 */
	public <T> void delete(Class<T> clazz,Object[] ids);
	/***
	 * 获取所有的数据
	 * @param clazz
	 * @return
	 */
	public <T> QueryResult<T> getScrollData(Class<T> clazz);
	/***
	 * 获取分页数据
	 * @param clazz 查询实体的class对象
	 * @param firstIndex 从第几个记录开始
	 * @param maxResult 最大获取多少条数据
	 * @return
	 */
	public <T> QueryResult<T> getScrollData(Class<T> clazz, int firstIndex, int maxResult);
	/***
	 * 根据查询条件获取数据
	 * @param clazz 查询实体的class对象
	 * @param firstIndex 从第几个记录开始
	 * @param maxResult 最大获取多少条数据
	 * @param whereSql 查询条件,必须加入关键字 “where”,实体别名为'o'
	 * @param params 查询参数
	 * @return
	 */
	public <T> QueryResult<T> getScrollData(Class<T> clazz, int firstIndex, int maxResult,String whereSql,Object[] params);
	/***
	 * 获取分页数据,并且进行排序查询
	 * @param clazz 查询实体的class对象
	 * @param firstIndex 从第几个记录开始
	 * @param maxResult 最大获取多少条数据
	 * @param orderBy 排序属性
	 * @return
	 */
	public <T> QueryResult<T> getScrollData(Class<T> clazz, int firstIndex, int maxResult,LinkedHashMap<String,String> orderBy);
	/***
	 * 获取分页数据在符合条件下,并且进行排序查询
	 * @param clazz 查询实体的class对象
	 * @param firstIndex 从第几个记录开始
	 * @param maxResult 最大获取多少条数据
	 * @param orderBy 排序属性
	 * @param whereSql 查询条件,必须加入关键字 “where”
	 * @param params 查询参数
	 * @return
	 */
	public <T> QueryResult<T> getScrollData(Class<T> clazz, int firstIndex, int maxResult,String whereSql,Object[] params,LinkedHashMap<String,String> orderBy);
}
