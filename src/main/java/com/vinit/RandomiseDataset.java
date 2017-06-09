package com.vinit;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FileUtils;

public class RandomiseDataset {

	public static void main(String[] args) {
		
		copyFilesFromOneFolderToAnother("D:\\weka_data\\classification_data\\balanced\\CCAT", "D:\\weka_data\\data_java_algorithm\\training\\");
		copyFilesFromOneFolderToAnother("D:\\weka_data\\classification_data\\balanced\\OTHERS", "D:\\weka_data\\data_java_algorithm\\training\\");
	}
	
	public static void copyFilesFromOneFolderToAnother(String sourceFolderPath, String destinationFolderPath){
		int i = 0;
		File directory = new File(sourceFolderPath);
		File[] files =  directory.listFiles();
		Random randomNumber = new Random();
		for(i = 0; i < 660; i++){
			File sourceFile = files[randomNumber.nextInt(files.length)];
			try {
				FileUtils.copyFile(sourceFile, new File(destinationFolderPath + sourceFile.getName()));
				System.out.println("File " + sourceFile.getName() + " is copied to "+ destinationFolderPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Total File copied :::::::::::::::::::::::::::: " + i);
	} 
}

