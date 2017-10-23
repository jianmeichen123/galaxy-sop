package com.galaxyinternet.service.hologram;

import com.galaxyinternet.model.project.Project;

import java.util.Map;
import java.util.Set;

public interface ReportExportService {

    public Map<String, Object> titleAnswerConversion(Long uid, Long proId, String preCode);
    /**
     * 查询出题答案
     *
     * NO  全息报告
     * DN  尽调报告
     * PN  决策报告
     * GN  融资报告
     * ON  运营报告
     * EN  评测报告
     * CN  初评报告
     * @param currentMark imageDir currentMark_tempName
     */
    public Map<String, Object> titleAnswerConversionTask(Long uid, Project project, String preCode, String currentMark, String tempfilePath);//currentMark_tempName

    public Map<String,Object> projectTitleResult(Set<Long> ids, Long projectId, Map<Long, String> valueIdNameMap);
    public Map<String,Object> resultTitleResult(Set<Long> ids, Long projectId, Map<Long, String> valueIdNameMap, String precode);
    public Map<String,Object> listdataTitleResult(Set<Long> ids, Long projectId, Map<Long, String> valueIdNameMap);
    public Map<String,Object> fixedtableTitleResult(Set<Long> ids, Long projectId, Map<Long, String> valueIdNameMap);
    public Map<String,Object> fileTitleResult(Set<Long> ids, Long projectId, String currentMark, String tempfilePath) throws Exception;
    public Map<String,Object> fileTitleResult(Set<Long> ids, Long projectId); //doc
    public Map<String,Object> titleScoreResult(Long projectId, String precode);

}
