# ServiceGenerator
## Plantilla de definición de servicios

Los servicios de una aplicación se definen en un fichero con formato XML
	
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

1.- Instalar dependencias

comando terminal...

*rake setup*

2.- Ejecutar generador

Comando terminal...

*ruby generator.rb -f [Ruta absoluta fichero xml] -pn [Nombre de proyecto] -package [Paquete Java Base] -aOutput [Ruta al proyecto Android] -iOutput [Ruta al proyecto iOS]*


## Parámetros

1.- [ANDROID] Parámetros a System.properties

### DEBUG
Si la propiedad DEBUG existe y tiene valor "true", la capa de servicios generará por consola el resultado de las llamadas a servidor

### LOG
Si la propiedad LOG existe y tiene valor "true", la capa de servicios generará un fichero log.txt con el resultado de la interacción con el servidor, dicho fichero se creará en la ruta especificada en la siguiente propiedad

####LOG.folder
Si la propiedad LOG.folder existe y contiene una ruta existente y dicha ruta hace referencia a un directorio, la aplicación generará en dicha ruta el fichero log.txt con el contenido resultado de la interacción con el servidor
