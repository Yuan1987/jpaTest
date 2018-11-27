	package com.jpatest;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
		String s = "12,344,233,,333s";
		
		String[] result = split(s,",");
		
		
		for(String re : result) {
			System.out.println("--->"+re);
		}
	}
	
	public static String [] split(String s, String regex) {
		
		List<String> list = new ArrayList<>();
		
		char[] charArray = s.toCharArray();
		
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < charArray.length;i++) {
			
			if((charArray[i]+"").equals(regex)) {
				
				if(i<charArray.length-1 && regex.equals((charArray[i+1]+""))) {
					continue;
				}
				
				list.add(sb.toString());
				sb = new StringBuffer();
			}else {
				sb.append(charArray[i]);
			}
		}
		
		list.add(sb.toString());
		
		return list.toArray(new String [] {});
	}

}
