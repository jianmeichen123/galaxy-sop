package com.galaxyinternet.hologram.controller;

import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.InformationListdataService;
import com.galaxyinternet.service.hologram.InformationTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zcy on 17-3-13.
 */

@Controller
@RequestMapping("/galaxy/team")
public class InformationListdataController extends BaseControllerImpl<InformationListdata,InformationListdata> {

    @Autowired
    private InformationListdataService informationListdataService;

    @Override
    protected BaseService<InformationListdata> getBaseService() {
        return this.informationListdataService;
    }


    @RequestMapping
    @ResponseBody
    public ResponseData<InformationListdata> query(HttpServletRequest request, @PathVariable("pinfoKey") Long parentId ) {
        return null;
    }
}
