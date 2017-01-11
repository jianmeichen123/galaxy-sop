package com.galaxyinternet.report.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxyinternet.common.utils.StrUtils;
import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.chart.DataFormat;
import com.galaxyinternet.model.report.BasicElement;
import com.galaxyinternet.model.report.SopReportModal;
import com.galaxyinternet.model.sopfile.SopDownLoad;
import com.galaxyinternet.report.manager.ImportExcel2007Manager;
import com.galaxyinternet.report.manager.Xlsx2CsvManager;
import com.galaxyinternet.service.ReportService;
import com.galaxyinternet.sopfile.controller.SopFileController;

public abstract class ReportServiceImpl<T extends PagableEntity> implements ReportService<T> {
	
	private ImportExcel2007Manager excelManager = new ImportExcel2007Manager();
	final Logger logger = LoggerFactory.getLogger(SopFileController.class);
	
	public SopReportModal createReport(DataFormat<T> dataSource,String templatePath,String tempFilePath,String suffix) throws Exception{
		String path = "";
		InputStream is = null;
		OutputStream out = null;
		SopReportModal modal = null;
		//临时报表生成文件
		File file = null;
		try {
			modal = createModal();
			//临时报表文件名称
			String tempName = Long.toString(System.currentTimeMillis());
			//报表后缀
			String templeteSuffix = modal.getFileSuffix();
			//临时报表生成路径
			path = tempFilePath + tempName + "." + templeteSuffix;
			//此处需判断模板格式并进行判断模板的生成后缀（xls,xlsx）(现在写死了xlsx)
			
			excelManager.copyFile(templatePath + "/" +  modal.getTemplateName(),tempFilePath,path);
			file = new File(path);
			is = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			List<XSSFSheet> sheetList = excelManager.getExcelSheetList(workbook);
			if(sheetList!=null && sheetList.size() > modal.getSheetPage()){
				XSSFSheet reportSheet = sheetList.get(modal.getSheetPage());
				//写入附表头
				writeSecondHeader(workbook,reportSheet, modal.getSecondTableHeader(), dataSource.getStartTime(),dataSource.getEndTime());
					//获取可写入行数
				int editRowNum = modal.getDataStartRow();
				List<T> data=dataSource.getList();
				//开始写入数据
				for(int i=0;i<dataSource.getList().size();i++){
					editRowNum++;
					T report = data.get(i);
					Row row = reportSheet.createRow(editRowNum);
					Font font1 = excelManager.createFonts(workbook,Font.BOLDWEIGHT_NORMAL,"宋体",false,(short)200);
					List<BasicElement> columnList = getColumns();
					for(BasicElement column : columnList){
						if(column.getGetterMethod().equals("num")){
							excelManager.createCell(workbook, row, (int)column.getColumn(), Integer.toString(i+1), font1,2);
						}else{
							Method method = report.getClass().getMethod((String) column.getGetterMethod(), null);
							Object value = (Object)method.invoke(report, null);
							//业务需要根据某字段设置单独的属性
							if("getFinalContribution".equals(column.getGetterMethod())){
								excelManager.createCell(workbook, row, (int)column.getColumn(),value, font1,1);
							}else{
								excelManager.createCell(workbook, row, (int)column.getColumn(),value, font1,2);
							}
							
						}
					}	
				}
				out =  new FileOutputStream(file);
				excelManager.saveDoc(workbook,out);
				if(suffix.equals("csv")){
					String tempCsvName = Long.toString(System.currentTimeMillis());
					String csvPath = tempFilePath + tempCsvName + ".csv";
					Xlsx2CsvManager csvConvert = new Xlsx2CsvManager(path, csvPath);
					csvConvert.process();
					tempName = tempCsvName;
					modal.setFileSuffix(suffix);
				}
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
				if(suffix.equals("csv")){
					file.delete();
				}
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
	
	public void writeSecondHeader(XSSFWorkbook workBook,XSSFSheet sheet,BasicElement be,String startTime,String endTime){
		Row row = sheet.getRow(be.getRow());
		Cell cell = row.getCell(be.getColumn());
		String dateOutPut = "";
		Font font = excelManager.createFonts(workBook,Font.BOLDWEIGHT_NORMAL,"宋体",false,(short)200);
		CellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
		if(BasicElement.VALUE_DATE.equals(be.getValue())){
			String time1 = "导出时间:" + DateUtil.longToString(System.currentTimeMillis());
			if(null==startTime||startTime.equals("")||null==endTime||endTime.equals("")){
				dateOutPut = time1;
			}else{
				String time2 = "   统计时段:" + startTime + "~" + endTime;
				dateOutPut = time1+time2;
			}
		}
		cell.setCellValue(dateOutPut);
	}
	
	public void download(HttpServletRequest request,
			HttpServletResponse response,SopReportModal modal) throws Exception {
		// TODO Auto-generated method stub

		InputStream fis = null;
		OutputStream out = null;
		
				
		File tempDir = new File(modal.getDownloadPath());
		File tempFile = new File(modal.getDownloadPath()+modal.getTempName()+"."+modal.getFileSuffix());
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		
		try{			
			String fileName = getFileNameByBrowser(request,modal.getDownloadName()+"."+modal.getFileSuffix());
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			response.setHeader("Content-Length", "" + tempFile.length());
			out = new BufferedOutputStream(response.getOutputStream());
			fis = new BufferedInputStream(new FileInputStream(tempFile.getPath()));
			byte[] buffer = new byte[1024 * 2];
			
			while (fis.read(buffer) != -1) {
				out.write(buffer);
			}
			response.flushBuffer();
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			try {
				if(fis != null)
				{
					fis.close();
				}
				if(out != null)
				{
					out.close();
				}
				tempFile.delete();
			} catch (IOException e) {
				logger.error("下载失败.",e);
			}
		}	
	}
	
	private String getFileNameByBrowser(HttpServletRequest request,String fileName) throws UnsupportedEncodingException{
		boolean ie10 = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
				.indexOf("MSIE") > 0;
		boolean ie11p = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
				.indexOf("RV:11") > 0
				&& request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
						.indexOf("LIKE GECKO") > 0;
		boolean iedge = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
				.indexOf("EDGE") > 0;
		boolean ie = ie10 || ie11p || iedge;
		if (ie) {
			fileName = new String(StrUtils.encodString(fileName).getBytes("UTF-8"), "ISO8859-1");

		} else {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");

		}
		return fileName;
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
