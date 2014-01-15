#!/usr/bin/env ruby

# generator.rb
require_relative 'xmlReader.rb'
require_relative 'iosGenerator.rb'
require_relative 'androidGenerator.rb'
require_relative 'string.rb'

require 'fileUtils'
require 'rexml/document'

########################################
# Process command line parameters     ##
########################################

file=nil
project_name=nil
package_name=nil
android_output=nil
android_version='1.0'
ios_output=nil
ios_version='1.0'

loop { case ARGV[0]
  # PROJECT FILE
  when '-f' then ARGV.shift;file=ARGV.shift
  # PROJECT NAME 
  when '-projectName' then ARGV.shift;project_name=ARGV.shift
  when '-pn' then ARGV.shift;project_name=ARGV.shift
  # Android PACKAGE NAME
  when '-package' then ARGV.shift;package_name=ARGV.shift
  # Android OUTPUT FOLDER
  when '-aOutput' then ARGV.shift;android_output=ARGV.shift
  # Android TEMPLATE VERSION
  when '-aVersion' then ARGV.shift;android_version=ARGV.shift
  # iOS OUTPUT FOLDER
  when '-iOutput' then ARGV.shift;ios_output=ARGV.shift
  # iOS TEMPLATE VERSION
  when '-iVersion' then ARGV.shift;ios_version=ARGV.shift
  else break
  end
}

puts 'Generation for:'.cyan << file
puts 'Project Name:'.cyan << project_name
puts 'Package Name:'.cyan << package_name if android_output
puts 'Android output:'.cyan << android_output if android_output
puts 'Android version:'.cyan << android_version if android_output
puts 'iOS output:'.cyan << ios_output if ios_output
puts 'iOS version:'.cyan << ios_version if ios_output

puts '-------------------'
protocol=XmlReader::read_xml file, (ios_version && ios_version.to_f > 4.0)
# ANDROID Generation
AndroidGenerator.new.generate(protocol,project_name,package_name,android_version,android_output) if android_output
# IOS Generation
IOSGenerator.new.generate(protocol,project_name,package_name,ios_version,ios_output) if ios_output
puts '-------------------'

puts 'Generation finished [OK].'
