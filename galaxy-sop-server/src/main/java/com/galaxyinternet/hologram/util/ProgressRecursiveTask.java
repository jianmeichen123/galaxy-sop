package com.galaxyinternet.hologram.util;

import com.galaxyinternet.common.utils.SpringContextManager;
import com.galaxyinternet.hologram.service.CacheOperationServiceImpl;
import com.galaxyinternet.model.hologram.InformationProgress;
import com.galaxyinternet.service.hologram.InformationProgressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;


public class ProgressRecursiveTask extends RecursiveTask<InformationProgress>{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(ProgressRecursiveTask.class);

    public static final ThreadLocal<String> tl = new ThreadLocal<String>();

    private InformationProgressService informationProgressService = SpringContextManager.getBean(InformationProgressService.class);

    private InformationProgress informationProgress;
    private String preCode;
    private Long proId;

    public ProgressRecursiveTask(){}
    public ProgressRecursiveTask(InformationProgress informationProgress,String preCode,Long proId){
        super();
        this.informationProgress = informationProgress;
        this.preCode = preCode;
        this.proId = proId;
    }



    @Override
    protected InformationProgress compute() {
        if(null == preCode){
            List<ProgressRecursiveTask> subTasks = new ArrayList<>(CacheOperationServiceImpl.pre_reports_codes.length);

            ProgressRecursiveTask subTask = null;

            for(String code : CacheOperationServiceImpl.pre_reports_codes) {
                subTask = new ProgressRecursiveTask(informationProgress,code,proId);
                subTasks.add(subTask);
            }

            invokeAll(subTasks);

            for(ProgressRecursiveTask tem : subTasks){
                tem.join();
            }
        }else{
            tl.set(preCode);
            //double progress = informationProgressService.getReportProgressByReportCode(preCode,proId);
            double progress = informationProgressService.getProgressByReportCode(preCode,proId);
            try {
                String setStr=  "set" + preCode.substring(0,1) + preCode.substring(1).toLowerCase();
                Method setMethod = InformationProgress.class.getMethod(setStr,Double.class);
                setMethod.invoke(informationProgress,progress);
            } catch (Exception e) {
                logger.error("ProgressRecursiveTask compute",e);
            }
        }

        return informationProgress;
    }



}
