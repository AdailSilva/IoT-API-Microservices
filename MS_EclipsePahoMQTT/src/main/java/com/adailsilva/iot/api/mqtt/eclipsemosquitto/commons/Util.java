package com.adailsilva.iot.api.mqtt.eclipsemosquitto.commons;

public class Util {

	public static String removeDoubleQuotes(String input) {

		StringBuilder sb = new StringBuilder();

		char[] tab = input.toCharArray();
		for (char current : tab) {
			if (current != '"')
				sb.append(current);
		}

		return sb.toString();
	}

}
