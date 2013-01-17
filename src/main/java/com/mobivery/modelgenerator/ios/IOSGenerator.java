package com.mobivery.modelgenerator.ios;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.mobivery.modelgenerator.BaseGenerator;
import com.mobivery.modelgenerator.Constants;
import com.mobivery.modelgenerator.Message;
import com.mobivery.modelgenerator.Type;
import com.mobivery.modelgenerator.XMLModelGeneratorDocumentHandler;

public final class IOSGenerator extends BaseGenerator{
	private static final  String IOS_FOLDER="MODEL/IOS/";
	public IOSGenerator()
	{
		
	}
	public void generateTypes(List<Type> types) throws IOException
	{
		String iosFolder=System.getProperty("ios.folder",IOS_FOLDER);
		
		new File(iosFolder).mkdirs();
		new File(iosFolder+"/Model/DAO").mkdirs();
		new File(iosFolder+"/Model/DTO").mkdirs();
		generateDAOs(iosFolder, types);
		generateDTOs(iosFolder, types);
	}
	public void generateServices(List<Message> messages,String onSend,String onReceive,String onError) throws IOException
	{
		String version=System.getProperty(Constants.GENERATOR_IOS_VERSION,"1.0");
		// JAVA
				// Creaci—n del Servicio
						
				String iosFolder=System.getProperty("ios.folder");
				
				VelocityEngine ve = new VelocityEngine();
				ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
				ve.setProperty("classpath.resource.loader.class",
						ClasspathResourceLoader.class.getName());
				ve.init();
				
				VelocityContext velocityContext;
				velocityContext = new VelocityContext();
				velocityContext.put("projectName",System.getProperties().get("project.name"));
				
				velocityContext.put("onSend",onSend);
				velocityContext.put("onReceive",onReceive);
				velocityContext.put("onError",onError);
				velocityContext.put("version", version);

				Map<String,List<Message>> services=new TreeMap<String, List<Message>>();
				Map<String,List<String>> extraImports=new TreeMap<String,List<String>>();
				for(Message message:messages)
				{
					String service=message.getService();
					if(service==null)service="Complete";
					if(!services.containsKey(service))
					{
							services.put(service, new ArrayList<Message>());
							extraImports.put(service,new ArrayList<String>());
					}
					services.get(service).add(message);
					if(!extraImports.get(service).contains(message.getRequest().getType()))
					{
						extraImports.get(service).add(message.getRequest().getType());
					}
					if(!extraImports.get(service).contains(message.getResponse().getType()))
					{
						extraImports.get(service).add(message.getResponse().getName());
					}
					if(!extraImports.get(service).contains(message.getResponse().getTypeIOSDAO()))
					{
						extraImports.get(service).add(message.getResponse().getTypeIOSDAO());
					}
					if(!extraImports.get(service).contains(message.getRequest().getTypeIOSDAO()))
					{
						extraImports.get(service).add(message.getRequest().getTypeIOSDAO());
					}
				}
				
				new File(iosFolder + "/Logic/Base/").mkdirs();
				
				for(String serviceName:services.keySet())
				{
					boolean hasGet=hasMethod(services.get(serviceName),"Get") || hasMethod(services.get(serviceName),"GetJSON");
					boolean hasPut=hasMethod(services.get(serviceName),"Put") || hasMethod(services.get(serviceName),"PutJSON");
					boolean hasPost=hasMethod(services.get(serviceName),"Post") || hasMethod(services.get(serviceName),"PostJSON");
					boolean hasDelete=hasMethod(services.get(serviceName),"Delete") || hasMethod(services.get(serviceName),"DeleteJSON");
					boolean hasMultipart=hasMultipart(services.get(serviceName),"Multipart");
					velocityContext.put("extraImports", extraImports.get(serviceName));
					velocityContext.put("hasGet", hasGet);
					velocityContext.put("hasPut", hasPut);
					velocityContext.put("hasPost", hasPost);
					velocityContext.put("hasDelete", hasDelete);
					velocityContext.put("hasMultipart", hasMultipart);
					velocityContext.put("messages",services.get(serviceName));
					velocityContext.put("serviceName", serviceName); 
					velocityContext.put("version", version);
					generate(new File(iosFolder 
							+ "/Logic/Base/Base" + serviceName + "Logic.h"),
							ve.getTemplate("templates/ios/"+version+"/ios_base_service_header.vm"), velocityContext);
					generate(new File(iosFolder 
							+ "/Logic/Base/Base" + serviceName + "Logic.m"),
							ve.getTemplate("templates/ios/"+version+"/ios_base_service_implementation.vm"), velocityContext);
					File derivedH=new File(iosFolder 
							+ "/Logic/" + serviceName + "Logic.h");
					if(!derivedH.exists())
					{
						generate(new File(iosFolder 
								+ "/Logic/" + serviceName + "Logic.h"),
								ve.getTemplate("templates/ios/"+version+"/ios_service_header.vm"), velocityContext);
						generate(new File(iosFolder 
								+ "/Logic/" + serviceName + "Logic.m"),
								ve.getTemplate("templates/ios/"+version+"/ios_service_implementation.vm"), velocityContext);
							
					}
					}
	}
	public void generateDAOs(String iosFolder,List<Type> types) throws IOException
	{
		String version=System.getProperty(Constants.GENERATOR_IOS_VERSION,"1.0");
			
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		ve.init();
		
		for (Type type : types) {
			if(type.getName().startsWith("Login"))
			{
				System.out.println();
			}
			String typeName=type.getType();
			String fieldName=type.getName();
			String daoTypeName = typeName.endsWith("DTO") ? typeName
					.replace("DTO", "DAO") : typeName + "DAO";
			//
			VelocityContext velocityContext;
			velocityContext = new VelocityContext();
			velocityContext.put("className",daoTypeName );
			velocityContext.put("name",fieldName );
			velocityContext.put("mainClassName",typeName);
			velocityContext.put("fields", type.getFields());
			velocityContext.put("extraImports", type.getIOSExtraImports());
			velocityContext.put("objectFields", type.getObjectFields());
			velocityContext.put("objectArrayFields", type.getObjectArrayFields());
			velocityContext.put("baseFields",type.getBaseFields());
			velocityContext.put("baseArrayFields", type.getBaseArrayFields());
			velocityContext.put("username",System.getProperty("user.name"));
			velocityContext.put("projectName","Modelo");
			velocityContext.put("version", version);
			
			// IOS
			// Creaci—n del .h
			generate(new File(iosFolder+"/Model/DAO/" + daoTypeName + ".h"),ve.getTemplate("templates/ios/"+version+"/ios_dao_header.vm"),velocityContext);

			// IOS
			// Creaci—n del .m
			generate(new File(iosFolder+"/Model/DAO/" + daoTypeName + ".m"),ve.getTemplate("templates/ios/"+version+"/ios_dao_implementation.vm"),velocityContext);
			
			
		}
	}
	
	public void generateDTOs(String iosFolder,List<Type> types) throws IOException
	{
		String version=System.getProperty(Constants.GENERATOR_IOS_VERSION,"1.0");	
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		ve.init();
		
		for (Type type : types) {
			String typeName=type.getType();
			//
			VelocityContext velocityContext;
			velocityContext = new VelocityContext();
			velocityContext.put("className",typeName );
			velocityContext.put("fields", type.getFields());
			velocityContext.put("extraImports", type.getIOSExtraImports());
			velocityContext.put("objectFields", type.getObjectFields());
			velocityContext.put("objectArrayFields", type.getObjectArrayFields());
			velocityContext.put("baseFields",type.getBaseFields());
			velocityContext.put("baseArrayFields", type.getBaseArrayFields());
			velocityContext.put("username",System.getProperty("user.name"));
			velocityContext.put("projectName","Modelo");
			velocityContext.put("version", version);
			
			// IOS
			// Creaci—n del .h
			generate(new File(iosFolder+"/Model/DTO/" + typeName + ".h"),ve.getTemplate("templates/ios/"+version+"/ios_dto_header.vm"),velocityContext);

			// IOS
			// Creaci—n del .m
			generate(new File(iosFolder+"/Model/DTO/" + typeName + ".m"),ve.getTemplate("templates/ios/"+version+"/ios_dto_implementation.vm"),velocityContext);
		}
	}
	public void generateTasks(List<Message> messages, String onTask) throws ResourceNotFoundException, ParseErrorException, IOException {
		String version=System.getProperty(Constants.GENERATOR_IOS_VERSION,"1.0");
		String iosFolder=System.getProperty("ios.folder");

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		VelocityContext velocityContext;
		velocityContext = new VelocityContext();
		velocityContext.put("projectName", System.getProperties().get("project.name"));
		velocityContext.put("version", version);
		String packageName = System.getProperty("package.name", "com.test.model");
		velocityContext.put("packagename", packageName);

		Map<String, List<Message>> services = new TreeMap<String, List<Message>>();
		for (Message message : messages) {
			new File(iosFolder + "/Logic/Tasks/" + message.getService().toLowerCase() + "/").mkdirs();
			velocityContext.put("methodUcase", message.getMethod().substring(0, 1).toUpperCase() + message.getMethod().substring(1));
			if (message.getRequest() != null) {
				velocityContext.put("requestDTO", message.getRequest().getName());
			} else {
				velocityContext.put("requestDTO", "void");
			}
			if (message.getResponse() != null) {
				velocityContext.put("responseDTO", message.getResponse().getName());
			} else {
				velocityContext.put("responseDTO", "void");
			}
			velocityContext.put("serviceNameLower", message.getService().toLowerCase());
			velocityContext.put("serviceName", message.getService());
			velocityContext.put("method", message.getMethod());
			velocityContext.put("onTask", onTask);

			String nameString = StringUtils.capitalize(message.getMethod());
			
			// IOS
			// Creaci—n del .h
			generate(new File(iosFolder+"/Logic/Tasks/" + nameString + "Task.h"),ve.getTemplate("templates/ios/"+version+"/ios_task_header.vm"),velocityContext);

			// IOS
			// Creaci—n del .m
			generate(new File(iosFolder+"/Logic/Tasks/" + nameString + "Task.m"),ve.getTemplate("templates/ios/"+version+"/ios_task_implementation.vm"),velocityContext);

		}
	}
	public void generateHelper() throws IOException
	{
		String version=System.getProperty(Constants.GENERATOR_IOS_VERSION,"1.0");
		// TODO Auto-generated method stub
				String iosFolder=System.getProperty("ios.folder");
				
				VelocityEngine ve = new VelocityEngine();
				ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
				ve.setProperty("classpath.resource.loader.class",
						ClasspathResourceLoader.class.getName());
				ve.init();
				
				VelocityContext velocityContext;
				velocityContext = new VelocityContext();
				velocityContext.put("projectName",System.getProperties().get("project.name"));
				velocityContext.put("version", version);
				File header=new File(iosFolder 
						+ "/Logic/" + System.getProperties().get("project.name") + "Helper.h");
				if(!header.exists())
				{
				generate(header,
						ve.getTemplate("templates/ios/"+version+"/ios_helper_header.vm"), velocityContext);
				generate(new File(iosFolder 
						+ "/Logic/" + System.getProperties().get("project.name") + "Helper.m"),
						ve.getTemplate("templates/ios/"+version+"/ios_helper_implementation.vm"), velocityContext);
				}
	}
	@Override
	public void init(XMLModelGeneratorDocumentHandler handler) {
		
	}
}
