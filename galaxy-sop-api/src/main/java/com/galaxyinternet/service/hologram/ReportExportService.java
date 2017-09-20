package com.galaxyinternet.service.hologram;

import com.galaxyinternet.model.project.Project;

import java.util.Map;
import java.util.Set;

public interface ReportExportService {

    public Map<String, Object> titleAnswerConversion(Long uid, Long proId, String preCode);
    public Map<String, Object> titleAnswerConversionTask(Long uid, Project project, String preCode);

    public Map<String,Object> projectTitleResult(Set<Long> ids, Long projectId);
    public Map<String,Object> resultTitleResult(Set<Long> ids,Long projectId, Map<Long, String> valueIdNameMap);
    public Map<String,Object> listdataTitleResult(Set<Long> ids,Long projectId, Map<Long, String> valueIdNameMap);
    public Map<String,Object> fixedtableTitleResult(Set<Long> ids,Long projectId, Map<Long, String> valueIdNameMap);
    public Map<String,Object> fileTitleResult(Set<Long> ids,Long projectId);

}
