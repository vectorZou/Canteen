package zzb.com.web.utils;

import java.util.HashMap;
import java.util.Map;

import zzb.com.base.QueryResult;
import zzb.com.domain.FoodType;
import zzb.com.service.FoodTypeService;

public class TypeTree {
	private Map<String,Object> treeMap;
	private FoodTypeService fts;
		
	public TypeTree(FoodTypeService fts){
		this.fts = fts;
		this.treeMap = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getTreeMap() {
		originalTypeGenerater();
		return treeMap;
	}

	public void originalTypeGenerater(){
		QueryResult<FoodType> qr = fts.getScrollData(FoodType.class, -1, -1, " where o.parent is null", null);
		for(FoodType type : qr.getResultList()){
			/***
			 * 如果没有子类型，那么就直接将name为key，id为value
			 * 否则就循环嵌套出所有的类型
			 */
			if( isLastChildType( type) ){
				treeMap.put(type.getName(), type.getID());
			}else{
				QueryResult<FoodType> cQr = fts.getScrollData(FoodType.class, -1, -1, " where o.parent.ID="+type.getID(), null);
				Map<String,Object> temp = new HashMap<String,Object>();
				temp.put(type.getID()+"", childMapGenerator(cQr)); // 在这里存放子类型
				treeMap.put(type.getName(), temp);
			}
		}
	}

	private Map<String,Object> childMapGenerator(QueryResult<FoodType> cQr) {
		Map<String,Object> pMap = new HashMap<String,Object>();
		for(FoodType type : cQr.getResultList()){
			if(isLastChildType(type)){
				pMap.put(type.getName(), type.getID());
			}else{
				QueryResult<FoodType> qr = fts.getScrollData(FoodType.class, -1, -1, " where o.parent.ID="+type.getID(), null);
				Map<String,Object> temp = new HashMap<String,Object>();
				temp.put(type.getID()+"", childMapGenerator(qr)); // 在这里存放子类型
				pMap.put(type.getName(), temp);
			}
		}
		return pMap;
	}
	/***
	 * 判断当前类型下有没有子类型
	 * @param id
	 * @return
	 */
	private boolean isLastChildType(FoodType type){
		if(type.getChilds() > 0){
			return false;
		} 
		return true;
	}
}
