function dateFormatter(val,row,index)
	{
		if(!isNaN(val))
		{
			return Number(val).toDate().format("yyyy-MM-dd");
		}
		return val;
	}
	function progressFormatter(val,row,index)
	{
		if(val != null)
		{
			return $('[name="ideaProgress"] option[value="'+val+'"]').text();
		}
	}
	
	function refreshIdeaList()
	{
		$("#data-table").bootstrapTable('refresh');
	}
	function getDepartment()
	{
		sendGetRequest(
				platformUrl.getIdeaDepartment,
				null,
				function(data){
					if(data.result.status = 'OK')
					{
						var $depField = $("#custom-toolbar [name='departmentId']");
						$depField.empty();
						if(data.entityList.length >1)
						{
							$depField.append('<option value="">全部</option>');
						}
						$.each(data.entityList,function(){
							$depField.append('<option value="'+this.id+'">'+this.name+'</option>');
						});
					}
				}
		);
	}