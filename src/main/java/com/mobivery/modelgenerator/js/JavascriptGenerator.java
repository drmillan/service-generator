package com.mobivery.modelgenerator.js;

import java.io.IOException;
import java.util.List;

import com.mobivery.modelgenerator.BaseGenerator;
import com.mobivery.modelgenerator.Message;
import com.mobivery.modelgenerator.Type;
import com.mobivery.modelgenerator.XMLModelGeneratorDocumentHandler;

public class JavascriptGenerator extends BaseGenerator {

	private static final String JAVA_FOLDER = "gen/";
	private XMLModelGeneratorDocumentHandler handler;
	
	@Override
	public void generateServices(List<Message> messages, String onSend,
			String onReceive, String onError) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateTasks(List<Message> messages, String onTask)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateTypes(List<Type> types) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateHelper() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(XMLModelGeneratorDocumentHandler handler) {
		this.handler = handler;
	}

}
