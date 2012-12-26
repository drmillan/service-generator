package com.mobivery.modelgenerator;

/**
 * Mapeo de tipos b‡sicos a tipos espec’ficos de IOS o Java
 * @author DRM
 *
 */
public enum DataType {
	STRING("NSString","String"),
	NUMBER(Constants.NSNUMBER,"Integer"),
	URL("NSUrl","java.net.URL"),
	INT(Constants.NSNUMBER,"Integer"),
	BIGINT(Constants.NSNUMBER,"Long"),
	LONG(Constants.NSNUMBER,"Long"),
	BOOLEAN("NSNumber","Boolean"),
	CHAR(Constants.NSNUMBER,"String"),
	DATE("NSDate","String"),
	FLOAT(Constants.NSNUMBER,"Float"),
	DOUBLE(Constants.NSNUMBER,"Double"),
	FILE("NSString","String")
	;
	private String iosClass,javaClass;
	private DataType(String iosClass,String javaClass)
	{
		this.iosClass=iosClass;
		this.javaClass=javaClass;
	}
	/**
	 * Devuelve el tipo IOS para este tipo de dato
	 * @return
	 */
	public String getIosClass() {
		return iosClass;
	}
	/**
	 * Devuelve el tipo Java para este tipo de dato
	 * @return
	 */
	public String getJavaClass() {
		return javaClass;
	}
	
	public String getJavascriptClass() {
		return "var";
	}
	
}
