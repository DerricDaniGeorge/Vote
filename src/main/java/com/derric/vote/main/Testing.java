package com.derric.vote.main;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class Testing {

	public static void main(String[] args) {
		String seq="AACAAAAACAAAACCAAAAACAAAAACAAAA";
		int length=3;
		int startIndex=0;
		Set<String> set=new TreeSet<>();
		String piece="";
		for(int i=0;i<seq.length();i++) {
			for(int j=startIndex;j<startIndex+length;j++) {
				piece+=seq.charAt(j)+"";
			}
			if(seq.contains(piece)) {
				set.add(piece);
			}
		}
		System.out.println(set);
	}

}
