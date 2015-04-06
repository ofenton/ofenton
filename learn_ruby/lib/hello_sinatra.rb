# 1. gem install sinatra
# 2. ruby hello_sinatra.rb
# 3. Navigate to http://localhost:4567/hello

# Used to load the code from the Sinatra gem, adding its functionality to our program.
require 'sinatra'

# This is a route handler in Sinatra, and it is an example of what is called a “block” in Ruby.
# This is an HTTP 'get' call with /hello as the route.
get '/hello' do
  # Sinatra automatically outputs a string if it is the last line of code in the route handler.
  # So no need to use 'puts'
  "Hello sinatra!"
end
