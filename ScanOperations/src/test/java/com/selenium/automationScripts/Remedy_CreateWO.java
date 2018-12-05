package com.selenium.automationScripts;


import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.selenium.constants.Constants;
import com.selenium.helper.CreateDescription;
import com.selenium.helper.PDFRead;
import com.selenium.pageFactory.Remedy_PF;
import com.selenium.util.SeleniumUtil;

public class Remedy_CreateWO implements Constants {

	WebDriver driver;
	SeleniumUtil selUtil;
	Remedy_PF itsm_PF;
	Properties prop = new Properties();
	FileReader fileReader = null;
	CreateDescription description;
	PDFRead pdfRead;
	String issueDescription;

	Iterator<Map.Entry<String, String>> iterator;
	
	static int i = 0;
	static int invocationCount=0;
	
	long startTime=0;
	long endTime=0;

	
	@BeforeTest
	public void beforeTest() throws IOException{
		startTime=System.currentTimeMillis();
		selUtil = new SeleniumUtil();
		description = new CreateDescription();
		fileReader = new FileReader(System.getProperty("user.dir")+"\\test-data\\Data.properties");
		prop.load(fileReader);

	}


	@BeforeMethod
	public void beforeMethod() throws MalformedURLException{

		driver = selUtil.setBrowser(browserName);
		itsm_PF = PageFactory.initElements(driver, Remedy_PF.class);
		//setting implicit wait
		selUtil.setImplicitWaitTime(driver, implicitWaitTime);
		//selUtil.setBrowser("Chrome");
		//wwims_01_login.login(driver);
		selUtil.maximizeBrowser(driver);
		pdfRead = new PDFRead();
	}

	@AfterMethod
	public void afterMethod(){
		//increment the invocation count
		invocationCount++;
	}

	@Test()
	public void test_01() throws IOException, InterruptedException{

		try{

			//open Remedy application and login
			itsm_PF.login(prop.getProperty("URL_Remedy"),prop.getProperty("username"), prop.getProperty("password"));
			// go to WO window
			itsm_PF.goToWOWindow();

			//fill WO details
			pdfRead.readPDF();
			iterator = PDFRead.map_issueCount.entrySet().iterator();
			while(iterator.hasNext()){

				Map.Entry<String, String> entry = iterator.next();
				//System.out.println("GET KEY ->"+entry.getKey());
				if(entry.getKey()!=null){
					issueDescription=description.writeDescription(entry.getKey());
					itsm_PF.fillWODetails(prop.getProperty("applicationName"),issueDescription, entry.getKey());
				}
			}

			Thread.sleep(5000);
			//navigate to Home Page
			itsm_PF.navigateToHomePage();

			
			//search and fetch WO number
			iterator = Remedy_PF.map_requestNum.entrySet().iterator();
			while(iterator.hasNext()){

				Map.Entry<String, String> entry = iterator.next();
				System.out.println("----------------Search and Fetch WO------------------");
				System.out.println("Issue Type "+entry.getKey());
				if(entry.getKey()!=null){
					itsm_PF.fetchWONumber(entry.getKey());
				}
			}

			//logout
			itsm_PF.logout();
			endTime=System.currentTimeMillis();
			System.out.println("Time Taken to Create WOs:"+((endTime-startTime)/1000)+" seconds");

		}
		catch(org.openqa.selenium.NoSuchElementException noSuchElementException){
			System.out.println(noSuchElementException.getStackTrace());
			throw noSuchElementException;

		}
		catch(Exception exception){
			throw exception;

		}
		finally{
			driver.quit();
		}

	}
}
