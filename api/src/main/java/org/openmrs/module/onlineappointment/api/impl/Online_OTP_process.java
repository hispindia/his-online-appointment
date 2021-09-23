package org.openmrs.module.onlineappointment.api.impl;

import java.util.Random;

public class Online_OTP_process {
	
	public String generate_OTP() {
		
		String numbers = "1234567890";
		Random random = new Random();
		char[] otp = new char[4];
		
		for (int i = 0; i < 4; i++) {
			otp[i] = numbers.charAt(random.nextInt(numbers.length()));
		}
		
		return new String(otp);
	}
	
}
