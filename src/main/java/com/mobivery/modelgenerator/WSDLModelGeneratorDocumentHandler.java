package com.mobivery.modelgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class WSDLOperation {
	String name;
	String documentation;
	String inputMessage;
	String outputMessage;
	WSDLMessage inputMessageObject;
	WSDLMessage outputMessageObject;
}

class WSDLPart {
	String name;
	String type;
}

class WSDLElement {
	String name;
	String type;
	int minOccurs;
}

class WSDLComplexType {
	String name;
	List<WSDLElement> elements = new ArrayList<WSDLElement>();
}

class WSDLMessage {
	String name;
	List<WSDLPart> parts = new ArrayList<WSDLPart>();
}

/**
 * Handler del parseador del fichero de definici—n del modelo
 * 
 * @author DRM
 * 
 */
public class WSDLModelGeneratorDocumentHandler extends DefaultHandler {
	private static final Logger LOG = Logger.getLogger(WSDLModelGeneratorDocumentHandler.class.getName());

	Map<String, WSDLOperation> operationsWSDL = new TreeMap<String, WSDLOperation>();
	Map<String,WSDLMessage> messagesWSDL=new TreeMap<String, WSDLMessage>();
	WSDLOperation currentOperation = null;
	WSDLMessage currentMessage=null;

	private List<Type> types = new ArrayList<Type>();
	private List<Message> messages = new ArrayList<Message>();

	

	/**
	 * Callback de parse de inicio de elemento
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("operation")) {
			String name = attributes.getValue("name");
			if (operationsWSDL.containsKey(name)) {
				currentOperation = null;
			} else {
				currentOperation = new WSDLOperation();
				currentOperation.name = attributes.getValue("name");
				operationsWSDL.put(currentOperation.name, currentOperation);
			}
		}
		if (qName.equals("input")) {
			if (currentOperation != null) {
				currentOperation.inputMessage = attributes.getValue("message");
			}
		}
		if (qName.equals("output")) {
			if (currentOperation != null) {
				currentOperation.outputMessage = attributes.getValue("message");
			}
		}
		if (qName.equals("message")) {
			String name = attributes.getValue("name");
			currentMessage=new WSDLMessage();
			currentMessage.name=name;
			messagesWSDL.put(currentMessage.name, currentMessage);
		}
		if(qName.equals("part")){
			WSDLPart part=new WSDLPart();
			part.name=attributes.getValue("name");
			part.type=attributes.getValue("type");
			currentMessage.parts.add(part);
		}
	}

	public void endDocument() {
		System.out.println("Finished");
		for (WSDLOperation operation : operationsWSDL.values()) {
			if(operation.inputMessage!=null && operation.inputMessage.startsWith("typens:"))
			{
				String key=operation.inputMessage.substring("typens:".length());
				operation.inputMessageObject=messagesWSDL.get(key);
			}
			if(operation.outputMessage!=null && operation.outputMessage.startsWith("typens:"))
			{
				String key=operation.inputMessage.substring("typens:".length());
				operation.outputMessageObject=messagesWSDL.get(key);
			}
			System.out.println(operation.name + " " + operation.inputMessage + " " + operation.outputMessage);
			
			
			// Creamos el mensaje de tipo mobivery
			Message message=new Message();
			
			message.setType("Post");
			message.setDescription(operation.documentation);
			message.setMethod(operation.name);
			/**
			 * REQUEST
			 */
			if(operation.inputMessageObject!=null)
			{
				Request request=new Request();
				request.setName(operation.inputMessageObject.name.substring(0,1).toUpperCase()+operation.inputMessageObject.name.substring(1));;
				for(WSDLPart part:operation.inputMessageObject.parts)
				{
					TypeField typeField=new TypeField();
					typeField.setComment("");
					typeField.setName(part.name);
					typeField.setType(part.type);
				}
				types.add(request);
				message.setRequest(request);
			}
			/**
			 * RESPONSE
			 */
			if(operation.outputMessageObject!=null)
			{
				Type response=new Type();
				response.setName(operation.outputMessageObject.name.substring(0,1).toUpperCase()+operation.outputMessageObject.name.substring(1));;
				for(WSDLPart part:operation.outputMessageObject.parts)
				{
					TypeField typeField=new TypeField();
					typeField.setComment("");
					typeField.setName(part.name);
					typeField.setType(part.type);
				}
				types.add(response);
				message.setResponse(response);
			}
			
			message.setService("WSDL");
			messages.add(message);
		}
	}

	public List<Type> getTypes() {
		return types;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public String getOnReceive() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOnError() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOnSend() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getOnTask(){
		return null;
	}

}
