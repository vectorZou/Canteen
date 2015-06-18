package zzb.com.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Food implements Serializable{
	private static final long serialVersionUID = 1L;
	/**食品编号**/
	private Integer ID;
	/**所属食品类型**/
	private FoodType type;
	/**食品名称**/
	private String name;
	/**食品价格**/
	private String price;
	/**食品供货商**/
	private String supplier;
	/**食品介绍**/
	private String brief;
	/**仓库数量**/
	private Integer store;
	/**食品图片**/
	private String image;
	/**进货时间**/
	private String stockTime;
	/**有效期**/
	private int days;
	
	public Food() {
		super();
	}
	
	public Food(String name, String price, String image) {
		super();
		this.name = name;
		this.price = price;
		this.image = image;
	}

	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="typeID")
	public FoodType getType() {
		return type;
	}
	public void setType(FoodType type) {
		this.type = type;
	}
	@Column(length=20,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=5,nullable=false)
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Column(length=40,nullable=false)
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	@Column(length=200)
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	@Column(nullable=false)
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	@Column(nullable=false)
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Column(nullable=false)
	public String getStockTime() {
		return stockTime;
	}
	public void setStockTime(String stockTime) {
		this.stockTime = stockTime;
	}
	@Column(nullable=false)
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}

}
