require_relative 'model/field.rb'
require_relative 'model/service_type.rb'
require_relative 'model/protocol.rb'
require_relative 'model/service.rb'
require_relative 'model/message.rb'
require 'rexml/document'
require 'mustache'
require 'active_support/inflector'

##################################
# XML Definition File Reader
##################################
class XmlReader

  #####################
  # Read XML definition
  #####################
  def XmlReader.read_xml(xmlFile)
    puts 'Loading xml'

    # Start XML loading
    contents=REXML::Document.new(File.new(xmlFile))
    protocol=Protocol.new

    # Read Protocol definition
    protocol.onReceive=contents.root.attributes['onReceive']
    protocol.onSend=contents.root.attributes['onSend']
    protocol.onError=contents.root.attributes['onError']
    protocol.onTask=contents.root.attributes['onTask']

    # Read Types
    protocol.types=XmlReader.read_types(contents.root)

    # Read Messages    
    XmlReader.read_messages(contents.root,protocol)

    puts 'Finished loading xml definition'
    return protocol
  end

  ##########################################
  # Read Messages from protocol root node
  ##########################################
  def XmlReader.read_messages(protocolNode,protocol)
    protocol.services=Hash.new
    protocol.messages=Array.new
    i=0
    protocolNode.each_element("//message") do |xmlMessage|

      # Create message object
      protocol.messages[i]=Message.new

      # Read url
      protocol.messages[i].url=XmlReader.read_url(xmlMessage)


      # Read Request Type
      protocol.messages[i].request=XmlReader.read_type(xmlMessage.elements['request'])
      # Read Response Type
      protocol.messages[i].response=XmlReader.read_type(xmlMessage.elements['response'])

      # Add types to global types
      protocol.types << protocol.messages[i].request
      protocol.types << protocol.messages[i].response

      # Read common protocol properties
      protocol.messages[i].name=xmlMessage.attributes['name']
      protocol.messages[i].service=xmlMessage.attributes['service']
      protocol.messages[i].method=xmlMessage.attributes['method']
      protocol.messages[i].description=xmlMessage.attributes['description']
      protocol.messages[i].type=xmlMessage.attributes['type']

      # Fix standard names if no request type name is given
      if !protocol.messages[i].request.type && !protocol.messages[i].request.name
        protocol.messages[i].request.type=protocol.messages[i].methodUpperCase+'RequestDTO'
        protocol.messages[i].request.name=protocol.messages[i].methodUpperCase+'RequestDTO'
      end

      # Fix standard names if no response type name is given
      if !protocol.messages[i].response.type && !protocol.messages[i].response.name
        protocol.messages[i].response.type=protocol.messages[i].methodUpperCase+'ResponseDTO'
        protocol.messages[i].response.name=protocol.messages[i].methodUpperCase+'ResponseDTO'
      end


      if(!protocol.services[protocol.messages[i].service])
        protocol.services[protocol.messages[i].service]=Service.new
        protocol.services[protocol.messages[i].service].messages=Array.new
      end
      protocol.services[protocol.messages[i].service].messages<<protocol.messages[i]

      i=i+1
    end
  end

  ##########################################
  # Read Types from protocol root node
  ##########################################
  def XmlReader.read_types(protocolNode)
    types=Array.new
    i=0
    protocolNode.each_element("//types//type") do |xmlType|
      types[i]=XmlReader.read_type(xmlType)
      i=i+1
    end
    return types
  end

  ##########################################
  # Read Type definition
  ##########################################
  def XmlReader.read_type(xmlServiceType)
    serviceType=ServiceType.new
    serviceType.name=xmlServiceType.attributes['name']
    serviceType.type=xmlServiceType.attributes['type']
    serviceType.fields=Array.new
    i=0
    xmlServiceType.each_element('field') do |xmlField|
      serviceType.fields[i]=XmlReader.read_field(xmlField)
      i=i+1
    end
    return serviceType
  end

  ##########################################
  # Read Field
  ##########################################
  def XmlReader.read_field(xmlField)
    field=Field.new
    field.name=xmlField.attributes['name']
    field.type=xmlField.attributes['type']
    field.mimeType=xmlField.attributes['mimeType']
    field.description=xmlField.attributes['description']
    return field
  end

  ##########################################
  # Read url
  ##########################################
  def XmlReader.read_url(xmlMessage)
    urlPattern=xmlMessage.elements['urlPattern']
    url=urlPattern.elements['url']
    return url.attributes['address']
  end

end
