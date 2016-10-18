package com.galaxyinternet.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.galaxyinternet.model.report.BasicElement;
import com.galaxyinternet.model.report.KpiGradeReport;
import com.galaxyinternet.model.report.SopReportModal;
import com.galaxyinternet.report.service.ReportService;

public class KpiGradeService extends ReportService<KpiGradeReport> {

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
				element.setGetterMethod("getCreateProjectSource");
				break;
			case 3:
				element.setColumn(i);
				element.setValue("分数/通过CEO评审");
				element.setGetterMethod("getCeoReviewSource");
				break;
			case 4:
				element.setColumn(i);
				element.setValue("分数/通过立项会");
				element.setGetterMethod("getProjectMettingSource");
				break;
			case 5:
				element.setColumn(i);
				element.setValue("总分数");
				element.setGetterMethod("getTotalSource");
				break;
			case 6:
				element.setColumn(i);
				element.setValue("过会率/CEO评审会");
				element.setGetterMethod("getCeoReviewRate");
				break;
			case 7:
				element.setColumn(i);
				element.setValue("过会率/立项会");
				element.setGetterMethod("getProjectMettingRate");
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
		tableHeader.setValue("合伙人日常业务绩效评分表");
		BasicElement secondHeader = new BasicElement();
		secondHeader.setRow(1);
		secondHeader.setColumn(5);
		secondHeader.setValue(BasicElement.VALUE_DATE);
		modal.setTableHeader(tableHeader);
		modal.setSecondTableHeader(secondHeader);
		modal.setColumns(getColumns());
		return modal;
	}

}
