package core;

import java.io.*;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class CurrencyCode {

       public static void main(String[] args) throws IOException, ParseException {

    	   JSONParser jp = new JSONParser();            
    	   
    	   URL cc = new URL("http://www.geoplugin.net/json.gp?ip=88.191.179.56");
    	   BufferedReader in = new BufferedReader(new InputStreamReader(cc.openConnection().getInputStream()));

    	   JSONObject cc_jo = (JSONObject) jp.parse(in);
    	   //JSONObject cc_jo = (JSONObject) jp.parse(new FileReader("file_name.json"));
       
    	   System.out.println("Country: \t\t"       + cc_jo.get("geoplugin_countryName"));
    	   System.out.println("Currency Code: \t\t" + cc_jo.get("geoplugin_currencyCode"));
    	   System.out.println("Currency Symbol: \t" + cc_jo.get("geoplugin_currencySymbol_UTF8"));
     }
}
