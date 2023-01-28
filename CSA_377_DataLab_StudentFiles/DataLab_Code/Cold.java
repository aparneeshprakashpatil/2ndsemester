import core.data.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Cold {
   public static void main(String[] args) {

   
      DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/index.xml").load();
      ArrayList<WeatherStation> allstns = ds.fetchList("WeatherStation", "station/station_name", 
             "station/station_id", "station/state",
             "station/latitude", "station/longitude");
      System.out.println("Total stations: " + allstns.size());
      
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter a state abbreviation: ");
      String state = sc.next();

      for (WeatherStation ws : allstns) {
         if (ws.isLocatedInState(state)) {
            String id = ws.getId();
            
            DataSource ds1 = DataSource.connect("http://weather.gov/xml/current_obs/" + id + ".xml"); 
            ds1.setCacheTimeout(15 * 60);  
            ds1.load(); 

            Observation ob1 = ds1.fetch("Observation", "weather", "temp_f", "wind_degrees");

            System.out.println(ob1);
         }
      }

      
      
   }
}