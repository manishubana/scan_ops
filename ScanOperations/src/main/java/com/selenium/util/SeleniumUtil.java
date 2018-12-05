package com.selenium.util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.internal.Version;

import com.selenium.constants.Constants;

public class SeleniumUtil implements Constants {


	//**** Wait Methods ****//


	/**
	 * Method to set the implicit wait time for all the elements in a test script
	 *
	 * @param driver   : Target Webdriver
	 * @param waitTime : Waiting time for an element in seconds
	 */

	public void setImplicitWaitTime(WebDriver driver, int waitTime){
		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}

	//used to check if the element is present on the DOM of a page and visible. 
	//Visibility means that the element is not just displayed but also should also has a 
	//height and width that is greater than 0
	public void visibilityOfElementLocated(WebDriver driver, By by, int waitTime){
		WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));	
	}
	//used for checking that an element is either invisible or not present on the DOM
	public void invisibilityOfElementLocated(WebDriver driver, By by, int waitTime){
		WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
		webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));	
	}
	//use it when - when we don't care about the element visible or not, we just need to know if it's on the page
	public void prescenceOfElementLocated(WebDriver driver, By by, int waitTime){
		WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	//used to check if an element is visible and enabled such that we can click on the element
	public void elementToBeClickable(WebDriver driver, By by, int waitTime){
		WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
		webDriverWait.until(ExpectedConditions.elementToBeClickable(by));
	}

	//**** URL & Browser ****//
	
	/**
	 * Method to open URL
	 * @param driver   : WebDriver reference
	 * @param          : URL to open
	 */
	public void openURL(WebDriver driver, String URL){
		driver.get(URL);
		driver.manage().window().maximize();
	}
	
	/**
	 * Method to set the browser property and create browser object
	 * @param driver   : WebDriver reference
	 */
	public WebDriver setBrowser(String browserName){
		if(browserName=="Firefox"){
			System.setProperty(firefoxDriver, firefoxDriverPath);
			return new FirefoxDriver();
		}
		else if(browserName=="Chrome"){
			System.setProperty(chromeDriver, chromeDriverPath);
			//ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.addArguments("--headless");
			return new ChromeDriver();
		}
		else if(browserName=="HtmlUnitDriver"){
			return new HtmlUnitDriver(true);
		}
			return null;
	}

	/**
	 * Method to maximize the browser window
	 * @param driver   : WebDriver reference
	 */
	public void maximizeBrowser(WebDriver driver){
		driver.manage().window().maximize();
	}

	//**** Mouse Operations ****//
	
	/**
	 * Method to hover the mouse on a link only (does not click a submenu under the main menu)
	 * @param driver   : WebDriver reference
	 * @param mainMenu : reference to the main menu link 
	 */
	public void doMouseHover(WebDriver driver, WebElement mainMenu) throws InterruptedException{
		Actions actions = new Actions(driver);
		actions.moveToElement(mainMenu).perform();
		Thread.sleep(3000);
	}

	/**
	 * Method to hover the mouse on a link and click a submenu under the main menu
	 * @param driver   : WebDriver reference
	 * @param mainMenu : reference to the main menu link 
	 * @param subMenu  : reference to the sub menu link under main menu
	 */
	public void doMouseHover(WebDriver driver, WebElement mainMenu , WebElement subMenu) throws InterruptedException{
		Actions actions = new Actions(driver);
		actions.moveToElement(mainMenu).perform();
		Thread.sleep(2000);
		actions.moveToElement(subMenu).click().perform();
	}

	//method to left click from mosue
	public void rightClickMouse(WebDriver driver, WebElement element){
		
		Actions actions = new Actions(driver);
		actions.contextClick(element).build().perform();
		
		
	}


	//**** Select Operations ****//
	
	//method to select vale from drop down
	/**
	 * Method to scroll down the screen
	 * @param driver  : WebDriver reference
	 * @param element : reference to the element by where the screen needs to be scrolled 
	 */
	public void selectDropDownByValue(WebDriver driver,WebElement element, String valueToSelect){
		new Select(element).selectByValue(valueToSelect);
	}

	//method to select vale from drop down
	public void selectDropDownByVisibleText(WebDriver driver, WebElement element, String valueToSelect){
		new Select(element).selectByVisibleText(valueToSelect);		
	}

	//method to select vale from drop down
	public void selectDropDownByIndex(WebDriver driver,WebElement element, int index){
		new Select(element).selectByIndex(index);		
	}

	//**** Table Operations ****//
	public void table(WebDriver driver, String baseColName, String colNameToSearch){

	}

	//**** Frame & WINDDOW ****//

	public void switchToFrame(WebDriver driver){

	}

	//**** OTHER METHODS ****//

	/**
	 * Method to scroll down the screen
	 * @param driver  : WebDriver reference
	 * @param element : reference to the element by where the screen needs to be scrolled 
	 */
	public void scroll(WebDriver driver, WebElement element){

		element.sendKeys(Keys.PAGE_DOWN);

		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	//metod to click an image
	public void clickImage(){

	}

	/**
	 * Method to capture screen shot
	 * @param driver   			  : WebDriver reference
	 * @param destinationFilePath : path where screen shots will be saved
	 */
	public void takeScreenShot(WebDriver driver, String destinationFilePath){

		try {
			File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File(destinationFilePath));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//**** Table ****//

	/**
	 * Method to find the row count of a table
	 * @param element   : table element
	 */
	public int findTableRowCount(WebElement element){
		//find elements by tag name tr
		return element.findElements(By.tagName("tr")).size();

	}


	/**
	 * Method to find the column count of a table
	 * @param element   : table element
	 */
	public int findTableColCount(WebElement element){
		//find elements by tag name th
		return element.findElements(By.tagName("th")).size();
	}


	/**
	 * Method to find the column count of a table
	 * @param driver   : WebDriver reference
	 * @param element  : element whose tool tip need to be checked
	 */
	public void tooltip(WebDriver driver, WebElement element) throws InterruptedException{
		
		Actions actions =  new Actions(driver);
		actions.moveToElement(element).perform();
		Thread.sleep(3000);
	
	}

	
	public void dragAndDrop(WebDriver driver, WebElement source, WebElement target){
		
		Actions actions = new Actions(driver);
		actions.dragAndDrop(source, target);	
		
	}






}
