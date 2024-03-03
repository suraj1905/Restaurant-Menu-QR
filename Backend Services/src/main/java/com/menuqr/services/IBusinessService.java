package com.menuqr.services;


import java.util.List;

import com.menuqr.models.BusinessModel;


public interface IBusinessService {
	
	BusinessModel getBusinessById(int businessId);
	
	List<BusinessModel> GetBusinessesByLocation(String longitude, String latitude);
	
}