import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

/*
SensorDatabase

Authors: 
Name:Aashir Gajjar ID:agajjar
Name:Nishant Parekh ID:nmparekh

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


public class SensorDatabase{
    

    // A list of all DataObjects stored by this database
    private ArrayList<DataObject> metrics;
    private Integer portID = 6789;
    
    int 	Hours;				// Data sample time stamp hours
    int 	Minutes;			// Data sample time stamp minutes
    int 	Seconds;			// Data sample time stamp seconds
    float	currentHumidity;			// Relative Humidity
    float	currentTemperature;		// Temperature in Degrees Fahrenheit 	
    float	currentPressure;			// Pressure in Kilo Pascals (kPa)
    int SECONDS_TO_READ = 10; 
    int nextOccurCount;

    // This is the stream used to read data from the server for reading data
 	DataInputStream inFromServer;
 	
 	// This is the stream used to send data to the server
 	DataOutputStream outToServer;
 	


    /*
      init: This function opens up the socket 
            and initializes 
     */
    public void init() throws UnknownHostException, IOException{
	
	// Local Variables
	String 	START = "start\n";	// String that signals the DataGen server to begin sending data
	
	
	

	// Initialize all metrics
	metrics = new ArrayList<DataObject>();
	metrics.add(new DataObject("pressure"));
	metrics.add(new DataObject("temperature"));
	metrics.add(new DataObject("humidity"));
	
	
	System.out.println("Configuring Sensor Database to support the following metrics: ");

	for(int i =0; i<metrics.size();i++){
	    System.out.println(this.metrics.get(i).getName());
	    }
	
	// Initliaze the socket and open it for reading
	
	System.out.println("\nReading data from port "+this.portID);

	// This is the socket used to connect to the server
	Socket clientSocket = new Socket("localhost", this.portID);
	
	// This is the stream used to read data from the server for reading data
	this.inFromServer = new DataInputStream(clientSocket.getInputStream());
	
	// This is the stream used to send data to the server
	this.outToServer = new DataOutputStream(clientSocket.getOutputStream());
	
	// Send the start string to the server to begin collecting data...
	
	outToServer.writeBytes(START);		
	
	// Read data from socket and store it in the custom structure
	readFromDataGen();
	
	// Closing the socket is a signal to the DataGen server that you don't want any more data.
	clientSocket.close();

	System.out.println("Data Read.\n\n");

    }





    /*
      readFromDataGen: This function reads from port 6789
      and stores data in the custom data structures.
      
     */
    private void readFromDataGen () throws IOException{

	// Parse the currentline and add data in the relevant "DataObject"
	// of the metrics list
	
	System.out.println("Reading data for "+SECONDS_TO_READ+" seconds..");

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
	    
	    if(i%5 == 0){
			System.out.println((SECONDS_TO_READ-i)+" seconds left..\n"
					+ "Current time: "+currentTime.toString());
			
		    }
	    
	    
	    
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
      getSummary: this function prints the details about the sensor database 
      on System.out. Details about metrics stored, unit of all metrics, total 
      values stored, the start time and the end time of the stored data.
     */
    public void getSummary(){


    }


    /*
      firstOccurance: This function finds the first
      occurance of the value of a given metric that is
      read from the 'Data Generator'
     */
    public Time firstOccurance(String metric, double value){
	
	for(DataObject eachMetric : this.metrics){
	    if(eachMetric.getName().equals(metric)){
		return eachMetric.findData(value).get(0);
	    }
	}
	
	return null;

    }
    




    /*
      nextOccurance: this function prints the time when a given 
      metric value is read. Every time the call is made, the function 
      returns the next time when the metric value occured, if the 
      value had occured multiple times.
     */
    public void nextOccurance(String metric, double value){
	
		
    	for(DataObject tempmetric : this.metrics){
    		
    		if(tempmetric.getName().equals(metric)){
    			tempmetric.nextOccurance(value);
    			return;
    		}
	    }
    	
    	// If not returned yet, the metric doesnt exist
    	
    	System.out.println("The metric used is not present the Sensor Database");

	
	
    }
    



    /*
      readData: this function ouputs all the data read between the start time 
      and the end time on System.out. It outputs an error message for invalid 
      input parameters.This function assumes the 'DataObject' stores data in 
      sorted order of time

     */ 
    public void readData(Time startTime, Time endTime, String metricToRead){


	for(DataObject metric : this.metrics){
	
	    if(metric.getName().equals(metricToRead)){
		
		metric.readData(startTime,endTime);		
		return;
	    }
	}

	// If nothing is printed yet, metric parameter is illegal


    }

    
    /*
      Main function to test the methods provided 
      by the class. The main function is an example 
      as to how the API can be used.
     */
    
    public static void main(String[] args) throws UnknownHostException, IOException{

	// Local variables
    
    String userInput = null;
    String metricinput = null;
    String starttime = null;
    String[] starttimeSeperated = null;
    String endtime = null;
    String[] endtimeSeperated = null;
    
    Time startTime= null;
    Time endTime= null;
    
    
	SensorDatabase sdb = new SensorDatabase();
	sdb.init();
	Scanner input = new Scanner( System.in );
	
	
	while(true){	
	    // Wait for user input in order to search,read or quit
	    System.out.println("Press r to read through collected data\nPress s to search for data\nPress q to quit\n");
	    userInput=input.nextLine();
	    switch(userInput){
	
	    case "r":
	    	System.out.println("Enter metric to read");
	    	metricinput = input.nextLine();
	    	System.out.println("Enter start time in hrs:min:secs");
	    	starttime  = input.nextLine();
	    	System.out.println("Enter end time in hrs:min:secs");
	    	endtime = input.nextLine();
	    	
	    	starttimeSeperated = starttime.split(":"); 
	    	endtimeSeperated = endtime.split(":");
	    			
	    			
	    	startTime = new Time(Integer.parseInt(starttimeSeperated[0]),Integer.parseInt(starttimeSeperated[1]),Integer.parseInt(starttimeSeperated[2]));
	    	endTime = new Time(Integer.parseInt(starttimeSeperated[0]),Integer.parseInt(endtimeSeperated[1]),Integer.parseInt(endtimeSeperated[2]));
	    	sdb.readData(startTime, endTime, metricinput);	
	    		
	    	
	    	
	    	break;
	    case"s":
	    	
	    	break;
	    default:
	    	System.out.println("Invalid input");
	    	break;
	    }
	
	}

    }
    
    


}



