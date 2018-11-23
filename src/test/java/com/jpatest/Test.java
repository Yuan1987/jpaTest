package com.jpatest;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
		String s = "12,344";
		
		String[] result = split(s,",");
		
		
		for(String re : result) {
			System.out.println("--->"+re);
		}
	}
	
	public static String [] split(String s, String regex) {
		
		List<String> list = new ArrayList<>();
		
		if(s ==null) {
			return new String [] {};
		}
		
		int off = 0;
		int ind = s.indexOf(regex , off);
		
		if(ind == -1) {
			return new String [] {s};
		}
		
		while(ind != -1) {
			list.add(s.substring(off, ind));
			off = ind + 1;
			ind = s.indexOf(regex, off);
		}
		
		list.add(s.substring(off, s.length()));
		
		return list.toArray(new String [] {});
		
	}

}
