class String
  def self.colorize(text, color_code)
    "\e[#{color_code}m#{text}\e[0m"
  end

  def cyan
    self.class.colorize(self, 36)
  end

  def green
    self.class.colorize(self, 32)
  end
end

desc 'Initialize and download dependencies'
task :setup do
  puts 'This will download all the dependencies. It might also ask your admin password.'.cyan
  `sudo gem install mustache activesupport`
  puts 'Done!'.green
end

# Run setup by default
task :default => :setup
