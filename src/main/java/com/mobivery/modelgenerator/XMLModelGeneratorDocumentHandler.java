package com.mobivery.modelgenerator;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Handler del parseador del fichero de definici�n del modelo
 * @author DRM
 *
 */
public class XMLModelGeneratorDocumentHandler extends DefaultHandler
{
	public static final int TAG_MESSAGE = 1;
	public static final int TAG_REQUEST = 2;
	public static final int TAG_RESPONSE = 3;
	public static final int TAG_TYPE = 4;
	
	private static final String NAME="name";
	private static final String TYPE="type";
	private static final String STYLE="style";
	private Message currentMessage;
	private Type currentType;
	private int currentParent;
	private String onSend;
	private String onReceive;
	private String onError;
	private String onTask;
	private String javaServiceException;
	private String javaServiceExceptionListener;
	
	
	private List<Type> types = new ArrayList<Type>();
	private List<Message> messages = new ArrayList<Message>();
	
	/**
	 * Callback de parse de inicio de elemento
	 */
	public void startElement(String uri, String localName,
			String qName, Attributes attributes)
			throws SAXException {
		if(qName.equals("protocol"))
		{
			onSend=attributes.getValue("onSend");
			onReceive=attributes.getValue("onReceive");
			onError=attributes.getValue("onError");
			onTask=attributes.getValue("onTask");
			javaServiceException=attributes.getValue("javaServiceException");
			javaServiceExceptionListener=attributes.getValue("javaServiceExceptionListener");
		}
		if (qName.equals("message")) {
			currentMessage = new Message();
			currentMessage.setName(attributes.getValue(NAME)
					.trim());
			currentMessage.setType(attributes.getValue("type")
					.trim());
			currentMessage.setDescription(attributes
					.getValue("description"));
			currentMessage.setService(attributes.getValue("service"));
			currentMessage.setMethod(attributes.getValue("method")==null?"Complete":attributes.getValue("method"));
			currentParent = TAG_MESSAGE;
			messages.add(currentMessage);
			
			
			// Creamos un request Dummy por si no est� definido
			
			Request request = new Request();
			String type=currentMessage.getName()+"RequestDTO";
			request.setType(type);
			request.setName(type);
			currentMessage.setRequest(request);
			types.add(request);
		}
		if(qName.equals("url"))
		{
			currentMessage.setUrl(attributes.getValue("address"));
			currentMessage.setServer(attributes.getValue("server"));
			currentMessage.setPath(attributes.getValue("path"));
		}
		if (qName.equals("request")) {
			Request request = new Request();
			String name=attributes.getValue(NAME);
			String type=attributes.getValue(TYPE);
			if(currentMessage.getRequest()!=null)
			{
				types.remove(currentMessage.getRequest());
			}
			if(type==null && name==null)
			{
				type=currentMessage.getName()+"RequestDTO";
				name=currentMessage.getName()+"RequestDTO";
			}
			if(name==null && type!=null)
			{
				request.setName(type);
				request.setType(type);
			}
			else if(name!=null && type==null)
			{
				request.setName(name);
				request.setType(name);				
			}
			else if(name!=null && type!=null)
			{
				request.setName(name);
				request.setType(type);								
			}
			request.setStyle(attributes.getValue(STYLE));
			
			types.add(request);
			currentMessage.setRequest(request);
			currentParent = TAG_REQUEST;
		}
		if (qName.equals("response")) {
			Type response = new Type();
			String name=attributes.getValue(NAME);
			String type=attributes.getValue(TYPE);
			if(type==null && name==null)
			{
				type=currentMessage.getName()+"ResponseDTO";
				name=currentMessage.getName()+"ResponseDTO";
			}
			if(name==null && type!=null)
			{
			response.setName(type);
			response.setType(type);
			}
			else if(name!=null && type==null)
			{
				response.setName(name);
				response.setType(name);				
			}
			else if(name!=null && type!=null)
			{
				response.setName(name);
				response.setType(type);								
			}
			types.add(response);
			currentMessage.setResponse(response);
			currentParent = TAG_RESPONSE;
		}
		if (qName.equals("type")) {
			currentType = new Type();
			String name=attributes.getValue(NAME);
			String type=attributes.getValue(TYPE);
			if(name==null && type!=null)
			{
				currentType.setName(type);
				currentType.setType(type);
			}
			else if(name!=null && type==null)
			{
				currentType.setName(name);
				currentType.setType(name);				
			}
			else if(name!=null && type!=null)
			{
				currentType.setName(name);
				currentType.setType(type);								
			}
			currentParent = TAG_TYPE;
			types.add(currentType);
		}
		if (qName.equals("field")) {
			TypeField typeField = new TypeField();
			typeField.setName(attributes.getValue(NAME));
			typeField.setComment(attributes
					.getValue("description"));
			typeField.setType(attributes.getValue("type")
					.trim());
			typeField.setMimeType(attributes.getValue("mimeType"));
			switch (currentParent) {
			case TAG_REQUEST:
				currentMessage.getRequest().getFields()
						.add(typeField);
				break;
			case TAG_RESPONSE:
				currentMessage.getResponse().getFields()
						.add(typeField);
				break;
			case TAG_TYPE:
				currentType.getFields().add(typeField);
				break;
			}
		}
	}

	public List<Type> getTypes() {
		return types;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public String getOnSend() {
		return onSend;
	}

	public void setOnSend(String onSend) {
		this.onSend = onSend;
	}

	public String getOnReceive() {
		return onReceive;
	}

	public void setOnReceive(String onReceive) {
		this.onReceive = onReceive;
	}

	public String getOnError() {
		return onError;
	}

	public void setOnError(String onError) {
		this.onError = onError;
	}

	public String onTask() {
		return onTask;
	}

	public String getJavaServiceException() {
		return javaServiceException;
	}

	public String getJavaServiceExceptionListener() {
		return javaServiceExceptionListener;
	}
	
	
}
