/**
 * 
 */
package com.fisa.demo.vo;

import java.time.LocalTime;
import java.util.Collection;

import lombok.Data;

/**
 * Vo for sessions.
 * @author aguato on <b>2021/01/23.</b>
 *
 */
@Data
public class SessionVO {
	private Integer sessionCode;
	private String sessionName;
	private LocalTime startTime;
	private LocalTime endTime;
	private Collection<TalkVO> talkslist; 
	
	/*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sessionCode == null) ? 0 : sessionCode.hashCode());
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
        SessionVO other = (SessionVO) obj;
        if (sessionCode == null) {
            if (other.sessionCode != null)
                return false;
        } else if (!sessionCode.equals(other.sessionCode))
            return false;
        return true;
    }
}
