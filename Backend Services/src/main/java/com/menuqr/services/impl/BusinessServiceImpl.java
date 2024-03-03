package com.menuqr.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.menuqr.models.BusinessModel;
import com.menuqr.models.ItemCategoryModel;
import com.menuqr.models.ItemModel;
import com.menuqr.repository.IBusinessRepository;
import com.menuqr.repository.IItemCategoryRepository;
import com.menuqr.repository.IItemRepository;
import com.menuqr.services.IBusinessService;

@Service
public class BusinessServiceImpl implements IBusinessService{
	
	@Autowired 
	private IBusinessRepository businessRepository;
	
		
	@Autowired 
	private IItemCategoryRepository itemCategoryRepository;
	
	@Autowired 
	private IItemRepository itemRepository;

	@Override
	public BusinessModel getBusinessById(int businessId) {
		BusinessModel result = businessRepository.getBusinessById(businessId);
		List<ItemCategoryModel> itemCategories = itemCategoryRepository.getByBusinessId(result.getId());
		result.setItemCategories(itemCategories);
		for(ItemCategoryModel itemCategory: itemCategories) {
			List<ItemModel> items = itemRepository.getByItemCategoryId(itemCategory.getId());
			itemCategory.setItems(items);
		}
		return result;
	}

	@Override
	public List<BusinessModel> GetBusinessesByLocation(String longitude, String latitude) {
		List<BusinessModel> results = businessRepository.getBusinessByLocation(longitude, latitude);
		for(BusinessModel business: results) {
			List<ItemCategoryModel> itemCategories = itemCategoryRepository.getByBusinessId(business.getId());
			business.setItemCategories(itemCategories);
			for(ItemCategoryModel itemCategory: itemCategories) {
				List<ItemModel> items = itemRepository.getByItemCategoryId(itemCategory.getId());
				itemCategory.setItems(items);
			}
		}
		return results;
	}
	
	

}
