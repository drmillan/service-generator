require 'mustache'

class IOSGenerator
  def generate(protocol,project_name,package_name,ios_version,ios_output)
    #puts protocol.inspect

    puts '-------------------'
    ###################################
    # iOS GENERATION
    ###################################
    parameters=Hash.new
    parameters['projectName']=project_name
    parameters['packagename']=package_name
    parameters['version']='1.0'
    parameters['dtos']=protocol.types
    parameters['version']=ios_version if ios_version

    ############ DTOs
    dto_dir=ios_output+'/Classes/gen/Model/DTO'
    FileUtils.mkdir_p(dto_dir) unless File.exist?dto_dir
    FileUtils.mkdir_p(dto_dir+'/base')
    puts 'DTOs'
    puts '-------------'
    protocol.types.each do |type|
      puts "\tCreating DTO ... \t#{type.name}"
      dto_header_file=dto_dir+'/'+type.name+'.h'
      dto_implementation_file=dto_dir+'/'+type.name+'.m'
      base_dto_header_file=dto_dir+'/base/Base'+type.name+'.h'
      base_dto_implementation_file=dto_dir+'/base/Base'+type.name+'.m'
      parameters['className']=type.name
      parameters['dto']=type
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_base_dto_header.mustache').read,parameters)
      File.open(base_dto_header_file, 'w') { |file| file.write(res) }
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_base_dto_implementation.mustache').read,parameters)
      File.open(base_dto_implementation_file, 'w') { |file| file.write(res) }
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_dto_header.mustache').read,parameters)
      File.open(dto_header_file, 'w') { |file| file.write(res) } unless File.exist?dto_header_file
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_dto_implementation.mustache').read,parameters)
      File.open(dto_implementation_file, 'w') { |file| file.write(res) } unless File.exist?dto_implementation_file


    end

    ############ DAOs
    dto_dir=ios_output+'/Classes/gen/Model/DAO'
    FileUtils.mkdir_p(dto_dir)
    puts 'DAOs'
    puts '-------------'
    protocol.types.each do |type|
      puts "\tCreating DAO ... \t#{type.daoClassName}"
      dao_header_file=dto_dir+'/'+type.daoClassName+'.h'
      dao_implementation_file=dto_dir+'/'+type.daoClassName+'.m'
      parameters['className']=type.daoClassName
      parameters['dto']=type
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_dao_header.mustache').read,parameters)
      File.open(dao_header_file, 'w') { |file| file.write(res) }
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_dao_implementation.mustache').read,parameters)
      File.open(dao_implementation_file, 'w') { |file| file.write(res) }
    end

    ########### BASE SERVICES
    base_service_dir=ios_output+'/Classes/gen/Logic/Base'
    service_dir=ios_output+'/Classes/gen/Logic'
    FileUtils.mkdir_p(base_service_dir)
    puts 'SERVICES'
    puts '--------------'
    protocol.services.keys.each do |serviceKey|
      puts "\tCreating Service ... \t#{serviceKey}"
      service=protocol.services[serviceKey]

      ###### Create Import Table
      imports=Array.new
      service.messages.each do |message|
        imports << message
      end
      parameters['imports']=imports


      service_header_file=service_dir+'/'+serviceKey+'Logic.h'
      service_implementation_file=service_dir+'/'+serviceKey+'Logic.m'

      base_service_header_file=base_service_dir+'/Base'+serviceKey+'Logic.h'
      base_service_implementation_file=base_service_dir+'/Base'+serviceKey+'Logic.m'

      parameters['service']=service
      parameters['serviceName']=serviceKey;
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_base_service_header.mustache').read,parameters)
      File.open(base_service_header_file,'w'){|file| file.write(res)}
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_base_service_implementation.mustache').read,parameters)
      File.open(base_service_implementation_file,'w'){|file| file.write(res)}
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_service_header.mustache').read,parameters)
      File.open(service_header_file,'w'){|file| file.write(res)} unless File.exist?(service_header_file)
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_service_implementation.mustache').read,parameters)
      File.open(service_implementation_file,'w'){|file| file.write(res)} unless File.exists?(service_implementation_file)
    end

    ########### TASKS
    tasks_dir=ios_output+'/Classes/gen/Logic/Tasks'
    FileUtils.mkdir_p(tasks_dir)
    puts 'TASKS'
    puts '--------------'
    protocol.messages.each do |message|
      imports=Array.new
      imports << message.service
      parameters['imports']=imports
      parameters['message']=message
      puts "\tCreating Task ... \t#{message.methodUpperCase}Task"

      task_header_file=tasks_dir+'/'+message.methodUpperCase+'Task.h'
      task_implementation_file=tasks_dir+'/'+message.methodUpperCase+'Task.m'
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_task_header.mustache').read,parameters)
      File.open(task_header_file,'w'){|file| file.write(res)}
      res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_task_implementation.mustache').read,parameters)
      File.open(task_implementation_file,'w'){|file| file.write(res)}
    end

    ########### HELPERS
    helper_dir=ios_output+'/Classes/gen/Logic'
    FileUtils.mkdir_p(helper_dir)
    puts 'HELPERS'
    puts '--------------'
    puts "\tCreating Helper ... \t#{project_name}Helper"
    helper_header_file=helper_dir+'/'+project_name+'Helper.h'
    helper_implementation_file=helper_dir+'/'+project_name+'Helper.m'
    res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_helper_header.mustache').read,parameters)
    File.open(helper_header_file,'w'){|file| file.write(res)}  unless File.exists?(helper_header_file)
    res=Mustache.render(File.open('templates/ios/'+ios_version+'/ios_helper_implementation.mustache').read,parameters)
    File.open(helper_implementation_file,'w'){|file| file.write(res)} unless File.exists?(helper_implementation_file)



    ############ Copy common folder
    puts 'Copying common folder'
    FileUtils.cp_r Dir.glob("templates/ios/"+ios_version+"/static/*"),ios_output


  end
end