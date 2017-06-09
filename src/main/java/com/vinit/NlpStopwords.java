package com.vinit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.monitorjbl.xlsx.StreamingReader;

public class NlpStopwords {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Workbook workbook = null;
		try {
			workbook = getWorkbook();  
	        Sheet datatypeSheet = workbook.getSheetAt(1);
	        double sumOfValuesOfColumn = 0.0;
	    	int counterForAverage = 0;
	    	double columnAverage = 0.0;
	    	HashMap<String, Double> wordsFrequencyMap = new HashMap<String, Double>();
	    	
	    	// get max number of columns
	        
//	        for(int i = 0; i < datatypeSheet.getPhysicalNumberOfRows() ; i++){
//	        	tmp = datatypeSheet.getRow(i).getPhysicalNumberOfCells();
//	        	if(tmp > columns) {
//	        		columns = tmp;
//	        	} 
//	        }
	    	
	    	// get max number of columns
	    	
//	    	for(Row row : datatypeSheet){
//	    		int cellCount = 0;
//	    		for(@SuppressWarnings("unused") Cell cell : row){
//	    			cellCount++;
//	    		}
//	    		if(cellCount > columns) {
//	    			columns = cellCount;
//	    		}
//	    	}
	        
	        // get average of column values and store in a map
	        
	        /*for(int i = 2; i < columns; i++){
	        	sumOfValuesOfColumn = 0.0;
	        	counterForAverage = 0;
	        	for(int j = 1; j < datatypeSheet.getPhysicalNumberOfRows(); j++) {
	        		double cellValue = 0.0;
	        		if((cellValue = datatypeSheet.getRow(j).getCell(i).getNumericCellValue()) != 0) {
	        			sumOfValuesOfColumn += cellValue;
	        			counterForAverage ++;
	        		}
	        	}
	        	columnAverage = sumOfValuesOfColumn / counterForAverage;
	        	wordsFrequencyMap.put(firstRow.getCell(i).getStringCellValue() , columnAverage);
	        }*/
	    	
//	    	Workbook newWorkbook = getWorkbook();
//	    	datatypeSheet = newWorkbook.getSheetAt(1);
	    	
	    	for(int i = 2; i < 1422; i++){
	    		System.out.println("Calculating for column "+ (i-2) + " " + new Date()); 
	    		for(Row row : datatypeSheet) {
	    			if(row.getRowNum() > 0){
	    				for(Cell cell : row) {
	    					if(cell.getColumnIndex() == i) {
	    						double cellValue = 0.0;
	    		        		if((cellValue = cell.getNumericCellValue()) != 0) {
	    		        			sumOfValuesOfColumn += cellValue;
	    		        			counterForAverage ++;
	    		        		}
	    		        		break;
	    					}
		    			}
	    			}
	    		}
	    		// Column Average
	    		
	    		columnAverage = sumOfValuesOfColumn / counterForAverage;
	    		
	    		Workbook newWorkbook2 = getWorkbook();
	    		
	    		datatypeSheet = newWorkbook2.getSheetAt(1);
	    		
	    		// Get name of column
	    		String rowName = "";
	    		for(Row row : datatypeSheet){
	    			for(Cell cell : row){
	    				if(cell.getColumnIndex() == i){
	    					rowName = cell.getStringCellValue();
	    					break;
	    				}
	    			}
	    			break;
	    		}
	    		
	    		wordsFrequencyMap.put(rowName, columnAverage);
	    		
	    		sumOfValuesOfColumn = 0.0;
	    		columnAverage = 0.0;
	    		counterForAverage = 0;
	    	}
	    	
	    	System.out.println("----------------------------------------------------------------------");
	    	System.out.println("Sorting the map");
	    	
	    	// Sort the map
	    	
	    	HashMap sortedMap = getSortedMap(wordsFrequencyMap);
	    	Iterator it = sortedMap.entrySet().iterator();
	    	while(it.hasNext()){
	    		Map.Entry entrySet = (Map.Entry) it.next();
	    		System.out.print(entrySet.getKey() + " = ");
	    		System.out.println(entrySet.getValue());
	    	}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	public static Workbook getWorkbook(){
		FileInputStream fs = null;
		Workbook workbook = null;
		try {
			fs = new FileInputStream(new File("D:\\Study Stuff\\AUT\\NLP\\weka_output.xlsx"));
			workbook = StreamingReader.builder()
		            .rowCacheSize(100)    
		            .bufferSize(4096)     
		            .open(fs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return workbook;
	}
}
