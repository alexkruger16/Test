package com.fisa.demo.vo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

/**
 * Request for conference.
 * 
 * @author aguato on <b>2021/01/23.</b>
 *
 */
@Data
public class ConferenceRequestVO {
	String nameConference; 
	LocalDate startDate; 
	LocalDate endDate;
	
	LocalTime startTimeMorning;
	LocalTime endTimeMorning;
	LocalTime startTimeAfternoon;
	LocalTime endTimeAfternoon;
	LocalTime timeLuch;
	LocalTime timeNetworkingEvent;
	
	List<TalkVO> talks;

}
