
##################################
# Message class
##################################
class Message
  attr_accessor :request
  @request=nil
  attr_accessor :response
  @response=nil
  attr_accessor :name
  @name=nil
  attr_accessor :service
  @service=nil
  attr_accessor :method
  @method=nil
  attr_accessor :description
  @description=nil
  attr_accessor :type
  @type=nil
  attr_accessor :url
  @url=nil

  def inURLbutNotField
    fields=Array.new
    url.scan(/\{(.*?)\}/) {
        |found|
      isField=false
      request.fields.each do |field|
        if field.name==found[0]
          isField=true
        end
      end
      if !isField
        fields << found[0]
      end
    }
    return fields
  end

  def serviceNameLower
    service.downcase
  end
  
  def methodUpperCase
    method.classify
  end
  
  def javaRequestParams
    params=Array.new
    request.fields.each do |field|
      params << field.typeJava+' '+field.javaName
    end
    params.join(',')
  end
  def simpleType
    type.gsub('JSON','')
  end
  def isWrite
    isPost() || isPut() || isDelete()
  end
  def isWriteJSON
    isPostJSON() || isPutJSON() || isDeleteJSON()
  end
  def isGet
    return type=='Get'
  end
  def isPost
    return type=='Post'
  end
  def isPostJSON
    return type=='PostJSON'
  end
  def isPut
    return type=='Put'
  end
  def isPutJSON
    return type=='PutJSON'
  end
  def isDelete
    return type=='Delete'
  end
  def isDeleteJSON
    return type=='DeleteJSON'
  end
  def isHttps
    return url.index 'https'
  end
end