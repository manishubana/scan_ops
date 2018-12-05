package com.selenium.automationScripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
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
import com.selenium.pageFactory.Connect_PF;
import com.selenium.util.SeleniumUtil;

public class Connect_UploadAndShare implements Constants {

	WebDriver driver;
	SeleniumUtil selUtil;

	Connect_PF connect_PF;
	Properties prop = new Properties();
	FileInputStream fileInputStream=null;
	public static Map<String, String> linkToScanFiles =  new LinkedHashMap<String, String>();
	CreateDescription createDescription;

	static int i = 0;
	static int invocationCount=0;

	@BeforeTest
	public void beforeTest() throws IOException{

		selUtil = new SeleniumUtil();
		fileInputStream = new FileInputStream(System.getProperty("user.dir")+"\\test-data\\Data.properties");
		prop.load(fileInputStream);
	}


	@BeforeMethod
	public void beforeMethod() throws MalformedURLException{

		driver = selUtil.setBrowser(browserName);
		connect_PF = PageFactory.initElements(driver, Connect_PF.class);
		connect_PF.setProperties(prop);
		//setting implicit wait
		selUtil.setImplicitWaitTime(driver, implicitWaitTime);
		selUtil.maximizeBrowser(driver);

	}

	@AfterMethod
	public void afterMethod(){
		//increment the invocation count
		invocationCount++;
	}

	@Test(priority=0, invocationCount=1, enabled=true)
	public void uploadAndShare() throws Exception {

		try{

			connect_PF.openApp();
			for(int i=1;i<=3;i++){
				Thread.sleep(3000);
				connect_PF.setFileName(prop.getProperty("file_0"+i));
				connect_PF.uploadFile();
				//connect_PF.shareFile();
			}
			connect_PF.printScanFileLinks();
			driver.close();

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
