// Cash Register Challenge (minimize the denominations returned)
// Example Input:
// 104.09
// 50
// Output: 
// Fifty Pounds
// Two Pounds
// Two Pounds
// Five Pence
// Two Pence
// Two Pence

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.lang.Math;

public class Main {
  /**
   * Iterate through each line of input.
   */
  public static void main(String[] args) throws IOException {
    InputStreamReader reader = new InputStreamReader(System.in);
    BufferedReader in = new BufferedReader(reader);

    try {
        double purchasePrice = Double.parseDouble(in.readLine());
        double cash = Double.parseDouble(in.readLine());
        Main.calculateChange(purchasePrice, cash);
    } catch (Exception e) {
        System.out.println(e);
    }
  }

  public static void calculateChange(double purchasePrice, double cash) {
    // Access your code here. Feel free to create other classes as required
	Denominations d = new Denominations();
	SortedMap<Double, String> map = d.genMap();
	
	if(purchasePrice < cash) System.out.println("ERROR");
	else if(purchasePrice == cash) System.out.println("ZERO");
	else  {
			double cashRemaining = purchasePrice - cash;
		    d.computeCount(map, cashRemaining);
			//DEBUG: System.out.println(d.final_count);
	}
  }

}

class Denominations {
		
	public static int final_count = 0;
	
	public static SortedMap<Double, String> genMap() {
	    
		SortedMap<Double, String> map = new TreeMap<Double, String>(Collections.reverseOrder());
		/*Populate map with given denominations*/
		map.put(0.01, "One Pence");
		map.put(0.02, "Two Pence");
		map.put(0.05, "Five Pence");
		map.put(0.10, "Ten Pence");
		map.put(0.20, "Twenty Pence");
		map.put(0.50, "Fifty Pence");
		map.put(1.0,  "One Pounds");
		map.put(2.0,  "Two Pounds");
		map.put(5.0,  "Five Pounds");
		map.put(10.0, "Ten Pounds");
		map.put(20.0, "Twenty Pounds");
		map.put(50.0, "Fifty Pounds");
	    
	    return map;
	    // final_count = computeCount(map, cashRemaining);
        //DEBUG: System.out.println(final_count);
	} // main
 
 public static boolean keyExists(SortedMap<Double, String> map, double key) {
    // check if key exists
	if(map.containsKey(key)) return true;
	else  return false;
 } // keyExists
 
 // compare cash remaining to  map entries
  public static int computeCount(SortedMap<Double, String> map, double cashRemaining) {
    // main logic
    int count = 0;
    double c = 0;
        while(cashRemaining > 0) {
            if(keyExists(map, cashRemaining)) {
                System.out.println(map.get(cashRemaining));
                return count+1;
            }
            else {
                  // iterate the map
                    Set set = map.entrySet();
		            Iterator iterator = set.iterator();
		            while(iterator.hasNext()) {
			            SortedMap.Entry entry = (SortedMap.Entry)iterator.next();
			            // typecast entry to double
			            double key = ((Number) entry.getKey()).doubleValue();
			            cashRemaining = Math.round(cashRemaining*100.0)/100.0;
			            //DEBUG: System.out.println(cashRemaining);
                        if(cashRemaining >= key) {
                                c = cashRemaining / key;
                                int p = (int)Math.floor(c);
                                for(int i = 0; i < p; i++) {
                                    System.out.println(entry.getValue());
                                }
                                cashRemaining %= key;
                            count += c;
                            }
		                 }
                    }  
        } // else
        return count;
  } // computeCount
 
    
} // Denominations
