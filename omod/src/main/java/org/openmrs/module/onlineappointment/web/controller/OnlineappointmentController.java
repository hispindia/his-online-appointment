/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.onlineappointment.web.controller;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import org.openmrs.api.context.Context;
import org.openmrs.module.onlineappointment.Online_OTP;
import org.openmrs.module.onlineappointment.Online_appointment;
import org.openmrs.module.onlineappointment.api.OnlineappointmentService;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.openmrs.Visit;
import org.openmrs.Patient;
import org.openmrs.api.VisitService;
import org.openmrs.api.PatientService;

/**
 * Controller that lets a client check the status of their session, and log out. (Authenticating is
 * handled through a filter, and may happen through this or any other resource.
 */
@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/onlineappointment")
public class OnlineappointmentController extends MainResourceController {
	
	public static final String USER_CUSTOM_REP = "(uuid,display,username,systemId,userProperties,person:(uuid),privileges:(uuid,name),roles:(uuid,name))";
	
	@Autowired
	RestService restService;
	
	@Autowired
	OnlineappointmentService online_app_Service;
	
	/**
	 * Tells the user their sessionId, and whether or not they are authenticated.
	 * 
	 * @param request
	 * @return
	 * @should return the session id if the user is authenticated
	 * @should return the session id if the user is not authenticated
	 */
	
	@RequestMapping(value = "/location_types", method = RequestMethod.GET)
	@ResponseBody
	public Object getloctaion_types() {
		
		SimpleObject location_types = new SimpleObject();
		location_types.add("sessionLocation",
		    ConversionUtil.convertToRepresentation(online_app_Service.getLocationType(), Representation.REF));
		
		return location_types;
	}
	
	@RequestMapping(value = "/location_types", method = RequestMethod.POST)
	public ResponseEntity setloctaion_types(@RequestParam String location_type) {
		
		online_app_Service.saveLocationType(location_type);
		
		return new ResponseEntity(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	@ResponseBody
	public Object getonline_locations(@RequestParam String location_type_id) {
		
		SimpleObject location_types = new SimpleObject();
		location_types.add("sessionLocation",
		    ConversionUtil.convertToRepresentation(online_app_Service.getLocation(location_type_id), Representation.REF));
		
		return location_types;
	}
	
	@RequestMapping(value = "/hospitals", method = RequestMethod.GET)
	@ResponseBody
	public Object gethospital_details(@RequestParam String country_id, @RequestParam String state_id,
	        @RequestParam String district_id) {
		
		SimpleObject location_types = new SimpleObject();
		location_types.add("sessionLocation", ConversionUtil.convertToRepresentation(
		    online_app_Service.getHospital(country_id, state_id, district_id), Representation.REF));
		
		return location_types;
	}
	
	@RequestMapping(value = "/getOTP", method = RequestMethod.POST)
	@ResponseBody
	public Object getOTP(@RequestBody Online_OTP otp_req, HttpServletRequest req) {
		
		SimpleObject OTP = new SimpleObject();
		OTP.add("OTP", ConversionUtil.convertToRepresentation(online_app_Service.getOTP(otp_req), Representation.REF));
		Context.addConfigProperty(otp_req.getPhone(), otp_req.getOTP());
		return OTP;
	}
	
	@RequestMapping(value = "/confirmOTP", method = RequestMethod.POST)
	@ResponseBody
	public Object confirmOTP(@RequestBody Online_OTP otp_confirm_req, HttpServletRequest req) {
		
		SimpleObject OTP = new SimpleObject();
		otp_confirm_req.setStatus("FAIL");
		System.out.println(Context.getConfigProperties().get(otp_confirm_req.getPhone()));
		
		if (otp_confirm_req.getOTP().equals(Context.getConfigProperties().get(otp_confirm_req.getPhone()))) {
			
			otp_confirm_req.setStatus("SUCCESS");
			
		}
		
		Context.removeConfigProperty(otp_confirm_req.getPhone());
		OTP.add("OTP", ConversionUtil.convertToRepresentation(otp_confirm_req, Representation.REF));
		
		//extract to process class, check for time as well
		
		return OTP;
	}
	
	@RequestMapping(value = "/saveappointment", method = RequestMethod.POST)
	@ResponseBody
	public Object saveappointment(@RequestBody Online_appointment appointment, HttpServletRequest req) {
		
		SimpleObject saveappointment = new SimpleObject();
		saveappointment.add("saveappointment", ConversionUtil.convertToRepresentation(
		    online_app_Service.saveOnlineAppointment(appointment), Representation.REF));
		return saveappointment;
	}
	
	@RequestMapping(value = "/listappointment", method = RequestMethod.POST)
	@ResponseBody
	public Object listappointment(String mobile, String adhar) {
		
		SimpleObject listappointment = new SimpleObject();
		//online_app_Service.getOnlineAppointment(mobile, adhar);
		listappointment.add("appointmentlist", ConversionUtil.convertToRepresentation(
		    online_app_Service.getOnlineAppointment(mobile, adhar), Representation.REF));
		return listappointment;
	}
	
	@RequestMapping(value = "/appointmentstatus", method = RequestMethod.POST)
	@ResponseBody
	public Object appointmentstatus(String appointment_id, String status) {
		
		SimpleObject appointmentstatus = new SimpleObject();
		appointmentstatus.add("appointmentstatus", ConversionUtil.convertToRepresentation(
		    online_app_Service.statusOnlineAppointment(appointment_id, status), Representation.REF));
		return appointmentstatus;
	}
	
	@RequestMapping(value = "/onlinePatientVisits", method = RequestMethod.GET)
	@ResponseBody
	public Object getOnlinePatientVisit(@RequestParam String patientId) throws ParseException {
		
		Patient patient = Context.getService(PatientService.class).getPatientByUuid(patientId);
		List<Visit> visits = new ArrayList<Visit>(Context.getService(VisitService.class).getVisitsByPatient(patient));
		
		SimpleObject onlineVisits = new SimpleObject();
		onlineVisits.add("visits", ConversionUtil.convertToRepresentation(visits, Representation.REF));
		return onlineVisits;
	}
	
	@RequestMapping(value = "/onlineAppointments", method = RequestMethod.GET)
	@ResponseBody
	public Object getAppointmentByDate(@RequestParam String frdate, @RequestParam String todate) throws ParseException {
		
		SimpleObject appointments = new SimpleObject();
		appointments.add("appointments", ConversionUtil.convertToRepresentation(
		    online_app_Service.getAppointmentByDate(frdate, todate), Representation.REF));
		return appointments;
	}
	
	@RequestMapping(value = "/allhospitals", method = RequestMethod.GET)
	@ResponseBody
	public Object getHospitalCount(@RequestParam(required = false) String district) {
		
		SimpleObject hospitalCount = new SimpleObject();
		hospitalCount
		        .add("hospital_count", ConversionUtil.convertToRepresentation(online_app_Service.getHospitalCount(district)
		                .size(), Representation.REF));
		hospitalCount.add("hospital_data",
		    ConversionUtil.convertToRepresentation(online_app_Service.getHospitalCount(district), Representation.REF));
		return hospitalCount;
	}
	
	@RequestMapping(value = "/appointments", method = RequestMethod.GET)
	@ResponseBody
	public Object getAppointmentCount(@RequestParam(required = false, value = "district_id") String district_id,
	        @RequestParam(required = false, value = "hospital_id") String hospital_id,
	        @RequestParam(required = false, value = "from_date") String from_date,
	        @RequestParam(required = false, value = "to_date") String to_date) throws ParseException {
		
		SimpleObject appointmentCount = new SimpleObject();
		
		appointmentCount.add(
		    "appointmentDetail",
		    ConversionUtil.convertToRepresentation(
		        online_app_Service.getAppointmentCount(district_id, hospital_id, from_date, to_date), Representation.REF));
		return appointmentCount;
	}
	
	@RequestMapping(value = "/districts", method = RequestMethod.GET)
	@ResponseBody
	public Object getDistrictCount() {
		
		SimpleObject districtCount = new SimpleObject();
		districtCount.add("district_count",
		    ConversionUtil.convertToRepresentation(online_app_Service.getDistrictCount().size(), Representation.REF));
		districtCount.add("district_list",
		    ConversionUtil.convertToRepresentation(online_app_Service.getDistrictCount(), Representation.REF));
		return districtCount;
	}
}
