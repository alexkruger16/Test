package com.fisa.demo.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import lombok.Data;

/**
 * Vo for tracks.
 * @author aguato on <b>2021/01/23.</b>
 *
 */
@Data
public class TrackVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer trackCode;
	private SessionVO sessionMorning;
	private SessionVO sessionAfternoon;
	private LocalDate schedulingDate;
	
	/*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((trackCode == null) ? 0 : trackCode.hashCode());
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
        TrackVO other = (TrackVO) obj;
        if (trackCode == null) {
            if (other.trackCode != null)
                return false;
        } else if (!trackCode.equals(other.trackCode))
            return false;
        return true;
    }

}
