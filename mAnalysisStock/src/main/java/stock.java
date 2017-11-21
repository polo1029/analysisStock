import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class stock {

	public static void main(String[] args) throws Exception {
		System.setProperty("https.proxyHost", "10.6.3.23");
        System.setProperty("https.proxyPort", "3128");
		//sendGet("00005", "1");
		//sendGet("01800", "1");
        String index_[] = { "00001", "00002", "00003", "00004", "00005", "00006", "00011", "00012", "00016", "00017", 
        		"00019", "00023", "00027", "00066", "00083", "00101", "00135", "00144", "00151", "00175", 
        		"00267", "00293", "00386", "00388", "00688", "00700", "00762", "00823", "00836", "00857", 
        		"00883", "00939", "00941", "00992", "01038", "01044", "01088", "01109", "01113", "01299", 
        		"01398", "01928", "02018", "02318", "02319", "02388", "02628", "03328", "03988" };
        
        String index[] = {  "00016"  };
		
        for (int i = 0; i < index.length; i++) {
        	Thread.sleep(2000);
        	sendGet(index[i], "1");
        }
		
		return ;
	}
	
	private static void sendGet(String url_, String printAll) throws Exception {
		HttpURLConnection conn = null;
		
		BigDecimal HsiPe = new BigDecimal("15.69");
		BigDecimal HsiPeg = new BigDecimal("11");
		
		BigDecimal currentPrice = null;
		BigDecimal profit = null;
		BigDecimal estimateProfit = null;
		BigDecimal beta = null;
		BigDecimal passPrice01 = null;
		BigDecimal passPrice02 = null;
		
		try{
			URL url = new URL("http://www.etnet.com.hk/www/tc/stocks/realtime/quote_profit.php?code=" + url_);
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/html");
	
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
			String output;
			StringBuffer response = new StringBuffer();
			boolean isPrint = false;
			boolean isProPrint = false;
			boolean isListHeader = false;
			boolean isListBody = false;
			String price = null;
			int count = 0;
			
			response.append("[" + url_ + "]\t");
			
			while ((output = br.readLine()) != null) {
				
				if(output.matches(".*\\<li\\>每股盈利\\<\\/li\\>.*") || isPrint){
					if(isPrint) {
						Pattern pattern = Pattern.compile("\\>(.+)\\<");
			            
			            Matcher matcher = pattern.matcher(output);
			            
			            if(matcher.find()) {
			            	price = matcher.group(1);
			            	response.append("每股盈利: " + price + "\t");
			            	profit = strToBigDecimal(price);
			            } else {
			            	response.append("每股盈利: ----" + "\t");
			            }
						
					}
					isPrint = !isPrint;
				} else {
					isPrint = false;
				}
				
				//System.out.println(output);
				if(output.matches(".*綜合盈利預測.*") || isProPrint){
					//System.out.println("----" + output);
					isProPrint = true;
					
					if(output.matches(".*ListHeader.*")){
						isListHeader = true;
					}
					//System.out.println("isListHeader : " + isListHeader);
					if( isListHeader && output.matches(".*\\<li class=\"Vol\"\\>.*")){
						count = count + 1;
					}
					
					if(output.matches(".*每股盈利.*")){
						isListHeader = false;
					}
					
					if(output.matches(".*\\<ul\\>.*")){
						isListBody = true;
					}
					
					if(isListBody){
						if(output.matches(".*\\<li class=\"Vol\"\\>.*")){
							count = count - 1;
						}
						if(count < 1){
							Pattern pattern = Pattern.compile("\\>(.+)\\<");
				            
				            Matcher matcher = pattern.matcher(output);
				            
				            if (matcher.find()) {
				            	price = matcher.group(1);
				            	response.append("預測每股盈利: " + price + "\t");
				            	estimateProfit = strToBigDecimal(price);
				            } else {
				            	response.append("預測每股盈利: ----" + "\t");
				            }
				            
							
							isProPrint = false;
							isListBody = false;
						}
					}
				}	
				
			}
			
			String inputLine;
			try {
				
	            URL data = new URL("https://api.investtab.com/api/quote/"+url_+":HK/fundamentals");
	            
	            HttpsURLConnection con = (HttpsURLConnection) data.openConnection(); 
	            
	            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            
	            inputLine = in.readLine();
	            
	            Pattern pattern = Pattern.compile("\"last_close\":([\\d|\\.]+),");
	            
	            Matcher matcher = pattern.matcher(inputLine);
	            
	            if (matcher.find()) {
	                //response.append("現價: "+matcher.group(1) + "\t");
	                currentPrice = new BigDecimal(matcher.group(1));
	            } else {
	            	//response.append("現價: ----" + "\t");
	            }
	            
	        	pattern = Pattern.compile("\"beta_250d\":([\\d|\\.]+),");
	            
	            matcher = pattern.matcher(inputLine);
	            
	            if (matcher.find()) {
	            	response.append("Beta: "+matcher.group(1) + "\t");
	            	beta = new BigDecimal(matcher.group(1));
	            	if( new BigDecimal(2.5).compareTo(beta) < 0 ){
	            		beta = new BigDecimal(2.5);
	            	}
	            } else {
	            	response.append("Beta: ----" + "\t");
	            }
	            
            	pattern = Pattern.compile("\"last_peg\":([\\d|\\.]+),");
	            
	            matcher = pattern.matcher(inputLine);
	            
	            if (matcher.find()) {
	            	response.append("PE: "+ currentPrice.divide(profit.divide(new BigDecimal(100), 3, RoundingMode.HALF_UP), 3, RoundingMode.HALF_UP) + "\t");
	            	response.append("PEG: "+matcher.group(1) + "\t");
	            } else {
	            	response.append("PEG: ----" + "\t");
	            }
	            
	            in.close();
	            
	            con.disconnect();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			System.out.println(response.toString());
			
			try{
				passPrice01 = currentPrice.multiply(beta.multiply(HsiPe).divide(currentPrice.divide(profit.divide(new BigDecimal(100), 3, RoundingMode.HALF_UP), 3, RoundingMode.HALF_UP), 3, RoundingMode.HALF_UP));
			} catch (ArithmeticException e) {
				passPrice01 = BigDecimal.ZERO;
	        } catch (Exception e) {
	        	passPrice01 = null;
	        }
			
			try{
				passPrice02 = currentPrice.multiply(HsiPe.divide(HsiPe,3,RoundingMode.HALF_UP).multiply(beta).divide(currentPrice.divide(profit.divide(new BigDecimal(100), 3, RoundingMode.HALF_UP), 3, RoundingMode.HALF_UP).divide(estimateProfit.divide(new BigDecimal(100), 3, RoundingMode.HALF_UP).divide(profit.divide(new BigDecimal(100), 3, RoundingMode.HALF_UP), 3, RoundingMode.HALF_UP).subtract(new BigDecimal(1)).multiply(new BigDecimal(100)), 3, RoundingMode.HALF_UP),3,RoundingMode.HALF_UP));
			} catch (ArithmeticException e) {
	            passPrice02 = BigDecimal.ZERO;
	        } catch (Exception e) {
	        	passPrice02 = null;
	        }
			
			try{
				
				System.out.print("\t現價: " + (currentPrice == null ? "----" : currentPrice.toString()) + "\t");
				
				System.out.print("PE合理價: " + (passPrice01 == null ? "----" : passPrice01.toString()) + "\t");
				
				System.out.print("PEG合理價: " + (passPrice02 == null ? "----" : passPrice02.toString()) + "\t");
				
			} catch (NullPointerException e) {
	            e.printStackTrace();
	        }
			
		} catch(Exception e){	
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
	}

	public static BigDecimal strToBigDecimal( String str ){
      // String to be scanned to find the pattern.
      String line = str.replace(",", "");
      String pattern = "(\\d+)";

      // Create a Pattern object
      Pattern r = Pattern.compile(pattern);

      // Now create matcher object.
      Matcher m = r.matcher(line);
      if (m.find( )) {
         return new BigDecimal(m.group(0));
      } 
      
      return null;
   }
}
