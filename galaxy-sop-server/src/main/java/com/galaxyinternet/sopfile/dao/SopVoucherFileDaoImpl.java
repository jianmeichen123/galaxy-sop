package com.galaxyinternet.sopfile.dao;


import org.springframework.stereotype.Repository;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.sopfile.SopVoucherFile;

@Repository("sopVoucherFileDao")
public class SopVoucherFileDaoImpl extends BaseDaoImpl<SopVoucherFile, Long> implements SopVoucherFileDao  {


}
