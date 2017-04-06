package com.galaxyinternet.hologram.dao;
import org.springframework.stereotype.Repository;
import com.galaxyinternet.dao.hologram.InformationFileDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hologram.InformationFile;

@Repository("informationFileDao")
public class InformationFileDaoImpl extends BaseDaoImpl<InformationFile, Long> implements InformationFileDao{

}
