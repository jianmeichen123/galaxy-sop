package com.galaxyinternet.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.chart.ProjectData;
import com.galaxyinternet.model.report.BasicElement;
import com.galaxyinternet.model.report.SopReportModal;
import com.galaxyinternet.report.service.ReportServiceImpl;
import com.galaxyinternet.service.chart.ProjectGradeService;

@Service("projectGradeServiceImpl") 
public class ProjectGradeServiceImpl extends ReportServiceImpl<ProjectData> implements ProjectGradeService {
	
	@Override
	public SopReportModal createModal() {
		// TODO Auto-generated method stub
		SopReportModal modal = new SopReportModal();
		modal.setDataStartRow(2);
		modal.setSheetPage(0);
		BasicElement tableHeader = new BasicElement();
		tableHeader.setRow(1);
		tableHeader.setColumn(0);
		tableHeader.setValue("事业部创投项目列表");
		BasicElement secondHeader = new BasicElement();
		secondHeader.setRow(1);
		secondHeader.setColumn(5);
		secondHeader.setValue(BasicElement.VALUE_DATE);
		modal.setTableHeader(tableHeader);
		modal.setSecondTableHeader(secondHeader);
		modal.setColumns(getColumns());
		modal.setTemplateName("template/projectGradeTemplate.xlsx");
		modal.setDownloadName("合伙人日常业务绩效评分表"+DateUtil.longToString(System.currentTimeMillis(),"yyyyMMdd"));
		modal.setFileSuffix("xlsx");
		return modal;
	}

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
				element.setValue("项目名称");
				element.setGetterMethod("projectName");
				break;
			case 2:
				element.setColumn(i);
				element.setValue("公司名称");
				element.setGetterMethod("departmentName");
				break;
			case 3:
				element.setColumn(i);
				element.setValue("项目类型");
				element.setGetterMethod("type");
				break;
			case 4:
				element.setColumn(i);
				element.setValue("事业部");
				element.setGetterMethod("departmentName");
				break;
			case 5:
				element.setColumn(i);
				element.setValue("融资状态");
				element.setGetterMethod("financeStatus");
				break;
			case 6:
				element.setColumn(i);
				element.setValue("注资时间");
				element.setGetterMethod("ctime");
				break;
			case 7:
				element.setColumn(i);
				element.setValue("投资金额（万）");
				element.setGetterMethod("finalContribution");
				break;
			case 8:
				element.setColumn(i);
				element.setValue("占比");
				element.setGetterMethod("radioStr");
				break;
			case 9:
				element.setColumn(i);
				element.setValue("融资历史");
				element.setGetterMethod("financeHistory");
				break;
			case 10:
				element.setColumn(i);
				element.setValue("项目现状");
				element.setGetterMethod("healthState");
				break;
			case 11:
				element.setColumn(i);
				element.setValue("商业模式");
				element.setGetterMethod("projectDescribeFinancing");
				break;
			case 12:
				element.setColumn(i);
				element.setValue("业务简要概述和项目亮点");
				element.setGetterMethod("projectDescribe");
				break;
			default:
				break;
			}
			columnList.add(element);
		}
		return columnList;	
	}
	

}
