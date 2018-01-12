package com.galaxyinternet.project_danao;

import com.galaxyinternet.dao.SopSearchHistoryDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.SopSearchHistory;
import com.galaxyinternet.service.SopSearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("com.galaxyinternet.service.SopSearchHistoryService")
public class SopSearchHistoryServiceImpl extends BaseServiceImpl<SopSearchHistory> implements SopSearchHistoryService {

    @Autowired
    private SopSearchHistoryDao sopSearchHistoryDao;

    //规定保存10条
    private Integer saveItem = 10;

    @Override
    protected BaseDao<SopSearchHistory, Long> getBaseDao() {
        return this.sopSearchHistoryDao;
    }


    /**
     * 保存查询记录
     * 规定保存10条
     * 返回更新后的数据
     */
    public SopSearchHistory saveSearchHistory(Long uid, String keyword)
    {
        SopSearchHistory query = new SopSearchHistory();
        try {
            //查询记录
            query.setUid(uid);
            SopSearchHistory result = sopSearchHistoryDao.selectOne(query);

            if (result == null) {
                query.setContent(keyword);
                sopSearchHistoryDao.insert(query);
            } else {
                String content = result.getContent();
                if (result.getHisList() != null && !result.getHisList().contains(keyword)) {
                    if (result.getHisList().size() >= saveItem) {
                        content = content.substring(0,content.lastIndexOf(result.getSpiltMark()));
                    }
                    content = keyword + result.getSpiltMark() + content;
                }else{
                    content = keyword;
                }
                result.setContent(content);
                sopSearchHistoryDao.updateById(result);
                return result;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return query;
    }


}