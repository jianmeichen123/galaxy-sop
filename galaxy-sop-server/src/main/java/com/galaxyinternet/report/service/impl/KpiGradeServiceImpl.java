package com.galaxyinternet.report.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.chart.ChartDataBo;
import com.galaxyinternet.common.utils.StrUtils;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.report.BasicElement;
import com.galaxyinternet.model.report.SopReportModal;
import com.galaxyinternet.model.sopfile.SopDownLoad;
import com.galaxyinternet.report.service.ReportServiceImpl;
import com.galaxyinternet.service.chart.KpiGradeService;
import com.galaxyinternet.sopfile.controller.SopFileController;

@Service("kpiGradeServiceImpl") 
public class KpiGradeServiceImpl extends ReportServiceImpl<ChartDataBo> implements KpiGradeService {
	
	
	@Override
	public List<BasicElement> getColumns() {
		// TODO Auto-generated method stub
		List<BasicElement> columnList = new ArrayList<BasicElement>();
		for(int i=0;i<8;i++){
			BasicElement element = new BasicElement();
			switch (i) {
			case 0:
				element.setColumn(i);
				element.setValue("序号");
				element.setGetterMethod(BasicElement.GETTERMETHOD_NUM);
				break;
			case 1:
				element.setColumn(i);
				element.setValue("事业线");
				element.setGetterMethod("getDepartmentName");
				break;
			case 2:
				element.setColumn(i);
				element.setValue("分数/生成项目");
				element.setGetterMethod("getScore1");
				break;
			case 3:
				element.setColumn(i);
				element.setValue("分数/通过CEO评审");
				element.setGetterMethod("getScore2");
				break;
			case 4:
				element.setColumn(i);
				element.setValue("分数/通过立项会");
				element.setGetterMethod("getScore3");
				break;
			case 5:
				element.setColumn(i);
				element.setValue("总分数");
				element.setGetterMethod("getSumScore");
				break;
			case 6:
				element.setColumn(i);
				element.setValue("过会率/CEO评审会");
				element.setGetterMethod("getCeoRateStr");
				break;
			case 7:
				element.setColumn(i);
				element.setValue("过会率/立项会");
				element.setGetterMethod("getLxhRateStr");
				break;
			default:
				break;
			}
			columnList.add(element);
		}
		return columnList;	
	}

	@Override
	public SopReportModal createModal() {
		// TODO Auto-generated method stub
		SopReportModal modal = new SopReportModal();
		modal.setDataStartRow(2);
		modal.setSheetPage(0);
		BasicElement tableHeader = new BasicElement();
		tableHeader.setRow(1);
		tableHeader.setColumn(0);
		tableHeader.setValue("合伙人绩效考核表");
		BasicElement secondHeader = new BasicElement();
		secondHeader.setRow(1);
		secondHeader.setColumn(5);
		secondHeader.setValue(BasicElement.VALUE_DATE);
		modal.setTableHeader(tableHeader);
		modal.setSecondTableHeader(secondHeader);
		modal.setColumns(getColumns());
		modal.setTemplateName("template/kpiGradeTemplate.xlsx");
		modal.setDownloadName("合伙人绩效考核表"+DateUtil.longToString(System.currentTimeMillis(),"yyyyMMdd"));
		modal.setFileSuffix("xlsx");
		return modal;
	}

	

}
