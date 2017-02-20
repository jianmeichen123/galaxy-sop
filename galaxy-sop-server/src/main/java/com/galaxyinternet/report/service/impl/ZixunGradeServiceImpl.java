package com.galaxyinternet.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.chart.ProjectData;
import com.galaxyinternet.model.chart.ZixunData;
import com.galaxyinternet.model.report.BasicElement;
import com.galaxyinternet.model.report.SopReportModal;
import com.galaxyinternet.report.service.ReportServiceImpl;
import com.galaxyinternet.service.chart.ProjectGradeService;
import com.galaxyinternet.service.chart.ZixunGradeService;

@Service("zixunGradeServiceImpl") 
public class ZixunGradeServiceImpl extends ReportServiceImpl<ZixunData> implements ZixunGradeService {
	
	@Override
	public SopReportModal createModal() {
		// TODO Auto-generated method stub
		SopReportModal modal = new SopReportModal();
		modal.setDataStartRow(1);
		modal.setSheetPage(0);
		BasicElement tableHeader = new BasicElement();
		tableHeader.setRow(0);
		tableHeader.setColumn(0);
		tableHeader.setValue("创情资讯");
		BasicElement secondHeader = new BasicElement();
		secondHeader.setRow(0);
		secondHeader.setColumn(28);
		secondHeader.setValue(BasicElement.VALUE_DATE);
		modal.setTableHeader(tableHeader);
		modal.setSecondTableHeader(secondHeader);
		modal.setColumns(getColumns());
		modal.setTemplateName("template/zixunGradeTemplate.xlsx");
		modal.setDownloadName("创情资讯"+DateUtil.longToString(System.currentTimeMillis(),"yyyyMMdd"));
		modal.setFileSuffix("xlsx");
		return modal;
	}

	@Override
	public List<BasicElement> getColumns() {
		// TODO Auto-generated method stub
		List<BasicElement> columnList = new ArrayList<BasicElement>();
		for(int i=0;i<30;i++){
			BasicElement element = new BasicElement();
			switch (i) {
			case 0:
				element.setColumn(i);
				element.setValue("更新日期");
				element.setGetterMethod("getUpdateDate");
				element.setType("1");
				break;
			case 1:
				element.setColumn(i);
				element.setValue("公司名称");
				element.setGetterMethod("getCompanyName");
				element.setType("1");
				break;
			case 2:
				element.setColumn(i);
				element.setValue("公司名称");
				element.setGetterMethod("getCompanyName");
				element.setType("1");
				break;
			case 3:
				element.setColumn(i);
				element.setValue("细分领域");
				element.setGetterMethod("getCompanyField");
				element.setType("1");
				break;
			case 4:
				element.setColumn(i);
				element.setValue("归属部门");
				element.setGetterMethod("getDepartmentName");
				element.setType("1");
				break;
			case 5:
				element.setColumn(i);
				element.setValue("简介");
				element.setGetterMethod("getRemark");
				break;
			case 6:
				element.setColumn(i);
				element.setValue("详细介绍");
				element.setGetterMethod("getDetailInfo");
				element.setType("1");
				break;
			case 7:
				element.setColumn(i);
				element.setValue("时间1");
				element.setGetterMethod("getTime1");
				element.setType("2");
				break;
			case 8:
				element.setColumn(i);
				element.setValue("金额");
				element.setGetterMethod("getMoney1");
				element.setType("1");
				break;
			case 9:
				element.setColumn(i);
				element.setValue("时间2");
				element.setGetterMethod("getTime2");
				element.setType("1");
				break;
			case 10:
				element.setColumn(i);
				element.setValue("金额");
				element.setGetterMethod("getMoney2");
				element.setType("1");
				break;
			case 11:
				element.setColumn(i);
				element.setValue("时间3");
				element.setGetterMethod("getTime3");
				element.setType("1");
				break;
			case 12:
				element.setColumn(i);
				element.setValue("金额");
				element.setGetterMethod("getMoney3");
				element.setType("1");
				break;
			case 13:
				element.setColumn(i);
				element.setValue("时间4");
				element.setGetterMethod("getTime4");
				element.setType("2");
				break;
			case 14:
				element.setColumn(i);
				element.setValue("金额");
				element.setGetterMethod("getMoney4");
				element.setType("1");
				break;
			case 15:
				element.setColumn(i);
				element.setValue("时间5");
				element.setGetterMethod("getTime5");
				element.setType("1");
				break;
			case 16:
				element.setColumn(i);
				element.setValue("金额");
				element.setGetterMethod("getMoney5");
				element.setType("1");
				break;
			case 17:
				element.setColumn(i);
				element.setValue("时间6");
				element.setGetterMethod("getTime6");
				element.setType("1");
				break;
			case 18:
				element.setColumn(i);
				element.setValue("金额");
				element.setGetterMethod("getMoney6");
				element.setType("1");
				break;
			case 19:
				element.setColumn(i);
				element.setValue("时间7");
				element.setGetterMethod("getTime7");
				element.setType("1");
				break;
			case 20:
				element.setColumn(i);
				element.setValue("金额");
				element.setGetterMethod("getMoney7");
				element.setType("1");
				break;
			case 21:
				element.setColumn(i);
				element.setValue("时间8");
				element.setGetterMethod("getTime8");
				element.setType("1");
				break;
			case 22:
				element.setColumn(i);
				element.setValue("金额");
				element.setGetterMethod("getMoney8");
				element.setType("1");
				break;
			case 23:
				element.setColumn(i);
				element.setValue("时间9");
				element.setGetterMethod("getTime9");
				element.setType("1");
				break;
			case 24:
				element.setColumn(i);
				element.setValue("金额");
				element.setGetterMethod("getMoney9");
				element.setType("1");
				break;
			case 25:
				element.setColumn(i);
				element.setValue("时间10");
				element.setGetterMethod("getTime10");
				element.setType("2");
				break;
			case 26:
				element.setColumn(i);
				element.setValue("金额");
				element.setGetterMethod("getMoney10");
				element.setType("1");
				break;
			case 27:
				element.setColumn(i);
				element.setValue("创始人");
				element.setGetterMethod("getCompanyCuser");
				element.setType("1");
				break;
			case 28:
				element.setColumn(i);
				element.setValue("所在地");
				element.setGetterMethod("getCompanyAddress");
				element.setType("1");
				break;
			case 29:
				element.setColumn(i);
				element.setValue("网址");
				element.setGetterMethod("getCompanyUrl");
				element.setType("1");
				break;
			default:
				break;
			}
			columnList.add(element);
		}
		return columnList;	
	}
	

}
