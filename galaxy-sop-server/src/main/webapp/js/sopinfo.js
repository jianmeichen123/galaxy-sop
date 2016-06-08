function showTabs(id,index)
{
	switch(index)
	{
	case 0 : 
		
	case 1 :
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
	case 6 :
	default : 
		forwardWithHeader(Constants.sopEndpointURL+"/galaxy/project/detail/" + id);
	
	}
}