package zzb.com.base;

import java.util.List;

public class QueryResult<T> {
	private List<T> resultList;
	private long count;
	
	
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
}
