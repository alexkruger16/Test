/**
 * 
 */
package com.fisa.demo.services;

import com.fisa.demo.vo.ConferenceRequestVO;
import com.fisa.demo.vo.ConferenceVO;

/**
 * @author asguato on <b>2021/01/23.</b>
 *
 */
public interface IConferenceTrackManagerService {
	
	/**
	 * Gets conference by talks and schedule.
	 * @author asguato on <b>2021/01/23.</b>
	 * 
	 * @param conferenceRequestVO {@link ConferenceRequestVO} object to get conference.     
	 * @return
	 * @throws Exception 
	 */
	ConferenceVO getConference(ConferenceRequestVO conferenceRequestVO) throws Exception;

}
