package com.selenium.helper;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.selenium.automationScripts.Connect_UploadAndShare;

public class CreateDescription {

	Properties prop = new Properties();
	FileReader fileReader = null;
	PDFRead pdfRead=new PDFRead();
	
	public String writeDescription(String issueType) throws IOException{
		
		int high_end=PDFRead.map_issueTypeCount.get("HIGH");
		int medium_end=PDFRead.map_issueTypeCount.get("HIGH")+PDFRead.map_issueTypeCount.get("MEDIUM");
		int low_end=PDFRead.map_issueTypeCount.get("MEDIUM")+PDFRead.map_issueTypeCount.get("LOW");
		
		fileReader = new FileReader(System.getProperty("user.dir")+"//test-data//Data.properties");
		prop.load(fileReader);

		String commonString=

				"\nAdmin Links to reports below:\n"+ 
						"\nExecutive Summary Report: "+ prop.getProperty("executiveSummary")+ 
						"\n\nOWASP Report: "+ prop.getProperty("owaspReport") +
						"\n\nDetailed Report:  "+ prop.getProperty("detailedReport") +

						"\n\nIf there is any issue accessing reports, please reach out to "+prop.getProperty("wwid1")+" or "+prop.getProperty("wwid2")+"."+ 

					"\n\nThanks,"+ 
					"\nGWC QA Team";
		
		
		/*String commonString=

				"\nAdmin Links to reports below:\n"+ 
						"\nExecutive Summary Report: "+ Connect_UploadAndShare.linkToScanFiles.get("ES")+ 
						"\n\nOWASP Report: "+ Connect_UploadAndShare.linkToScanFiles.get("OR") +
						"\n\nDetailed Report:  "+ Connect_UploadAndShare.linkToScanFiles.get("DR") +

						"\n\nIf there is any issue accessing reports, please reach out to "+prop.getProperty("wwid1")+" or "+prop.getProperty("wwid2")+"."+ 

					"\n\nThanks,"+ 
					"\nGWC QA Team";*/


		if(issueType.equalsIgnoreCase("HIGH")){
			String high = 
					"Hi Team,\n\n"+

					"We have completed the security audit for "+ prop.getProperty("applicationName") +" and shared the reports over mail.\nThis WO is for high priority issues found during the scan and the number of issues found is "+ PDFRead.map_issueCount.get("HIGH")+". \nPlease refer to the reports for further details. The SLA for the resolution is 30 days. \nPlease do not cancel this WO without prior information to the GWC QA Team as this will be tracked by GWC QA Leaders/Stakeholders." +

					"\n\nIssue details:\n\n";

			for(int i=0;i<high_end;i++){
				high=high+PDFRead.arr_issueType[i]+"\n";
			}
			return high+commonString;
		}
		else if(issueType.equalsIgnoreCase("MEDIUM")){

			String medium = 
					"Hi Team,\n\n"+

					"We have completed the security audit for "+ prop.getProperty("applicationName") +" and shared the reports over mail.\nThis WO is for medium priority issues found during the scan and the number of issues found is "+ PDFRead.map_issueCount.get("MEDIUM")+". \nPlease refer to the reports for further details. The SLA for the resolution is 60 days. \nPlease do not cancel this WO without prior information to the GWC QA Team as this will be tracked by GWC QA Leaders/Stakeholders." +

					"\n\nIssue details:\n\n";

			for(int i=high_end;i<medium_end;i++){
				medium=medium+PDFRead.arr_issueType[i]+"\n";
			}

			return medium+commonString;
		}
		else if(issueType.equalsIgnoreCase("LOW")){
			String low = 
					"Hi Team,\n\n"+

					"We have completed the security audit for "+ prop.getProperty("applicationName") +" and shared the reports over mail.\nThis WO is for low priority issues found during the scan and the number of issues found is "+ PDFRead.map_issueCount.get("LOW")+". \nPlease refer to the reports for further details. The SLA for the resolution is 90 days. \nPlease do not cancel this WO without prior information to the GWC QA Team as this will be tracked by GWC QA Leaders/Stakeholders." +

					"\n\nIssue details:\n\n";

			for(int i=medium_end;i<low_end;i++){
				low=low+PDFRead.arr_issueType[i]+"\n";
			}
			return low+commonString;
		}
		else
			return null;

	}

}
