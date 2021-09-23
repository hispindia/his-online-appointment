package org.openmrs.module.onlineappointment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "onlineappointment.online_loc")
@Table(name = "online_locations")
public class Online_locations implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "online_location_id")
	private Integer online_location_id;
	
	@Basic
	private String online_location_name;
	
	@ManyToOne
	@JoinColumn(name = "online_location_type_id")
	private Online_location_type online_location_type_id;
	
	@Basic
	private Date createdDateTime;
	
	public Integer getOnline_location_id() {
		return online_location_id;
	}
	
	public void setOnline_location_id(Integer online_location_id) {
		this.online_location_id = online_location_id;
	}
	
	public String getOnline_location_name() {
		return online_location_name;
	}
	
	public void setOnline_location_name(String online_location_name) {
		this.online_location_name = online_location_name;
	}
	
	public Online_location_type getOnline_location_type_id() {
		return online_location_type_id;
	}
	
	public void setOnline_location_type_id(Online_location_type online_location_type_id) {
		this.online_location_type_id = online_location_type_id;
	}
	
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
}
