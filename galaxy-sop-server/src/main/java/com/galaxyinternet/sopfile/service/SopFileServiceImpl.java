package com.galaxyinternet.sopfile.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.bo.sopfile.SopVoucherFileBo;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.service.SopFileService;

@Service("com.galaxyinternet.service.SopFileService")
public class SopFileServiceImpl extends BaseServiceImpl<SopFile> implements
		SopFileService {
	
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private SopVoucherFileDao voucherFileDao;

	@Override
	protected BaseDao<SopFile, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.sopFileDao;
	}

	
	
	@Override
	public List<SopFile> selectByFileTypeList(SopFileBo sbo) {
		return sopFileDao.queryByFileTypeList(sbo);
	}
	
	/**
	 * 通过项目ID及业务分类获取唯一档案
	 * @param sf
	 * @return
	 */
	public SopFile selectByProjectAndFileWorkType(SopFile sf){
		return sopFileDao.queryByProjectAndFileWorkType(sf);
	}


	
	public Page<SopFile> queryPageList(SopFile query, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<SopFile> pageEntity = super.queryPageList(query, pageable);
		List<SopFile> sopFileList = pageEntity.getContent();
//		Map<String,Object> map = new HashMap<String,Object>();
//		map
//		sopFileDao.queryProjectName(sopFileList);
//		return null;
		return null;
	}



	@Override
	public List<SopFile> queryList(SopFile query) {
		List<SopFile> list = super.queryList(query);
		if(list != null && list.size()>0)
		{
			List<Long> vIds = new ArrayList<Long>();
			for(SopFile file : list)
			{
				if(file.getVoucherId() != null)
				{
					vIds.add(file.getVoucherId());
				}
			}
			if(vIds != null && vIds.size()>0)
			{
				SopVoucherFileBo bo = new SopVoucherFileBo();
				bo.setIds(vIds.toArray(new Long[vIds.size()]));
				List<SopVoucherFile> voucherList = voucherFileDao.selectList(bo);
				for(SopFile file : list)
				{
					if(file.getVoucherId() != null)
					{
						for(SopVoucherFile voucher : voucherList)
						{
							if(voucher.getId().equals(file.getVoucherId()))
							{
								file.setVoucherFileName(voucher.getFileName());
							}
						}
					}
				}
				
			}
		}
		return list;
	}
	
	
	
	
	

}
