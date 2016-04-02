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
import edu.nus.iss.SE24PT8.universityStore.util.ReturnObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mugunthan
 */
public class VendorManager implements IManager {

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
		ArrayList<Vendor> vendorsList = null;
		if (catCode != null && !catCode.trim().equals("")) {
			vendorsList = vendors.get(categoryMgr.getCategory(catCode));
		}

		return vendorsList;
	}

	public ReturnObject addVendor(String catCode, String name, String desc) {
		if (catCode == null || catCode.trim().equals(""))
			return new ReturnObject(false, Constants.CONST_VENDOR_ERR_CATCODEMISSING, null);

		Category cat = categoryMgr.getCategory(catCode);
		if (cat != null) {
			Vendor newVendor;
			try {
				newVendor = new Vendor(name, desc);
				vendors.get(catCode).add(newVendor); // Update vendor list
				categoryMgr.updateVendorList(vendors.get(catCode), catCode); //Update category vendor list in object
				DataAdapter.writeVendors(vendors.get(catCode), catCode);
				return new ReturnObject(true, Constants.CONST_VENDOR_MSG_CREATION_SUCUESS, newVendor);
			} catch (BadVendorException ex) {
				return new ReturnObject(false, ex.getMessage(), null);
			}
		} else {
			return new ReturnObject(false, Constants.CONST_CAT_ERR_NOTEXIST, null);
		}

	}

	// This method may used to change the vendor preference
	public ReturnObject saveVendors(ArrayList<Vendor> vendorList, String catCode) {

		if (catCode == null || catCode.trim().equals(""))
			return new ReturnObject(false, Constants.CONST_CAT_ERR_NOTEXIST, null);

		Category cat = categoryMgr.getCategory(catCode);
		if (cat != null) {
			vendors.put(cat.getCategoryCode(), vendorList);

			/// Edited by Hendry. should use the vendor list passed in. Vendor
			/// list not updated in memory
			// DataAdapter.writeVendors(vendors.get(cat), catCode);
			DataAdapter.writeVendors(vendorList, catCode);
			return new ReturnObject(true, Constants.CONST_VENDOR_MSG_UPDATE_SUCUESS, vendors.get(cat));
		} else {
			return new ReturnObject(false, Constants.CONST_CAT_ERR_NOTEXIST, null);
		}
	}

	public HashMap<String, ArrayList<Vendor>> getVendors() {
		return vendors;
	}
	
	public int getTotalVendorCount(){
		int count = 0;
		ArrayList<Category> categories = categoryMgr.getCategories();
		for (Category category : categories) {
			count += category.getVendorList().size();
		}
		return count;
	}

	@Override
	public void getRelatedObjects() {
		
	}

	@Override
	public void saveData() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Object[][] prepareListToTableModel() {
		int count = 0;
		Object[][] tableData = new Object[getTotalVendorCount()][2];
		ArrayList<Category> categories = categoryMgr.getCategories();
		for (Category category : categories) {
			ArrayList<Vendor> list = category.getVendorList();
			for (Vendor vendor : list){
				Object[] rowData = new Object[2];
				rowData[0] = vendor.getVendorName();
				rowData[1] = category.getCategoryName();
				tableData[count] = rowData;
				count++;
			}
		}
		return tableData;
	}

}
