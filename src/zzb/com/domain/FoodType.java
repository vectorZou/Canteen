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
public class FoodType implements Serializable {
	private static final long serialVersionUID = 1L;
	/**食品类型编号**/
	private Integer ID;
	/**食品类型名称**/
	private String name;
	/**食品类型备注**/
	private String note;
	/**关联子类**/
	private int childs;
	//private Set<FoodType> childTypes; //在这里配置了onetomany关系会造成问题，自己现在还是很清楚该如何做，所以现在暂时把它注释掉
	/**父类**/
	private FoodType parent;
	
	public FoodType() {
		super();
	}

	public FoodType(String name,String note) {
		super();
		this.name = name;
		this.note = note;
	}
	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	@Column(length=20,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=200)
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
//	@OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="parent",fetch=FetchType.EAGER)
//	public Set<FoodType> getChildTypes() {
//		return childTypes;
//	}
//	
//	public void setChildTypes(Set<FoodType> childTypes) {
//		this.childTypes = childTypes;
//	}
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="parentID")
	public FoodType getParent() {
		return parent;
	}

	public void setParent(FoodType parent) {
		this.parent = parent;
	}
	@Column(nullable=false)
	public int getChilds() {
		return childs;
	}

	public void setChilds(int childs) {
		this.childs = childs;
	}

	
}
