/*
DataObject 

Authors:

This class is an generic class which can be inherited
by special classes which represent specific data metrics
and can have more features like unit conversion, etc. 

 */


public class DataObject {
    
    
    private String name = "genertic data"; // default metric name
    private Map<double,ArrayList<Time>> searchMap; // map to store data for faster search
    private ListArray<double> allData; // list to store data for iterating through all data
    private String unit = "unit"; // default unit
    private String timeResolution = "1 min"; // default resolution of time series data stored

    public DataObject(String metricName){

	this.searchMap = new Map<double,ArrayList<Time>>();
	this.allData = new ListArray<double>();
	this.name = metricName;
    }



    /*
      getName: This function returns the name of the 
      metric that it deals with. eg: pressure, temperature,etc.
     */
    public String getName(){
    	return this.name;
    }
    

    public void setUnit(String metricUnit){
	this.unit = metricUnit;
    }

    public String getUnit(){

	return this.unit;
    }

    
    /*
      addData: This function helps in adding the time series data 
     */
    public boolean addData(double value, Time currentTime){
	
	// Local variables	
	ArrayList<Time> tempList;

	// Add the new value to seach map
	tempList = this.searchMap.get(value);
	
	if(tempList){
	    // If the value is already in the search map, 
	    // add the new time to the time list
	    
	    tempList.add(currentTime); // check if the value in map is also updated
	    
	}else{
	    // Create a new Time list and add it in the search map
	    
	    tempList = new ArrayList<Time>();
	    tempList.add(currentTime);
	    searchMap.put(value,tempList);
	}

	// Add the value in the list of all data 
	this.allData.add(value);
	
    }
    


    /*
      readData: This function displays all data recorded from the 
      starttime to the endtime
     */
    public void readData(Time startTime, Time endTime){
	
	// Iterarte through the list to get the start and end time
    }

    

    /*
      findData: This function searches the stored data for the 
      value that is passed as a parameter
     */
    public ArrayList<Time> findData(double value){
	
	return this.seachMap.get(value);
    }

}

