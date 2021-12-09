/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.onlineappointment.api.dao;

import java.util.Date;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import java.text.ParseException;

import org.hibernate.Hibernate;

import org.hibernate.Query;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.onlineappointment.Online_appointment;
import org.openmrs.module.onlineappointment.Online_hospitals;
import org.openmrs.module.onlineappointment.Online_location_type;
import org.openmrs.module.onlineappointment.Online_locations;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.hibernate.criterion.Projection;
import java.text.SimpleDateFormat;

@Repository("onlineappointment.OnlineappointmentDao")
public class OnlineappointmentDao {
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public List<Online_location_type> getLocationType() {
		
		Criteria criteria = getSession().createCriteria(Online_location_type.class);
		return (List<Online_location_type>) criteria.list();
		
	}
	
	public Online_location_type getLocationType_ByID(String location_type) {
		
		Online_location_type on_loc_type = (Online_location_type) getSession().get(Online_location_type.class,
		    Integer.parseInt(location_type));
		
		return on_loc_type;
		
	}
	
	public List<Online_locations> getLocation(Online_location_type loc_type_id) {
		
		Criteria criteria = getSession().createCriteria(Online_locations.class);
		criteria.add(Restrictions.eq("online_location_type_id", loc_type_id));
		return (List<Online_locations>) criteria.list();
		
	}
	
	public List<Online_hospitals> getHospitalInstance(String country, String state, String district) {
		
		Criteria criteria = getSession().createCriteria(Online_hospitals.class);
		criteria.add(Restrictions.eq("online_hospital_country",
		    (Online_locations) getSession().get(Online_locations.class, Integer.parseInt(country))));
		criteria.add(Restrictions.eq("online_hospital_state",
		    (Online_locations) getSession().get(Online_locations.class, Integer.parseInt(state))));
		criteria.add(Restrictions.eq("online_hospital_district",
		    (Online_locations) getSession().get(Online_locations.class, Integer.parseInt(district))));
		
		return (List<Online_hospitals>) criteria.list();
		
	}
	
	public void saveLocationType(String locationtype) throws DAOException {
		
		Online_location_type ol = new Online_location_type();
		ol.setOnline_location_type(locationtype);
		ol.setCreatedDateTime(new Date());
		getSession().saveOrUpdate(ol);
		
	}
	
	public void saveLocation(Online_locations location) throws DAOException {
		
		/*
		 * Online_location_type ol = new Online_location_type();
		 * ol.setOnline_location_type(locationtype); getSession().saveOrUpdate(ol);
		 */
	}
	
	public Online_appointment saveOnlineAppointment(Online_appointment appointment) throws DAOException {
		getSession().saveOrUpdate(appointment);
		return appointment;
		
	}
	
	public Online_appointment statusOnlineAppointment(String appointment_id, String status) throws DAOException {
		
		Online_appointment ola = (Online_appointment) getSession().get(Online_appointment.class,
		    Integer.parseInt(appointment_id));
		ola.setAppointment_cancellation_date(new Date());
		ola.setAppointment_status(status);
		getSession().saveOrUpdate(ola);
		return ola;
		
	}
	
	public List<Online_appointment> getOnlineAppointment(String mobile_no, String adhar) throws APIException {
		Criteria criteria = getSession().createCriteria(Online_appointment.class);
		if (StringUtils.isBlank(mobile_no) && StringUtils.isBlank(adhar)) {
			
			throw new APIException("Mobile or Adhar is required");
		}
		
		if (StringUtils.isNotBlank(mobile_no)) {
			criteria.add(Restrictions.eq("patient_mobile", mobile_no));
		}
		if (StringUtils.isNotBlank(adhar)) {
			criteria.add(Restrictions.eq("patient_adhar", adhar));
		}
		return (List<Online_appointment>) criteria.list();
	}
	
	public List<Map<String, Object>> getHospitalCount(String district) {
		
		Criteria criteria = getSession().createCriteria(Online_hospitals.class);
		Projection projection1 = Projections.property("online_hospital_id");
		Projection projection2 = Projections.property("online_hospital_name");
		Projection projection3 = Projections.property("online_hospital_district");
		ProjectionList pList = Projections.projectionList();
		pList.add(projection1);
		pList.add(projection2);
		pList.add(projection3);
		if (StringUtils.isNotBlank(district)) {
			criteria.add(Restrictions.eq("online_hospital_district",
			    (Online_locations) getSession().get(Online_locations.class, Integer.parseInt(district))));
		}
		criteria.setProjection(pList);
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		List list2 = criteria.list();
		int districtCount = 0;
		if (list2.size() > 0) {
			districtCount = districtCount++;
			ListIterator it2 = list2.listIterator();
			while (it2.hasNext()) {
				Object[] obj = (Object[]) it2.next();
				Map<String, Object> hospMap = new HashMap<String, Object>();
				//obj[2];
				//Map<String, String> name = (Map<String, String>) obj[2];
				//Online_locations[] obj2 = (Online_locations[]) obj[2];
				//hospMap.put("districtName", obj[2]);
				hospMap.put("hospitalId", obj[0]);
				hospMap.put("hospitalName", obj[1]);
				returnList.add(hospMap);
			}
			
		}
		return (List<Map<String, Object>>) returnList;
		
	}
	
	public String getDistrict_name(int districtId) {
		Online_locations onlineLocations = new Online_locations();
		onlineLocations.setOnline_location_id(districtId);
		return onlineLocations.getOnline_location_name();
	}
	
	public List<Map<String, Object>> getDistrictCount() {
		
		Criteria criteria = getSession().createCriteria(Online_locations.class);
		
		Projection projection1 = Projections.property("online_location_id");
		Projection projection2 = Projections.property("online_location_name");
		ProjectionList pList = Projections.projectionList();
		pList.add(projection1);
		pList.add(projection2);
		criteria.add(Restrictions.eq("online_location_type_id",
		    (Online_locations) getSession().get(Online_locations.class, 3)));
		criteria.setProjection(pList);
		
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		List list2 = criteria.list();
		if (list2.size() > 0) {
			Iterator it2 = list2.iterator();
			
			while (it2.hasNext()) {
				Object[] obj = (Object[]) it2.next();
				Map<String, Object> distMap = new HashMap<String, Object>();
				distMap.put("districtId", obj[0]);
				distMap.put("districtName", obj[1]);
				returnList.add(distMap);
			}
		}
		return (List<Map<String, Object>>) returnList;
	}
	
	public List<Online_appointment> getAppointmentByDate(String from_date, String to_date) throws ParseException {
		Criteria criteria = getSession().createCriteria(Online_appointment.class);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date strDate = formatter.parse(from_date);
		Date toDate = formatter.parse(to_date);
		criteria.add(Restrictions.ge("appointment_date", strDate));
		criteria.add(Restrictions.lt("appointment_date", toDate));
		
		return (List<Online_appointment>) criteria.list();
	}
	
	public List<Map<String, Object>> getAppointmentCount(String district_id, String hospital_id, String from_date,
	        String to_date) throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Criteria criteria = getSession().createCriteria(Online_appointment.class);
		Projection projection1 = Projections.rowCount();
		Projection projection2 = Projections.property("patient_gender");
		ProjectionList pList = Projections.projectionList();
		pList.add(projection1);
		pList.add(projection2);
		if (StringUtils.isNotBlank(district_id)) {
			criteria.add(Restrictions.eq("district_id", district_id));
		}
		if (StringUtils.isNotBlank(hospital_id)) {
			criteria.add(Restrictions.eq("hospital_id", hospital_id));
		}
		if (StringUtils.isNotBlank(from_date)) {
			Date strDate = formatter.parse(from_date);
			criteria.add(Restrictions.ge("appointment_date", strDate));
		}
		if (StringUtils.isNotBlank(to_date)) {
			Date endDate = formatter.parse(to_date);
			criteria.add(Restrictions.le("appointment_date", endDate));
		}
		criteria.setProjection(pList);
		
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		List list2 = criteria.list();
		Map<String, Object> appointMap = new HashMap<String, Object>();
		
		//returnList.add(appointMap);
		Iterator it2 = list2.iterator();
		int femaleCount = 0;
		int maleCount = 0;
		while (it2.hasNext()) {
			Object[] obj = (Object[]) it2.next();
			appointMap.put("totalappointmentCount", obj[0]);
			if (obj[1].equals((CharSequence) "M")) {
				maleCount = maleCount + 1;
			} else if (obj[1].equals((CharSequence) "F")) {
				femaleCount = femaleCount + 1;
			}
		}
		
		appointMap.put("maleCount", maleCount);
		appointMap.put("femaleCount", femaleCount);
		returnList.add(appointMap);
		
		return (List<Map<String, Object>>) returnList;
		
	}
	
	/*public List<Map<String, Object>> getAppointmentCount2(String district_id, String hospital_id, String from_date,
	        String to_date) throws ParseException {
		
		String hql = "SELECT COUNT(appointment_id), patient_gender, monthname(appointment_date) "
		        + "FROM onlineappointment.online_appointment"
		        + " GROUP BY patient_gender,(appointment_date), YEAR(appointment_date) "
		        + "order by month(appointment_date)";
		Query query = getSession().createQuery(hql);
		List list2 = query.list();
		Map<String, Object> appointMap = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		Iterator it2 = list2.iterator();
		int femaleCount = 0;
		int maleCount = 0;
		int totalCount = 0;
		while (it2.hasNext()) {
			Map<String, Object> appointMap2 = new LinkedHashMap<String, Object>();
			Object[] obj = (Object[]) it2.next();
			totalCount = totalCount + Integer.parseInt(obj[0].toString());
			int monthCount = Integer.parseInt(obj[0].toString());
			
			if (appointMap2.containsKey("monthName")) {
				monthCount = monthCount + Integer.parseInt(obj[0].toString());
			} else {
				appointMap2.put("monthName", obj[2]);
			}
			appointMap2.put("monthCount", monthCount);
			if (obj[1].equals((CharSequence) "M")) {
				maleCount = maleCount + Integer.parseInt(obj[0].toString());
				appointMap2.put("month_maleCount", obj[0]);
			}
			if (obj[1].equals((CharSequence) "F")) {
				femaleCount = femaleCount + Integer.parseInt(obj[0].toString());
				appointMap2.put("month_femaleCount", obj[0]);
			}
			
			appointMap.put("totalAppointment", totalCount);
			appointMap.put("totalMale", maleCount);
			appointMap.put("totalFemale", femaleCount);
			appointMap.put(obj[2] + "-Data", appointMap2);
		}
		returnList.add(appointMap);
		return (List<Map<String, Object>>) returnList;
		
	}*/
}
