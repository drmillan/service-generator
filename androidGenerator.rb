# androidGenerator.rb
class AndroidGenerator
  def generate(protocol,projectName,packageName,aVersion,aOutput)
    puts 'Android Generation'
    puts '------------------'
    ###################################
    # ANDROID GENERATION
    ###################################
    parameters=Hash.new
    parameters['projectName']=projectName
    parameters['packagename']=packageName
    parameters['version']="1.0"
    parameters['dtos']=protocol.types
    parameters['version']=aVersion

    ############ DTO-BUNDLE generation (Base DTOs)
    puts 'DTOs'
    puts '-------------'

    baseDTODir=aOutput+'/'+packageName.gsub('.','/')+"/model/dto/base/"
    baseDTOFile=baseDTODir+'/'+projectName+"DTOBundle.java"
    FileUtils.mkdir_p(baseDTODir)
    puts "\tCreating Base DTO Bundle... \t#{projectName}DTOBundle"
    res=Mustache.render(File.open("templates/android/"+aVersion+"/android_base_dto_bundle.mustache").read,parameters)
    File.open(baseDTOFile, 'w') { |file| file.write(res) }

    ############ Extended DTOs (Extended DTOs)
    dtoDir=aOutput+'/'+packageName.gsub(".",'/')+"/model/dto/"
    FileUtils.mkdir_p(dtoDir)
    protocol.types.each do |type|
      puts "\tCreating DTO ... \t#{type.name}"
      dtoFile=dtoDir+'/'+type.name+'.java'
      parameters['className']=type.name
      parameters['daoName']=type.daoClassName
      res=Mustache.render(File.open("templates/android/"+aVersion+"/android_base_dto.mustache").read,parameters)
      File.open(dtoFile, 'w') { |file| file.write(res) }  unless File.exist?dtoFile
    end

    ############ DAO generation
    daoDir=aOutput+'/'+packageName.gsub('.','/')+"/model/dao/"
    FileUtils.mkdir_p(daoDir)
    puts 'DAOs'
    puts '-------------'
    protocol.types.each do |type|
      puts "\tCreating DAO ... \t#{type.daoClassName}"
      daoFile=daoDir+'/'+type.daoClassName+'.java'
      parameters['entity']=type
      res=Mustache.render(File.open("templates/android/"+aVersion+"/android_dao.mustache").read,parameters)
      File.open(daoFile, 'w') { |file| file.write(res) }
    end



    ########### Services
    puts 'Services'
    puts '--------'
    logicBaseDir=aOutput+'/'+packageName.gsub(".",'/')+"/logic/base"
    logicDir=aOutput+'/'+packageName.gsub(".",'/')+"/logic"
    FileUtils.mkdir_p(logicBaseDir)
    protocol.services.keys.each do |serviceKey|
      puts "\tCreating Service ... \t#{serviceKey}"
      logicBaseFile=logicBaseDir+"/Base"+serviceKey+"Logic.java"
      logicFile=logicDir+'/'+serviceKey+"Logic.java"
      parameters['serviceName']=serviceKey
      parameters['messages']=protocol.services[serviceKey].messages
      parameters['service']=protocol.services[serviceKey];
      res=Mustache.render(File.open("templates/android/"+aVersion+"/android_base_service.mustache").read,parameters)
      File.open(logicBaseFile, 'w') { |file| file.write(res) }
      res=Mustache.render(File.open("templates/android/"+aVersion+"/android_service.mustache").read,parameters)
      File.open(logicFile, 'w') { |file| file.write(res) }      unless File.exists?(logicFile)
    end
    ########## Tasks
    puts 'Tasks'
    puts '-------'
    protocol.services.keys.each do |serviceKey|
      protocol.services[serviceKey].messages.each do |message|
        puts "\tCreating Task ... \t#{message.name}Task"
        taskFileDir=aOutput+'/'+packageName.gsub(".",'/')+"/tasks/"+serviceKey.downcase
        FileUtils.mkdir_p(taskFileDir)
        taskFile=taskFileDir+'/'+message.methodUpperCase+"Task.java"
        parameters['message']=message
        parameters['service']=protocol.services[serviceKey]
        res=Mustache.render(File.open("templates/android/"+aVersion+"/android_tasks.mustache").read,parameters)
        File.open(taskFile,"w"){|file| file.write(res)}
      end
    end

    ############ Helper if needed
    puts 'HELPERS'
    puts '--------------'
    puts "\tCreating Helper ... \t#{projectName}Helper"
    helperFile=aOutput+'/'+packageName.gsub(".",'/')+"/logic/"+projectName+"Helper.java"
    res=Mustache.render(File.open("templates/android/"+aVersion+"/android_helper.mustache").read,parameters)
    File.open(helperFile, 'w') { |file| file.write(res) } unless File.exists?(helperFile)


    ############ Copy common folder
    puts 'Copying common folder'
    FileUtils.cp_r Dir.glob("templates/android/"+aVersion+"/static/*"),aOutput

  end
end