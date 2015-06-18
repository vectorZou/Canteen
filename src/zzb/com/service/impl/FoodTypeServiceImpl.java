package zzb.com.service.impl;

import org.springframework.stereotype.Service;

import zzb.com.base.DAOSupport;
import zzb.com.service.FoodTypeService;

@Service("foodTypeService")
public class FoodTypeServiceImpl extends DAOSupport implements FoodTypeService {

	@Override
	public <T> int count(Class<T> clazz,String whereSql, Object[] params) {
		if(whereSql == null) return 0;
		String entityName = getEntityName(clazz);
		javax.persistence.Query query = em.createQuery("Select count(o) from " + entityName + " o " + whereSql);
		buildWhereCondition(query, params);
		return (Integer) query.getSingleResult();
	}


}
