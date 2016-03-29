/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.UniversityStore.domain;

import edu.nus.iss.SE24PT8.UniversityStore.exception.BadVendorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mugunthan
 */
public class VendorTest {

    private Vendor v1, v2, v3, v4, v5, v6, v7;

    @Before
    public void setUp() throws Exception {

        v1 = new Vendor("Nancy's Gifts", "Best of the best gifts from Nancy's");
        v2 = new Vendor("office Sovenirs", "One and only Sovenirs");
        v3 = new Vendor("Pen's and Such", "Sovenirs and gifts for alloccasions");
        v4 = new Vendor("ArtWorks Stationary Store", null);
    }

    @After
    public void tearDown() {
        v1 = null;
        v2 = null;
        v3 = null;
        v4 = null;
    }

    @Test
    public void testVendorName() {
        assertEquals("Nancy's Gifts", v1.getVendorName());
    }

    @Test
    public void testVendorDescription() {
        assertEquals("One and only Sovenirs", v2.getVendorDescription());
        assertNull(v4.getVendorDescription());
    }

    @Test
    public void testToString() {
        v1.show();
        v2.show();
        v3.show();
        v4.show();
        assertTrue(true);
    }

    @Test
    public void testException() {
        try {
            v5 = new Vendor(null, "Description");
            fail();
        } catch (BadVendorException e) {
            assertEquals("No valid vendor name specified", e.getMessage());
        }
        try {
            v6 = new Vendor("", "Description");
            fail();
        } catch (BadVendorException e) {
            assertEquals("No valid vendor name specified", e.getMessage());
        }
        try {
            v7 = new Vendor(" ", "Description");
        } catch (BadVendorException e) {
            fail();
            e.printStackTrace();
        }
    }

    @Test
    public void testEquals() throws BadVendorException {
        assertEquals(v1, new Vendor("Nancy's Gifts", "Best of the best gifts from Nancy's"));
        assertEquals(v4, new Vendor("ArtWorks Stationary Store", null));
        assertFalse(v2.equals(new Vendor("Nancy's Gifts", "Best of the best gifts from Nancy's")));
        assertFalse(new Vendor("ArtWorks Stationary Store", null).equals(v3));
        assertTrue(v4.equals(new Vendor("ArtWorks Stationary Store", null)));
    }
}
