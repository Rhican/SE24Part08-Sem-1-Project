/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.UniversityStore.domain;


import org.junit.After;
import org.junit.Before;

/**
 *
 * @author Mugunthan
 */
public class CategoryTest {
    
    Category cat1;
    
    public CategoryTest() {
    }
    
    @Before
    public void setUp() {
        cat1 = new Category("CLO", "Clothing", null);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
