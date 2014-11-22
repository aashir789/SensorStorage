/*


 */


public class SensorReader{
    

    

    private Map<String,String> TempMap;
    private Map<String,String> PressureMap;
    private Map<String,String> HumidityMap;
    private SensorData[] totalData;  



    /*
      init: This function opens up the socket 
            and initializes 
     */
    public void init();


    /*
      
     */
    private void readFromDataGen ();


    /*
      
     */
    public void firstOccurance();
    
    /*

     */
    public void lastOccurance();
    
    /*
      
     */ 
    public void readData();


    
}



