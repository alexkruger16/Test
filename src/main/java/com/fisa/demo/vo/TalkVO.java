/**
 * 
 */
package com.fisa.demo.vo;

import java.io.Serializable;
import java.time.LocalTime;

import lombok.Data;

/**
 * Vo for talks.
 * @author aguato on <b>2021/01/23.</b>
 *
 */
@Data
public class TalkVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer talkCode;
	private String talkName;
	private Integer timeDuration;
	private LocalTime startTime;
	private LocalTime endTime;
	private Boolean scheduled;
	
	public TalkVO() {
		
	}
	
	public TalkVO(Integer talkCode, String talkName, Integer timeDuration) {
		this.talkCode = talkCode;
		this.talkName = talkName;
		this.timeDuration = timeDuration;
	}
	
	/*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((talkCode == null) ? 0 : talkCode.hashCode());
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
        TalkVO other = (TalkVO) obj;
        if (talkCode == null) {
            if (other.talkCode != null)
                return false;
        } else if (!talkCode.equals(other.talkCode))
            return false;
        return true;
    }

}
