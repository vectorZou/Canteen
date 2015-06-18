package zzb.com.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StockInfo {
	private String id;
	/**名称**/
	private String name;
	/**价格**/
	private double price;
	/**数量**/
	private int count;
	/**总价**/
	private double totalPrice;
	/**进货时间**/
	private String stockTime;
	/**进货人**/
	private String purchaser;
	/**检验员**/
	private String checkPerson;
	
	@Id @Column(length=36,nullable=false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(length=20,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable=false)
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Column(nullable=false)
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Column(nullable=false)
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		if(totalPrice == 0){
			this.totalPrice = this.price * this.count ;
		}else{
			this.totalPrice = totalPrice;
		}
	}
	@Column(length=20,nullable=false)
	public String getStockTime() {
		return stockTime;
	}
	public void setStockTime(String stockTime) {
		this.stockTime = stockTime;
	}
	@Column(length=20,nullable=false)
	public String getPurchaser() {
		return purchaser;
	}
	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}
	@Column(length=20,nullable=false)
	public String getCheckPerson() {
		return checkPerson;
	}
	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}
	
	
	
}
