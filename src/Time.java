/*
 Time

 Authors: 
 Name:Aashir Gajjar ID:agajjar
 Name:Nishant Parekh ID:nmparekh

 This class stores time information in hours
 minutes and seconds. The information is stored in 
 the 24 hour time format  
 


 */

public class Time {

    private int hours;
    private int minutes;
    private int seconds;



    public Time(int h, int m,int s){
	
	this.hours = h;
	this.minutes = m;
	this.seconds = s;

    }
    
    
    public int getHours(){
    	return this.hours;
    }
    
    public int getMinutes(){
    	return this.minutes;
    }
    
    public int getSeconds(){
    	return this.seconds;
    }
    
    // Has a bug when the time of the day changes
    public static int getDiff(Time t1, Time t2){
		
    	// Local variables
    	
    	int totalSecs1 = 0; 
    	int totalSecs2 = 0;
    	
    	// assumes that hour is in 24 hr format 
    	totalSecs1 = t1.getSeconds() + t1.getMinutes()*60 + t1.getHours()*3600;
    	totalSecs2 = t2.getSeconds() + t2.getMinutes()*60 + t2.getHours()*3600;
    	
    	return totalSecs1-totalSecs2;
    	
    		
    }

    @Override
    public String toString(){
	return new String(this.hours+":"+this.minutes+":"+this.seconds);
    }

}
