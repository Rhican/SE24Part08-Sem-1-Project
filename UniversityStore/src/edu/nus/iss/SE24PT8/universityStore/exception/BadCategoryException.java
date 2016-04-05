package edu.nus.iss.SE24PT8.universityStore.exception;
/**
*
* @author Mugunthan
*/
public class BadCategoryException extends Exception{
	public BadCategoryException () {
    }

    public BadCategoryException (String msg) {
        super (msg);
    }
}
