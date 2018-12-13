package com.hxbd.clp.utils.common;

public class RandomUtil {
	private RandomUtil() {

	}
public static String getRandom() {
	String num = "";
	for(int i = 0;i<6;i++) {
		num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
	}
	return num;
}

public static String getRandom15() {
	String num = "";
	for(int i = 0;i<15;i++) {
		num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
	}
	return num;
}
}
