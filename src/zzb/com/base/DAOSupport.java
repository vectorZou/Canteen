package zzb.com.base;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class DAOSupport implements DAO {
	@PersistenceContext
	protected EntityManager em;

	@Override
	public void save(Object object) {
		em.persist(object);
	}

	@Override
	public void update(Object object) {
		em.merge(object);
	}

	@Override
	public <T> void delete(Class<T> clazz, Object id) {
		em.remove(em.getReference(clazz, id));
	}

	@Override
	public <T> void delete(Class<T> clazz, Object[] objects) {
		for (Object id : objects) {
			delete(clazz, id);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public <T> T findById(Class<T> clazz, Object object) {
		return em.find(clazz, object);
	}

	
	@Override
	public <T> QueryResult<T> getScrollData(Class<T> clazz) {
		return getScrollData(clazz,-1,-1,null,null,null);
	}

	@Override
	public <T> QueryResult<T> getScrollData(Class<T> clazz, int firstIndex,
			int maxResult) {
		return getScrollData(clazz,firstIndex,maxResult,null,null,null);
	}
	
	@Override
	public <T> QueryResult<T> getScrollData(Class<T> clazz, int firstIndex,
			int maxResult, LinkedHashMap<String, String> orderBy) {
		return getScrollData(clazz,firstIndex,maxResult,null,null,orderBy);
	}
	
	@Override
	public <T> QueryResult<T> getScrollData(Class<T> clazz, int firstIndex,
			int maxResult, String whereSql, Object[] params) {
		return getScrollData(clazz,firstIndex,maxResult,whereSql,params,null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> clazz, int firstIndex,
			int maxResult, String whereSql, Object[] params, LinkedHashMap<String, String> orderBy) {
		QueryResult<T> qr = new QueryResult<T>();
		String entityName = getEntityName(clazz);
		Query query = em.createQuery("Select o from " + entityName + " o " + ((whereSql == null) ? "": whereSql) + buildOrderBy(orderBy, "o"));
		buildWhereCondition(query, params);
		if(firstIndex != -1){
			query.setFirstResult(firstIndex);
		}
		if(maxResult != -1){
			query.setMaxResults(maxResult);
		}
		qr.setResultList(query.getResultList());
		query = em.createQuery("Select count(o) from " + entityName + " o " + ((whereSql == null) ? "": whereSql));
		buildWhereCondition(query, params);
		qr.setCount((Long) query.getSingleResult());
		return qr;
	}	
	
	protected void buildWhereCondition(Query query,Object[] params) {
		if(params != null && params.length != 0){
			for(int i=0, len=params.length;i<len;i++){
				query.setParameter((i+1), params[i]);
			}
		}
	}

	protected String buildOrderBy(LinkedHashMap<String, String> orderBy,
			String prefix) {
		StringBuffer sb = new StringBuffer("");
		if (orderBy != null) {
			sb.append(" order by ");
			for (Map.Entry<String, String> entry : orderBy.entrySet()) {
				if (prefix != null && !"".equals(prefix)) {
					sb.append(prefix + ".").append(entry.getKey()).append(" ")
							.append(entry.getValue()).append(",");
				} else {
					sb.append(entry.getKey()).append(" ")
							.append(entry.getValue()).append(",");
				}
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	protected <T> String getEntityName(Class<T> clazz) {
		String entityName = clazz.getSimpleName();
		Entity entity = clazz.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityName = entity.name();
		}
		return entityName;
	}
}
