package com.mobivery.modelgenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Clase base para generador de DAO y DTO JSON
 * @author DRM
 *
 */
public abstract class BaseGenerator {
	Logger logger=Logger.getLogger("Generator");
	/**
	 * Aplica la plantilla a un fichero utilizando el contexto indicado
	 * @param file Fichero sobre el que se genera el artefacto
	 * @param template Plantilla para la generaci—n del fichero
	 * @param context Contexto Velocity con par‡metros para la generaci—n
	 * @throws IOException
	 */
	protected void generate(File file,Template template,VelocityContext context) throws IOException
	{
		file.createNewFile();
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
		template.merge(context, bw);
		bw.flush();
		bw.close();
		
		
		String configFile="binutils/default.cfg";
		if(file.getName().endsWith(".java"))
		{
			configFile="binutils/default.cfg";
		}
		else if(file.getName().endsWith(".h") || file.getName().endsWith(".m"))
		{
			configFile="binutils/objc.cfg";
		}
		
		String execLine="binutils/uncrustify --no-backup -c "+ configFile+" -f "+file.getAbsolutePath();
		try
		{
			Runtime.getRuntime().exec(execLine).waitFor();
			logger.info("[beautify code]:"+file.getName());
		}
		catch(Exception ex)
		{
			logger.warn("[beautify code error]:"+file.getName());
		}
	}

	protected boolean hasMultipart(List<Message> list, String string) {
		for(Message message:list)
		{
			if(message.getRequest().isMultipart())return true;
		}
		return false;
	}

	protected boolean hasMethod(List<Message> list, String methodName) {
		for(Message message:list)
		{
			if(message.getType()!=null && message.getType().equalsIgnoreCase(methodName))return true;
		}
		return false;
	}
	public abstract void generateServices(List<Message> messages,String onSend,String onReceive,String onError) throws IOException;
	public abstract void generateTasks(List<Message> messages,String onTask) throws IOException;
	public abstract void generateTypes(List<Type> types) throws IOException;
	public abstract void generateHelper() throws IOException;
	public abstract void init(XMLModelGeneratorDocumentHandler handler);
}
