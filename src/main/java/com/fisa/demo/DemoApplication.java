package com.fisa.demo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fisa.demo.services.IConferenceTrackManagerService;
import com.fisa.demo.vo.ConferenceRequestVO;
import com.fisa.demo.vo.ConferenceVO;
import com.fisa.demo.vo.SessionVO;
import com.fisa.demo.vo.TalkVO;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	
	@Autowired
	IConferenceTrackManagerService conferenceTrackManagerService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws Exception {
		ConferenceVO conference = conferenceTrackManagerService.getConference(getParametersConference());
		printConference(conference);
    }

	/**
	 * @return
	 */
	private ConferenceRequestVO getParametersConference() {
		ConferenceRequestVO conferenceRequestVO = new ConferenceRequestVO();
		conferenceRequestVO.setNameConference("Programming conference");
		conferenceRequestVO.setStartDate(LocalDate.now().plusDays(1));
		conferenceRequestVO.setEndDate(LocalDate.now().plusDays(2));
		conferenceRequestVO.setStartTimeMorning(LocalTime.of(9, 0));
		conferenceRequestVO.setEndTimeMorning(LocalTime.of(12, 0));
		conferenceRequestVO.setTimeLuch(LocalTime.of(12, 0));
		conferenceRequestVO.setStartTimeAfternoon(LocalTime.of(13, 0));
		conferenceRequestVO.setEndTimeAfternoon(LocalTime.of(17, 0));
		conferenceRequestVO.setTimeNetworkingEvent(LocalTime.of(17, 0));
		List<TalkVO> talks = new ArrayList<TalkVO>();
		talks.add(new TalkVO(1, "Writing Fast Tests Against Enterprise Rails", 60));
		talks.add(new TalkVO(2, "Overdoing it in Python", 45));
		talks.add(new TalkVO(3, "Lua for the Masses", 30));
		talks.add(new TalkVO(4, "Ruby Errors from Mismatched Gem Versions", 45));
		talks.add(new TalkVO(5, "Common Ruby Errors", 45));
		talks.add(new TalkVO(6, "Rails for Python Developers ", 5));		
		talks.add(new TalkVO(7, "Communicating Over Distance", 60));
		talks.add(new TalkVO(8, "Accounting-Driven Development", 45));
		talks.add(new TalkVO(9, "Woah", 30));
		talks.add(new TalkVO(10, "Sit Down and Write", 30));
		talks.add(new TalkVO(11, "Pair Programming vs Noise", 45));
		talks.add(new TalkVO(12, "Rails Magic", 60));
		talks.add(new TalkVO(13, "Ruby on Rails: Why We Should Move On", 60));
		talks.add(new TalkVO(14, "Clojure Ate Scala (on my project)", 45));
		talks.add(new TalkVO(15, "Programming in the Boondocks of Seattle", 30));
		talks.add(new TalkVO(16, "Ruby vs. Clojure for Back-End Development", 30));
		talks.add(new TalkVO(17, "Ruby on Rails Legacy App Maintenance", 60));
		talks.add(new TalkVO(18, "A World Without HackerNews", 30));
		talks.add(new TalkVO(19, "User Interface CSS in Rails Apps", 30));
		conferenceRequestVO.setTalks(talks);
		return conferenceRequestVO;
	}
	
	private static void printConference(ConferenceVO conference) {
		System.out.println("Conference name: " + conference.getNameConference());
		conference.getTracks().forEach(track -> {
			System.out.println("=========================================");
			System.out.println("Track : " + track.getTrackCode());
			printTalks(track.getSessionMorning());
			System.out.println(conference.getTimeLuch().toString().concat(" ").concat("Lunch"));
			printTalks(track.getSessionAfternoon());
			System.out.println(conference.getTimeNetworkingEvent().toString().concat(" ").concat("Networking Event"));
		});
		
		
	}

	private static void printTalks(SessionVO sessionVO) {
		sessionVO.getTalkslist().forEach(talk -> {
			System.out.println(talk.getStartTime().toString().concat(" ").concat(talk.getTalkName()).concat(" ").concat(talk.getTimeDuration().toString()).concat("m"));
		});
	}

}
