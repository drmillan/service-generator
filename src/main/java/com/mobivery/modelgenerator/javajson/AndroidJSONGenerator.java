package com.mobivery.modelgenerator.javajson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.texen.util.FileUtil;

import com.mobivery.modelgenerator.BaseGenerator;
import com.mobivery.modelgenerator.Constants;
import com.mobivery.modelgenerator.Message;
import com.mobivery.modelgenerator.Type;
import com.mobivery.modelgenerator.XMLModelGeneratorDocumentHandler;

public final class AndroidJSONGenerator extends BaseGenerator {
	private static final String JAVA_FOLDER = "src/main/java/";
	private XMLModelGeneratorDocumentHandler handler;

	public void generateTypesBundle(List<Type> types) throws IOException{
		String version=System.getProperty(Constants.GENERATOR_ANDROID_VERSION,"1.0");
		String androidFolder = System.getProperty("android.folder", JAVA_FOLDER);
		String commonFolder = System.getProperty("common.folder", JAVA_FOLDER);
		String packageName = System.getProperty("package.name", "com.test.model");
		
		new File(commonFolder + packageName.replace(".", "/") + "/model/dao").mkdirs();
		new File(commonFolder + packageName.replace(".", "/") + "/model/dto").mkdirs();

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		
		Map<String,Type> typeMap=new TreeMap<String, Type>();
		for(Type type:types)
		{			
			typeMap.put(type.getName(),type);
		}
		
		/*
		 * Properties props = new Properties(); props.put("input.encoding",
		 * "utf-8");
		 */
		ve.init();
		VelocityContext velocityContext=new VelocityContext();
		velocityContext.put("projectName", System.getProperties().get("project.name"));		
		velocityContext.put("serviceException", handler.getJavaServiceException());
		velocityContext.put("serviceExceptionListener", handler.getJavaServiceExceptionListener());
		velocityContext.put("packagename", packageName);
		velocityContext.put("version", version);
		velocityContext.put("dtos", typeMap.values());		
		
		new File(commonFolder + packageName.replace(".", "/") + "/model/dto/base/").mkdirs();
		generate(new File(commonFolder + packageName.replace(".", "/") + "/model/dto/base/" + System.getProperties().get("project.name") + "DTOBundle.java"), ve.getTemplate("templates/android/"+version+"/android_base_dto_bundle.vm"), velocityContext);
	}
	
	
	
	public void generateTypes(List<Type> types) throws IOException {

		String version=System.getProperty(Constants.GENERATOR_ANDROID_VERSION,"1.0");
		
		generateTypesBundle(types);
		
		String androidFolder = System.getProperty("android.folder", JAVA_FOLDER);
		String commonFolder = System.getProperty("common.folder", JAVA_FOLDER);
		String packageName = System.getProperty("package.name", "com.test.model");

		new File(commonFolder + packageName.replace(".", "/") + "/model/dao").mkdirs();
		new File(commonFolder + packageName.replace(".", "/") + "/model/dto").mkdirs();

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		/*
		 * Properties props = new Properties(); props.put("input.encoding",
		 * "utf-8");
		 */
		ve.init();

		for (Type type : types) {
			String typeName = type.getName();
			if (typeName != null) {
				//
				VelocityContext velocityContext;
				String daoTypeName = typeName.endsWith("DTO") ? typeName.replace("DTO", "DAO") : typeName + "DAO";
				velocityContext = new VelocityContext();
				velocityContext.put("projectName", System.getProperties().get("project.name"));
				velocityContext.put("daoClassName", daoTypeName);
				velocityContext.put("className", typeName);
				velocityContext.put("fields", type.getFields());
				velocityContext.put("extraImports", type.getJavaDTOExtraImports(packageName));
				velocityContext.put("objectFields", type.getObjectFields());
				velocityContext.put("objectArrayFields", type.getObjectArrayFields());
				velocityContext.put("baseFields", type.getBaseFields());
				velocityContext.put("baseArrayFields", type.getBaseArrayFields());
				velocityContext.put("serviceException", handler.getJavaServiceException());
				velocityContext.put("serviceExceptionListener", handler.getJavaServiceExceptionListener());
				velocityContext.put("packagename", packageName);
				velocityContext.put("version", version);

				// JAVA
				// Creaci—n del DTO
				
				File dtoFile=new File(commonFolder + packageName.replace(".", "/") + "/model/dto/" + typeName + ".java");
				if(!dtoFile.exists())
				{
					generate(dtoFile, ve.getTemplate("templates/android/"+version+"/android_base_dto.vm"), velocityContext);
				}
				// JAVA
				// Creaci—n del DAO

				velocityContext.put("extraImports", type.getJavaDAOExtraImports(packageName));
				
				File daoFile=new File(commonFolder + packageName.replace(".", "/") + "/model/dao/" + daoTypeName + ".java");
				generate(daoFile, ve.getTemplate("templates/android/"+version+"/android_dao.vm"), velocityContext);

			}
		}
	}

	public void generateTasks(List<Message> messages, String onTask) throws IOException {
		// JAVA
		// Creaci—n de Tareas
		String version=System.getProperty(Constants.GENERATOR_ANDROID_VERSION,"1.0");
		String androidFolder = System.getProperty("android.folder", JAVA_FOLDER);
		String commonFolder = System.getProperty("common.folder", JAVA_FOLDER);
		if (commonFolder == null) {
			commonFolder = androidFolder;
		}
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		VelocityContext velocityContext;
		velocityContext = new VelocityContext();
		velocityContext.put("projectName", System.getProperties().get("project.name"));
		String packageName = System.getProperty("package.name", "com.test.model");
		velocityContext.put("packagename", packageName);

		Map<String, List<Message>> services = new TreeMap<String, List<Message>>();
		for (Message message : messages) {
			new File(androidFolder + packageName.replace(".", "/") + "/tasks/" + message.getService().toLowerCase() + "/").mkdirs();
			velocityContext.put("methodUcase", message.getMethod().substring(0, 1).toUpperCase() + message.getMethod().substring(1));
			if (message.getRequest() != null) {
				velocityContext.put("requestDTO", message.getRequest().getName());
			} else {
				velocityContext.put("requestDTO", "Void");
			}
			if (message.getResponse() != null) {
				velocityContext.put("responseDTO", message.getResponse().getName());
			} else {
				velocityContext.put("responseDTO", "Void");
			}
			velocityContext.put("serviceNameLower", message.getService().toLowerCase());
			velocityContext.put("serviceName", message.getService());
			velocityContext.put("method", message.getMethod());
			velocityContext.put("onTask", onTask);
			velocityContext.put("serviceException", handler.getJavaServiceException());
			velocityContext.put("serviceExceptionListener", handler.getJavaServiceExceptionListener());
			velocityContext.put("version", version);

			File generationFile=new File(androidFolder + packageName.replace(".", "/") + "/tasks/" + message.getService().toLowerCase() + "/" + message.getMethod().substring(0, 1).toUpperCase()
					+ message.getMethod().substring(1) + "Task.java");
			generate(generationFile, ve.getTemplate("templates/android/"+version+"/android_tasks.vm"), velocityContext);
		}
	}

	public void generateServices(List<Message> messages, String onSend, String onReceive, String onError) throws IOException {
		String version=System.getProperty(Constants.GENERATOR_ANDROID_VERSION,"1.0");
		// JAVA
		// Creaci—n del Servicio

		String androidFolder = System.getProperty("android.folder", JAVA_FOLDER);
		String commonFolder = System.getProperty("common.folder", JAVA_FOLDER);
		if (commonFolder == null) {
			commonFolder = androidFolder;
		}

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		VelocityContext velocityContext;
		velocityContext = new VelocityContext();
		velocityContext.put("projectName", System.getProperties().get("project.name"));
		String packageName = System.getProperty("package.name", "com.test.model");
		velocityContext.put("packagename", packageName);

		velocityContext.put("onSend", onSend);
		velocityContext.put("onReceive", onReceive);
		velocityContext.put("onError", onError);
		velocityContext.put("version", version);

		Map<String, List<Message>> services = new TreeMap<String, List<Message>>();
		for (Message message : messages) {
			String service = message.getService();
			if (service == null)
				service = "Complete";
			if (!services.containsKey(service)) {
				services.put(service, new ArrayList<Message>());
			}
			services.get(service).add(message);
		}

		new File(commonFolder + packageName.replace(".", "/") + "/logic/base/").mkdirs();

		for (String serviceName : services.keySet()) {
			boolean hasGet=hasMethod(services.get(serviceName),"Get") || hasMethod(services.get(serviceName),"GetJSON");
			boolean hasPut=hasMethod(services.get(serviceName),"Put") || hasMethod(services.get(serviceName),"PutJSON");
			boolean hasPost=hasMethod(services.get(serviceName),"Post") || hasMethod(services.get(serviceName),"PostJSON");
			boolean hasDelete=hasMethod(services.get(serviceName),"Delete") || hasMethod(services.get(serviceName),"DeleteJSON");
			boolean hasMultipart = hasMultipart(services.get(serviceName), "Multipart");
			velocityContext.put("hasGet", hasGet);
			velocityContext.put("hasPut", hasPut);
			velocityContext.put("hasPost", hasPost);
			velocityContext.put("hasDelete", hasDelete);
			velocityContext.put("hasMultipart", hasMultipart);
			velocityContext.put("messages", services.get(serviceName));
			velocityContext.put("version", version);
			
			velocityContext.put("serviceName", serviceName);
			velocityContext.put("serviceException", handler.getJavaServiceException());
			velocityContext.put("serviceExceptionListener", handler.getJavaServiceExceptionListener());
			generate(new File(commonFolder + packageName.replace(".", "/") + "/logic/base/Base" + serviceName + "Logic.java"), ve.getTemplate("templates/android/"+version+"/android_base_service.vm"), velocityContext);
			// Comprobar si existe o no el servicio derivado
			new File(androidFolder + packageName.replace(".", "/") + "/logic/").mkdirs();
			File f = new File(androidFolder + packageName.replace(".", "/") + "/logic/" + serviceName + "Logic.java");			
			// Comprobar si existe o no el servicio derivado
			if (!f.exists()) {
				generate(f, ve.getTemplate("templates/android/"+version+"/android_service.vm"), velocityContext);
			}
		}
		
		// Copy NetworkTools
		new File(androidFolder+"/com/mobivery/utils").mkdirs();
		FileUtils.copyInputStreamToFile(getClass().getResourceAsStream("/templates/android/CustomSSLSocketFactory.java"), 
				new File(androidFolder+"/com/mobivery/utils/CustomSSLSocketFactory.java"));
		FileUtils.copyInputStreamToFile(getClass().getResourceAsStream("/templates/android/HttpClientHelper.java"), 
				new File(androidFolder+"/com/mobivery/utils/HttpClientHelper.java"));
		
	}

	@Override
	public void init(XMLModelGeneratorDocumentHandler handler) {
		this.handler = handler;
	}

	public void generateHelper() throws IOException {
		String version=System.getProperty(Constants.GENERATOR_ANDROID_VERSION,"1.0");
		// JAVA
		// Creaci—n del Servicio

		String androidFolder = System.getProperty("android.folder", JAVA_FOLDER);
		String commonFolder = System.getProperty("common.folder", JAVA_FOLDER);
		if (commonFolder == null) {
			commonFolder = androidFolder;
		}

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

			new File(commonFolder + packageName.replace(".", "/") + "/logic/").mkdirs();
			File f = new File(commonFolder + packageName.replace(".", "/") + "/logic/"+System.getProperties().get("project.name")+"Helper.java");
			// Comprobar si existe o no el servicio derivado
			if (!f.exists()) {
				generate(f, ve.getTemplate("templates/android/"+version+"/android_helper.vm"), velocityContext);
			}
	}
}
