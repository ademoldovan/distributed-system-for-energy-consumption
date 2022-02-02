# distributed-system-for-energy-consumption

  The project consists of an online platform designed to manage clients and smart devices equipped with energy consumption sensors. 
  The sensor simulator is developed as a desktop application and configured as a message producer. The message-oriented middleware 
allows the sensor system to send data tuples in a JSON format. The message consumer component of the system(backend) processes 
each message and notifies asynchronously using WebSockets the client application(frontend). 
  The clients have intelligent home appliances that are simulated with desktop applications and controlled remotely using remote 
procedure call(RPC).
