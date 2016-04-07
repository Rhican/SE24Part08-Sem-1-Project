/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;

import edu.nus.iss.SE24PT8.universityStore.domain.Category;
import edu.nus.iss.SE24PT8.universityStore.domain.Vendor;
import edu.nus.iss.SE24PT8.universityStore.exception.BadVendorException;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Mugunthan
 */
public class VendorManager {

	private static VendorManager instance = null;
	private HashMap<String, ArrayList<Vendor>> vendors = new HashMap<String, ArrayList<Vendor>>();

	private static CategoryManager categoryMgr = CategoryManager.getInstance();

	public static VendorManager getInstance() {
		if (instance == null) {
			instance = new VendorManager();
		}
		return instance;
	}

	private VendorManager() {
		ArrayList<Category> categories = categoryMgr.getCategories();
		for (Category category : categories) {
			vendors.put(category.getCategoryCode(), category.getVendorList());
		}
	}

	public ArrayList<Vendor> getVendorsListByCategory(String catCode) {
		ArrayList<Vendor> vendorsList = new ArrayList<Vendor>();
		if (catCode != null && !catCode.trim().equals("")) {
			vendorsList = vendors.get(catCode);
		}

		return vendorsList;
	}

	public Vendor addVendor(String catCode, String name, String desc) throws BadVendorException {
		if (catCode == null || catCode.trim().equals(""))
			throw new BadVendorException(Constants.CONST_VENDOR_ERR_CATCODEMISSING);
		if (name == null || name.trim().equals(""))
			throw new BadVendorException(Constants.CONST_VENDOR_ERR_NAMEMISSING);

		Category cat = categoryMgr.getCategory(catCode);
		if (cat != null) {
			if(isVendorExist(cat , name)){
				throw new BadVendorException(Constants.CONST_VENDOR_ERR_EXIST);
			} else {
				Vendor newVendor;
					newVendor = new Vendor(name, desc);
					vendors.get(catCode).add(newVendor); // Update vendor list
					categoryMgr.updateVendorList(vendors.get(catCode), catCode); //Update category vendor list in object
					DataAdapter.writeVendors(vendors.get(catCode), catCode);
					return newVendor;//new ReturnObject(true, Constants.CONST_VENDOR_MSG_CREATION_SUCUESS, newVendor);			
			}
		} else {
			throw new BadVendorException(Constants.CONST_CAT_ERR_NOTEXIST);
		}
	}
	
	private boolean isVendorExist(Category cat, String name) {
		boolean isExist = false;
		for(Vendor v : cat.getVendorList()){
			if(v.getVendorName().equals(name))
				isExist = true;
		}
		return isExist;
	}

	public void updateVendorList(String catCode, ArrayList<Vendor> vendorList){
		ArrayList<Vendor> oldList = vendors.get(catCode);
		oldList = vendorList;
	}

	public HashMap<String, ArrayList<Vendor>> getVendors() {
		return vendors;
	}

	public Object[][] prepareListToTableModel(String catCode) {
		Object[][] tableData = new Object[0][0];
		int maxLengh = 100;
		ArrayList<Vendor> vendorList = categoryMgr.getCategory(catCode).getVendorList();
		if (vendorList != null && vendorList.size() > 0){
			tableData = new Object[vendorList.size()][2];
			for (int i = 0; i < vendorList.size(); i++) {
			Object[] rowData = new Object[2];
			rowData[0] = vendorList.get(i).getVendorName();
			if (vendorList.get(i).getVendorDescription().length() < maxLengh)
				maxLengh = vendorList.get(i).getVendorDescription().length();
			rowData[1] = vendorList.get(i).getVendorDescription().substring(0, maxLengh);
			tableData[i] = rowData;
				
			}
		}
		return tableData;
	}

}
