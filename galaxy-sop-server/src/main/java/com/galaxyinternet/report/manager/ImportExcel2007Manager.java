package com.galaxyinternet.report.manager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel上传管理器类.
 * <p>
 *
 * @version 2011-06-09
 * @author zhangzl
 */
public class ImportExcel2007Manager {

	public List<Object[]> generateObjectList(XSSFWorkbook workbook, int startRowNum)
			throws FileNotFoundException, IOException {
		List<XSSFSheet> sheetList = getExcelSheetList(workbook);
		XSSFSheet sheet = sheetList.get(0);
		List<Object[]> li = getDataFromSheet(sheet, 0);
		return li;
	}

	/**
	 * 根据sheet对象获取sheet页的数据
	 * 
	 * @param sheet
	 * @param startRowNum
	 * @return
	 */
	public List<Object[]> getDataFromSheet(XSSFSheet sheet, int startRowNum) {
		if (sheet.getLastRowNum() == 0)
			return null;
		List<Object[]> excelData = new ArrayList<Object[]>();
		for (int rownum = startRowNum; rownum <= sheet.getLastRowNum(); rownum++) {
			Object[] rowData = getExcelRow(sheet, rownum);
			if (rowData != null) {
				excelData.add(rowData);
			}
		}
		return excelData;
	}

	/**
	 * 根据sheet对象 rownum获取该行的数据
	 * 
	 * @param sheet
	 * @param rownum
	 * @return
	 */
	public Object[] getExcelRow(XSSFSheet sheet, int rownum) {
		XSSFRow aRow = sheet.getRow(rownum);
		
		if (aRow == null)
			return null;
		Object[] rowData = new Object[aRow.getLastCellNum()];
		boolean fullnullflag = false;
		for (int i = 0; i < aRow.getLastCellNum(); i++) {
			XSSFCell aCell = aRow.getCell(i);
			Object returnvalue = getValuefromCell(aCell);
			if (returnvalue != null) {
				fullnullflag = true;
				rowData[i] = returnvalue;
			}
		}
		if (fullnullflag == true) {
			return rowData;
		} else {
			return null;
		}
	}

	/**
	 * 获得工作表的数组
	 */
	public List<XSSFSheet> getExcelSheetList(XSSFWorkbook workbook)
			throws FileNotFoundException, IOException {
		List<XSSFSheet> bookList = new ArrayList<XSSFSheet>();
		Integer sheetCount = workbook.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			bookList.add(sheet);
		}
		return bookList;
	}


	/**
	 * 获取cell的值
	 * 
	 * @param cell
	 * @return
	 */
	private Object getValuefromCell(XSSFCell cell) {
		Object value = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();
					Date date = HSSFDateUtil.getJavaDate(d);
					SimpleDateFormat format1 = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					value = format1.format(date);
				} else {
					// value = cell.getNumericCellValue();
					DecimalFormat df = new DecimalFormat("0");
					value = df.format(cell.getNumericCellValue());
				}
				break;
			case XSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				break;
			case XSSFCell.CELL_TYPE_ERROR:
				value = cell.getErrorCellValue();
				break;
			case XSSFCell.CELL_TYPE_FORMULA:
				FormulaEvaluator evaluator = cell.getSheet().getWorkbook()
						.getCreationHelper().createFormulaEvaluator();
				evaluator.evaluateFormulaCell(cell);
				value = cell.getNumericCellValue();
				break;
			}
		}
		return value;
	}
	 
	
	/**
	 * 创建单元格并设置样式
	 * @param wb excel文件对象
	 * @param row 行
	 * @param column 列
	 * @param value 设置值
	 * @param font  设置字体
	 */
	public void createCellText(Workbook wb,Row row,int column,Object value,Font font){
		Cell cell = row.createCell(column);
		CellStyle cellStyle2 = wb.createCellStyle();  
        DataFormat format = wb.createDataFormat();  
        cellStyle2.setDataFormat(format.getFormat("@"));  
        cell.setCellStyle(cellStyle2);  
        cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
        cell.setCellValue(value+"");
	}
	
	public void createCell(Workbook wb,Row row,int column,Object value,Font font,int type){
		Cell cell = row.createCell(column);
		CellStyle cellStyle = wb.createCellStyle();  
		switch(type){
		case 1 :   
		        DataFormat format = wb.createDataFormat();  
		        cellStyle.setDataFormat(format.getFormat("@"));  
		        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
				cellStyle.setFont(font);
				cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
				cellStyle.setBorderTop(CellStyle.BORDER_THIN);
				cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
				cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		        cell.setCellStyle(cellStyle);  
		        cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
		        cell.setCellValue(value+"");
			    break;
	    case 2 :   
		        cell.setCellValue(value.toString());
				cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
				cellStyle.setFont(font);
				cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
				cellStyle.setBorderTop(CellStyle.BORDER_THIN);
				cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
				cellStyle.setBorderRight(CellStyle.BORDER_THIN);
				cell.setCellStyle(cellStyle);
		    break;
			      
		default:
				cell.setCellValue(value.toString());
				cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
				cellStyle.setFont(font);
				cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
				cellStyle.setBorderTop(CellStyle.BORDER_THIN);
				cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
				cellStyle.setBorderRight(CellStyle.BORDER_THIN);
				cell.setCellStyle(cellStyle);
				break;
		}
		
		
	}
	
	/**
	 * 设置字体
	 * @param wb
	 * @param bold
	 * @param fontName
	 * @param isItalic
	 * @param hight
	 * @return
	 */
	public Font createFonts(Workbook wb,short bold,String fontName,boolean isItalic,short hight){
		Font font = wb.createFont();
		font.setFontName(fontName);
		font.setBoldweight(bold);
		font.setItalic(isItalic);
		font.setFontHeight(hight);
		return font;
	}
	
	
	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public boolean isNumberic(String str){
		if(str == null || "".equals(str.trim()) || str.length() > 10)
			return false;
		Pattern pattern = Pattern.compile("^0|[1-9]\\d*(\\.\\d+)?$");
		return pattern.matcher(str).matches();
	}
	
	
	
	public void saveDoc(Workbook wb,OutputStream out) throws IOException {
		wb.write(out);
	}
	
	
	/**
	 * 文件拷贝到新文件夹
	 * @param oldPath
	 * @param newPath
	 */
	public void copyFile(String oldPath,String newParentPath,String newPath) throws FileNotFoundException,IOException{
		InputStream in = null;
		FileOutputStream out = null;
		BufferedInputStream fis = null;
		BufferedOutputStream fos = null;
		File oldfile = new File(oldPath);
		File parentPath = new File(newParentPath);
		File newFile = new File(newPath);
		try {
			// 文件存在时
			if (oldfile.exists()) {
				if(!newFile.exists()){
					if(!parentPath.exists()){
						parentPath.mkdirs();
					}	
					newFile.createNewFile();//生成文件 
				}
				// 读入原文件
				in = new FileInputStream(oldPath);
				out = new FileOutputStream(newFile);
				fis = new BufferedInputStream(in);
				fos = new BufferedOutputStream(out);
				byte[] buffer = new byte[1024 * 2];
				while (fis.read(buffer) != -1) {
					fos.write(buffer);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotFoundException(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new IOException(e.getMessage());
		}finally{
			if(fis!=null){
				fis.close();
			}
			if(fos!=null){
				fos.close();
			}
			if(in!=null){
				in.close();
			}
			if(out!=null){
				out.close();
			}	
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}