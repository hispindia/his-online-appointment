package org.openmrs.module.onlineappointment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "onlineappointment.online_hosp")
@Table(name = "online_hospitals")
public class Online_hospitals implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "online_hospital_id")
	private Integer online_hospital_id;
	
	@Basic
	private String online_hospital_name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "online_hospital_country")
	private Online_locations online_hospital_country;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "online_hospital_state")
	private Online_locations online_hospital_state;
	
	@ManyToOne
	@JoinColumn(name = "online_hospital_district")
	private Online_locations online_hospital_district;
	
	@ManyToOne
	@JoinColumn(name = "online_hospital_type")
	private Online_locations online_hospital_type;
	
	@Basic
	private String online_hospital_url;
	
	@Basic
	private Date createdDateTime;
	
	public Integer getOnline_hospital_id() {
		return online_hospital_id;
	}
	
	public void setOnline_hospital_id(Integer online_hospital_id) {
		this.online_hospital_id = online_hospital_id;
	}
	
	public String getOnline_hospital_name() {
		return online_hospital_name;
	}
	
	public void setOnline_hospital_name(String online_hospital_name) {
		this.online_hospital_name = online_hospital_name;
	}
	
	public Online_locations getOnline_hospital_country() {
		return online_hospital_country;
	}
	
	public void setOnline_hospital_country(Online_locations online_hospital_country) {
		this.online_hospital_country = online_hospital_country;
	}
	
	public Online_locations getOnline_hospital_state() {
		return online_hospital_state;
	}
	
	public void setOnline_hospital_state(Online_locations online_hospital_state) {
		this.online_hospital_state = online_hospital_state;
	}
	
	public Online_locations getOnline_hospital_district() {
		return online_hospital_district;
	}
	
	public void setOnline_hospital_district(Online_locations online_hospital_district) {
		this.online_hospital_district = online_hospital_district;
	}
	
	public Online_locations getOnline_hospital_type() {
		return online_hospital_type;
	}
	
	public void setOnline_hospital_type(Online_locations online_hospital_type) {
		this.online_hospital_type = online_hospital_type;
	}
	
	public String getOnline_hospital_url() {
		return online_hospital_url;
	}
	
	public void setOnline_hospital_url(String online_hospital_url) {
		this.online_hospital_url = online_hospital_url;
	}
	
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
}
