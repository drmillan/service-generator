package com.mobivery.modelgenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mobivery.modelgenerator.ios.IOSGenerator;
import com.mobivery.modelgenerator.javajson.AndroidJSONGenerator;
import com.mobivery.modelgenerator.server.ServerGenerator;
import com.mobivery.modelgenerator.util.LOG;

/**
 * Generador del c—digo de lectura de JSON para IOS y Java
 * @author DRM
 *
 */
public final class GenerateDTO {

	private final static Map<String,BaseGenerator> generators=new TreeMap<String,BaseGenerator>();
	
	
	/**
	 *  Constructor privado
	 */
	private GenerateDTO()
	{
		generators.put("ios.folder", new IOSGenerator());
		generators.put("android.folder", new AndroidJSONGenerator());
		generators.put("server.folder", new ServerGenerator());
	}
	/**
	 * Generador de c—digo de lectura de JSON para IOS y Java
	 * @param args
	 * @throws Exception
	 */
	public final static void main(String args[]) throws IOException,SAXException,ParserConfigurationException {
		Logger logger = Logger.getRootLogger();
		
		logger.setLevel(Level.ERROR);
		
		if("DEBUG".equals(System.getProperties().get("log.level")))
		{
			logger.setLevel(Level.DEBUG);
		}
		if("INFO".equals(System.getProperties().get("log.level")))
		{
			logger.setLevel(Level.INFO);
		}
		
		BasicConfigurator.configure();
		
		new GenerateDTO();				
		if(args.length==0)return;
		InputSource is;
			if(args[0].startsWith("/"))
			{
				is=new InputSource(new FileInputStream(args[0]));
			}
			else
			{
				String filePath = "src/main/resources/"+args[0];
				File generatorDef = new File(filePath);
				is=new InputSource(new FileInputStream(generatorDef));
			}
			is.setEncoding("utf-8");
			LOG.info("[config loading]:Reading project definition:"+args[0]);
			XMLModelGeneratorDocumentHandler handler=new XMLModelGeneratorDocumentHandler();	
			SAXParserFactory.newInstance().newSAXParser().parse(is, handler);
						
			// Comprobamos los generadores que hay que ejecutar
			for(Map.Entry<String, BaseGenerator> generatorEntry:generators.entrySet())
			{				
				if(System.getProperties().containsKey(generatorEntry.getKey()))
				{
					LOG.info("*** [code generation]:Start source code generation ["+generatorEntry.getKey()+"]");
					generatorEntry.getValue().init(handler);
					LOG.info("[code generation]:Type generation ["+generatorEntry.getKey()+"]");
					generatorEntry.getValue().generateTypes(handler.getTypes());
					LOG.info("[code generation]:Service generation ["+generatorEntry.getKey()+"]");
					generatorEntry.getValue().generateServices(handler.getMessages(),handler.getOnSend(),handler.getOnReceive(),handler.getOnError());
					LOG.info("[code generation]:Tasks generation ["+generatorEntry.getKey()+"]");
					generatorEntry.getValue().generateTasks(handler.getMessages(),handler.onTask());
					generatorEntry.getValue().generateHelper();
					LOG.info("[code generation]:End source code generation ["+generatorEntry.getKey()+"]");					
				}
			}
	}

}
