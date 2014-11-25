import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
DataObject 

Authors:

This class is an generic class which can be inherited
by special classes which represent specific data metrics
and can have more features like unit conversion, etc. 

 */


public class DataObject {
    
    
    private String name = "genertic data"; // default metric name
    private Map<Double,ArrayList<Time>> searchMap; // map to store data for faster search
    private ArrayList<Double> allData; // list to store data for iterating through all data
    private String unit = "unit"; // default unit
    private String timeResolution = "1 sec"; // default resolution of time series data stored
    private Time startTime;
    private Time endTime;
    public int nextOccurCount;

    public DataObject(String metricName){

	this.searchMap = new HashMap<Double,ArrayList<Time>>();
	this.allData = new ArrayList<Double>();
	this.name = metricName;
	this.nextOccurCount = 0;
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
      It is assumed that this function is called in a lood and the 
      currentTime parameter increases by a constant
     */
    public boolean addData(double value, Time currentTime){
	
	// Local variables	
	ArrayList<Time> tempList;

	// Add the new value to seach map
	tempList = this.searchMap.get(value);
	
	if(tempList != null){
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
	

	// Update the start and end time
	if(this.startTime == null){
	    this.startTime = currentTime;
	}
	
	this.endTime = currentTime;
	
	return true;

    }
    


    /*
      readData: This function displays all data recorded from the 
      starttime to the endtime
     */
    public void readData(Time startTime, Time endTime){
	
	// Local variables
	DataObject currentMetric;
	int startIndex;
	int endIndex = Time.getDiff(startTime,endTime);

	// Check for illegal start and end times





	// Iterarte through the list to get the start and end time
	startIndex = Time.getDiff(this.startTime,startTime);
	

    }

    

    /*
      findData: This function searches the stored data for the 
      value that is passed as a parameter
     */
    public ArrayList<Time> findData(double value){
	
    	return this.searchMap.get(value);
    }
    
    
    /*
     *nextOccurance:  
     * 
     */
    public void nextOccurance(double value){
    	
    	ArrayList<Time> tempTimeList = null;
    	tempTimeList = this.findData(value);
    	
    	if(tempTimeList != null){
    		
    		if(nextOccurCount<tempTimeList.size()){
    			
    			System.out.println(tempTimeList.get(nextOccurCount));
    	    	this.nextOccurCount++;
    	    	return;

    		}
    		else{
    			
    			System.out.println("All next occurances shown. Counter reset.");
    			this.nextOccurCount =0;
    		}
    		
    	}
    	else{
    		
    		System.out.println("Value entered does not exist for metric: "+this.getName());
    		
    	} 
    	
    	
    	
    	
    }

}

