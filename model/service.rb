
class Service
  attr_accessor :messages
  @messages
  def hasPost    
    messages.each do |message|
      if message.isPost || message.isPostJSON
        return true
      end
    end
    return false
  end
  def hasGet
    messages.each do |message|
      if message.isGet
        return true
      end
    end
    return false
  end
  def hasPut
    messages.each do |message|
      if message.isPut || message.isPutJSON
        return true
      end      
    end
    return false
  end
  def hasDelete
    messages.each do |message|
      if message.isDelete || message.isDeleteJSON
        return true
      end      
    end
    return false
  end  
  def hasMultipart
    messages.each do |message|
      if message.request.isMultipart
        return true
      end      
    end
    return false
  end
end
