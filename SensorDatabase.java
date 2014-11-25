/*
SensorDatabase

Authors: 
Name:Aashir Gajjar ID:agajjar
Name:Nishant Shah ID:

This program reads incoming data from port 6789
and stores it such that it is efficient to read 
and search through the data

The program requires initialization of the data that
it is supposed to be storing at compile time and this
is done in the init function. Hence, it cannot support 
new type of data dynamically. The program is also coupled 
with the DataGenerator and requires the data generator 
to follow a specific format in order to read the data 
correctly.



 */


public class SensorReader{
    

    // A list of all DataObjects stored by this database
    private ArrayList<DataObject> metrics;
    private Integer portID = 6789;
    
    int 	Hours;				// Data sample time stamp hours
    int 	Minutes;			// Data sample time stamp minutes
    int 	Seconds;			// Data sample time stamp seconds
    float	currentHumidity;			// Relative Humidity
    float	currentTemperature;		// Temperature in Degrees Fahrenheit 	
    float	currentPressure;			// Pressure in Kilo Pascals (kPa)
    int SECONDS_TO_READ = 300; 




    /*
      init: This function opens up the socket 
            and initializes 
     */
    public void init(){
	
	// Local Variables
	String 	START = "start\n";	// String that signals the DataGen server to begin sending data

	// Initialize all metrics
	metrics = new ArrayList<DataObject>();
	metrics.add(new DataObject("pressure"));
	metrics.add(new DataObject("temperature"));
	metrics.add(new DataObject("humidity"));
	
	System.out.println("Configuring Sensor Database to support the following metrics: ")

	for(int i =0; i<metrics.size();i++){
	    System.out.println(metric.get(i).getName());
	    }
	
	// Initliaze the socket and open it for reading
	
	System.out.println("Reading data from port "+this.portID);

	// This is the socket used to connect to the server
	Socket clientSocket = new Socket("localhost", this.portID);
	
	// This is the stream used to read data from the server for reading data
	DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
	
	// This is the stream used to send data to the server
	DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	
	// Send the start string to the server to begin collecting data...
	
	outToServer.writeBytes(START);		
	
	// Read data store it in the custom structure
	readFromDataGen();

    }





    /*
      readFromDataGen: This function reads from port 6789
      and stores data in the custom data structures.
      
     */
    private void readFromDataGen (){


	// Parse the currentline and add data in the relevant "DataObject"
	// of the metrics list
	
	// Data loop... 
 		
	for (int i=0; i<SECONDS_TO_READ; i++){
	// Here we read the data from the socket...
	  		
	    Hours = inFromServer.readInt();
	    Minutes = inFromServer.readInt();
	    Seconds = inFromServer.readInt();
	    currentHumidity = inFromServer.readFloat();
	    currentTemperature = inFromServer.readFloat();  		
	    currentPressure = inFromServer.readFloat();  		
	    
	    // Assign the current time to the new object
	    Time currentTime = new Time(Hours,Minutes,Seconds);

	    // Check the time taken to add data
	    
	    for(DataObject metric : this.metrics){
		
		if(metric.getName().equals("temperature")){
		    metric.addData(currentTemperature,currentTime);
		}
		else if(metric.getName().equals("pressure")){
		    metric.addData(currentPressure,currentTime);
		}
		else if(metric.getName().equals("humidity")){
		    metric.addData(currentHumidity,currentTime);
		}

	    }
	}




    }


    /*
      firstOccurance: This function finds the first
      occurance of the value of a given metric that is
      read from the 'Data Generator'
     */
    public void firstOccurance(String metric, double value){
	
	


    }
    




    /*

     */
    public void nextOccurance(String metric, double value){}
    



    /*
      
     */ 
    public void readData(){}

    
    /*
      Main function to test the methods provided 
      by the class.
     */
    public static void main(String[] args){

	

    }
    
    


}



