package com.mobivery.modelgenerator;

/**
 * Decodificador de tipos
 * @author DRM
 *
 */
public class TypeDecoder {
	/**
	 * Devuelve el tipo IOS para un tipo determinado
	 * @param type
	 * @return
	 */
	public String getIOSType(String type)
	{
		String upType=type.toUpperCase();
		return DataType.valueOf(upType).getIosClass();
	}
	/**
	 * Devuelve el tipo Java para un tipo determinado
	 * @param type
	 * @return
	 */
	public String getJavaType(String type)
	{
		String upType=type.toUpperCase();
		return DataType.valueOf(upType).getJavaClass();
	}
	
	/**
	 * Devuelve el tipo Javascript para un tipo determinado
	 * @param type
	 * @return
	 */
	public String getJavascriptType(String type)
	{
		String upType=type.toUpperCase();
		return DataType.valueOf(upType).getJavascriptClass();
	}
}
