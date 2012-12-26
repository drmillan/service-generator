
- Generador de c�digo fuente de acceso y consumo de servicios web

	**********************************************************************
	Configuraci�n de Eclipse
	**********************************************************************
	
	Si Eclipse no tiene instalado el m�dulo de Eclipse Marketplace (la opci�n aparece bajo el menu Help), instalarlo
	mediante  Help -> Install new software, buscar marketplace e instalar
	
	Desde Eclipse Marketplace, buscar Maven , e instalar el plugin m2e [Maven para eclipse]
	
	**********************************************************************
	Modelo de definici�n de servicios
	**********************************************************************

	- Los servicios de una aplicaci�n se definen en un fichero localizado
	en la ruta src/main/resources/projects con formato XML
	
	- El fichero de definici�n define los tipos y servicios asociados a la
	aplicaci�n siguiendo el siguiente formato:
	
	**********************************************************************
	Esquema de fichero de definici�n de servicios:
	**********************************************************************
	
	<protocol [OPCIONES_PROTOCOL]>
		<messages>
			<!-- ############################# -->
			<!-- LISTADO DE MENSAJES DE LA APP -->
			<!-- ############################# -->
			<message [ATRIBUTOS_MESSAGE]>
				<urlPattern>
					<url [ATRIBUTOS_URL]/>
				</urlPattern>
				<request [ATRIBUTOS_REQUEST]>
					<field [ATRIBUTOS_FIELD]/>
					[...]
				</request>
				<response [ATRIBUTOS_RESPONSE]>
					<field [ATRIBUTOS_FIELD]/>
					[...]
				</response>
			</message>
			[...]
		</messages>
		<types>
			<!-- ########################## -->
			<!-- LISTADO DE TIPOS DE LA APP -->
			<!-- ########################## -->		
			<type [ATRIBUTOS_TYPE]>
				<field [ATRIBUTOS_FIELD]/>
				[...]
			</type>
		</types>
	</protocol>
	
	
	Atributos de las etiquetas del fichero de definici�n de servicios:
	
	---------------------------------------------------------------------
	
	TAG:
		protocol
	
	ATRIBUTOS:
		javaServiceException [Opcional]  Clase java (generaci�n Android, Server) que encapsula la excepci�n de servicio
    	javaServiceExceptionListener [Opcional] Interfaz java (generaci�n Android, Server) que es llamada con la excepci�n
    	onSend [Opcional] Callback java notificado en caso de env�o de mensaje 
    	onReceive [Opcional] Callback java notificado en caso de recepci�n de mensaje 
    	onError [Opcional] Callback java notificado en caso de error
	---------------------------------------------------------------------
	
	
	TAG: 
		message
		
	ATRIBUTOS:
		name: Nombre �nico del servicio 
		service: Grupo de servicios al que pertenece 
			(varios mensajes con mismo service generar�n la l�gica en 
			el mismo fichero)
		method: Nombre del m�todo para acceder al servicio
		description [Opcional]: Descripci�n del mensaje, comentario en documentaci�n
		type: Tipo del mensaje
			valores: Put, Get, Post, Delete
			
	---------------------------------------------------------------------		
	
	TAG:
		url
		
	ATRIBUTOS:
		address: La direcci�n con la que se accede al servicio.
			La url de acceso al servicio puede contener par�metros, que 
			estar�n definidos con el formato ${nombreParametro}, estos 
			par�metros se sustituir�n por el valor del mismo en las 
			propiedades de sistema (System.getProperty) que se pueden
			establecer en la invocaci�n del servicio ( -DnombrePropiedad=valorPropiedad),
			y si no est�n en �se nivel, se consultan entre las propiedades 
			del objeto utilizado en la petici�n (fields pertenecientes al 
			request)
			
	---------------------------------------------------------------------
	
	TAG:
		request
	ATRIBUTOS:
		name:
			Nombre del DTO en el c�digo generado
	---------------------------------------------------------------------
	
	TAG:
		response
	ATRIBUTOS:
		name:
			Nombre del DTO en el c�digo generado
	---------------------------------------------------------------------
	
	TAG:
		field
	ATRIBUTOS:
		name: Nombre del campo (Nombre de la propiedad en el DTO generado)
		type: 
			Tipo del campo, los tipos pueden ser :
			- b�sicos:
				int,String,long,float,double
			- compuestos
				[Nombres de tipos (v�ase tipos m�s adelante)]
			- Arrays
				Se utiliza el nombre del tipo (base o compuesto) seguido de *
		description:
			 [Opcional] Comentario que tendr� el campo en c�digo fuente
	---------------------------------------------------------------------
	
	TAG:
		type
	ATRIBUTOS:
		name: Nombre del tipo (Es el nombre que tendr� el DTO generado)
		
		
	**********************************************************************
	Ejecuci�n
	**********************************************************************
	
	Proyecto : ModelGenerator
	Clase Main: com.mobivery.modelgenerator.GenerateDTO
	
	Par�metros de la app:
		fichero XML de definici�n de servicios: 
			/projects/simyo.xml
	
	Par�metros de la m�quina virtual
		
		Par�metros:
			
			common.folder [Obligatorio si generaci�n Java]
					Carpeta de generaci�n de modelo de datos (DAOs y DTOs)
					java. El fuente generado es platform independent, pudiendo
					ser utilizado desde j2se
							
			
			android.folder [Obligatorio si generaci�n Java]
					Carpeta de generaci�n de c�digo espec�fico java para 
					Android, Tasks y Servicios
							
			is.folder [Obligatorio si generaci�n iOS]
					Carpeta de generaci�n de c�digo espec�fico ObjectiveC
		
			project.name [Obligatorio]
					Nombre del proyecto
				
			package.name [Obligatorio]
					Nombre de paquete del proyecto
					
			log.level [Opcional, valor por defecto=ERROR]
					DEBUG: Establece nivel de log m�nimo a DEBUG
					INFO: Establece nivel de log m�nimo a INFO
					ERROR: Establece nivel de log m�nimo a ERROR
					
			Ejemplo de ejecuci�n:
		
			-Dcommon.folder=/Users/DRM/Documents/workspace/Simyo-Android/src/ 
			-Dandroid.folder=/Users/DRM/Documents/workspace/Simyo-Android/src/ 
			-Dios.folder=/Users/DRM/Documents/workspace/Simyo-IOS/Simyo/Classes/gen 
			-Dproject.name=Simyo 
			-Dpackage.name=com.simyo
			-Dlog.level=DEBUG
	
	
	
	
	
	
	---------------------------------------------------------------------