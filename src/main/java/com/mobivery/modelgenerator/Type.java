package com.mobivery.modelgenerator;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Tipo de atributo
 * @author DRM
 *
 */
public class Type {
	/**
	 * Nombre del tipo
	 */
	private String name;
	/**
	 * Clase del tipo
	 */
	private String type;
	/**
	 * Lista de atributos miembro
	 */
	private List<TypeField> fields = new ArrayList<TypeField>();
	/**
	 * Devuelve el nombre del tipo
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Establece el nombre del tipo
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Getter de la lista de campos del tipo
	 * @return
	 */
	public List<TypeField> getFields() {
		return fields;
	}
	/**
	 * Setter de la lista de campos del tipo
	 * @param fields
	 */
	public void setFields(List<TypeField> fields) {
		this.fields = fields;
	}
	/**
	 * MŽtodo que devuelve la lista de imports necesarios para este tipo
	 * @return
	 */
	public List<String> getJavaDTOExtraImports(String basePackageName) {
		List<String> imports=new ArrayList<String>();
		
		for (TypeField typeField : getFields()) {
			String subTypeName;
			if (typeField.getType().endsWith("*")) {
				if(!imports.contains("import java.util.List;"))
				{
					imports.add("import java.util.List;");
				}
				subTypeName=typeField.getType().substring(0,
						typeField.getType().length() - 1);
				
			} else {
				subTypeName=typeField.getType();
			}
			
			try {
				DataType.valueOf(subTypeName.toUpperCase());
			} catch (Exception ex) {
				
				if(!imports.contains("import "+basePackageName+".model.dto."+subTypeName+";"))
				{
					imports.add("import "+basePackageName+".model.dto."+subTypeName+";");
				}
			}
		}
		return imports;
	}
	/**
	 * MŽtodo que devuelve la lista de imports necesarios para este tipo
	 * @return
	 */
	public List<String> getJavaDAOExtraImports(String basePackageName) {
		List<String> imports=new ArrayList<String>();
		
		for (TypeField typeField : getFields()) {
			String subTypeName;
			if (typeField.getType().endsWith("*")) {
				if(!imports.contains("import java.util.ArrayList;"))
				{
					imports.add("import java.util.ArrayList;");
					imports.add("import org.json.JSONArray;");
				}
				subTypeName=typeField.getType().substring(0,
						typeField.getType().length() - 1);
				
			} else {
				subTypeName=typeField.getType();
			}
			
			try {
				DataType.valueOf(subTypeName.toUpperCase());
			} catch (Exception ex) {
				
				if(!imports.contains("import "+basePackageName+".model.dto."+subTypeName+";"))
				{
					imports.add("import "+basePackageName+".model.dto."+subTypeName+";");
				}
			}
		}
		return imports;
	}
	public List<String> getIOSExtraImports() {
		List<String> imports=new ArrayList<String>();
		
		for (TypeField typeField : getFields()) {
			String subTypeName;
			if (typeField.getType().endsWith("*")) {
				subTypeName=typeField.getType().substring(0,
						typeField.getType().length() - 1);
				
			} else {
				subTypeName=typeField.getType();
			}
			
			try {
				DataType.valueOf(subTypeName.toUpperCase());
			} catch (Exception ex) {
				
				if(!imports.contains(subTypeName))
				{
					imports.add(subTypeName);
				}
			}
		}
		return imports;
	}
	
	/**
	 * MŽtodo que devuelve los atributos que son de tipo Objeto
	 * @return
	 */
	public List<TypeField> getObjectFields() {
		List<TypeField> returnTypes=new ArrayList<TypeField>();
		for(TypeField typeField:getFields())
		{
			if(typeField.isObject())
			{
				returnTypes.add(typeField);
			}
		}
		return returnTypes;
	}
	/**
	 * MŽtodo que devuelve los atributos que son de tipo Array de Objetos 
	 * @return
	 */
	public List<TypeField> getObjectArrayFields() {
		List<TypeField> returnTypes=new ArrayList<TypeField>();
		for(TypeField typeField:getFields())
		{
			if(typeField.isObjectArray())
			{
				returnTypes.add(typeField);
			}
		}
		return returnTypes;
	}
	/**
	 * MŽtodo que devuelve los atributos que son de tipo base
	 * @return
	 */
	public List<TypeField> getBaseFields() {
		List<TypeField> returnTypes=new ArrayList<TypeField>();
		for(TypeField typeField:getFields())
		{
			if(typeField.isBase())
			{
				returnTypes.add(typeField);
			}
		}
		return returnTypes;
	}
	/**
	 * MŽtodo que devuelve los atributos que son de tipo Array de tipo base
	 * @return
	 */
	public List<TypeField> getBaseArrayFields() {
		List<TypeField> returnTypes=new ArrayList<TypeField>();
		for(TypeField typeField:getFields())
		{
			if(typeField.isBaseArray())
			{
				returnTypes.add(typeField);
			}
		}
		return returnTypes;
	}
	
	public String getJavaInstanceName()
	{
		return name.substring(0,1).toLowerCase()+name.substring(1);
	}
	
	public String getDaoName()
	{
		if(type.endsWith("DTO"))
		{
			return type.replace("DTO", "DAO");
		}
		return type+"DAO";
	}
	
	public boolean isMultipart()
	{
		for(TypeField typeField:getFields())
		{
			if(typeField.getType().equalsIgnoreCase("file"))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * Getter del tipo IOS
	 * @return
	 */
	public String getTypeIOSDAO(){
		String typeName=type;
		return typeName.endsWith("DTO")?typeName.replace("DTO", "DAO"):typeName+"DAO";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
