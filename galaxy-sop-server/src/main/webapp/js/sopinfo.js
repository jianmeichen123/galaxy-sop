function showTabs(id,index)
{
	switch(index)
	{
	case 0 : 
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/project/detail/" + id);
		break;
	case 1 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/personTab/" + id);
		break;
	case 2 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/project/showProject/" + id+"/2");
		break;
	case 3 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/project/proview/" + id);
		break;
	case 4 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/project/promeet/" + id);
		break;
	case 5 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/project/toFileList/" + id);
		break;
	case 6 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/project/toprolog/" + id);
		break;
	case 7 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/delivery/toprodeliver/" + id);
		break;
	case 8 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/project/toAppropriation/" + id);
		break;
	case 9 :
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/project/postOperation/toPostMeeting/" + id);
		break;
	default : 
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/project/detail/" + id);
	}
	

}