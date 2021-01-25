/**
 * 
 */
package com.fisa.demo.vo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import lombok.Data;

/**
 * Vo for conference
 * @author aguato on <b>2021/01/23.</b>
 *
 */
@Data
public class ConferenceVO {

	private Integer confereneCode;
	private String nameConference;
	private LocalDate startTime;
	private LocalDate endTime;
	private LocalTime timeLuch;
	private LocalTime timeNetworkingEvent;
	private Collection<TrackVO> tracks;
	
	/*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((confereneCode == null) ? 0 : confereneCode.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConferenceVO other = (ConferenceVO) obj;
        if (confereneCode == null) {
            if (other.confereneCode != null)
                return false;
        } else if (!confereneCode.equals(other.confereneCode))
            return false;
        return true;
    }
}
