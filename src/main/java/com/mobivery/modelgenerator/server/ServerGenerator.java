package com.mobivery.modelgenerator.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.mobivery.modelgenerator.BaseGenerator;
import com.mobivery.modelgenerator.Message;
import com.mobivery.modelgenerator.Type;
import com.mobivery.modelgenerator.XMLModelGeneratorDocumentHandler;

public final class ServerGenerator extends BaseGenerator {
	private static final String JAVA_FOLDER = "src/main/java/";
	public void generateTypes(List<Type> types) throws IOException {
		
		String serverFolder=System.getProperty("server.folder",JAVA_FOLDER);
		String packageName = System.getProperty("package.name",
				"com.test.model");
		
		new File(serverFolder + packageName.replace(".", "/") + "/model/dao").mkdirs();
		new File(serverFolder + packageName.replace(".", "/") + "/model/dto").mkdirs();

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		/*Properties props = new Properties();
		props.put("input.encoding", "utf-8");*/
		ve.init();

		
		for (Type type : types) {
			String typeName = type.getName();
			if (typeName != null) {
				//
				VelocityContext velocityContext;
				String daoTypeName = typeName.endsWith("DTO") ? typeName
						.replace("DTO", "DAO") : typeName + "DAO";
				velocityContext = new VelocityContext();
				velocityContext.put("projectName",
						System.getProperties().get("project.name"));
				velocityContext.put("daoClassName", daoTypeName);
				velocityContext.put("className", typeName);
				velocityContext.put("fields", type.getFields());
				velocityContext.put("extraImports", type.getJavaDTOExtraImports(packageName));
				velocityContext.put("objectFields", type.getObjectFields());
				velocityContext.put("objectArrayFields",
						type.getObjectArrayFields());
				velocityContext.put("baseFields", type.getBaseFields());
				velocityContext.put("baseArrayFields",
						type.getBaseArrayFields());

				velocityContext.put("packagename", packageName);

				
				
				// JAVA
				// Creaci—n del DTO
				generate(new File(serverFolder + packageName.replace(".", "/")
						+ "/model/dto/" + typeName + ".java"),
						ve.getTemplate("android_dto.vm"), velocityContext);
				// JAVA				
				// Creaci—n del DAO

				velocityContext.put("extraImports", type.getJavaDAOExtraImports(packageName));
				
				generate(new File(serverFolder + packageName.replace(".", "/")
						+ "/model/dao/" + daoTypeName + ".java"),
						ve.getTemplate("android_dao.vm"), velocityContext);
				
				

			}
		}
	}
	public void generateTasks(List<Message> messages,String onTask) throws IOException {
//		// JAVA
//		// Creaci—n de Tareas
//		
//		String androidFolder=System.getProperty("android.folder",JAVA_FOLDER);
//		String commonFolder=System.getProperty("common.folder",JAVA_FOLDER);
//		if(commonFolder==null)
//		{
//			commonFolder=androidFolder;
//		}
//		VelocityEngine ve = new VelocityEngine();
//		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//		ve.setProperty("classpath.resource.loader.class",
//				ClasspathResourceLoader.class.getName());
//		ve.init();
//		
//		VelocityContext velocityContext;
//		velocityContext = new VelocityContext();
//		velocityContext.put("projectName",System.getProperties().get("project.name"));
//		String packageName = System.getProperty("package.name",
//				"com.test.model");
//		velocityContext.put("packagename", packageName);
//
//		Map<String,List<Message>> services=new TreeMap<String, List<Message>>();
//		for(Message message:messages)
//		{
//			new File(androidFolder + packageName.replace(".", "/") + "/tasks/"+message.getService().toLowerCase()+"/").mkdirs();
//			velocityContext.put("methodUcase", message.getMethod().substring(0,1).toUpperCase()+message.getMethod().substring(1));
//			if(message.getRequest()!=null)
//			{
//				velocityContext.put("requestDTO", message.getRequest().getName());
//			}
//			else
//			{
//				velocityContext.put("requestDTO","Void");
//			}
//			if(message.getResponse()!=null)
//			{
//				velocityContext.put("responseDTO", message.getResponse().getName());
//			}
//			else
//			{
//				velocityContext.put("responseDTO","Void");
//			}
//			velocityContext.put("serviceNameLower", message.getService().toLowerCase());
//			velocityContext.put("serviceName", message.getService());
//			velocityContext.put("method", message.getMethod());
//			velocityContext.put("onTask",onTask);
//			generate(new File(androidFolder + packageName.replace(".", "/")
//					+ "/tasks/"+message.getService().toLowerCase() +"/"+ message.getMethod().substring(0,1).toUpperCase()+message.getMethod().substring(1) + "Task.java"),
//					ve.getTemplate("android_tasks.vm"), velocityContext);
//		}		 
	}
	
	public void generateServices(List<Message> messages,String onSend,String onReceive,String onError) throws IOException {
		// JAVA
		// Creaci—n del Servicio
		
		String serverFolder=System.getProperty("server.folder",JAVA_FOLDER);
		
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		ve.init();
		
		VelocityContext velocityContext;
		velocityContext = new VelocityContext();
		velocityContext.put("projectName",System.getProperties().get("project.name"));
		String packageName = System.getProperty("package.name",
				"com.test.model");
		velocityContext.put("packagename", packageName);
		
		velocityContext.put("onSend",onSend);
		velocityContext.put("onReceive",onReceive);
		velocityContext.put("onError",onError);

		
		new File(serverFolder + packageName.replace(".", "/") + "/server/rest").mkdirs();
		
		velocityContext.put("messages",messages);
		generate(new File(serverFolder + packageName.replace(".", "/")
				+ "/server/rest/" + System.getProperties().get("project.name") + "Rest.java"),
				ve.getTemplate("server.vm"), velocityContext);
	}
	@Override
	public void init(XMLModelGeneratorDocumentHandler handler) {
		// TODO Auto-generated method stub
		
	}
	public void generateHelper()
	{
		
	}
}
