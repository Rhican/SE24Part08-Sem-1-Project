package edu.nus.iss.SE24PT8.universityStore;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.nus.iss.SE24PT8.universityStore.domain.CategoryTest;
import edu.nus.iss.SE24PT8.universityStore.domain.CustomerTest;
import edu.nus.iss.SE24PT8.universityStore.domain.MemberTest;
import edu.nus.iss.SE24PT8.universityStore.domain.NonMemberTest;
import edu.nus.iss.SE24PT8.universityStore.domain.StoreKeeperTest;
import edu.nus.iss.SE24PT8.universityStore.domain.VendorTest;
import edu.nus.iss.SE24PT8.universityStore.manager.CategoryManagerTest;
import edu.nus.iss.SE24PT8.universityStore.manager.MemberManagerTest;
import edu.nus.iss.SE24PT8.universityStore.manager.StoreKeeperManagerTest;
import edu.nus.iss.SE24PT8.universityStore.util.ApplicationConfig;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;
import edu.nus.iss.SE24PT8.universityStore.util.FileOperations;
import junit.framework.TestCase;

/**
*
* @author THIRILWIN added the test suite() with member and StoreKeeper classes
* 
*/
@RunWith(Suite.class)
@Suite.SuiteClasses({CustomerTest.class,
					 MemberTest.class,
					 NonMemberTest.class,
					 StoreKeeperTest.class,
					 CategoryTest.class,
					 VendorTest.class,
					 MemberManagerTest.class,
					 StoreKeeperManagerTest.class,
					 CategoryManagerTest.class,
					 VendorTest.class})

public class UniversityStoreTest extends TestCase {
	
	private static final String ROOT_DATA_FOLDER = ApplicationConfig.getInstance()
			.getValue(ApplicationConfig.KEY_DATA_ROOT_FOLDER);
	private static final String BK_FOLDER = ROOT_DATA_FOLDER + "_bak";

	@BeforeClass 
	public static void backupData(){
		FileOperations.createDir(Paths.get(BK_FOLDER));
		FileOperations.moveDir(Paths.get(ROOT_DATA_FOLDER), Paths.get(BK_FOLDER));
		FileOperations.createDir(Paths.get(ROOT_DATA_FOLDER));
		DataAdapter.createFiles();
	}
	
	@AfterClass
	public static void restoreData() throws IOException{
		FileOperations.deleteDir(Paths.get(ROOT_DATA_FOLDER));
		FileOperations.createDir(Paths.get(ROOT_DATA_FOLDER));
		FileOperations.moveDir(Paths.get(BK_FOLDER), Paths.get(ROOT_DATA_FOLDER));
	}
}
