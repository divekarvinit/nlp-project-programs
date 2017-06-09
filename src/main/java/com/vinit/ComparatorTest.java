package com.vinit;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ComparatorTest {

	public static void main(String[] args) {
		HashMap<String, Double> testMap = new HashMap<String, Double>();
		testMap.put("a", 98.32);
		testMap.put("c", 32.221);
		testMap.put("z", 0.32);
		testMap.put("s", 32.221);
		testMap.put("x", 42.32);
		
		HashMap sortedMap = getSortedMap(testMap);
    	Iterator it = sortedMap.entrySet().iterator();
    	while(it.hasNext()){
    		Map.Entry entrySet = (Map.Entry) it.next();
    		System.out.print(entrySet.getKey() + " = ");
    		System.out.println(entrySet.getValue());
    	}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static HashMap getSortedMap(HashMap<String, Double> wordsFrequencyMap) {
		List list = new LinkedList(wordsFrequencyMap.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
               return ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
            }
		});

       // Here I am copying the sorted list in HashMap
       // using LinkedHashMap to preserve the insertion order
       HashMap sortedHashMap = new LinkedHashMap();
       for (Iterator it = list.iterator(); it.hasNext();) {
              Map.Entry entry = (Map.Entry) it.next();
              sortedHashMap.put(entry.getKey(), entry.getValue());
       } 
		return sortedHashMap;
	}

}
