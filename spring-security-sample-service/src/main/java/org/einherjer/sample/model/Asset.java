package org.einherjer.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="assets")
public class Asset extends AbstractEntity {
	
	@Column(nullable=false, length=30, unique=true)
	public String code;

	
	public Asset() {}
	public Asset(String code) {
		this.code = code;
	}
	
	public void set(Asset data) {
		this.code = data.getCode();
	}
	
	
	public String getCode() {
		return code;
	}
	
}
