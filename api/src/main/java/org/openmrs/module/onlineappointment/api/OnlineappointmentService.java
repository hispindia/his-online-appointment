/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.onlineappointment.api;

import java.util.*;

import org.openmrs.api.APIException;
import java.text.ParseException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.onlineappointment.Online_OTP;
import org.openmrs.module.onlineappointment.Online_appointment;
import org.openmrs.module.onlineappointment.Online_hospitals;
import org.openmrs.module.onlineappointment.Online_location_type;
import org.openmrs.module.onlineappointment.Online_locations;
import org.springframework.transaction.annotation.Transactional;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface OnlineappointmentService extends OpenmrsService {
	
	@Transactional(readOnly = false)
	public void saveLocationType(String location_type) throws APIException;
	
	@Transactional(readOnly = false)
	public void saveLocation(Integer userID) throws APIException;
	
	@Transactional(readOnly = false)
	public void saveHospital(Online_hospitals hospital) throws APIException;
	
	@Transactional(readOnly = true)
	public List<Online_location_type> getLocationType() throws APIException;
	
	@Transactional(readOnly = true)
	public List<Online_locations> getLocation(String loc_type_id) throws APIException;
	
	@Transactional(readOnly = true)
	public List<Online_hospitals> getHospital(String country, String state, String district) throws APIException;
	
	public Online_OTP getOTP(Online_OTP online_otp);
	
	public String confirmOTP(Online_OTP online_otp);
	
	@Transactional(readOnly = false)
	public Online_appointment saveOnlineAppointment(Online_appointment appointment) throws APIException;
	
	@Transactional(readOnly = false)
	public Online_appointment cancelOnlineAppointment(String appointment_id) throws APIException;
	
	@Transactional(readOnly = true)
	public List<Online_appointment> getOnlineAppointment(String mobile_no, String adhar) throws APIException;
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getHospitalCount(String district) throws APIException;
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getAppointmentCount(String district_id, String hospital_id, String from_date,
	        String to_date) throws APIException, ParseException;
	
	/*@Transactional(readOnly = true)
	public List<Map<String, Object>> getAppointmentCount2(String district_id, String hospital_id, String from_date,
	        String to_date) throws APIException, ParseException;
			*/
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getDistrictCount() throws APIException;
	
	@Transactional(readOnly = true)
	public List<Online_appointment> getAppointmentByDate(String from_date, String to_date) throws APIException,
	        ParseException;
}
