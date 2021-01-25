package com.fisa.demo.util;

import lombok.Getter;

@Getter
public enum EnumTypeSession {

	MORNING("Morning"),AFTERNOON("Afternoon");
	private String typeSession;
	
	private EnumTypeSession(String typeSession) {
		this.typeSession = typeSession;
	}
	
	
}
