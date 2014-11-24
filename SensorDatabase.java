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
    
    

    /*
      init: This function opens up the socket 
            and initializes 
     */
    public void init(){

	// Initialize all metrics
	metrics = new ArrayList<DataObject>();
	metrics.add(new PressureData());
	metrics.add(new TemperatureData());
	metrics.add(new HumidityData());
	
	// Initliaze the socket and open it for reading



    }






    /*
      readFromDataGen: This function reads from port 6789
      and stores data in the custom data structures.
      
     */
    private void readFromDataGen (){


	// Parse the currentline and add data in the relevant "DataObject"
	// of the metrics list
	
	




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



