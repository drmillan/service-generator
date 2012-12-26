package com.mobivery.modelgenerator.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
class TestObject2
{
	private int value1;
	private int value2;
	public int getValue1() {
		return value1;
	}
	public void setValue1(int value1) {
		this.value1 = value1;
	}
	public int getValue2() {
		return value2;
	}
	public void setValue2(int value2) {
		this.value2 = value2;
	}
}
class TestObject
{
	private int value1;
	private long value2;
	private double value3;
	private float value4;
	private boolean value5;
	private List<TestObject2> value6;
	public int getValue1() {
		return value1;
	}
	public void setValue1(int value1) {
		this.value1 = value1;
	}
	public long getValue2() {
		return value2;
	}
	public void setValue2(long value2) {
		this.value2 = value2;
	}
	public double getValue3() {
		return value3;
	}
	public void setValue3(double value3) {
		this.value3 = value3;
	}
	public float getValue4() {
		return value4;
	}
	public void setValue4(float value4) {
		this.value4 = value4;
	}
	public boolean isValue5() {
		return value5;
	}
	public void setValue5(boolean value5) {
		this.value5 = value5;
	}
	public List<TestObject2> getValue6() {
		return value6;
	}
	public void setValue6(List<TestObject2> value6) {
		this.value6 = value6;
	}
	
}
/**
 * POC de generaci—n aleatoria de DTOs
 * @author DRM
 *
 */
public class DTORandomizer {
	Random random=new Random();
	public Object randomizeDTO(Class objectClass)
	{
		if(objectClass==Integer.class || objectClass==Integer.TYPE)
		{
			return new Integer(random.nextInt());
		}
		else if(objectClass==Float.class|| objectClass==Float.TYPE)
		{
			return new Float(random.nextFloat());
		}
		else if(objectClass==Double.class|| objectClass==Double.TYPE)
		{
			return new Double(random.nextFloat());
		}
		else if(objectClass==Long.class|| objectClass==Long.TYPE)
		{
			return new Long(random.nextLong());
		}
		else if(objectClass==Boolean.class|| objectClass==Boolean.TYPE)
		{
			return Boolean.valueOf(random.nextBoolean());
		}
		/*
		else if(objectClass==List.class || objectClass==ArrayList.class)
		{			
			return new ArrayList();
		}
		*/		
		else
		{
			try
			{
				Field fields[]=objectClass.getDeclaredFields();
				Object instance=objectClass.newInstance();
				for(Field field:fields)
				{
					String fieldName=field.getName();
					Class fieldType=field.getType();
					// Check if setterMethod exists
					try
					{
						String setMethod="set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
						Method setterMethod=objectClass.getMethod(setMethod, fieldType);
						if(setterMethod!=null)
						{
							if(fieldType==List.class)
							{
								if(field.getGenericType() instanceof ParameterizedType)
								{
									ParameterizedType parametrizedType=(ParameterizedType)field.getGenericType();
									List items=new ArrayList();
									int nItems=Math.abs(random.nextInt())%100;
									for(int i=0;i!=nItems;i++)
									{
										
										items.add(randomizeDTO((Class)parametrizedType.getActualTypeArguments()[0]));
									}
									setterMethod.invoke(instance,items);
								}
								else
								{
									List items=new ArrayList();
									int nItems=Math.abs(random.nextInt())%100;
									for(int i=0;i!=nItems;i++)
									{
										
										items.add(randomizeDTO(field.getGenericType().getClass()));
									}
									setterMethod.invoke(instance,items);
								}
							}
							else
							{
								setterMethod.invoke(instance,randomizeDTO(fieldType));
							}
						}
						else
						{
							
						}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			
			return instance;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		}
		return null;
	}
	public static final void main(String args[])
	{
		Object obj=new DTORandomizer().randomizeDTO(TestObject.class);
		
		System.out.println(obj);
	}
}
