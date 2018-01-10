package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.SopSearchHistory;

public interface SopSearchHistoryService extends BaseService<SopSearchHistory>{

    /**
     * 保存查询记录
     * 规定保存10条
     * 返回更新后的数据
     */
    public SopSearchHistory saveSearchHistory(Long uid, String keyword);

}
