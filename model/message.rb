
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
    url.scan(/\$\{(.*?)\}/) {
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
    return method.slice(0,1).capitalize + method.slice(1..-1)
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
    isPost() || isPut() || isDelete() || multipart()
  end
  def isWriteJSON
    isPostJSON() || isPutJSON() || isDeleteJSON()
  end
  def isGet
    return type.casecmp('Get')==0
  end
  def isPost
    return type.casecmp('Post')==0
  end
  def isPostJSON
    return type.casecmp('PostJSON')==0
  end
  def isPut
    return type.casecmp('Put')==0
  end
  def isPutJSON
    return type.casecmp('PutJSON')==0
  end
  def isDelete
    return type.casecmp('Delete')==0
  end
  def isDeleteJSON
    return type.casecmp('DeleteJSON')==0
  end
  def isHttps
    return url.index 'https'
  end
  def multipart
    request.fields.each do |field|
      if field.isFile
        return true
      end
    end
    return false
  end
end