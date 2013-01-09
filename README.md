# ServiceGenerator
## Configuración de Eclipse

Si Eclipse no tiene instalado el módulo de Eclipse Marketplace (la opción aparece bajo el menu Help), instalarlo
mediante  **Help** -> **Install new software**, buscar **marketplace** e instalar
    
Desde **Eclipse Marketplace**, buscar **Maven** , e instalar el **plugin m2e** [Maven para eclipse]

## Plantilla de definición de servicios

Los servicios de una aplicación se definen en un fichero localizado
en la ruta `src/main/resources/projects` con formato XML
	
El fichero de definición define los tipos y servicios asociados a la
aplicación siguiendo el siguiente formato:

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


Atributos de las etiquetas del fichero de definición de servicios:


### TAG: protocol

#### ATRIBUTOS:
		
##### javaServiceException [Opcional]  
Clase java (generación Android, Server) que encapsula la excepción de servicio

##### javaServiceExceptionListener [Opcional] 
Interfaz java (generación Android, Server) que es llamada con la excepción
##### onSend [Opcional] 
Callback java notificado en caso de envío de mensaje 
##### onReceive [Opcional] 
Callback java notificado en caso de recepción de mensaje 

##### onError [Opcional] 
Callback java notificado en caso de error


### TAG: message

#### ATRIBUTOS:
##### name: 
Nombre único del servicio 
##### service: 
Grupo de servicios al que pertenece (varios mensajes con mismo service generarán la lógica en el mismo fichero)
##### method: 
Nombre del método para acceder al servicio
##### description [Opcional]: 
Descripción del mensaje, comentario en documentación
##### type: 
Tipo del mensaje valores: Put, Get, Post, Delete


### TAG: url

#### ATRIBUTOS:

##### address: La dirección con la que se accede al servicio.
La url de acceso al servicio puede contener parámetros, que estarán definidos con el formato `${nombreParametro}`, estos parámetros se sustituirán por el valor del mismo en las propiedades de sistema (`System.getProperty`) que se pueden establecer en la invocación del servicio ( `-DnombrePropiedad=valorPropiedad`)<br>

Si no se encuentran los parámetrosen ése nivel, se consultan entre las propiedades del objeto utilizado en la petición (fields pertenecientes al request)
		
### TAG: request

#### ATRIBUTOS:
##### name:
Nombre del DTO en el código generado
                
### TAG: response

#### ATRIBUTOS:
##### name:
Nombre del DTO en el código generado

### TAG: field

#### ATRIBUTOS:
##### name: 
Nombre del campo (Nombre de la propiedad en el DTO generado)
##### type: 
Tipo del campo, los tipos pueden ser :<br>
- básicos:<br>
int,String,long,float,double<br>
- compuestos<br>
[Nombres de tipos (véase tipos más adelante)]<br/>
- Arrays<br/>
Se utiliza el nombre del tipo (base o compuesto) seguido de `*`<br/>
##### description:[Opcional] 
Comentario que tendrá el campo en código fuente


### TAG: type

#### ATRIBUTOS:
##### name: 
Nombre del tipo (Es el nombre que tendrá el DTO generado)

## Generación

#### Proyecto : `service_generator`<br/>

Clase Main: `com.mobivery.modelgenerator.GenerateDTO`<br/>

#### Parámetros de la app:

fichero XML de definición de servicios: 
`/projects/simyo.xml`
	
#### Parámetros de la máquina virtual

`common.folder` [Obligatorio si generación Java]

Carpeta de generación de modelo de datos (DAOs y DTOs) java. El fuente generado es platform independent, pudiendo ser utilizado desde j2se
							
			
`android.folder` [Obligatorio si generación Java]

Carpeta de generación de código específico java para Android, Tasks y Servicios
							
`ios.folder` [Obligatorio si generación iOS]
Carpeta de generación de código específico ObjectiveC
		
`project.name` [Obligatorio]
Nombre del proyecto
				
`package.name` [Obligatorio]
Nombre de paquete del proyecto
					
`log.level` [Opcional, valor por defecto=ERROR]

**DEBUG**: Establece nivel de log mínimo a DEBUG

**INFO**: Establece nivel de log mínimo a INFO

**ERROR**: Establece nivel de log mínimo a ERROR

`ios.version` [Opcional, valor por defecto=1.0]	

Versión de la plantilla a utilizar para iOS

`android.version` [Opcional, valor por defecto=1.0]

Versión de la plantilla a utilizar para Android
			
### Generación Android

	Parámetros de ejecución Android:
	
	-Dcommon.folder=/Users/DRM/Documents/workspace/Simyo-Android/src/ 
	-Dandroid.folder=/Users/DRM/Documents/workspace/Simyo-Android/src/ 
	-Dproject.name=Simyo 
	-Dpackage.name=com.simyo
	-Dlog.level=DEBUG
	-Dandroid.version=1.0

### Generación IOS

	-Dios.folder=/Users/DRM/Documents/workspace/Simyo-IOS/Simyo/Classes/gen 
	-Dproject.name=Simyo 
	-Dpackage.name=com.simyo
	-Dlog.level=DEBUG
	-Dios.version=1.0
