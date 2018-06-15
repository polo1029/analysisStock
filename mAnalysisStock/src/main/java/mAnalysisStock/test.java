package mAnalysisStock;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

	public static void main(String[] args) throws UnsupportedEncodingException {

		System.out.print(getRange(31541));
		
	}

	public static String getRange(double close){
		double range =0.01;

		int up_round_close = (int) Math.floor(close * range);

		int up_layers = (int) Math.floor( Math.sqrt( Math.floor( up_round_close / 8 ) * 2 ));

		int up_max_layers = (1 + up_layers) * up_layers / 2 * 8;

		if( up_max_layers > up_round_close ){
		    up_layers = up_layers - 1;
		    up_max_layers = (1 + up_layers) * up_layers / 2 * 8;
		}

		int up_resistance = (int) Math.floor( (up_round_close - up_max_layers) / (up_layers + 1) );

		int up_num1 =(up_max_layers + 1) + up_resistance * ( up_layers + 1 );

		System.out.println("up_num1 : " + up_num1);
		System.out.println("up_round_close : " + up_round_close);
		System.out.println("up_resistance : " + up_resistance);
		
		if( up_num1 > up_round_close ){
		    if(up_resistance == 0)
		        up_num1 = (up_max_layers + 1) + (up_resistance - 1) * (up_layers + 1) + 1;
		    else{
		        up_num1 = (up_max_layers + 1) + (up_resistance - 1) * (up_layers + 1);
		        System.out.println("up_num1 : " + up_num1);
		    }
		}
		      
		int up_num2 = (up_max_layers + 1) + up_resistance * (up_layers + 1);

		if( up_round_close == up_num2 || up_round_close == up_num2 + 1 || up_round_close == up_num2 - 1 || up_num2 <= up_round_close)
		    up_num2 = (up_max_layers + 1) + (up_resistance + 1) * (up_layers + 1);
		   
		//plot(up_num1 / range, color = red)
		//plot(up_num2 / range, color = green)
		return "" + (up_num1 / range) + " : " + (up_num2 / range);
	}
	
	public static String unicodeToString(String str) {
		 
	    Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
	    Matcher matcher = pattern.matcher(str);
	    char ch;
	    while (matcher.find()) {
	        ch = (char) Integer.parseInt(matcher.group(2), 16);
	        str = str.replace(matcher.group(1), ch + "");    
	    }
	    return str;
	}
	
	public static String getUnicode(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);
                 
            }
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static BigDecimal strToInt( String str ){
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
