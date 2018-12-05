package com.selenium.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {


	/*public ExcelUtil(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}*/

	private XSSFSheet ExcelWSheet;
	private XSSFWorkbook ExcelWBook;
	private XSSFCell Cell;
	private XSSFRow Row;
	public int colCount=0;

	public  void setExcel(String path, String workbook){
		File file = new File(path);
		try {
			FileInputStream fis = new FileInputStream(file);
			ExcelWBook = new XSSFWorkbook(fis);
			ExcelWSheet = ExcelWBook.getSheet(workbook);
			colCount=0;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}		
	}

	public String readExcel(int RowNum, int ColNum){

		Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
		String CellData = Cell.getStringCellValue();
		return CellData;

	}

	public String readString(String columnName) throws NullPointerException{
		while(ExcelWSheet.getRow(0).getCell(colCount).getStringCellValue()!=null){
			//System.out.println("cell value"+ExcelWSheet.getRow(0).getCell(colCount).getStringCellValue().toString());
			if(ExcelWSheet.getRow(0).getCell(colCount).getStringCellValue().equalsIgnoreCase(columnName)){
				return ExcelWSheet.getRow(1).getCell(colCount).getStringCellValue();
			}
			colCount++;
		}
		return null;
	}

	public String readNumber(String columnName, int invocationCount) throws NullPointerException{

		int colNum=0;
		if(invocationCount==0){
			colNum=findColNumber(columnName,invocationCount);
			return String.valueOf(((Double)ExcelWSheet.getRow(invocationCount+1).getCell(colNum).getNumericCellValue()).intValue());
		}
		else{
			return String.valueOf(((Double)ExcelWSheet.getRow(invocationCount+1).getCell(colNum).getNumericCellValue()).intValue());
		}

	}


	public int findColNumber(String columnName, int invocationCount){

		while(ExcelWSheet.getRow(invocationCount).getCell(colCount).getStringCellValue()!=null){
			if(ExcelWSheet.getRow(invocationCount).getCell(colCount).getStringCellValue().equalsIgnoreCase(columnName)){
				return colCount;
			}
			colCount++;
		}
		return colCount;
	}


	public String readDate(String columnName) throws NullPointerException{
		while(ExcelWSheet.getRow(0).getCell(colCount).getStringCellValue()!=null){
			//System.out.println("cell value"+ExcelWSheet.getRow(0).getCell(colCount).getStringCellValue().toString());
			if(ExcelWSheet.getRow(0).getCell(colCount).getStringCellValue().equalsIgnoreCase(columnName)){
				return ExcelWSheet.getRow(1).getCell(colCount).getStringCellValue();
				//return new SimpleDateFormat().format((Date)ExcelWSheet.getRow(1).getCell(colCount).getDateCellValue());
			}
			colCount++;
		}
		return null;
	}

	public String readBoolean(String columnName) throws NullPointerException{
		while(ExcelWSheet.getRow(0).getCell(colCount).getStringCellValue()!=null){
			//System.out.println("cell value"+ExcelWSheet.getRow(0).getCell(colCount).getStringCellValue().toString());
			if(ExcelWSheet.getRow(0).getCell(colCount).getStringCellValue().equalsIgnoreCase(columnName)){
				return ((Boolean)ExcelWSheet.getRow(1).getCell(colCount).getBooleanCellValue()).toString();
			}
			colCount++;
		}
		return null;
	}


	public static void flushExcel(){

	}

}
