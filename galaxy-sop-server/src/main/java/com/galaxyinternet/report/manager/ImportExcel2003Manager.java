package com.galaxyinternet.report.manager;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellValue;

/**
 * excel上传管理器类.
 * <p>
 *
 * @version 2011-06-09 
 * @author tgj 
 */
 public class  ImportExcel2003Manager {

	 private HSSFFormulaEvaluator evaluator;
	 private HSSFWorkbook workbook =null;


	
	
	private List<Object[]> getDateFromSheet(HSSFSheet sheet,int startRowNum){
		if(sheet.getLastRowNum()==0)
			return null;
		List<Object[]> excelData = new ArrayList<Object[]>();
		
		for (int rownum = startRowNum; rownum <= sheet.getLastRowNum(); rownum++) {
			Object[] rowData = getExcelRow(sheet,rownum);
			if(rowData!=null){				
				excelData.add(rowData);
			}
		}
		return excelData;
	}
	public Object[] getExcelRow(HSSFSheet sheet,int rownum){
		HSSFRow aRow = sheet.getRow(rownum);
		if(aRow==null)
			return null;
		Object[] rowData = new Object[aRow.getLastCellNum()]; 
		boolean fullnullflag = false;
        for(int i = 0; i < aRow.getLastCellNum(); i++)
        {
			HSSFCell aCell = aRow.getCell(i);
            Object returnvalue = getSaveValue(aCell);
            if(returnvalue!=null){
            	fullnullflag = true;
            	rowData[i] = returnvalue;
            }
        }
        if(fullnullflag==true){
        	return rowData;
        }else{
        	return null;
        }
	}
	
	public Object getSaveValue(HSSFCell aCell){
		Object value = getValue(aCell);
		if(value==null){
			return null;
		}
		if(value.equals("是")){
			return 1;
		}else if(value.equals("否")){
			return 0;
		}else{
			return value;
		}
	}

	protected Object getValue(HSSFCell aCell) {
		Object value = null;
		if (aCell == null){
			return null;
		}
		int type = aCell.getCellType();
		if (1 == type)
			value = aCell.getRichStringCellValue().getString();
		else if (type == 0) {
			value = get0TypeData(aCell);
			//DecimalFormat df = new DecimalFormat("##0.###");//
			//value =df.format(value);
		} else if (2 == type) {
			value = get2TypeData(aCell);
		} else if (4 == type)
			value = Boolean.valueOf(aCell.getBooleanCellValue());
		else if (2 == type && evaluator != null) {
			CellValue cellValue = evaluator.evaluate(aCell);
			value = getValue(cellValue);
		}
		return value;
	}

	private Object get2TypeData(HSSFCell aCell) {
		Object value = null;
		if(evaluator==null){
			evaluator = new HSSFFormulaEvaluator(aCell.getSheet().getWorkbook());
		}
		if (evaluator != null) {	
			CellValue cellValue  = evaluator.evaluate(aCell);
			int cellValueType = cellValue.getCellType();
			if (1 == cellValueType){
				value = cellValue.getStringValue();
			}else if (cellValueType == 0){
				//value = (int) (cellValue.getNumberValue());
				value =  (cellValue.getNumberValue());
			}
		}
		return value;
	}

	private Object get0TypeData(HSSFCell aCell) {
		Object value;
		if (HSSFDateUtil.isCellDateFormatted(aCell)) {
			Date d = aCell.getDateCellValue();
			DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = formater.format(d);
		} else {
			double doublevalue = aCell.getNumericCellValue();
			int intvalue = (int) aCell.getNumericCellValue();
			if(intvalue == doublevalue)
				value = intvalue;
			else
				value = doublevalue;
		}
		return value;
	}

    protected Object getValue(CellValue cellValue) {
		Object value = null;
		int cellValueType = cellValue.getCellType();
		if (1 == cellValueType)
			value = cellValue.getStringValue();
		else if (cellValueType == 0)
			value = (int) (cellValue.getNumberValue());
		else if (4 == cellValueType)
			value = Boolean.valueOf(cellValue.getBooleanValue());
		return value;
	}
    

	
	/**
	 * 获取sheet.
	 * <p>
	 * @param is
	 * @param index
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private HSSFSheet getExcelSheet(InputStream is,int index)
			throws FileNotFoundException, IOException {
		 workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheetAt(index);
		//evaluator = new HSSFFormulaEvaluator(sheet, sheet.getWorkbook());
		return sheet;
	}
	
	/**
	 * 获得工作表的数组
	 */
	private List<HSSFSheet> getExcelSheetList(InputStream is)
			throws FileNotFoundException, IOException {
		List<HSSFSheet> bookList = new ArrayList<HSSFSheet>();
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		Integer sheetCount = workbook.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			HSSFSheet sheet = workbook.getSheetAt(i);
			bookList.add(sheet);
		}
		return bookList;
	}




	
}