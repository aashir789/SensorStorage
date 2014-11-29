/*
 DataObject

 Authors: 
 Name:Aashir Gajjar ID:agajjar
 Name:Nishant Parekh ID:nmparekh

 This class is an generic class which stores information 
 about an individual metric. This class provides an interaface 
 for adding and searching data. 
  
 
 */



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class DataObject {

	private String name = "genertic data"; // default metric name
	private Map<Double, ArrayList<Time>> searchMap; // map to store data for
													// faster search
	private ArrayList<Double> allData; // list to store data for iterating
										// through all data
	
	private List<Double> displayBuffer;
	
	
	private String unit = " "; // default unit
	private String timeResolution = "1 sec"; // default resolution of time
												// series data stored
	private Time startTime;
	private Time endTime;
	public int nextOccurCount;
	public int SizeOfDisplayBuffer = 10;

	public DataObject(String metricName) {

		this.searchMap = new HashMap<Double, ArrayList<Time>>();
		this.allData = new ArrayList<Double>();
		this.name = metricName;
		this.nextOccurCount = 0;

	}

	/*
	 * getName: This function returns the name of the metric that it deals with.
	 * eg: pressure, temperature,etc.
	 */
	public String getName() {
		return this.name;
	}

	public void setUnit(String metricUnit) {
		this.unit = metricUnit;
	}

	public String getUnit() {

		return this.unit;
	}

	/*
	 * addData: This function helps in adding the time series data It is assumed
	 * that this function is called in a lood and the currentTime parameter
	 * increases by a constant
	 */
	public boolean addData(double value, Time currentTime) {

		// Local variables
		ArrayList<Time> tempList;

		// Rpund up decimal value up to 2 digits for searching
		value = (double) Math.round(value * 100) / 100;
		
		
		// Add the new value to seach map
		tempList = this.searchMap.get(value);

		if (tempList != null) {
			// If the value is already in the search map,
			// add the new time to the time list

			tempList.add(currentTime); // check if the value in map is also
										// updated

		} else {
			// Create a new Time list and add it in the search map

			tempList = new ArrayList<Time>();
			tempList.add(currentTime);
			searchMap.put(value, tempList);
		}

		// Add the value in the list of all data
		this.allData.add(value);

		// Update the start and end time
		if (this.startTime == null) {
			this.startTime = currentTime;
		}

		this.endTime = currentTime;

		return true;

	}

	/*
	 * readData: This function displays all data recorded from the starttime to
	 * the endtime
	 */
	public void readData(Time readstartTime, Time readendTime) {

		// Local variables
		DataObject currentMetric;
		int startIndex;
		int endIndex;

		// Check for illegal start and end times

		// Check if start time is earlier than the read data start time
		if (Time.getDiff(readstartTime, this.startTime) < 0) {

			System.out.println("The start time is too early");
			return;
		}

		// Check if start time is late than the read data end time
		if (Time.getDiff(readstartTime, this.endTime) > 0) {

			System.out.println("The start time is too late");
			return;
		}

		// Check if start time is earlier than the read data start time
		if (Time.getDiff(readendTime, this.startTime) < 0) {

			System.out.println("The end time is too early");
			return;
		}

		// Check if start time is earlier than the read data start time
		if (Time.getDiff(readendTime, this.endTime) > 0) {

			System.out.println("The end time is too late");
			return;
		}

		// Iterarte through the list to get the start and end time
		startIndex = Time.getDiff(readstartTime,this.startTime );
		endIndex = Time.getDiff(readendTime,this.startTime );
		
		fillBuffer(startIndex,endIndex);
		
		
		Scanner input = new Scanner( System.in );
		
		while(this.displayBuffer.size()>0){
			
			System.out.println("Press a key to display the next 10 data values");
			input.nextLine(); // dummy line
			displayFromBuffer();
		
		}
		
		
	}

	
	private void fillBuffer(int start, int end){
		
		this.displayBuffer = new ArrayList<Double>();
		displayBuffer = this.allData.subList(start, end);
			
	}
	
	
	public void displayFromBuffer(){
		
		int count = 0;
		
		while(this.displayBuffer.size()>0 && count <10){
			
			System.out.println(this.name+" : "+this.displayBuffer.remove(0) + " " + this.getUnit());
			
			
		}
		
		
	}
	
	
	
	/*
	 * findData: This function searches the stored data for the value that is
	 * passed as a parameter
	 */
	public ArrayList<Time> findData(double value) {

		return this.searchMap.get(value);
	}

	
	
	
	
	/*
	 * nextOccurance:
	 */
	public void nextOccurance(double value) {

		ArrayList<Time> tempTimeList = null;
		tempTimeList = this.findData(value);

		if (tempTimeList != null) {

			if (nextOccurCount < tempTimeList.size()) {

				System.out.println(tempTimeList.get(nextOccurCount));
				this.nextOccurCount++;
				return;

			} else {

				System.out.println("All next occurances shown. Counter reset.");
				this.nextOccurCount = 0;
			}

		} else {

			System.out.println("Value entered does not exist for metric: "
					+ this.getName());

		}

	}

	
	/*
	 * getMax - this function returns the max value stored in allData 
	 */
	
	public double getMax(){
		
		return Collections.max(allData);
		
	}
	
	/*
	 * getMin - this function returns the min value stored in allData 
	 */
	public double getMin(){
		
		
		return Collections.min(allData);
	}
	
	
	public Time getStartTime(){
		
		return this.startTime;
		
	}
	
	
	public Time getEndTime(){
		
		return this.endTime;
		
	}
	
	

	private long getsecondsfromTime(Time time1) {
		long seconds = 0;
		seconds = time1.getHours() * 3600 + time1.getMinutes() * 60
				+ time1.getSeconds();
		return seconds;
	}

	/* Compares two Time values. If greater then return true else return false */

	private boolean compareTimes(Time time1, Time time2) {

		long seconds1 = 0, seconds2 = 0;
		seconds1 = getsecondsfromTime(time1);
		seconds2 = getsecondsfromTime(time2);

		if (seconds1 > seconds2) {
			return true;
		} else {
			return false;
		}
	}

	private long TimeSub(Time time1, Time time2) {
		// TODO Auto-generated method stub

		long time1secs = getsecondsfromTime(time1);
		long time2secs = getsecondsfromTime(time2);

		return (time1secs - time2secs);
	}

	private Time TimeAdd(Time time1, long seconds) {
		// TODO Auto-generated method stub

		long time1secs = getsecondsfromTime(time1);
		time1secs += seconds;

		return ConvertSecstoTime(time1secs);
	}

	private Time ConvertSecstoTime(long seconds) {
		int hours = 0, mins = 0, secs = 0;
		if (seconds / 3600 != 0)
			hours = (int) seconds / 3600;
		seconds = seconds % 3600;
		if (seconds / 60 != 0)
			mins = (int) seconds / 60;
		secs = (int) seconds % 60;
		Time TimeValue = new Time(hours, mins, secs);
		return TimeValue;

	}

	private Time StringToTime(String Time) {
		// TODO Auto-generated method stub
		String values[] = Time.split(":");
		int hours = Integer.parseInt(values[0]);
		int mins = Integer.parseInt(values[1]);
		int seconds = Integer.parseInt(values[2]);
		Time time = new Time(hours, mins, seconds);
		return time;
	}

	private String TimetoString(Time time) {
		// TODO Auto-generated method stub
		String output = "";
		time.getHours();
		if (time.getHours() < 10)
			output += "0" + time.getHours() + ":";
		else
			output += time.getHours() + ":";

		if (time.getMinutes() < 10)
			output += "0" + time.getMinutes() + ":";
		else
			output += time.getMinutes() + ":";

		if (time.getMinutes() < 10)
			output += "0" + time.getSeconds();
		else
			output += time.getSeconds();

		return output;
	}

}
