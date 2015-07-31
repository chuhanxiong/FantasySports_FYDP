package com.fantasysports.util;

import java.util.ArrayList;

public class StringParser {

	public static String listToString(ArrayList<Integer> list) {
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				str += ",";
			}
			str += list.get(i);
		}
		return str;
	}

	public static ArrayList<Integer> stringToList(String str) {
		String[] tokens = str.split(",");
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (String s : tokens) {
			list.add(Integer.parseInt(s));
		}
		return list;
	}
}
