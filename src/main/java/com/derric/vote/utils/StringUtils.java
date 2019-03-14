package com.derric.vote.utils;

public class StringUtils {

	public boolean containsOnlyAlphabets(String string) {
		if (string!=null) {
			char[] chars = string.toCharArray();
			for (char c : chars) {
				if (!Character.isLetter(c)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean containsOnlyNumbers(String string) {
		if (string!=null) {
			char[] chars = string.toCharArray();
			for (char c : chars) {
				if (!Character.isDigit(c)) {
					return false;
				}
			}
		}
		return true;
	}
	public boolean containsOnlyLettersOrDigits(String string) {
		if (string!=null) {
			char[] chars = string.toCharArray();
			for (char c : chars) {
				if (!Character.isLetterOrDigit(c)) {
					return false;
				}
			}
		}
		return true;
	}
}
