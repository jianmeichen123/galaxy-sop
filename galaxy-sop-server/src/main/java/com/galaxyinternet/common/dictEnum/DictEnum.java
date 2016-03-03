package com.galaxyinternet.common.dictEnum;

public enum DictEnum {
	
	项目类型("项目类型","xmlx"),
	项目进度("项目进度","xmjd"),
	
	会议类型("会议类型","hylx"),
	
	会议结论("会议结论","hyjl"),
	
	任务名称("任务名称","rwmc"),
	任务类型("任务类型","rwlx"),
	任务状态("任务状态","rwzt"),
	
	档案类型("档案类型","dalx"),
	档案业务类型("档案业务类型","daywlx"),
	档案存储类型("档案存储类型","dacclx");
	
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

	
	//项目类型
	public enum xmlx {
		外部项目("外部项目","xmlx:1"),
		内部创建("内部创建","xmlx:2");
		private String name;

		private String code;

		private xmlx(String name, String code) {
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
	
	//项目进度
	public enum xmjd {
		接触访谈("接触访谈","xmjd:1"),
		内部评审("内部评审","xmjd:2"),
		CEO评审("CEO评审","xmjd:3"),
		立项会("立项会","xmjd:4"),
		投资意向书("投资意向书","xmjd:5"),
		尽职调查("尽职调查","xmjd:6"),
		投资决策会("投资决策会","xmjd:7"),
		投资协议("投资协议","xmjd:8"),
		股权交割("股权交割","xmjd:9"),
		投后运营("投后运营","xmjd:10");
		private String name;

		private String code;

		private xmjd(String name, String code) {
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
		
	//会议类型
	public enum hylx {
		内评会("内评会","hylx:1"),
		CEO评审("CEO评审","hylx:2"),
		立项会("立项会","hylx:3"),
		投决会("投决会","hylx:4");
		private String name;

		private String code;

		private hylx(String name, String code) {
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
	
	//会议结论
	public enum hyjl {
		通过("通过","hyjl:1"),
		待定("待定","hyjl:2"),
		否决("否决","hyjl:3");
		private String name;

		private String code;

		private hyjl(String name, String code) {
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
	
	//任务名称,
	public enum rwmc {
		审批流程("审批流程领","rwlx:1"),
		协同办公("协同办公","rwlx:2");
		private String name;

		private String code;

		private rwmc(String name, String code) {
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
	
	
	//任务类型
	public enum rwlx {
		审批流程("审批流程领","rwlx:1"),
		协同办公("协同办公","rwlx:2");
		private String name;

		private String code;

		private rwlx(String name, String code) {
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
	
	//任务状态
	public enum rwzt {
		待认领("待认领","rwzt:1"),
		待完工("待完工","rwzt:2"),
		已完成("已完成","rwzt:3");
		private String name;

		private String code;

		private rwzt(String name, String code) {
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
	
	
	//档案业务类型
	public enum daywlx {
		商业计划书("商业计划书","daywlx:1"),
		尽职调查报告("尽职调查报告","daywlx:2"),
		会议纪要("会议纪要","daywlx:3"),
		投资意向书("投资意向书","daywlx:4"),
		投资协议("投资协议","daywlx:5"),
		股权转让协议("股权转让协议","daywlx:6"),
		凭证图("凭证图","daywlx:7"),
		资金拨付凭证("资金拨付凭证","daywlx:8"),
		工商变更登记凭证("工商变更登记凭证","daywlx:9");
		
		private String name;

		private String code;

		private daywlx(String name, String code) {
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
	
	
	//档案存储类型
	public enum dacclx {
		档案("档案","dacclx:1"),
		音频文件("待音频文件","dacclx:2"),
		视频文件("视频文件","dacclx:3"),
		图片("图片","dacclx:4");
		private String name;

		private String code;

		private dacclx(String name, String code) {
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
	
	


}

