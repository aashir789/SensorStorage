import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/*
DataObject 

Authors:

This class is an generic class which can be inherited
by special classes which represent specific data metrics
and can have more features like unit conversion, etc. 

 */


public class DataObject {
    
    
    private String name = "genertic data"; 		   // default metric name
    private Map<Double,ArrayList<Time>> searchMap; // map to store data for faster search
    private ArrayList<Double> allData; 			   // list to store data for iterating through all data
    private String unit = "unit"; 				   // default unit
	private String timeResolution = "1 sec"; 	   // default resolution of time series data stored
    private Time startTime;
    private Time endTime;
    public int nextOccurCount;
    public int SizeOfDisplayBuffer=10;
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
    	
    	
    	
    	
    /*
     * Read Data for display
     * 
     * */
    public void InitiateRead(){

    	Scanner inp=new Scanner(System.in);
    	System.out.println("Please Enter Time From when data is to read. Please Follow(hh:mm:ss)");
    	String StartTime=inp.nextLine();
    	Time startTime=StringToTime(StartTime);
    	System.out.println("Please Enter Time till data is to be read. Please Follow(hh:mm:ss)");
    	String EndTime=inp.nextLine();
    	Time endTime=StringToTime(EndTime);
    	System.out.println("Please Enter Metrics to be considered for display seperated by commas(For eg: temperature,pressure,humidity)");
    	String Metrics=inp.nextLine();
    	ReadData(startTime,endTime,Metrics);
    }

	public void ReadData(Time startTime,Time endTime,String Metrics){
    	
    	long FirstIndex=0,LastIndex=0;
    if(TimeSub(startTime,endTime)>=10){
		System.out.println("Data Cannot be displayed at once will be displayed in groups of 10 seconds");
	}
    
    	Time samplingStartTime=SensorDatabase.metrics.get(0).startTime;
    	Time samplingEndTime=TimeAdd(samplingStartTime,300);
    		if(compareTimes(startTime,samplingStartTime) && compareTimes(endTime,samplingStartTime))
    			if(compareTimes(samplingEndTime,startTime) && compareTimes(samplingEndTime,endTime)){
    				 FirstIndex = TimeSub(startTime,samplingStartTime);
    			}
    		LastIndex=FirstIndex+SizeOfDisplayBuffer;
    		DisplayData(FirstIndex,SizeOfDisplayBuffer,Metrics,startTime);
    }
    
    /*
     * Display Data that is read in parts of 10 seconds.
     * 
     * */
    
    public void DisplayData(long startIndex,int SizeOfDisplayBuffer, String Metrics,Time StartTime){
    	SensorDatabase sd=new SensorDatabase();
    	double val=0;
    	
    	String output=""; 
    	
    	String values[]= Metrics.split(",");
    	output+="Time \t";

    	for(DataObject obj:SensorDatabase.metrics){
    	if(Arrays.asList(values).contains(obj.getName()))
    	output+=obj.getName()+"\t";
    }
    	
    	output+="\n";
   
    		for(int cntr=0;cntr<SizeOfDisplayBuffer;cntr++){

				StartTime=TimeAdd(StartTime,1);
				output+=StartTime.getHours()+":"+StartTime.getMinutes()+":"+StartTime.getSeconds()+"\t";
				
    			for(DataObject obj:SensorDatabase.metrics){

    				if(Arrays.asList(values).contains(obj.getName())){
    					val=(obj.allData.get((int)(startIndex+cntr)));
    					val = (double) Math.round(val * 10000) / 10000;
    					output+=val+"\t";
    				}
    			}
    			output+="\n";
    
    		}
    		
    		System.out.println(output);
    }

    
    
    private long getsecondsfromTime(Time time1){
    	long seconds=0;
    	seconds=time1.getHours()*3600+time1.getMinutes()*60+time1.getSeconds();
    	return seconds;
    }
    /*  Compares two Time values. If greater then return true else return false */
    
    private boolean compareTimes(Time time1,Time time2){
    
    	long seconds1=0,seconds2=0;
    	seconds1=getsecondsfromTime(time1);
    	seconds2=getsecondsfromTime(time2);
    	
    	if (seconds1>seconds2){	
    		return true;
    	}
    	else{
    		return false;
    	}
    }

	private long TimeSub(Time time1, Time time2) {
		// TODO Auto-generated method stub
		
		long time1secs=getsecondsfromTime(time1);
		long time2secs=getsecondsfromTime(time2);
		
		
		return (time1secs-time2secs);
	}
	
	private Time TimeAdd(Time time1, long seconds) {
		// TODO Auto-generated method stub
		
		long time1secs=getsecondsfromTime(time1);
			 time1secs+=seconds;
		
		return ConvertSecstoTime(time1secs);
	}
	
	private Time ConvertSecstoTime(long seconds){
		int hours=0,mins=0,secs=0;
		if(seconds/3600!=0)
		hours=(int)seconds/3600;
		seconds=seconds%3600;
		if(seconds/60!=0)
		mins=(int)seconds/60;
		secs=(int)seconds%60;
		 Time TimeValue = new Time(hours,mins,secs);
		 return TimeValue;
		
	}
	
	private Time StringToTime(String Time) {
		// TODO Auto-generated method stub
		String values[]=Time.split(":");
		int hours=Integer.parseInt(values[0]);
		int mins=Integer.parseInt(values[1]);
		int seconds=Integer.parseInt(values[2]);
		Time time=new Time(hours,mins,seconds);
		return time;
	}


}

