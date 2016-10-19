package com.galaxyinternet.report.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.report.BasicElement;
import com.galaxyinternet.model.report.DataReport;
import com.galaxyinternet.model.report.SopReportModal;
import com.galaxyinternet.report.manager.ImportExcel2007Manager;
import com.galaxyinternet.service.ReportService;

public abstract class ReportServiceImpl<T extends DataReport> implements ReportService<T> {
	
	private ImportExcel2007Manager excelManager = new ImportExcel2007Manager();
	
	public SopReportModal createReport(List<T> dataSource,String tempFilePath) throws Exception{
		String path = "";
		InputStream is = null;
		OutputStream out = null;
		SopReportModal modal = null;
		try {
			modal = createModal();
			//临时模板文件名称
			String tempName = Long.toString(System.currentTimeMillis());
			//模板后缀
			String templeteSuffix = modal.getFileSuffix();
			//模板生成路径
			path = tempFilePath + tempName + "." + templeteSuffix;
			//此处需判断模板格式并进行判断模板的生成后缀（xls,xlsx）(现在写死了xlsx)
			
			excelManager.copyFile(ReportServiceImpl.class.getClassLoader().getResource(modal.getTemplateName()).toString(), path);
			File file = new File(path);
			is = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			List<XSSFSheet> sheetList = excelManager.getExcelSheetList(workbook);
			if(sheetList!=null && sheetList.size() > modal.getSheetPage()){
				XSSFSheet reportSheet = sheetList.get(modal.getSheetPage());
				//写入附表头
				writeSecondHeader(workbook,reportSheet, modal.getSecondTableHeader(), dataSource.get(0));
				//获取可写入行数
				int editRowNum = modal.getDataStartRow();
				//开始写入数据
				for(int i=0;i<dataSource.size();i++){
					editRowNum++;
					T report = dataSource.get(i);
					Row row = reportSheet.createRow(editRowNum);
					Font font1 = excelManager.createFonts(workbook,Font.BOLDWEIGHT_NORMAL,"宋体",false,(short)200);
					List<BasicElement> columnList = getColumns();
					for(BasicElement column : columnList){
						if(column.getGetterMethod().equals("num")){
							excelManager.createCell(workbook, row, (int)column.getColumn(), Integer.toString(i+1), font1);
						}else{
							Method method = report.getClass().getMethod((String) column.getGetterMethod(), null);
							Object value = (Object)method.invoke(report, null);
							excelManager.createCell(workbook, row, (int)column.getColumn(),value, font1);
						}
					}	
				}
				out =  new FileOutputStream(file);
				excelManager.saveDoc(workbook,out);
				modal.setTempName(tempName);
				modal.setDownloadPath(tempFilePath);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotFoundException(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new IOException(e.getMessage());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			throw new NoSuchMethodException(e.getMessage());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			throw new SecurityException(e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new IllegalAccessException(e.getMessage());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException(e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new InvocationTargetException(e, e.getMessage());
		}finally{
			try {
				is.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new IOException(e.getMessage());
			}
			
		}
		return modal;	
	}
	
	public void writeTableHeader(XSSFSheet sheet,BasicElement be){
		Row row = sheet.getRow(be.getRow());
		Cell cell = row.getCell(be.getColumn());
		cell.setCellValue(be.getValue());
	}
	
	public void writeSecondHeader(XSSFWorkbook workBook,XSSFSheet sheet,BasicElement be,T t){
		Row row = sheet.getRow(be.getRow());
		Cell cell = row.getCell(be.getColumn());
		String dateOutPut = "";
		Font font = excelManager.createFonts(workBook,Font.BOLDWEIGHT_NORMAL,"宋体",false,(short)200);
		CellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
		if(BasicElement.VALUE_DATE.equals(be.getValue())){
			String time1 = "导出时间:" + DateUtil.longToString(System.currentTimeMillis());
			String time2 = "   统计时段:" + t.getStartTime() + "!!" + t.getEndTime();
			dateOutPut = time1 + time2;
		}
		cell.setCellValue(dateOutPut);
	}
	
	/**
	 * 模板列配置
	 * @return
	 */
	public abstract List<BasicElement> getColumns();
	/**
	 * 模板配置
	 * @return
	 */
	public abstract SopReportModal createModal();
	
	
	
	
}
