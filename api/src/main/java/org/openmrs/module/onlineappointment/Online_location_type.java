package org.openmrs.module.onlineappointment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "onlineappointment.loc_type")
@Table(name = "online_location_type")
public class Online_location_type implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "online_location_type_id")
	private Integer online_location_type_id;
	
	@Basic
	private String online_location_type;
	
	@Basic
	private Date createdDateTime;
	
	public Integer getOnline_location_type_id() {
		return online_location_type_id;
	}
	
	public void setOnline_location_type_id(Integer online_location_type_id) {
		this.online_location_type_id = online_location_type_id;
	}
	
	public String getOnline_location_type() {
		return online_location_type;
	}
	
	public void setOnline_location_type(String online_location_type) {
		this.online_location_type = online_location_type;
	}
	
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
}
