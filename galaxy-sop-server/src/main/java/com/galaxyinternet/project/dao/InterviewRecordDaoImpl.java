package com.galaxyinternet.project.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.dao.project.InterviewRecordDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.project.InterviewRecord;


@Repository("interviewRecordDao")
public class InterviewRecordDaoImpl extends BaseDaoImpl<InterviewRecord, Long> implements InterviewRecordDao {

	
	
	//sqlNamespace + SQLNAME_SEPARATOR + sqlId
	@Override
	public Page<InterviewRecordBo> selectInterviewPageList(InterviewRecordBo query, Pageable pageable) {
		try {
			List<InterviewRecordBo> contentList = sqlSessionTemplate.selectList(getSqlName("selectInterviewPage") ,getParams(query, pageable));
			
			System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			
			return new  Page<InterviewRecordBo>(contentList, pageable, selectInterviewCount(query));
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName("selectInterviewPage")), e);
		}
	}
	
	
	public Long selectInterviewCount(InterviewRecordBo query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectOne(this.getSqlNamespace()+ ".selectInterviewCount", params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName("selectInterviewCount")), e);
		}
	}
	
	
	
	
	
}
