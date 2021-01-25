/**
 * 
 */
package com.fisa.demo.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.fisa.demo.services.IConferenceTrackManagerService;
import com.fisa.demo.util.EnumTypeSession;
import com.fisa.demo.vo.ConferenceRequestVO;
import com.fisa.demo.vo.ConferenceVO;
import com.fisa.demo.vo.SessionVO;
import com.fisa.demo.vo.TalkVO;
import com.fisa.demo.vo.TrackVO;

/**
 * @author aguato on <b>2021/01/23.</b>
 * 
 *
 */
@Service
public class ConferenceTrackManagerService implements IConferenceTrackManagerService {

	@Override
	public ConferenceVO getConference(ConferenceRequestVO conferenceRequestVO) throws Exception {

		try {
			long minutesMorning = ChronoUnit.MINUTES.between(conferenceRequestVO.getStartTimeMorning(),
					conferenceRequestVO.getEndTimeMorning());
			long minutesAfternoon = ChronoUnit.MINUTES.between(conferenceRequestVO.getStartTimeAfternoon(),
					conferenceRequestVO.getEndTimeAfternoon());
			int totalMinutesTalks = conferenceRequestVO.getTalks().stream().mapToInt(TalkVO::getTimeDuration).sum();

			// Tracks by total talks minutes and minutes per day
			BigDecimal valorBigDecimal = BigDecimal
					.valueOf((double) totalMinutesTalks / (double) (minutesMorning + minutesAfternoon));
			int totalTracks = (valorBigDecimal.setScale(0, RoundingMode.UP)).intValue();

			List<TalkVO> talksRequest = conferenceRequestVO.getTalks();
			Collection<TrackVO> tracksConference = new LinkedHashSet<TrackVO>();
			LocalDate currentDate = LocalDate.now();

			for (int i = 0; i < totalTracks; i++) {
				TrackVO trackVO = new TrackVO();
				trackVO.setTrackCode(i);
				trackVO.setSchedulingDate(currentDate.plusDays(i + 1));
				trackVO.setSessionMorning(getSession(i, minutesMorning, conferenceRequestVO.getStartTimeMorning(),
						conferenceRequestVO.getEndTimeMorning(), EnumTypeSession.MORNING.getTypeSession(),
						talksRequest, true));
				trackVO.setSessionAfternoon(getSession(i + 1, minutesAfternoon,
						conferenceRequestVO.getStartTimeAfternoon(), conferenceRequestVO.getEndTimeAfternoon(),
						EnumTypeSession.AFTERNOON.getTypeSession(), talksRequest, false));
				tracksConference.add(trackVO);

			}

			// Conference
			ConferenceVO conferenceVO = new ConferenceVO();
			conferenceVO.setConfereneCode(0);
			conferenceVO.setStartTime(conferenceRequestVO.getStartDate());
			conferenceVO.setEndTime(conferenceRequestVO.getEndDate());
			conferenceVO.setNameConference(conferenceRequestVO.getNameConference());
			conferenceVO.setTimeLuch(conferenceRequestVO.getTimeLuch());
			conferenceVO.setTimeNetworkingEvent(conferenceRequestVO.getTimeNetworkingEvent());
			conferenceVO.setTracks(tracksConference);
			return conferenceVO;

		} catch (Exception e) {
			System.out.println("Error: " + e);
			throw e;
		}

	}

	/**
	 * Returns session with talks.
	 * 
	 * @param endTimeSession
	 * 
	 * @param trackVO
	 * @param minutesMorning
	 * @param minutesAfternoon
	 * @param startTimeMorning
	 * @param startTimeAfternoon
	 * @param talksRequest
	 * @throws Exception
	 */
	private SessionVO getSession(Integer sessionCode, long minutesSession, LocalTime startTimeSession,
			LocalTime endTimeSession, String sessionName, List<TalkVO> talksRequest, boolean completeSchedule) throws Exception {

		Collection<TalkVO> talksSession = new LinkedHashSet<TalkVO>();
		LocalTime startTimeCalculate = startTimeSession;

		SessionVO sessionVO = new SessionVO();
		sessionVO.setSessionCode(sessionCode);
		sessionVO.setSessionName(sessionName);
		sessionVO.setStartTime(startTimeSession);
		sessionVO.setEndTime(endTimeSession);
		
		if (completeSchedule) {
			TalkVO[] listCombinationSession = getCombinationBySession(minutesSession, talksRequest);
			if (listCombinationSession == null) {
				throw new Exception("Error to determinate talks");
			}

			for (TalkVO talkVO : listCombinationSession) {
				talkVO.setStartTime(startTimeCalculate);
				startTimeCalculate = startTimeCalculate.plusMinutes(talkVO.getTimeDuration());
				talkVO.setEndTime(startTimeCalculate);
				talksSession.add(talkVO);
			}
			
		} else {
			Iterator<TalkVO> iterator = talksRequest.iterator();
			// Talks
			while (iterator.hasNext() && minutesSession > 0) {
				TalkVO talk = iterator.next();
				if (talk.getTimeDuration() > minutesSession) {
					continue;
				}
				minutesSession = minutesSession - talk.getTimeDuration();
				talk.setStartTime(startTimeCalculate);
				startTimeCalculate = startTimeCalculate.plusMinutes(talk.getTimeDuration());
				talk.setEndTime(startTimeCalculate);
				talksSession.add(talk);
			}
		}
		talksRequest.removeAll(talksSession);
		sessionVO.setTalkslist(talksSession);
		return sessionVO;
	}

	/**
	 * Gets combination by session minutes.
	 * 
	 * @param minutesSession
	 * @param talksRequest
	 * @return
	 */
	private TalkVO[] getCombinationBySession(long minutesSession, List<TalkVO> talksRequest) {
		List<TalkVO[]> listCombination = new ArrayList<TalkVO[]>();
		int minutesCombination = 0;
		List<TalkVO> talksRequestSorted = talksRequest.stream().sorted(Comparator.comparing(TalkVO::getTalkCode)).collect(Collectors.toList());
		for (int i = 1; i <= talksRequestSorted.size(); i++) {
			TalkVO talksTemporal[] = new TalkVO[i];
			combinationTalks(talksRequestSorted.toArray(new TalkVO[0]), talksTemporal, 0, talksRequestSorted.size() - 1, 0, i,
					listCombination);
			if (CollectionUtils.isEmpty(listCombination)) {
				continue;
			}

			for (TalkVO[] talkVOArray : listCombination) {
				minutesCombination = 0;
				for (TalkVO talkVO : talkVOArray) {
					minutesCombination += talkVO.getTimeDuration();
				}
				if (minutesCombination == minutesSession) {
					return talkVOArray;
				}
			}
			listCombination = new ArrayList<TalkVO[]>();
		}
		return null;
	}

	/**
	 * Generates combination of talks
	 * 
	 * @param talks
	 * @param talksTemporal
	 * @param start
	 * @param end
	 * @param index
	 * @param sizeCombination
	 * @param listCombination
	 */
	private static void combinationTalks(TalkVO talks[], TalkVO talksTemporal[], int start, int end, int index,
			int sizeCombination, List<TalkVO[]> listCombination) {

		// Combination size is complete
		if (index == sizeCombination) {
			listCombination.add(Arrays.copyOf(talksTemporal, talksTemporal.length));
			return;
		}

		for (int i = start; i <= end && end - i + 1 >= sizeCombination - index; i++) {
			talksTemporal[index] = talks[i];
			combinationTalks(talks, talksTemporal, i + 1, end, index + 1, sizeCombination, listCombination);
		}
	}

}
