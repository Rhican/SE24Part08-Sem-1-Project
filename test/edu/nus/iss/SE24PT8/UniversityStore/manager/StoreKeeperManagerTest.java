
package edu.nus.iss.SE24PT8.UniversityStore.manager;

import edu.nus.iss.SE24PT8.UniversityStore.domain.StoreKeeper;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author THIRILWIN
 * @date 2016-March-17
 */
public class StoreKeeperManagerTest extends TestCase {
    
    
    public StoreKeeperManagerTest() {
    }
   
    @Before
    public void setUp() {
       
    }
    
    @After
    public void tearDown() {
    }
    
     @Test
     public void testStoreKeeperManager() {
         
         // (1) ArrayList must not be 0 or null
         StoreKeeperManager storeKeeperManager =StoreKeeperManager.getInstance();
         assertFalse(storeKeeperManager.getStoreKeeper() == null);
         assertFalse(storeKeeperManager.getStoreKeeper().size()==0);
         
         ArrayList<StoreKeeper> testList=(ArrayList<StoreKeeper>) storeKeeperManager.getStoreKeeper();
       
         assertEquals("Stacy",testList.get(0).getstoreKeeperName());
         assertEquals("Dean56s",testList.get(0).getPassword());
         
         //System.out.println(testList.get(1).getstoreKeeperName());
         //System.out.println(testList.get(1).getPassword());
         
         assertEquals("Johny",testList.get(1).getstoreKeeperName());
         assertEquals("123456",testList.get(1).getPassword());
      
     }
     
     
     @Test
     public void testcheckPassword()
     {
          StoreKeeperManager storeKeeperManager =StoreKeeperManager.getInstance();
         assertFalse(!storeKeeperManager.checkPassword("Stacy", "Dean56s"));
         assertTrue(storeKeeperManager.checkPassword("Johny", "123456"));
     }
     
     @Test
     public void testInstance()
     {
        StoreKeeperManager storeKeeperManager =StoreKeeperManager.getInstance();
        assertFalse(StoreKeeperManager.getInstance().getStoreKeeper() == null);
        assertFalse(StoreKeeperManager.getInstance().getStoreKeeper().size()==0);
         
         ArrayList<StoreKeeper> testList=(ArrayList<StoreKeeper>) StoreKeeperManager.getInstance().getStoreKeeper();
       
         assertEquals("Stacy",testList.get(0).getstoreKeeperName());
         assertEquals("Dean56s",testList.get(0).getPassword());
         
         //System.out.println(testList.get(1).getstoreKeeperName());
        // System.out.println(testList.get(1).getPassword());
         
         assertEquals("Johny",testList.get(1).getstoreKeeperName());
         assertEquals("123456",testList.get(1).getPassword());
         assertFalse(!StoreKeeperManager.getInstance().checkPassword("Stacy", "Dean56s"));
         assertTrue(StoreKeeperManager.getInstance().checkPassword("Johny", "123456"));
         
     }
}
