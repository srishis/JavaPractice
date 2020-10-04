// Stock Portfolio Benchmark matching
// sample input 1 = Tesla,STOCK,10|Apple,STOCK,15:Tesla,STOCK,15|Tesla,BOND,10|Apple,STOCK,10
// expected output:
//      SELL,Apple,STOCK,5
//      BUY,Tesla,STOCK,5
//      BUY,Tesla,BOND,10
// sample input 2 = Tesla,STOCK,10|Apple,STOCK,15|AMD,BOND,15:Tesla,STOCK,15|Apple,STOCK,10|AMD,BOND,15
// expected output:
//          SELL,Apple,STOCK,5
//          BUY,Tesla,STOCK,5
// Stock Portfolio Benchmark matching
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main{

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
             Main.matchBenchmark(line);
        } 
    } // main
        
    public static void matchBenchmark(String input) {
        String []data = input.split(":");
        String []pf = data[0].split("\\|");
        String []bm = data[1].split("\\|");
        //for(String i:pf)  System.out.println(i);
        
        ArrayList<Assets> portfolio = new ArrayList<>();
        ArrayList<Assets> benchmark = new ArrayList<>();
        
        for(int i = 0; i < bm.length; i++) {
            String[] bmData = bm[i].split(",");
            benchmark.add(new Assets(bmData[0], bmData[1], Integer.parseInt(bmData[2])));
        }
        for(int i = 0; i < pf.length; i++) {
           String[] pfData = pf[i].split(",");
            portfolio.add(new Assets(pfData[0], pfData[1], Integer.parseInt(pfData[2])));
        }
        Main.executeTrades(benchmark, portfolio);
    } // matchBenchmark
      
      public static void executeTrades(ArrayList<Assets> benchmark, ArrayList<Assets> portfolio) {
         Collections.sort(benchmark, new LexicographicComparator());
         Collections.sort(portfolio, new LexicographicComparator());
         int diff = 0;
         for(int i = 0; i < portfolio.size(); i++) {
            Assets bmAssets = benchmark.get(i);
            Assets pfAssets = portfolio.get(i);
            // System.out.println(benchmark.get(i).getSecurityType());
            int bmQty = bmAssets.getQty();
            int pfQty = pfAssets.getQty();
            // System.out.println(bmAssets.getQty());
            // System.out.println(pfAssets.getQty());
            if(bmQty < pfQty) {
                diff = pfQty - bmQty;
                System.out.println("SELL"+","+pfAssets.getTickerSymbol()+","+pfAssets.getSecurityType()+","+Integer.toString(diff));
            }
            else if(bmQty > pfQty) {
                diff = bmQty - pfQty;
                System.out.println("BUY"+","+pfAssets.getTickerSymbol()+","+pfAssets.getSecurityType()+","+Integer.toString(diff));             
            }
            else {
                // Do nothing if equal
            }
        }
        // if benchamrk has more entries
        if(portfolio.size() < benchmark.size()) {
               int i = portfolio.size();
               Assets bmAssets = benchmark.get(i);
               System.out.println("BUY"+","+bmAssets.getTickerSymbol()+","+bmAssets.getSecurityType()+","+bmAssets.getQty());
            }
      } // executeTrades
} // Main class

    // Overrides comparator to sort the assets in the ascending order of their names
    class LexicographicComparator implements Comparator<Assets> {
        @Override
        public int compare(Assets a, Assets b) {
            return a.getTickerSymbol().compareToIgnoreCase(b.getTickerSymbol());
        }
    } // LexicographicComparator
        
    // datastructure for  assets
    class Assets {
        private String tickerSymbol;
        private String SecurityType;
        private int Qty;
        
        // constructor
        public Assets(String tickerSymbol, String SecurityType, int Qty) {
            this.tickerSymbol = tickerSymbol;
            this.SecurityType = SecurityType;
            this.Qty = Qty;
        }
        public String getTickerSymbol() {
          return this.tickerSymbol;
        }
        public String getSecurityType() {
          return this.SecurityType;
        }
        public int getQty() {
          return this.Qty;
        }
    } // Assets
