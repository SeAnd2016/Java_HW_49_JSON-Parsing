package core;

import java.io.*;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class CurrencyRate {

       public static void main(String[] args) throws IOException, ParseException {

              JSONParser jp = new JSONParser();
              
              URL cc = new URL("http://www.geoplugin.net/json.gp?ip=88.191.179.56");
              
              JSONObject cc_json = (JSONObject) jp.parse(new BufferedReader(
              new InputStreamReader(cc.openConnection().getInputStream())));
              
              String usa_code = "USD";
              String local_code = (String) cc_json.get("geoplugin_currencyCode");  // EUR
              String pair_code = usa_code + "_" + local_code;                      // USD_EUR
              
              URL rate_url = new URL(
              "http://free.currencyconverterapi.com/api/v6/convert?q=" + pair_code + "&compact=y&apiKey=***"); // Enter apiKey
              
              JSONObject rate_json = (JSONObject) jp.parse(new BufferedReader(
              new InputStreamReader(rate_url.openConnection().getInputStream())));
              
              System.out.println("JSON: \t\t" + rate_json); // {"USD_EUR":{"val":0.855504}}
              
              JSONObject root = (JSONObject) rate_json.get(pair_code);
              String rate = ((Double) root.get("val")).toString();
              
              System.out.println("Rate: \t\t" + rate);     // 0.855504
       }
}

