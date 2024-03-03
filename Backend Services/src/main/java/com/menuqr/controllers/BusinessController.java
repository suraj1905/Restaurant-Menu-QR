package com.menuqr.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.menuqr.helpers.FileHandler;
import com.menuqr.helpers.ResponseHandler;
import com.menuqr.helpers.Util;
import com.menuqr.models.BusinessModel;
import com.menuqr.models.ClientModel;
import com.menuqr.models.ItemCategoryModel;
import com.menuqr.models.ItemModel;
import com.menuqr.repository.IBusinessRepository;
import com.menuqr.repository.IClientRepository;
import com.menuqr.repository.IItemCategoryRepository;
import com.menuqr.repository.IItemRepository;
import com.menuqr.services.IBusinessService;

import net.minidev.json.JSONObject;



@RestController
public class BusinessController {
	private static final Logger logger = LoggerFactory.getLogger(BusinessController.class);

	@Autowired 
	private IBusinessRepository businessRepository;
	
	@Autowired 
	private IBusinessService businessService;
	
	@Autowired 
	private IClientRepository clientRepository;
		
	@Autowired 
	private IItemCategoryRepository itemCategoryRepository;
	
	@Autowired 
	private IItemRepository itemRepository;
	
	@CrossOrigin
	@PostMapping(value="Business/Add")
	public ResponseEntity<Object> Add(@RequestHeader(value = "Authorization", required = false) String Authorization,BusinessModel business, MultipartFile imageFile,String mobileNumber){
		logger.info("Business Add Method "+LocalDateTime.now());
		try {
			if(Authorization == null) {
				return ResponseHandler.generateResponse("Missing required Header","Invalid User",HttpStatus.FORBIDDEN,false,"");
			}
			if(mobileNumber == null || mobileNumber.isEmpty()) {
				return ResponseHandler.generateResponse("Mobile number required","Validation error",HttpStatus.BAD_REQUEST,false,"");
			}
			else if(imageFile == null || imageFile.isEmpty()) {
				return ResponseHandler.generateResponse("Image File required","Validation error",HttpStatus.BAD_REQUEST,false,"");
			}
			JSONObject tokenData = Util.decodeJWT(Authorization);
			ClientModel client = clientRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(client.getIsactive() != 0 && client.getMobileNumber().equalsIgnoreCase(mobileNumber)) {
				String imageUrl = FileHandler.saveFile(imageFile, business.getName());
				business.setImageUrl(imageUrl);
				business.setClientId(client.getId());
				business.setIsOpen(0);
				businessRepository.save(business);
				return ResponseHandler.generateResponse("Business Added Successfully!","Business Added Successfully!", HttpStatus.OK,true,"" );
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Business Add Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	@CrossOrigin
	@GetMapping(value="Business/Get")
	public ResponseEntity<Object> Get(int businessId){
		logger.info("Business Get Method "+LocalDateTime.now());
		try {
			BusinessModel result = businessService.getBusinessById(businessId);
			return ResponseHandler.generateResponse("Fetched Successfully","Fetched Successfully", HttpStatus.OK,true, result );				
		}
		catch(Exception ex) {
			logger.error("Business Get Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	@CrossOrigin
	@GetMapping(value="Business/GetBusinessesByLocation")
	public ResponseEntity<Object> GetBusinessesByLocation(String longitude, String latitude){
		logger.info("Business GetBusinessesByLocation Method "+LocalDateTime.now());
		try {
			List<BusinessModel> results = businessService.GetBusinessesByLocation(longitude, latitude);
			return ResponseHandler.generateResponse("Fetched Successfully","Fetched Successfully", HttpStatus.OK,true, results );				
		}
		catch(Exception ex) {
			logger.error("Business GetBusinessesByLocation Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	@CrossOrigin
	@GetMapping(value="Business/GetClientBusiness")
	public ResponseEntity<Object> GetClientBusiness(@RequestHeader(value = "Authorization", required = false) String Authorization){
		logger.info("Business GetClientBusiness Method "+LocalDateTime.now());
		try {
			JSONObject tokenData = Util.decodeJWT(Authorization);
			ClientModel client = clientRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(client.getIsactive() != 0) {
				List<BusinessModel> results = businessRepository.getBusinessesByClientId(client.getId());
				for(BusinessModel business: results) {
					List<ItemCategoryModel> itemCategories = itemCategoryRepository.getByBusinessId(business.getId());
					business.setItemCategories(itemCategories);
					for(ItemCategoryModel itemCategory: itemCategories) {
						List<ItemModel> items = itemRepository.getByItemCategoryId(itemCategory.getId());
						itemCategory.setItems(items);
					}
				}
				return ResponseHandler.generateResponse("Fetched Successfully","Fetched Successfully", HttpStatus.OK,true, results );				
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Business GetClientBusiness Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}

	@CrossOrigin
	@PostMapping(value="Business/AddItemCategory")
	public ResponseEntity<Object> AddItemCategory(@RequestHeader(value = "Authorization", required = false) String Authorization,ItemCategoryModel itemCategory, String mobileNumber){
		logger.info("Business AddItemCategory Method "+LocalDateTime.now());
		ItemCategoryModel addedItemCategory = new ItemCategoryModel();
		try {
			if(Authorization == null) {
				return ResponseHandler.generateResponse("Missing required Header","Invalid User",HttpStatus.FORBIDDEN,false,"");
			}
			if(mobileNumber == null || mobileNumber.isEmpty()) {
				return ResponseHandler.generateResponse("Mobile number required","Validation error",HttpStatus.BAD_REQUEST,false,"");
			}
			JSONObject tokenData = Util.decodeJWT(Authorization);
			ClientModel client = clientRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(client.getIsactive() != 0 && client.getMobileNumber().equalsIgnoreCase(mobileNumber)) {
				addedItemCategory = itemCategoryRepository.saveAndFlush(itemCategory);
				addedItemCategory.setItems(new ArrayList<>());
				return ResponseHandler.generateResponse("Item Category Added Successfully!","Item Category  Added Successfully!", HttpStatus.OK,true,addedItemCategory );
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Business AddItemCategory Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	@CrossOrigin
	@PutMapping(value="Business/updateItemCategory")
	public ResponseEntity<Object> updateItemCategory(@RequestHeader(value = "Authorization", required = false) String Authorization,ItemCategoryModel updatedItemCategory, String mobileNumber, @RequestParam int itemCategoryId){
		logger.info("Business updateItemCategory Method "+LocalDateTime.now());
		ItemCategoryModel itemCategory = new ItemCategoryModel();
		try {
			if(Authorization == null) {
				return ResponseHandler.generateResponse("Missing required Header","Invalid User",HttpStatus.FORBIDDEN,false,"");
			}
			if(mobileNumber == null || mobileNumber.isEmpty()) {
				return ResponseHandler.generateResponse("Mobile number required","Validation error",HttpStatus.BAD_REQUEST,false,"");
			}
			JSONObject tokenData = Util.decodeJWT(Authorization);
			ClientModel client = clientRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(client.getIsactive() != 0 && client.getMobileNumber().equalsIgnoreCase(mobileNumber)) {
				itemCategory = itemCategoryRepository.getById(itemCategoryId,client.getId());
				if(itemCategory == null) {
					return ResponseHandler.generateResponse("Item Category doesn't belong to requestor","Invalid Item Category", HttpStatus.BAD_REQUEST,false,"" );
				}
				itemCategory.setItemCategoryName(updatedItemCategory.getItemCategoryName());
				itemCategory = itemCategoryRepository.saveAndFlush(itemCategory);
				if(itemCategory.getItems() == null) {
					itemCategory.setItems(new ArrayList<>());
				}
				return ResponseHandler.generateResponse("Item Category updated Successfully!","Item Category  updated Successfully!", HttpStatus.OK,true,itemCategory );
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Business updateItemCategory Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	
	@CrossOrigin
	@PutMapping(value="Business/UpdateStoreStatus")
	public ResponseEntity<Object> UpdateStoreStatus(@RequestHeader(value = "Authorization", required = false) String Authorization,boolean isOpen,boolean isDelivering, String mobileNumber){
		logger.info("Business UpdateStoreStatus Method "+LocalDateTime.now());
		try {
			if(Authorization == null) {
				return ResponseHandler.generateResponse("Missing required Header","Invalid User",HttpStatus.FORBIDDEN,false,"");
			}
			if(mobileNumber == null || mobileNumber.isEmpty()) {
				return ResponseHandler.generateResponse("Mobile number required","Validation error",HttpStatus.BAD_REQUEST,false,"");
			}
			JSONObject tokenData = Util.decodeJWT(Authorization);
			ClientModel client = clientRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(client.getIsactive() != 0 && client.getMobileNumber().equalsIgnoreCase(mobileNumber)) {
				BusinessModel business = businessRepository.getBusinessByClientId(client.getId());
				if(business == null) {
					return ResponseHandler.generateResponse("Invalid Business","Invalid Business", HttpStatus.BAD_REQUEST,false,"" );
				}
				if(isOpen) {
					business.setIsOpen(1);
				}
				else {
					business.setIsOpen(0);
				}
				businessRepository.save(business);
				return ResponseHandler.generateResponse("Store Status Updated!","Store Status Updated!", HttpStatus.OK,true,"" );
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Business UpdateStoreStatus Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	@CrossOrigin
	@PostMapping(value="Business/AddItem")
	public ResponseEntity<Object> AddItem(@RequestHeader(value = "Authorization", required = false) String Authorization,ItemModel item, String mobileNumber, MultipartFile imageFile){
		logger.info("Business AddItem Method "+LocalDateTime.now());
		ItemModel addedItem = new ItemModel();
		try {
			if(Authorization == null) {
				return ResponseHandler.generateResponse("Missing required Header","Invalid User",HttpStatus.FORBIDDEN,false,"");
			}
			if(mobileNumber == null || mobileNumber.isEmpty()) {
				return ResponseHandler.generateResponse("Mobile number required","Validation error",HttpStatus.BAD_REQUEST,false,"");
			}
			else if(imageFile == null || imageFile.isEmpty()) {
				return ResponseHandler.generateResponse("Image File required","Validation error",HttpStatus.BAD_REQUEST,false,"");
			}
			JSONObject tokenData = Util.decodeJWT(Authorization);
			ClientModel client = clientRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(client.getIsactive() != 0 && client.getMobileNumber().equalsIgnoreCase(mobileNumber)) {
				String businessName = businessRepository.getBusinessByCategoryId(item.getItemCategoryId());
				if(businessName == null || businessName.isEmpty()) {
					return ResponseHandler.generateResponse("Item Category doesn't belong to requestor","Add Item Failed!", HttpStatus.BAD_REQUEST,false,"" );
				}
				item.setIsAvailable(1);
				String imageUrl = FileHandler.saveFile(imageFile,businessName);
				item.setImageUrl(imageUrl);
				addedItem = itemRepository.saveAndFlush(item);
				return ResponseHandler.generateResponse("Item Added Successfully!","Item Added Successfully!", HttpStatus.OK,true,addedItem );
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Business AddItem Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	@CrossOrigin
	@PutMapping(value="Business/updateItem")
	public ResponseEntity<Object> updateItem(@RequestHeader(value = "Authorization", required = false) String Authorization,MultipartFile imageFile, ItemModel updatedItem, String mobileNumber,@RequestParam int itemId){
		logger.info("Business UpdateItem Method "+LocalDateTime.now());
		try {
			if(Authorization == null) {
				return ResponseHandler.generateResponse("Missing required Header","Invalid User",HttpStatus.FORBIDDEN,false,"");
			}
			if(mobileNumber == null || mobileNumber.isEmpty()) {
				return ResponseHandler.generateResponse("Mobile number required","Validation error",HttpStatus.BAD_REQUEST,false,"");
			}
			JSONObject tokenData = Util.decodeJWT(Authorization);
			ClientModel client = clientRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(client.getIsactive() != 0 && client.getMobileNumber().equalsIgnoreCase(mobileNumber)) {
				ItemModel item =itemRepository.getByItemId(itemId,client.getId()) ;
				if(item == null) {
					return ResponseHandler.generateResponse("Item doesn't belong to requestor","Invalid Item", HttpStatus.BAD_REQUEST,false,"" );
				}
				item.setItemName(updatedItem.getItemName() == null || updatedItem.getItemName().isEmpty() ? item.getItemName(): updatedItem.getItemName());
				item.setItemDescription( updatedItem.getItemDescription() == null || updatedItem.getItemDescription().isEmpty() ?  item.getItemDescription():updatedItem.getItemDescription());
				item.setItemCategoryId(item.getItemCategoryId());
				item.setItemPrice(updatedItem.getItemPrice() == 0.0? item.getItemPrice() : updatedItem.getItemPrice());
				item.setIsAvailable(updatedItem.getIsAvailable());
				item.setIsVeg(updatedItem.getIsVeg());
				item.setQuantity(updatedItem.getQuantity()== 0 ? item.getQuantity(): updatedItem.getQuantity() );
				String businessName = businessRepository.getBusinessByCategoryId(item.getItemCategoryId()); 
				if(imageFile != null) {
					String imageUrl = FileHandler.saveFile(imageFile, businessName);
					FileHandler.deleteFile(item.getImageUrl(),businessName);
					item.setImageUrl(imageUrl);
				}
				item = itemRepository.saveAndFlush(item);
				return ResponseHandler.generateResponse("Item Updated Successfully!","Item Updated Successfully!", HttpStatus.OK,true,item );
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Business UpdateItem Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	@CrossOrigin
	@GetMapping(value="Business/GetItemCategory")
	public ResponseEntity<Object> GetItemCategory(@RequestHeader(value = "Authorization", required = false) String Authorization,int businessId, String mobileNumber){
		logger.info("Business GetItemCategory Method "+LocalDateTime.now());
		List<ItemCategoryModel> itemCategories = new ArrayList<ItemCategoryModel>();
		try {
			if(Authorization == null) {
				return ResponseHandler.generateResponse("Missing required Header","Invalid User",HttpStatus.FORBIDDEN,false,"");
			}
			if(mobileNumber == null || mobileNumber.isEmpty()) {
				return ResponseHandler.generateResponse("Mobile number required","Validation error",HttpStatus.BAD_REQUEST,false,"");
			}
			JSONObject tokenData = Util.decodeJWT(Authorization);
			ClientModel client = clientRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(client.getIsactive() != 0 && client.getMobileNumber().equalsIgnoreCase(mobileNumber)) {
				itemCategories = itemCategoryRepository.getByBusinessId(businessId);
				for(ItemCategoryModel itemCategory: itemCategories) {
					List<ItemModel> items = itemRepository.getByItemCategoryId(itemCategory.getId());
					itemCategory.setItems(items);
				}
				return ResponseHandler.generateResponse("Item Category fetched Successfully!","Item Category fetched Successfully!", HttpStatus.OK,true,itemCategories );
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Business GetItemCategory Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}
	
	@CrossOrigin
	@GetMapping(value="Business/GetItems")
	public ResponseEntity<Object> GetItems(@RequestHeader(value = "Authorization", required = false) String Authorization,int itemCategoryId, String mobileNumber){
		logger.info("Business GetItems Method "+LocalDateTime.now());
		List<ItemModel> items = new ArrayList<ItemModel>();
		try {
			if(Authorization == null) {
				return ResponseHandler.generateResponse("Missing required Header","Invalid User",HttpStatus.FORBIDDEN,false,"");
			}
			if(mobileNumber == null || mobileNumber.isEmpty()) {
				return ResponseHandler.generateResponse("Mobile number required","Validation error",HttpStatus.BAD_REQUEST,false,"");
			}
			JSONObject tokenData = Util.decodeJWT(Authorization);
			ClientModel client = clientRepository.getById(Integer.parseInt(tokenData.getAsString("id")));
			if(client.getIsactive() != 0 && client.getMobileNumber().equalsIgnoreCase(mobileNumber)) {
				items = itemRepository.getByItemCategoryId(itemCategoryId);
				return ResponseHandler.generateResponse("Items fetched Successfully!","Item fetched Successfully!", HttpStatus.OK,true,items );
			}
			return ResponseHandler.generateResponse("Invalid Token","Unauthorized Access", HttpStatus.FORBIDDEN,false,"" );				
		}
		catch(Exception ex) {
			logger.error("Business GetItems Method Exception: "+ex.toString());
			return ResponseHandler.generateResponse(ex.toString(),"Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR,false,"" );
		}
	}

}
