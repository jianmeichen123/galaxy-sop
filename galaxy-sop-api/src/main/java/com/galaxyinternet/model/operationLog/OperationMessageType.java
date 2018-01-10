package com.galaxyinternet.model.operationLog;

import com.galaxyinternet.common.enums.DictEnum.MessageType;

public enum OperationMessageType
{
	DEL_PROJECT("/galaxy/project/deletePro",						MessageType.DEL_PROJECT.getCode()),
	TRANSFER_PROJECT("/galaxy/projectTransfer/applyTransfer",		MessageType.TRANSFER_PROJECT.getCode()),
	ASSIGN_PROJECT("/galaxy/projectTransfer/assign",				MessageType.ASSIGN_PROJECT.getCode()),
	CLAIM_TASK("/galaxy/soptask/goClaimtcPage",						MessageType.CLAIM_TASK.getCode()),
	TRANSFER_TASK("/galaxy/soptask/transfer",						MessageType.TRANSFER_TASK.getCode()),
	GIVEUP_TASK("/galaxy/soptask/giveup",							MessageType.GIVEUP_TASK.getCode()),
	ASSIGN_TASK("/galaxy/soptask/assign",							MessageType.ASSIGN_TASK.getCode()),
	INVESTIGATION1("/galaxy/progress/stageChange/"+UrlNumber.seven,	MessageType.INVESTIGATION.getCode()),
	INVESTIGATION2("/galaxy/progress/stageChange/"+UrlNumber.eight,	MessageType.INVESTIGATION.getCode()),
	DELIVERY1("/galaxy/progress/stageChange/"+UrlNumber.eleven,		MessageType.DELIVERY.getCode()),
	DELIVERY2("/galaxy/progress/stageChange/"+UrlNumber.twelve,		MessageType.DELIVERY.getCode()),
	COMPLETE_TASK1("/galaxy/soptask/submitTask/"+UrlNumber.two,		MessageType.COMPLETE_TASK.getCode()),
	COMPLETE_TASK2("/galaxy/soptask/submitTask/"+UrlNumber.three,	MessageType.COMPLETE_TASK.getCode()),
	COMPLETE_TASK3("/galaxy/soptask/submitTask/"+UrlNumber.four,	MessageType.COMPLETE_TASK.getCode()),
	COMPLETE_TASK4("/galaxy/soptask/submitTask/"+UrlNumber.five,	MessageType.COMPLETE_TASK.getCode()),
	COMPLETE_TASK5("/galaxy/soptask/submitTask/"+UrlNumber.six,		MessageType.COMPLETE_TASK.getCode())
	;
	
	private String uniqueKey;
	private String messageType;
	private OperationMessageType(String uniqueKey, String messageType)
	{
		this.uniqueKey = uniqueKey;
		this.messageType = messageType;
	}
	
	public static OperationMessageType getObject(String uniqueKey) {
		OperationMessageType[] types = OperationMessageType.values();
		OperationMessageType result = null;
		for (OperationMessageType type : types) {
			if (type.getUniqueKey()!=null && type.getUniqueKey().trim().length()>0){
					
				String requestNum = uniqueKey.substring(uniqueKey.lastIndexOf("/"));
				String localNum = type.getUniqueKey().substring(type.getUniqueKey().lastIndexOf("/"));
				
				if(requestNum.equals(localNum)){
					if ( uniqueKey.substring(0,uniqueKey.lastIndexOf("/")).contains(type.getUniqueKey().substring(0,type.getUniqueKey().lastIndexOf("/")))) {
						result = type;
						break;
					}
				}else if ( uniqueKey.contains(type.getUniqueKey())) {
					result = type;
					break;
				}
			}
		}
		return result;
	}
	public String getUniqueKey()
	{
		return uniqueKey;
	}
	public String getMessageType()
	{
		return messageType;
	}
	
}
