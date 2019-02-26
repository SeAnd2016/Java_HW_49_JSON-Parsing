package core;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import java.io.*;
import java.math.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {

       public static void main(String[] args) throws InterruptedException, IOException, ParseException {
              String us_currency_symbol = "$";
              ////////////////////////////////////////////////////////////////////////////////
              String ip_Euro = "88.191.179.56";
              String ip_Yuan = "61.135.248.220";
              String ip_Pound = "92.40.254.196";
              String ip_Hryvnia = "93.183.203.67";
              String ip_Ruble = "213.87.141.36";
              ////////////////////////////////////////////////////////////////////////////////
              
              // Disable the logs
  	   		  Logger logger = Logger.getLogger("");
  	   		  logger.setLevel(Level.OFF);
  	   		
              String url = "https://www.amazon.com/Echo-Dot-3rd-Gen-improved/dp/B0792KTHKJ/ref=dp_ob_title_def";
              
              WebDriver driver;
              
              /*
              String driverPath = "";
              driverPath = "./resources/webdrivers/mac/geckodriver.sh";
              System.setProperty("webdriver.gecko.driver", driverPath);
              driver = new FirefoxDriver();
              */
              
              /*
              System.setProperty("webdriver.chrome.driver", "./resources/webdrivers/mac/chromedriver");
              System.setProperty("webdriver.chrome.silentOutput", "true");
              ChromeOptions option = new ChromeOptions();
              option.addArguments("-start-fullscreen");
              driver = new ChromeDriver(option);
              */
              
              /*
              WebDriver driver = new HtmlUnitDriver();
      		  ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
              */
              
              driver = new SafariDriver();
  			  //driver.manage().window().maximize();
  			  
  			  WebDriverWait wait15 = new WebDriverWait(driver, 15);
              
              //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
              driver.get(url);
              
              //String tilte = driver.getTitle();
              //System.out.println(tilte);

              // All-New Echo Dot (2nd Generation) - Black
              
              WebElement p_t = wait15.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"productTitle\"]")));
              //String product_title = (String) ((p_t.getText().matches([^\s]([A-Za-z\s\d\(\)\-])*[^\s])));
              String product_title = (String) ((p_t.getText()));
              //String product_title = tilte;
              
              Pattern pattern = Pattern.compile("[^\\s]([A-Za-z\\s\\d\\(\\)\\-])*[^\\s]");
              Matcher matcher = pattern.matcher(product_title);
              
              //System.out.println(product_title);
              
              //String product_title = driver.findElement(By.xpath("//*[@id=\"productTitle\"]")).getText(); 
              WebElement o_p = wait15.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"priceblock_ourprice\"]")));
              double original_price = Double.parseDouble(o_p.getText().replace("$", ""));
              // 49.99
              
              //System.out.println(original_price);
              
              driver.quit();
              
              //String product_title = "product_title";
              //double original_price = 49.99;
              
              JSONParser jp = new JSONParser();
              
              URL cc = new URL("http://www.geoplugin.net/json.gp?ip=88.191.179.56");
              
              JSONObject cc_json = (JSONObject) jp.parse(new BufferedReader(
              new InputStreamReader(cc.openConnection().getInputStream())));
              
              String usa_code = "USD";
              String local_code = (String) cc_json.get("geoplugin_currencyCode");  // EUR
              String pair_code = usa_code + "_" + local_code;                      // USD_EUR
              
              URL rate_url = new URL("http://free.currencyconverterapi.com/api/v6/convert?q=" + pair_code + "&compact=y&apiKey=***"); // Enter apiKey
                      
              JSONObject rate_json = (JSONObject) jp.parse(new BufferedReader(
              new InputStreamReader(rate_url.openConnection().getInputStream())));
                      
              //System.out.println("JSON: \t\t" + rate_json); // {"USD_EUR":{"val":0.855504}}
                      
              JSONObject root = (JSONObject) rate_json.get(pair_code);
              String rate = ((Double) root.get("val")).toString();
              
              double rate2 = Double.parseDouble(rate);
              
              //System.out.println(rate2);

              URL api_url = new URL("http://www.geoplugin.net/json.gp?ip=" + ip_Ruble);
              
       	   	  BufferedReader in = new BufferedReader(new InputStreamReader(cc.openConnection().getInputStream()));
       	   	  JSONObject cc_jo = (JSONObject) jp.parse(in);
       	   
       	   	  String country_name = (String) cc_jo.get("geoplugin_countryName");
       	   	  String currency_symbol = (String) cc_jo.get("geoplugin_currencySymbol_UTF8");

              //URL rate_url = new URL("http://free.currencyconverterapi.com/api/v6/convert?q=" + pair_code + "&compact=y&apiKey=***");
              ////////////////////////////////////////////////////////////////////////////////

              ////////////////////////////////////////////////////////////////////////////////
             
       	   	  double eur_price = new BigDecimal(original_price * rate2).setScale(2, RoundingMode.HALF_UP).doubleValue();
              
       	      if (matcher.find()) {
       	   	  System.out.println("Item: " + matcher.group() + "; "
                                          + "US Price: " + us_currency_symbol + original_price + "; "
                                          + "for country: " + country_name + "; "
                                          + "Local Price: " + currency_symbol + eur_price);}

    }
}
