package com.galaxyinternet.model;

import com.galaxyinternet.framework.core.model.BaseEntity;

import java.util.Arrays;
import java.util.List;

public class SopSearchHistory extends BaseEntity {

    private String isOld; // "y":历史数据，不保存
    private Long uid;

    private String content;
    private String spiltMark; //"<nbsp>"
    private List<String> hisList;

    private String remark;

    private Integer type;

    private Integer isDelete; //0:正常数据；1:已删除数据；



    public String getIsOld() {
        return isOld;
    }

    public void setIsOld(String isOld) {
        this.isOld = isOld;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();

        if(this.content!=null ){
            if(this.spiltMark==null){
                this.spiltMark="<nbsp>";
            }
            String[] arr = this.content.split(spiltMark);
            this.hisList = Arrays.asList(arr);
        }
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        if(isDelete == null){
            isDelete = 0;
        }
        this.isDelete = isDelete;
    }


    public List<String> getHisList() {
        return hisList;
    }

    public void setHisList(List<String> hisList) {
        this.hisList = hisList;
    }

    public String getSpiltMark() {
        return spiltMark;
    }

    public void setSpiltMark(String spiltMark) {
        this.spiltMark = spiltMark;
    }
}