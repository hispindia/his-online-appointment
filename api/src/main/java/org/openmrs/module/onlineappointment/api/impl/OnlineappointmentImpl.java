/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.onlineappointment.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.onlineappointment.Online_OTP;
import org.openmrs.module.onlineappointment.Online_appointment;
import org.openmrs.module.onlineappointment.Online_hospitals;
import org.openmrs.module.onlineappointment.Online_location_type;
import org.openmrs.module.onlineappointment.Online_locations;
import org.openmrs.module.onlineappointment.api.OnlineappointmentService;
import org.openmrs.module.onlineappointment.api.dao.OnlineappointmentDao;

public class OnlineappointmentImpl extends BaseOpenmrsService implements OnlineappointmentService {
	
	OnlineappointmentDao dao;
	
	UserService userService;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(OnlineappointmentDao dao) {
		this.dao = dao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public void saveLocationType(String location_type) throws APIException {
		dao.saveLocationType(location_type);
		
	}
	
	@Override
	public void saveLocation(Integer userID) throws APIException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Online_location_type> getLocationType() throws APIException {
		
		return dao.getLocationType();
	}
	
	@Override
	public List<Online_locations> getLocation(String loc_type_id) throws APIException {
		Online_location_type ol_type = dao.getLocationType_ByID(loc_type_id);
		
		return dao.getLocation(ol_type);
	}
	
	@Override
	public List<Online_hospitals> getHospital(String country, String state, String district) throws APIException {
		
		return dao.getHospitalInstance(country, state, district);
	}
	
	@Override
	public void saveHospital(Online_hospitals hospital) throws APIException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Online_OTP getOTP(Online_OTP online_otp) {
		Online_OTP_process otp = new Online_OTP_process();
		online_otp.setOTP(otp.generate_OTP());
		return online_otp;
	}
	
	@Override
	public String confirmOTP(Online_OTP online_otp) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Online_appointment saveOnlineAppointment(Online_appointment appointment) throws APIException {
		appointment.setAppointment_status("BOOKED");
		appointment.setAppointment_creation_date(new Date());
		return dao.saveOnlineAppointment(appointment);
		
	}
	
	@Override
	public List<Online_appointment> getOnlineAppointment(String mobile_no, String adhar) throws APIException {
		List<Online_appointment> lis = new ArrayList<Online_appointment>();
		lis = dao.getOnlineAppointment(mobile_no, adhar);
		return lis;
	}
	
	@Override
	public Online_appointment cancelOnlineAppointment(String appointment_id) throws APIException {
		return dao.cancelOnlineAppointment(appointment_id);
	}
	
}
