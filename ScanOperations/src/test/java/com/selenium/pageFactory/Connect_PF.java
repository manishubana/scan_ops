package com.selenium.pageFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.selenium.automationScripts.Connect_UploadAndShare;
import com.selenium.util.SeleniumUtil;

public class Connect_PF {

	WebDriver driver;
	SeleniumUtil selUtil = new SeleniumUtil();
	Properties prop;
	FileInputStream fileInputStream=null;
	String fileName;


	public Connect_PF(WebDriver driver) {
		this.driver=driver;
	}

	public void setProperties(Properties prop){
		this.prop = prop;
	}

	public void setFileName(String fileName){
		this.fileName=fileName;
	}



	//folder
	@FindBy(how=How.XPATH, using= "//a[@class='ms-listlink ms-draggable'][contains(text(),'2018 Security Scan Reports_Audits_and_Results Fold')]") WebElement folderName;
	//file upload
	@FindBy(how=How.ID, using = "QCB1_Button2") WebElement link_upload;
	@FindBy(how=How.XPATH, using = "//iframe[starts-with(@id,'DlgFrame')]") WebElement iframe_document;
	@FindBy(how=How.XPATH, using ="//input[@title='Choose a file']") WebElement txt_ChooseAFile;
	@FindBy(how=How.XPATH, using ="//input[@value='OK']") WebElement btn_OK;
	@FindBy(how=How.XPATH, using ="//a[@title='Close dialog']") WebElement link_closeDialogue;

	//file sharing
	@FindBy(how=How.CLASS_NAME, using= "ms-list-itemLink  ") WebElement document;
	@FindBy(how=How.XPATH, using = "//li[@aria-label='Share']") WebElement link_share;
	@FindBy(how=How.ID,using="peoplePicker_TopSpan_EditorInput") WebElement txt_email;
	@FindBy(how=How.ID,using="DdlSimplifiedRoles") WebElement select_PermissionLevel;
	@FindBy(how=How.ID,using="btnShare") WebElement btn_share;
	@FindBy(how=How.ID,using="lnkShrDlg") WebElement link_invitePeople;

	//get a link
	@FindBy(how=How.ID, using = "lnkGetLnkDlg") WebElement link_getALink;
	@FindBy(how=How.ID,using = "js-manageLink-linkBox" ) WebElement txt_link;
	@FindBy(how=How.ID, using = "js-manageLink-linkTypeDropdown") WebElement select_LinkTypeDropDown;
	@FindBy(how=How.XPATH, using = "//span[contains(text(),'Edit link - Cummins account required')]") WebElement link_EditLink;


	public void openApp() throws InterruptedException, IOException{
		//open Cummins connect
		driver.get(prop.getProperty("URL_Connect"));
		System.out.println("Debug->Opening URL");
		//Thread.sleep(5000);
		//Runtime.getRuntime().exec(prop.getProperty("popHandler_AutoIT"));
		Thread.sleep(2500);
		//driver.switchTo().parentFrame().switchTo().activeElement().sendKeys("ced\\kv075");
	}
	
	public void uploadFile() throws IOException, InterruptedException{

		selUtil.visibilityOfElementLocated(driver,By.id("QCB1_Button2"), 120);
		link_upload.click();
		driver.switchTo().frame(iframe_document);
		//append folder name and extension to file name
		txt_ChooseAFile.sendKeys(prop.getProperty("folderName")+fileName+".pdf");
		btn_OK.click();
		System.out.println("Debug->Uploading File");
		Thread.sleep(500);
		//switching back to default content
		driver.switchTo().defaultContent();
		//close the dialogue box		
		link_closeDialogue.click();

	}

	public void shareFile() throws InterruptedException{
		//share the file
		System.out.println("Debug->File Sharing");
		selUtil.rightClickMouse(driver, document);
		Thread.sleep(800);
		link_share.click();

		/*//share 
		int count=1;
		while(prop.getProperty("emailId_"+count)!=null){
			txt_email.sendKeys(prop.getProperty("emailId_"+count));
			Thread.sleep(800);
			txt_email.sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(800);
			txt_email.sendKeys(Keys.ENTER);
			Thread.sleep(800);
			count++;
		}

		//select permission
		selUtil.selectDropDownByVisibleText(driver, select_PermissionLevel, "Can view");*/

		//click get a link
		System.out.println("Debug->Fetching Link");
		link_getALink.click();
		//selecting View a Link from drop down
		select_LinkTypeDropDown.sendKeys(Keys.ENTER);
		Thread.sleep(800);
		select_LinkTypeDropDown.sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(800);
		select_LinkTypeDropDown.sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(800);
		select_LinkTypeDropDown.sendKeys(Keys.ENTER);
		Thread.sleep(800);
		//fetching value of link and storing in map
		if(fileName.contains("Executive"))
			Connect_UploadAndShare.linkToScanFiles.put("ES", txt_link.getAttribute("value"));
		else if(fileName.contains("Detailed"))
			Connect_UploadAndShare.linkToScanFiles.put("DR", txt_link.getAttribute("value"));
		else
			Connect_UploadAndShare.linkToScanFiles.put("OR", txt_link.getAttribute("value"));

		//share
		link_invitePeople.click();
		btn_share.click();
		System.out.println("Debug->File Shared");

		Thread.sleep(500);
	}

	public void printScanFileLinks(){
		System.out.println("----Link To AppScan Reports---");
		System.out.println("Executive Summary ->"+Connect_UploadAndShare.linkToScanFiles.get("ES"));
		System.out.println("Detailed Report ->"+Connect_UploadAndShare.linkToScanFiles.get("DR"));
		System.out.println("OWASP Report ->"+Connect_UploadAndShare.linkToScanFiles.get("OR"));
	}

}
