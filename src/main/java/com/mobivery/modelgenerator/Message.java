package com.mobivery.modelgenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Message {
	private Type response;
	private Request request;
	private String name;
	private String type;
	private String description;
	private String url;
	private String service="Complete";
	private String method;
	private String server;
	private String path;
	
	public Type getResponse() {
		return response;
	}

	public void setResponse(Type response) {
		this.response = response;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}
	public String getTypeUCase(){
		return type.toUpperCase();
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
/*
	public List<String> getResponseImports() {
		if(getResponse()!=null){
			return response.getExtraImports();
		}
		return new ArrayList<String>();
	}
	public List<String> getRequestImports() {
		if(getRequest()!=null){
			return request.getExtraImports();
		}
		return new ArrayList<String>();
	}
*/
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}
	
	public String getMethodUpperCase() {
		return StringUtils.capitalize(method);
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public String getServerJavaRequestParams()
	{
		if(request==null)return "";
		StringBuilder sb=new StringBuilder();
		List<TypeField> typeFields=request.getFields();
		if(getType().equalsIgnoreCase("POST"))
		{
			for(TypeField typeField:typeFields)
			{
				sb.append("@FormParam(value = \""+typeField.getJavaName()+"\")"+ typeField.getTypeJava()+" "+typeField.getJavaName());
				sb.append(",");
			}
		}
		if(getType().equalsIgnoreCase("GET"))
		{
			for(TypeField typeField:typeFields)
			{
				sb.append("@PathParam(value = \""+typeField.getJavaName()+"\")"+ typeField.getTypeJava()+" "+typeField.getJavaName());
				sb.append(",");
			}
		}
		String result=sb.toString();
		if(result.length()>0)
		{
			return result.substring(0,result.length()-1);
		}
		return "";
	}
	public String getJavaRequestParams()
	{
		if(request==null)return "";
		StringBuilder sb=new StringBuilder();
		List<TypeField> typeFields=request.getFields();
		for(TypeField typeField:typeFields)
		{
			sb.append(typeField.getTypeJava()+" "+typeField.getJavaName());
			sb.append(",");
		}
		String result=sb.toString();
		if(result.length()>0)
		{
			return result.substring(0,result.length()-1);
		}
		return "";
	}
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public List<String> getInURLbutNotField()
	{
		String url=getUrl();
		ArrayList<String> result=new ArrayList<String>();
		Pattern pattern=Pattern.compile("\\{([a-zA-Z0-9_]*)\\}");
		Matcher matcher=pattern.matcher(url);
		while(matcher.find())
		{
			System.out.println(matcher.group(1));
			boolean found=false;
			for(TypeField typeField:getRequest().getFields())
			{
				if(typeField.getName().equals(matcher.group(1)))
				{
					found=true;
				}
			}
			if(!found)
			{
				result.add(matcher.group(1));
			}
		}
		return result;
	}
	public boolean isHttps()
	{
		return getUrl().toLowerCase().startsWith("https");
	}
}
