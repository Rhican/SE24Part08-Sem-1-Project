
package edu.nus.iss.SE24PT8.UniversityStore.manager;

import edu.nus.iss.SE24PT8.UniversityStore.domain.StoreKeeper;
import edu.nus.iss.SE24PT8.UniversityStore.util.DataAdapter;
import java.util.*;


/**
 *
 * @author THIRILWIN
 * @date 2016-March-17
 */
public class StoreKeeperManager implements IManager{
    
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
    
    public boolean checkPassword(String storeKeeperName, String password)
    {
        //to update with return object
        if((password !=null)&& (!password.isEmpty()) && (storeKeeperName !=null) && (!storeKeeperName.isEmpty()) )
        {
            Iterator<StoreKeeper> i=storeKeeper.iterator();
            while(i.hasNext()){
               StoreKeeper storeKeeper=i.next();
               if(storeKeeper.getstoreKeeperName().equals(storeKeeperName))
               {
                   if(storeKeeper.getPassword().equals(password))
                   {
                       return true;
                   }
               }
            }
        }
        return false;
    }
    
     @Override
    public void getRelatedObjects() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void saveData() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
