package org.openmrs.module.onlineappointment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "onlineappointment.online_appointment")
@Table(name = "online_appointment_request")
public class Online_appointment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "appointment_id")
	private Integer appointment_id;
	
	@Basic
	private String hospital_appointment_id;
	
	@Basic
	private String patient_id;
	
	@Basic
	private String hospital_id;
	
	@Basic
	private String district_id;
	
	@Basic
	private String hospital_url;
	
	@Basic
	private String patient_mobile;
	
	@Basic
	private String patient_adhar;
	
	@Basic
	private String appointment_status;
	
	@Basic
	private String patient_name;
	
	@Basic
	private String patient_gender;
	
	@Basic
	private String patient_age;
	
	@Basic
	private String hospital_name;
	
	@Basic
	private String district_name;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date appointment_date;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date appointment_cancellation_date;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date appointment_creation_date;
	
	public Integer getAppointment_id() {
		return appointment_id;
	}
	
	public void setAppointment_id(Integer appointment_id) {
		this.appointment_id = appointment_id;
	}
	
	public String getHospital_appointment_id() {
		return hospital_appointment_id;
	}
	
	public void setHospital_appointment_id(String hospital_appointment_id) {
		this.hospital_appointment_id = hospital_appointment_id;
	}
	
	public String getPatient_id() {
		return patient_id;
	}
	
	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}
	
	public String getHospital_id() {
		return hospital_id;
	}
	
	public void setHospital_id(String hospital_id) {
		this.hospital_id = hospital_id;
	}
	
	public String getDistrict_id() {
		return district_id;
	}
	
	public void setDistrict_id(String district_id) {
		this.district_id = district_id;
	}
	
	public String getHospital_url() {
		return hospital_url;
	}
	
	public void setHospital_url(String hospital_url) {
		this.hospital_url = hospital_url;
	}
	
	public String getPatient_mobile() {
		return patient_mobile;
	}
	
	public void setPatient_mobile(String patient_mobile) {
		this.patient_mobile = patient_mobile;
	}
	
	public String getPatient_adhar() {
		return patient_adhar;
	}
	
	public void setPatient_adhar(String patient_adhar) {
		this.patient_adhar = patient_adhar;
	}
	
	public String getAppointment_status() {
		return appointment_status;
	}
	
	public void setAppointment_status(String appointment_status) {
		this.appointment_status = appointment_status;
	}
	
	public Date getAppointment_date() {
		return appointment_date;
	}
	
	public void setAppointment_date(Date appointment_date) {
		this.appointment_date = appointment_date;
	}
	
	public Date getAppointment_cancellation_date() {
		return appointment_cancellation_date;
	}
	
	public void setAppointment_cancellation_date(Date appointment_cancellation_date) {
		this.appointment_cancellation_date = appointment_cancellation_date;
	}
	
	public Date getAppointment_creation_date() {
		return appointment_creation_date;
	}
	
	public void setAppointment_creation_date(Date appointment_creation_date) {
		this.appointment_creation_date = appointment_creation_date;
	}
	
	public String getPatient_name() {
		return patient_name;
	}
	
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	
	public String getPatient_gender() {
		return patient_gender;
	}
	
	public void setPatient_gender(String patient_gender) {
		this.patient_gender = patient_gender;
	}
	
	public String getPatient_age() {
		return patient_age;
	}
	
	public void setPatient_age(String patient_age) {
		this.patient_age = patient_age;
	}
	
	public String getHospital_name() {
		return hospital_name;
	}
	
	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}
	
	public String getDistrict_name() {
		return district_name;
	}
	
	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}
	
}
