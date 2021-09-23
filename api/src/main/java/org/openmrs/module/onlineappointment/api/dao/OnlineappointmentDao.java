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
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
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
import org.springframework.stereotype.Repository;

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
	
	public Online_appointment cancelOnlineAppointment(String appointment_id) throws DAOException {
		
		Online_appointment ola = (Online_appointment) getSession().get(Online_appointment.class,
		    Integer.parseInt(appointment_id));
		ola.setAppointment_cancellation_date(new Date());
		ola.setAppointment_status("CANCELLED");
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
}
