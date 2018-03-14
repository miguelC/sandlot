package net.sandlotnow.config.interop;

public class InteropServiceException extends Exception {
    
	private static final long serialVersionUID = -7371267793876176200L;

	public InteropServiceException()
    {
    }

    public InteropServiceException(String message)
    {
        super(message);
    }

    public InteropServiceException(Throwable cause)
    {
        super(cause);
    }

    public InteropServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InteropServiceException(String message, Throwable cause, 
                                       boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
