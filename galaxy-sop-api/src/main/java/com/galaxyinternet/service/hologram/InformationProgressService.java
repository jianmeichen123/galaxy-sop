package com.galaxyinternet.service.hologram;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationProgress;

public interface InformationProgressService extends BaseService<InformationProgress>{


    void threadForUpdate(final Long uid, Long proId);


   /* void usersProAllReportProgress(Long uid, final Long proId);
    void checkUsersProReportProgress(Long uid, final Long proId);*/


    InformationProgress initUsersAllReportProgressOfPro(Long uid, final Long proId);
    //Double getReportProgressByReportCode(String code,final Long proId);
    Double getProgressByReportCode(String code,final Long proId);

    void updateUsersAllReportProgressOfPro(Long uid, final Long proId);
}
