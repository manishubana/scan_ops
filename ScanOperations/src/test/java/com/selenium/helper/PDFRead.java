package com.selenium.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class PDFRead {
	
	Properties prop;
	FileInputStream fileInputStream=null;
	
	private PDFTextStripperByArea area ;
	private PDDocument pdfDoc;
	private PDFTextStripper pdfTextStripper;

	private String pdfInString;
	private String issueCount;
	private String issueType;
	
	public static String[] arr_issueType;
	
	public static Map<String, String> map_issueCount;
	public static Map<String, Integer> map_issueTypeCount;

	
	Pattern pattern;
	Matcher matcher;


	public void readPDF() throws InvalidPasswordException, IOException{

		prop=new Properties();
		map_issueCount = new HashMap<String, String>();
		map_issueTypeCount = new HashMap<String, Integer>();
		
		fileInputStream = new FileInputStream(System.getProperty("user.dir")+"//test-data//Data.properties");
		prop.load(fileInputStream);

		pdfDoc = PDDocument.load(new File(prop.getProperty("folderName")+prop.getProperty("file_01")+".pdf"));
		area = new PDFTextStripperByArea();
		area.setSortByPosition(true);
		
		pdfTextStripper = new PDFTextStripper();
		pdfInString= pdfTextStripper.getText(pdfDoc);

		//fetching issue count from PDF file (From Introduction Section of AppScan Report)
		issueCount = 
				pdfInString.split("This report contains the results of a web application security scan performed "
						+ "by IBM Security AppScan Standard")[1]
								.split("Scan file name:")[0]
										.split("General Information")[0];

		
		
		
		//fetching number of high, medium and low issues
		if(issueCount.contains("High severity issues: ")){
			map_issueCount.put("HIGH", issueCount.split("High severity issues: ")[1].split("Medium severity issues: ")[0].trim());
		}
		if(issueCount.contains("Medium severity issues: ")){
			map_issueCount.put("MEDIUM", issueCount.split("Medium severity issues: ")[1].split("Low severity issues: ")[0].trim());
		}
		if(issueCount.contains("Low severity issues: ")){
			map_issueCount.put("LOW", issueCount.split("Low severity issues: ")[1].split("Informational severity issues: ")[0].trim());
		}

		//fetching issue type (H/M/L) from PDF file (From Summary Section of AppScan Report)
		issueType = pdfInString.split("Issue Type Number of Issues")[1].split("Vulnerable URLs")[0].split("I ")[0];
		
		map_issueTypeCount.put("HIGH", Integer.valueOf(issueTypeCount("H",issueType)));
		map_issueTypeCount.put("MEDIUM", Integer.valueOf(issueTypeCount("M",issueType)));
		map_issueTypeCount.put("LOW", Integer.valueOf(issueTypeCount("L",issueType)));

		arr_issueType = issueType.trim().split("\n");
		
		System.out.println("------------------------------");
		System.out.println("Count of Issue H - "+map_issueCount.get("HIGH"));
		System.out.println("Count of Issue M - "+map_issueCount.get("MEDIUM"));
		System.out.println("Count of Issue L - "+map_issueCount.get("LOW"));
		System.out.println("-------------------------------");
		System.out.println("Number of High Issues Type  - " + map_issueTypeCount.get("HIGH"));
		System.out.println("Number of Medium Issues Type - " + map_issueTypeCount.get("MEDIUM"));
		System.out.println("Number of Low Issues Type   - " + map_issueTypeCount.get("LOW"));
		System.out.println("-------------------------------");
		
		//System.out.println(pdfInString);
		//System.out.println("-------------------------------");
		System.out.println(issueCount);
		//System.out.println("-------------------------------");
		System.out.println(issueType);

	}

	public String issueTypeCount(String issueCategory, String issueType){

		int count=0;
		String arr[]= issueType.split("[0-9]");
		
		for(int i=0;i<arr.length;i++){
			//System.out.print("arr value"+arr[i]);
			if(arr[i].trim().startsWith(issueCategory)){
				count++;
			}
		}
		//System.out.println("count->"+count);
		return Integer.toString(count);
	}

}
