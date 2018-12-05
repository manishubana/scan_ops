package com.selenium.pageFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.selenium.helper.CreateDescription;
import com.selenium.helper.PDFRead;
import com.selenium.util.SeleniumUtil;

public class Remedy_PF {

	WebDriver driver;
	SeleniumUtil selUtil = new SeleniumUtil();
	List<String> list_requestNum = new ArrayList<String>();
	PDFRead pdfRead=new PDFRead();
	String issueDescription;
	CreateDescription description = new CreateDescription();
	public static Map<String, String> map_requestNum = new LinkedHashMap<String, String>();

	public Remedy_PF(WebDriver driver) {
		this.driver=driver;
	}

	//******************************************************************************************************************//
	//login
	@FindBy(how=How.NAME, using = "USER") WebElement txt_username;
	@FindBy(how=How.ID, using = "valuser") WebElement btn_password;
	@FindBy(how=How.NAME, using = "PASSWORD") WebElement txt_password;
	@FindBy(how=How.ID, using = "submitbtn") WebElement btn_submit;
	//******************************************************************************************************************//
	//application menu
	@FindBy(how=How.XPATH, using = "//img[@alt='Show Application List']") WebElement link_applications;
	@FindBy(how=How.XPATH, using = "//span[contains(text(),'Service Request Management')]") WebElement link_serviceRequestManagement;
	@FindBy(how=How.XPATH, using = "//span[contains(text(),'Request Entry')]") WebElement link_requestEntry;
	//Request Service Page
	@FindBy(how=How.XPATH, using = "//iframe[starts-with(@id,'VF304274040_')]") WebElement frame_availableRequests;
	@FindBy(how=How.XPATH, using ="//div[contains(text(),'Application standard maintenance request')]") WebElement link_applicationStandardMaintenanceRequest; 
	@FindBy(how=How.ID, using ="btn_request_now") WebElement btn_requestNow;
	//******************************************************************************************************************//
	//Instructions Pop up
	@FindBy(how=How.XPATH,using="//iframe[starts-with(@src,'/arsys/forms/onbmc-s/')]") WebElement frame_instructions;
	@FindBy(how=How.ID, using = "arid_WIN_0_300070001") WebElement txtArea_appName;
	@FindBy(how=How.ID, using = "arid_WIN_0_303408700") WebElement txtArea_activity;
	@FindBy(how=How.ID, using = "arid_WIN_0_303408900") WebElement txtArea_description;
	@FindBy(how=How.ID, using = "arid_WIN_0_303408800") WebElement txtArea_activityType;
	@FindBy(how=How.XPATH, using = "//div[@class='f1'][contains(text(),'Submit')]") WebElement btn_submitWO;
	//******************************************************************************************************************//
	//confirmation pop up
	@FindBy(how=How.XPATH,using="//td[@class='DIVPopupBodyNoBorder DIVPopupBodyBorder']//iframe[1]") WebElement frame_confirmationPopUp;
	@FindBy(how=How.XPATH, using="//div[@id='PopupMsgFooter']//a[contains(text(),'OK')]") WebElement btn_OK;
	@FindBy(how=How.ID,using="PopupMsgBox") WebElement div_confirmationMsg;
	//******************************************************************************************************************//
	//Home Page
	@FindBy(how=How.XPATH,using="//img[@artxt='Home']") WebElement img_Home;
	@FindBy(how=How.XPATH,using="//div[contains(text(),'IT Home Page')]") WebElement link_IT_HomePage;
	@FindBy(how=How.XPATH,using="//textarea[@id='arid_WIN_0_302258625']") WebElement txt_search;
	@FindBy(how=How.XPATH,using="//img[@alt='Global Search']") WebElement img_globalSearch;
	//******************************************************************************************************************//
	//global Search Results
	@FindBy(how=How.XPATH, using = "//table[@class='BaseTable']//td[1]//span[contains(text(),'WO')]") WebElement WO_Number;
	@FindBy(how=How.XPATH, using= "//a[@ardbn='z3Btn_WindowsClose']") WebElement link_closeSearchWindow;
	@FindBy(how=How.XPATH, using="//a[@id='WIN_3_302259032']//img[@title='Search']") WebElement img_resultsSearch;
	//******************************************************************************************************************//
	//logout
	@FindBy(how=How.XPATH,using="//a[@id='WIN_0_300000044']//div[contains(text(),'Logout')]") WebElement link_Logout;

	//******************************************************************************************************************//


	//login to the application
	public void login(String URL_Remedy, String username, String password) throws InterruptedException{

		driver.get(URL_Remedy);
		txt_username.sendKeys(username);
		btn_password.click();
		txt_password.sendKeys(password);
		Thread.sleep(1000);
		txt_password.sendKeys(Keys.TAB);
		txt_password.sendKeys(Keys.ENTER);
	}


	public void goToWOWindow() throws InterruptedException{

		//expand applications menu
		link_applications.click();
		//go to service request management->Request Entry
		selUtil.doMouseHover(driver, link_serviceRequestManagement, link_requestEntry);
		
	}

	public void fillWODetails(String applicationName, String issueDescription, String issueType) throws InterruptedException, InvalidPasswordException, IOException{

		//switch to iFrame
		driver.switchTo().frame(frame_availableRequests);
		
		//click Application Standard Maintenance Request
		link_applicationStandardMaintenanceRequest.click();
		
		//click Request Now Button
		btn_requestNow.click();
		
		//coming back to the default content from frame
		driver.switchTo().defaultContent();

		//switch to frame on instructions pop up
		driver.switchTo().frame(frame_instructions);

		//selecting the application name
		txtArea_appName.click();
		txtArea_appName.sendKeys(applicationName);

		//selecting the activity - Information
		txtArea_activity.click();
		Thread.sleep(1000);
		for(int i=0;i<4;i++){
			txtArea_activity.sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(1000);
		}

		txtArea_activity.sendKeys(Keys.ENTER);
		Thread.sleep(1000);

		//selecting the activity type - Application
		txtArea_activityType.click();
		Thread.sleep(1000);
		txtArea_activityType.sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(1000);
		txtArea_activityType.sendKeys(Keys.ENTER);
		Thread.sleep(1000);

		//activity description
		txtArea_description.click();
		txtArea_description.sendKeys(issueDescription);

		//click WO Submit button
		btn_submitWO.click();

		Thread.sleep(2000);

		//switch to iframe on the pop up 
		driver.switchTo().frame(frame_confirmationPopUp);

		//adding confirmation message to the list after splitting and fetching the REQ Number
		map_requestNum.put(issueType, div_confirmationMsg.getText().split("Your Request ")[1].split(" has been submitted.")[0].toString());
		
		//System.out.println("REQ NUmber - " + list_requestNum.get(0).toString());

		btn_OK.click();
		Thread.sleep(2000);
		
	}


	public void navigateToHomePage() throws InterruptedException{
		//click on the Home page image
		img_Home.click();
		//click on the IT Home Page link in the Home drop down list
		link_IT_HomePage.click();
		Thread.sleep(2000);
	}


	public void fetchWONumber(String issueType) throws InterruptedException{

		//search for the request
		//System.out.println("search value-" + map_requestNum.get(issueType) );
		txt_search.clear();
		txt_search.sendKeys(map_requestNum.get(issueType));
		img_globalSearch.click();
		
		while(WO_Number.getText()==null){
			System.out.println("inside while loop");
			img_resultsSearch.click();
			Thread.sleep(2000);
		}

		System.out.println("REQ NUmber - " + map_requestNum.get(issueType));
		System.out.println("WO  Number - " + WO_Number.getText());
		System.out.println("----------------");

		//closing search window
		link_closeSearchWindow.click();
	}

	public void logout(){
		//click logout
		link_Logout.click();
	}


}
