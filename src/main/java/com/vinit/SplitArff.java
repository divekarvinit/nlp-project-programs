package com.vinit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class SplitArff {

	private static String PATHOFFILETOREAD = "D:\\weka_data\\balanced.arff";
	private static String PATHFORCCAT = "D:\\weka_data\\arff_to_files\\CCAT\\";
	private static String PATHFOROTHERS = "D:\\weka_data\\arff_to_files\\OTHERS\\";
	
	public static void main(String[] args) {
		int i = 0;
		int k = 0;
		
		String fileContent = readFileIntoString(PATHOFFILETOREAD);
		String[] filesContents = fileContent.split("',CCAT");
		if(filesContents != null){
			for (String contentToWriteToFile : filesContents) {
				
				// Remove redundant data added by ARFF generator for the first file STARTS
				
				if(i == 0){
					contentToWriteToFile = contentToWriteToFile.substring(contentToWriteToFile.indexOf("@data")  +  5, contentToWriteToFile.length());
				}
				
				// Remove redundant data added by ARFF generator for the first file ENDS
				
				if(contentToWriteToFile.contains("',OTHERS")){
					String[] splitByOthers = contentToWriteToFile.split("',OTHERS");
					for(int j = 0; j < splitByOthers.length; j++){
						if(j < splitByOthers.length - 1){
							// Put in OTHERS
							splitByOthers[j] = splitByOthers[j].substring(1, splitByOthers[j].length());
							writeToFile(PATHFOROTHERS + k + System.nanoTime() + ".txt", splitByOthers[j]);
						} else {
							// put in CCAT
							splitByOthers[j] = splitByOthers[j].substring(1, splitByOthers[j].length());
							writeToFile(PATHFORCCAT + k + System.nanoTime() + ".txt", splitByOthers[j]);
						}
						k++;
					}
				} else {
					// put in CCAT
					contentToWriteToFile = contentToWriteToFile.substring(1, contentToWriteToFile.length());
					writeToFile(PATHFORCCAT + i + System.nanoTime() + ".txt", contentToWriteToFile);
					i++;
				}
			}
		}
	}
	
	public static void writeToFile(String filePath, String fileContents){
		FileOutputStream fos = null;
		File file = new File(filePath);
		try{
			fos = new FileOutputStream(file);
			fos.write(fileContents.getBytes());
			System.out.println("Data written to file: " + file.getName());
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public static String readFileIntoString(String filePath){
		String fileContents = "";
		FileInputStream fis = null;
		BufferedReader br = null;
		try{
			fis = new FileInputStream(new File(filePath));
			br = new BufferedReader(new InputStreamReader(fis));
			String temp = "";
			while((temp = br.readLine()) != null){
				fileContents += temp;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileContents;
	}
}
