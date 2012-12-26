package com.mobivery.modelgenerator;

/**
 * Atributo de objeto
 * @author DRM
 *
 */
public class TypeField {
	// Nombre de la propiedad
	private String name;
	// Tipo de la propiedad
	private String type;
	// Tipo mime
	private String mimeType;
	// Comentario de la propiedad
	private String comment;
	/**
	 * Getter del nombre de la propiedad
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter del nombre de la propiedad, devuelve la propiedad en JavaStyle
	 * @return
	 */
	public String getJavaName()
	{
		String returnName=name;
		if(name.contains("_"))
		{
			String subs[]=name.split("_");
			returnName=subs[0];
			for(int i=1;i!=subs.length;i++)
			{
				returnName+=subs[i].substring(0,1).toUpperCase()+subs[i].substring(1);
			}
		}
		return returnName;
	}
	/**
	 * Getter del nombre de la propiedad en mayœscula
	 * @return
	 */
	public String getNameUcase() {
		String returnName=getJavaName();
		return returnName.substring(0,1).toUpperCase()+returnName.substring(1);
	}
	/**
	 * Setter de la propiedad nombre
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Getter del tipo del atributo
	 * @return
	 */
	public String getType() {
		return type;
	}
	/**
	 * Getter del tipo base en singular
	 * @return
	 */
	public String getBaseTypeSingular(){
		if(isBaseArray())
		{
			return DataType.valueOf(this.getTypeSingular().toUpperCase()).getIosClass();
		}
		return getTypeSingular();
	}
	/**
	 * Getter del tipo base en singular
	 * @return
	 */
	public String getJavaBaseTypeSingular(){
		if(isBaseArray())
		{
			return DataType.valueOf(this.getTypeSingular().toUpperCase()).getJavaClass();
		}
		return getTypeSingular();
	}
	/**
	 * Getter del tipo en singular
	 * @return
	 */
	public String getTypeSingular() {
		return type.endsWith("*")?type.substring(0,type.length()-1):type;
	}
	/**
	 * Setter del tipo del atributo
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Getter del comentario del atributo
	 * @return
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * Setter del comentario del atributo
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * Getter del tipo java
	 * @return
	 */
	public String getTypeJava(){
		String baseType=type.endsWith("*")?type.substring(0,type.length()-1):type;
		if(isBaseType())
		{
			baseType=DataType.valueOf(baseType.toUpperCase()).getJavaClass();
		}
		
		if(this.type.endsWith("*"))
		{
			return "List<"+baseType+">";
		}
		else
		{
			return baseType;
		}
	}
	/**
	 * Getter del tipo java
	 * @return
	 */
	public String getTypeJavaDAO(){
		String typeName=getBaseTypeSingular();
		return typeName.endsWith("DTO")?typeName.replace("DTO", "DAO"):typeName+"DAO";
	}
	
	/**
	 * Getter del tipo javascript
	 * @return
	 */
	public String getTypeJavascriptDAO(){
		String typeName=getBaseTypeSingular();
		return typeName.endsWith("DTO")?typeName.replace("DTO", "DAO"):typeName+"DAO";
	}
	/**
	 * Getter del tipo IOS
	 * @return
	 */
	public String getTypeIOSDAO(){
		String typeName=getBaseTypeSingular();
		return typeName.endsWith("DTO")?typeName.replace("DTO", "DAO"):typeName+"DAO";
	}
	/**
	 * Getter del tipo IOS
	 * @return
	 */
	public String getTypeIOs()
	{
		if(this.type.endsWith("*"))
		{
			return "NSArray";
		}
		try	
		{
			return DataType.valueOf(this.type.toUpperCase()).getIosClass();
		}
		catch(IllegalArgumentException ex)
		{
			return this.type;
		}
	}
	/**
	 * Comprobaci—n de si la clase es de tipo base
	 * @return
	 */
	private boolean isBaseType()
	{

		try
		{
			return DataType.valueOf(this.getTypeSingular().toUpperCase()).getIosClass()!=null?true:false;
		}
		catch(IllegalArgumentException ex)
		{
			return false;
		}
	}
	/**
	 * Comprobaci—n de si el tipo es un objeto
	 * @return
	 */
	public boolean isObject() {
		return(!this.type.endsWith("*") && !isBaseType());
	}
	/**
	 * Comprobaci—n de si el tipo es un array de objetos
	 * @return
	 */
	public boolean isObjectArray() {
		return(this.type.endsWith("*") && !isBaseType());
	}
	/**
	 * Comprobaci—n de si el tipo es un tipo b‡sico
	 * @return
	 */
	public boolean isBase() {
		return(!this.type.endsWith("*") && isBaseType());
	}
	/**
	 * Comprobaci—n de si el tipo es un array de tipo b‡sico
	 * @return
	 */
	public boolean isBaseArray() {
		return(this.type.endsWith("*") && isBaseType());
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
}
