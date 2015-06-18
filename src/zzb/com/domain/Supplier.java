package zzb.com.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Supplier {
	/**通过外部传入一个唯一的值来作为主键**/
	private String sId;
	/**供应商名字**/
	private String name;
	/**供应商性别,1:male,0:female**/
	private int gender;
	/**供应商年龄**/
	private int age;
	/**供应商公司**/
	private String company;
	/**供应商电话**/
	private String telephone;
	/**供应商地址**/
	private String address;
	/**供应商email**/
	private String email;
	/**供应商图片**/
	private String image;
	/**供应商公司简介**/
	private String company_brief;
	
	@Id @Column(length=36,nullable=false)
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	@Column(length=20,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable=false)
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Column(length=1,nullable=false)
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	@Column(length=40)
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	@Column(length=11,nullable=false)
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(length=120,nullable=false)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length=40)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(length=100,nullable=false)
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Column(length=200,nullable=false)
	public String getCompany_brief() {
		return company_brief;
	}
	public void setCompany_brief(String company_brief) {
		this.company_brief = company_brief;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sId == null) ? 0 : sId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		if (sId == null) {
			if (other.sId != null)
				return false;
		} else if (!sId.equals(other.sId))
			return false;
		return true;
	}
	
}
