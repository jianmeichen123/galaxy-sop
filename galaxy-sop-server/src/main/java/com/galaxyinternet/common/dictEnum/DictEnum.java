package com.galaxyinternet.common.dictEnum;

public enum DictEnum {
	
	项目类型("项目类型","projectType"),
	项目进度("项目进度","projectProgress"),
	
	会议类型("会议类型","meetingType"),
	
	会议结论("会议结论","meetingResult"),
	
	任务类型("任务类型","taskType"),
	任务状态("任务状态","taskStatus"),
	
	档案状态("档案状态","fileStatus"),
	档案业务分类("档案业务分类","fileWorktype"),
	档案存储类型("档案存储类型","fileType"),
	//排期状态
	scheduleStatusa("排期状态","scheduleStatus"),
	//行业归属
	行业归属("行业归属","industryOwn");
	
	private String name;

	private String code;

	private DictEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

    /**
     * 根据code get name
     * @param reslut
     * @return
     */
    public static String getNameByCode(String code) {
        
        if (code != null && !code.trim().equals("")) {
            
        	DictEnum[] values = DictEnum.values();
            for (int i = 0;i < values.length;i++) {
                if (code.equals(values[i].getCode())) {
                    return values[i].getName();
                }                
            }
        }
        return null;
    }
    
	//项目类型
	public enum projectType {
		投资("投资","projectType:1"),
		创建("创建","projectType:2");
		private String name;

		private String code;

		private projectType(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}
		
		/**
	     * 根据code get name
	     * @param reslut
	     * @return
	     */
	    public static String getNameByCode(String code) {
	        
	        if (code != null && !code.trim().equals("")) {
	            
	        	projectType[] values = projectType.values();
	            for (int i = 0;i < values.length;i++) {
	                if (code.equals(values[i].getCode())) {
	                    return values[i].getName();
	                }                
	            }
	        }
	        return null;
	    }
	}	
	
	//项目进度
	public enum projectProgress {
		接触访谈("接触访谈","projectProgress:1"),
		内部评审("内部评审","projectProgress:2"),
		CEO评审("CEO评审","projectProgress:3"),
		立项会("立项会","projectProgress:4"),
		投资意向书("投资意向书","projectProgress:5"),
		尽职调查("尽职调查","projectProgress:6"),
		投资决策会("投资决策会","projectProgress:7"),
		投资协议("投资协议","projectProgress:8"),
		股权交割("股权交割","projectProgress:9"),
		投后运营("投后运营","projectProgress:10");
		private String name;

		private String code;

		private projectProgress(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}
		
		/**
	     * 根据code get name
	     * @param reslut
	     * @return
	     */
	    public static String getNameByCode(String code) {
	        
	        if (code != null && !code.trim().equals("")) {
	            
	        	projectProgress[] values = projectProgress.values();
	            for (int i = 0;i < values.length;i++) {
	                if (code.equals(values[i].getCode())) {
	                    return values[i].getName();
	                }                
	            }
	        }
	        return null;
	    }
	}
		
	//会议类型
	public enum meetingType {
		内评会("内评会","meetingType:1"),
		CEO评审("CEO评审","meetingType:2"),
		立项会("立项会","meetingType:3"),
		投决会("投决会","meetingType:4");
		private String name;

		private String code;

		private meetingType(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}
		
		/**
	     * 根据code get name
	     * @param reslut
	     * @return
	     */
	    public static String getNameByCode(String code) {
	        
	        if (code != null && !code.trim().equals("")) {
	            
	        	meetingType[] values = meetingType.values();
	            for (int i = 0;i < values.length;i++) {
	                if (code.equals(values[i].getCode())) {
	                    return values[i].getName();
	                }                
	            }
	        }
	        return null;
	    }
	}
	
	//会议结论
	public enum meetingResult {
		通过("通过","meetingResult:1"),
		待定("待定","meetingResult:2"),
		否决("否决","meetingResult:3");
		private String name;

		private String code;

		private meetingResult(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}
		
		/**
	     * 根据code get name
	     * @param reslut
	     * @return
	     */
	    public static String getNameByCode(String code) {
	        
	        if (code != null && !code.trim().equals("")) {
	            
	        	meetingResult[] values = meetingResult.values();
	            for (int i = 0;i < values.length;i++) {
	                if (code.equals(values[i].getCode())) {
	                    return values[i].getName();
	                }                
	            }
	        }
	        return null;
	    }
	}		
	
	//任务类型
	public enum taskType {
		审批流程("审批流程领","taskType:1"),
		协同办公("协同办公","taskType:2");
		private String name;

		private String code;

		private taskType(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}
		
		/**
	     * 根据code get name
	     * @param reslut
	     * @return
	     */
	    public static String getNameByCode(String code) {
	        
	        if (code != null && !code.trim().equals("")) {
	            
	        	taskType[] values = taskType.values();
	            for (int i = 0;i < values.length;i++) {
	                if (code.equals(values[i].getCode())) {
	                    return values[i].getName();
	                }                
	            }
	        }
	        return null;
	    }
	}
	
	//任务状态
	public enum taskStatus {
		待认领("待认领","taskStatus:1"),
		待完工("待完工","taskStatus:2"),
		已完成("已完成","taskStatus:3");
		private String name;

		private String code;

		private taskStatus(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}
		
		/**
	     * 根据code get name
	     * @param reslut
	     * @return
	     */
	    public static String getNameByCode(String code) {
	        
	        if (code != null && !code.trim().equals("")) {
	            
	        	taskStatus[] values = taskStatus.values();
	            for (int i = 0;i < values.length;i++) {
	                if (code.equals(values[i].getCode())) {
	                    return values[i].getName();
	                }                
	            }
	        }
	        return null;
	    }

	}
			
	
	
	//档案类型
	public enum dalx {
		缺失("缺失","dalx:1"),
		已上传("任务类型","dalx:2"),
		已签署("已签署","dalx:3");
		private String name;

		private String code;

		private dalx(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}

	}	
	
	
	//档案状态
	public enum fileStatus{
		缺失("缺失","fileStatus:1"),
		已上传("已上传","fileStatus:2"),
		已签署("已签署","fileStatus:3");
		
		private String name;
		private String code;
		private fileStatus(String name, String code) {
			this.name = name;
			this.code = code;
		}
		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}
	}
	
	//档案业务分类
	public enum fileWorktype {
		业务尽职调查报告("业务尽职调查报告","fileWorktype:1"),
		人力资源尽职调查报告("人力资源尽职调查报告","fileWorktype:2"),
		法务尽职调查报告("法务尽职调查报告","fileWorktype:3"),
		财务尽职调查报告("财务尽职调查报告","fileWorktype:4"),
		投资意向书("投资意向书","fileWorktype:5"),
		投资协议("投资协议","fileWorktype:6"),
		股权转让协议("股权转让协议","fileWorktype:7"),
		工商转让凭证("工商转让凭证","fileWorktype:8"),
		资金拨付凭证("资金拨付凭证","fileWorktype:9"),
		公司资料("公司资料","fileWorktype:10"),
		财务预测报告("财务预测报告","fileWorktype:11"),
		商业计划("商业计划书","fileWorktype:12"),
		业务尽职调查清单("业务尽职调查清单","fileWorktype:13"),
		人力资源尽职调查清单("人力资源尽职调查清单","fileWorktype:14"),
		法务尽职调查清单("法务尽职调查清单","fileWorktype:15"),
		财务尽职调查清单("财务尽职调查清单","fileWorktype:16");
		
		private String name;

		private String code;

		private fileWorktype(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}
		

		/**
	     * 根据code get name
	     * @param reslut
	     * @return
	     */
	    public static String getNameByCode(String code) {
	        
	        if (code != null && !code.trim().equals("")) {
	            
	        	fileWorktype[] values = fileWorktype.values();
	            for (int i = 0;i < values.length;i++) {
	                if (code.equals(values[i].getCode())) {
	                    return values[i].getName();
	                }                
	            }
	        }
	        return null;
	    }
	}
	
	
	//档案存储类型
	public enum fileType {
		文档("文档","fileType:1"),
		音频文件("音频文件","fileType:2"),
		视频文件("视频文件","fileType:3"),
		图片("图片","fileType:4");
		private String name;

		private String code;

		private fileType(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}
		
		/**
	     * 根据code get name
	     * @param reslut
	     * @return
	     */
	    public static String getNameByCode(String code) {
	        
	        if (code != null && !code.trim().equals("")) {
	            
	        	fileType[] values = fileType.values();
	            for (int i = 0;i < values.length;i++) {
	                if (code.equals(values[i].getCode())) {
	                    return values[i].getName();
	                }                
	            }
	        }
	        return null;
	    }
	}
	//排期状态(秘书)
	public enum scheduleStatus {
		daipaiqi("待排期","scheduleStatus:1"),
		yipaiqi("已排期","scheduleStatus:2"),
		yitongguo("已通过","scheduleStatus:3");		
		private String name;

		private String code;

		private scheduleStatus(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}
		
		/**
	     * 根据code get name
	     * @param reslut
	     * @return
	     */
	    public static String getNameByCode(String code) {
	        
	        if (code != null && !code.trim().equals("")) {
	            
	        	scheduleStatus[] values = scheduleStatus.values();
	            for (int i = 0;i < values.length;i++) {
	                if (code.equals(values[i].getCode())) {
	                    return values[i].getName();
	                }                
	            }
	        }
	        return null;
	    }
	}
	
	//行业归属
	public enum industryOwn {
		//互联网餐饮("互联网餐饮","7"),
		//O2O及电商("O2O及电商","11"),
		电子商务("电子商务","11"),
		
		//社交媒体("社交媒体","16"),
		媒体营销("媒体营销","16"),
		
		互联网旅游("互联网旅游","10"),
		
		/**
		 * 部门调整新加:
		互联网物流
		工业互联网
		云计算服务
		金融科技
		物联网
		前沿科技
		*/
		互联网物流("互联网物流","3005"),
		
		//云计算大数据("云计算大数据","8"),
		大数据("大数据","8"),
		
		互联网农业("互联网农业","12"),
		
		/**
		 * 部门调整新加:
		互联网物流
		工业互联网
		云计算服务
		金融科技
		物联网
		前沿科技
		*/
		工业互联网("工业互联网","3006"),
		云计算服务("云计算服务","1999"),
		
		//虚拟现实("虚拟现实","31"),
		虚拟现实("虚拟现实/增强","31"),
		
		企业服务("企业服务","28"),
		
		互联网教育("互联网教育","14"),
		
		互联网汽车("互联网汽车","17"),
		
		数字娱乐("数字娱乐","5"),
		
		/**
		 * 部门调整新加:
		互联网物流
		工业互联网
		云计算服务
		金融科技
		物联网
		前沿科技
		*/
		金融科技("金融科技","2001"),
		
		//互联网医疗("互联网医疗","9"),
		生命科技("生命科技","9"),
		
		//互联网房地产("互联网房地产","26"),
		互联网房产("互联网房产","26"),
		
		//互联网金融("互联网金融","6"),
		互联网银行("互联网银行","6"),
		
		人工智能("人工智能","13"), 
		
		/**
		 * 部门调整新加:
		互联网物流
		工业互联网
		云计算服务
		金融科技
		物联网
		前沿科技
		*/
		物联网("物联网","3007"),
		前沿科技("前沿科技","3008");
		
		//--互联网服装("互联网服装","15")
		private String name;

		private String code;

		private industryOwn(String name, String code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public String getCode() {
			return code;
		}
		
		/**
	     * 根据code get name
	     * @param reslut
	     * @return
	     */
	    public static String getNameByCode(String code) {
	        
	        if (code != null && !code.trim().equals("")) {
	            
	        	industryOwn[] values = industryOwn.values();
	            for (int i = 0;i < values.length;i++) {
	                if (code.equals(values[i].getCode())) {
	                    return values[i].getName();
	                }                
	            }
	        }
	        return null;
	    }
	}
}

