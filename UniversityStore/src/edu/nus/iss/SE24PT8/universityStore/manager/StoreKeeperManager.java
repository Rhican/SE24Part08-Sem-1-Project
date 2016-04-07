
package edu.nus.iss.SE24PT8.universityStore.manager;
import edu.nus.iss.SE24PT8.universityStore.domain.StoreKeeper;
import edu.nus.iss.SE24PT8.universityStore.exception.BadStoreKeeperAdminException;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author THIRILWIN
 * @date 2016-March-17
 */
public class StoreKeeperManager {
    
    private static StoreKeeperManager Instance=null;
    private ArrayList<StoreKeeper> storeKeeper=null;
    
    public static StoreKeeperManager getInstance(){
        if(Instance==null){
            Instance=new StoreKeeperManager();
        }
        return Instance;
    }
    
    private StoreKeeperManager()
    {
        storeKeeper=DataAdapter.loadStoreKeepers();
    }
    
    public List<StoreKeeper> getStoreKeeper()
    {
        return this.storeKeeper;
    }
    
    public boolean checkPassword(String storeKeeperName, String password) throws BadStoreKeeperAdminException
    {
        ValidateUserNameAndPassword(storeKeeperName,password,"STOPERKEEPERNAME");
        ValidateUserNameAndPassword(storeKeeperName,password,"PASSWORD");
        
        Iterator<StoreKeeper> i=storeKeeper.iterator();
        while(i.hasNext()){
           StoreKeeper storeKeeper=i.next();
           if(storeKeeper.getstoreKeeperName().equalsIgnoreCase(storeKeeperName))
           {
               if(storeKeeper.getPassword().equals(password))
               {
                   return true;
               }
               else
                {
                     throw new BadStoreKeeperAdminException(Constants.CONST_STOREKEEPER_ERR_LOGIN);
                }
           }
        }
        return false;
    }
    
    public void AddStoreKeeper(String storeKeeperName, String password) throws  BadStoreKeeperAdminException{
        ValidateUserNameAndPassword(storeKeeperName,password,"STOPERKEEPERNAME");
        ValidateUserNameAndPassword(storeKeeperName,password,"PASSWORD");
        ValidateUserNameAndPassword(storeKeeperName,password,"SPECIALCHAR_IN_PASSWORD");
        ValidateUserNameAndPassword(storeKeeperName,password,"SPECIALCHAR_IN_NAME");
        StoreKeeper e=new StoreKeeper(storeKeeperName,password);
        if(storeKeeper == null)
        {
            storeKeeper=new ArrayList<StoreKeeper>();
        }
        for(StoreKeeper s:storeKeeper)
        {
            if(storeKeeperName.toUpperCase().equals(s.getstoreKeeperName().toUpperCase()))
            {
                throw new BadStoreKeeperAdminException(Constants.CONST_STOREKEEPER_ERR_MEMBEREXIST);
            }
        }
        storeKeeper.add(e);
        saveData();
    }
    
    public void ValidateUserNameAndPassword(String storeKeeperName,String password,String validateBy) throws BadStoreKeeperAdminException {
        
        if(validateBy.equals("STOPERKEEPERNAME"))
        {
            if(storeKeeperName == null){
             throw new BadStoreKeeperAdminException(Constants.CONST_STOREKEEPER_ERR_LOGIN);
           }
           else if(storeKeeperName.equals(""))
           {
             throw new BadStoreKeeperAdminException(Constants.CONST_STOREKEEPER_ERR_LOGIN);
           }
        }
        if(validateBy.equals("PASSWORD"))
        {
            if(password == null)
            {
                throw new BadStoreKeeperAdminException(Constants.CONST_STOREKEEPER_ERR_LOGIN);
            }
            else if(password.equals(""))
            {
                throw new BadStoreKeeperAdminException(Constants.CONST_STOREKEEPER_ERR_LOGIN);
            }
        }
         Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        if(validateBy.equals("SPECIALCHAR_IN_PASSWORD"))
        {
           password=password.replace(" ", "");
           Matcher m = p.matcher(password);
           boolean b = m.find();
           if (b)
           {
              throw new BadStoreKeeperAdminException(Constants.CONST_STOREKEEPER_ERR_SPECIALCHAR);
           }
        }
        if(validateBy.equals("SPECIALCHAR_IN_NAME"))
        {
            Matcher m2 = p.matcher(storeKeeperName);
            boolean b2 = m2.find();
            if (b2)
            {
               throw new BadStoreKeeperAdminException(Constants.CONST_STOREKEEPER_ERR_SPECIALCHAR);
            }
        }
    }
    
    public void DeleteStoreKeeper(String storeKeeperName) throws BadStoreKeeperAdminException{
       ValidateUserNameAndPassword(storeKeeperName,null,"STOPERKEEPERNAME");
       StoreKeeper data=null;
       if(storeKeeper != null)
        {
            for(StoreKeeper s:storeKeeper)
            {
                if(storeKeeperName.equals(s.getstoreKeeperName()))
                {
                    data=s;
                }
            }
        }
       if(data!=null)
       {
           storeKeeper.remove(data);
           saveData();
       }
    }
    
    public StoreKeeper getStoreKeeper(String storeKeeperName) throws BadStoreKeeperAdminException
    {
        ValidateUserNameAndPassword(storeKeeperName,null,"STOPERKEEPERNAME");
        if(storeKeeper != null)
        {
            for(StoreKeeper s:storeKeeper){
            if(storeKeeperName.equals(s.getstoreKeeperName()))
                {
                    return s;
                }
            }
        }
        return null;
    }
    
    public void saveData() {
        DataAdapter.writeStoreKeepers(storeKeeper); 
    }
    
    public Object[][] prepareMemberTableModel() {
		
 		Object[][] tableData = new Object[storeKeeper.size()][2];
 		for (int i = 0; i < storeKeeper.size(); i++) {
 			StoreKeeper s = storeKeeper.get(i);
 			Object[] rowData = new Object[2];
 			rowData[0] = s.getstoreKeeperName();
 			rowData[1] = "***";
 			tableData[i] = rowData;
 		}
 		return tableData;
 	}
}

