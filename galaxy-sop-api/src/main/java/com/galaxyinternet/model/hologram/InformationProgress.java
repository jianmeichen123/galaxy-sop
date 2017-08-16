package com.galaxyinternet.model.hologram;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class InformationProgress  extends PagableEntity {
    private static final long serialVersionUID = 1L;

    private Long projectId;

    private Long uid;

    private Double no;

    private Double dn;

    private Double pn;

    private Double gn;

    private Double on;

    private Double en;

    private Double cn;


    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Double getNo() {
        return no;
    }

    public void setNo(Double no) {
        this.no = no;
    }

    public Double getDn() {
        return dn;
    }

    public void setDn(Double dn) {
        this.dn = dn;
    }

    public Double getPn() {
        return pn;
    }

    public void setPn(Double pn) {
        this.pn = pn;
    }

    public Double getGn() {
        return gn;
    }

    public void setGn(Double gn) {
        this.gn = gn;
    }

    public Double getOn() {
        return on;
    }

    public void setOn(Double on) {
        this.on = on;
    }

    public Double getEn() {
        return en;
    }

    public void setEn(Double en) {
        this.en = en;
    }

    public Double getCn() {
        return cn;
    }

    public void setCn(Double cn) {
        this.cn = cn;
    }




}