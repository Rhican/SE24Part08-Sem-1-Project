package edu.nus.iss.SE24PT8.universityStore.exception;

/**
*
* @author Zehua
*/
public class TransactionException extends Exception {
	private Exception innerException = null;
	
	public TransactionException () {
		
    }

    public TransactionException (String msg) {
        super (msg);
    }
    
    public TransactionException (String msg, Exception innerException) {
        super (msg);
        this.innerException = innerException;
    }
    
    public Exception getInnerException() {
    	return this.innerException;
    }
}
