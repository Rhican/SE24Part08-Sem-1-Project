package edu.nus.iss.SE24PT8.universityStore;

import edu.nus.iss.SE24PT8.universityStore.domain.CustomerTest;
import edu.nus.iss.SE24PT8.universityStore.domain.MemberTest;
import edu.nus.iss.SE24PT8.universityStore.domain.NonMemberTest;
import edu.nus.iss.SE24PT8.universityStore.domain.StoreKeeperTest;
import edu.nus.iss.SE24PT8.universityStore.manager.MemberManagerTest;
import edu.nus.iss.SE24PT8.universityStore.manager.StoreKeeperManagerTest;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
*
* @author THIRILWIN added the test suite() with member and StoreKeeper classes
* 
*/
public class UniversityStoreTest extends TestCase {

	public static junit.framework.Test suite(){
		TestSuite suite=new TestSuite("Testing the university store application");
		suite.addTestSuite(CustomerTest.class);
		suite.addTestSuite(MemberTest.class);
		suite.addTestSuite(NonMemberTest.class);
		suite.addTestSuite(StoreKeeperTest.class);
		suite.addTestSuite(MemberManagerTest.class);
		suite.addTestSuite(StoreKeeperManagerTest.class);
		
		return suite;
	}
	
}
