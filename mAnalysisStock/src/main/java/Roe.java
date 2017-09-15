import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;



public class Roe {
	
	public static void main(String[] args) {
		try{
			System.setProperty("https.proxyHost", "10.6.3.23");
	        System.setProperty("https.proxyPort", "3128");
			//sendGet("00005", "1");
			//sendGet("01800", "1");
	        String index_2[] = { "00001", "00002", "00003", "00004", "00005", "00006", "00011", "00012", "00016", "00017", 
	        		"00019", "00023", "00027", "00066", "00083", "00101", "00135", "00144", "00151", "00175", 
	        		"00267", "00293", "00386", "00388", "00688", "00700", "00762", "00823", "00836", "00857", 
	        		"00883", "00939", "00941", "00992", "01038", "01044", "01088", "01109", "01113", "01299", 
	        		"01398", "01928", "02018", "02318", "02319", "02388", "02628", "03328", "03988" };
	        
	        String index_[] = {  "00084", "00097", "00104", "00113", "00121", "00128", "00162", "00178", "00244", "00280", 
	        		"00289", "00312", "00398", "00493", "00602", "00647", "00653", "00693", "00720", "00759", 
	        		"00814", "00825", "00831", "00848", "00881", "00887", "00891", "00970", "00974", "00980", 
	        		"00984", "00990", "00999", "01139", "01150", "01173", "01212", "01255", "01268", "01280", 
	        		"01293", "01353", "01365", "01373", "01386", "01388", "01470", "01700", "01728", "01771", 
	        		"01828", "01929", "02136", "02213", "03308", "03368", "03389", "03669", "03813", "03836", 
	        		"06288", "06808", "08048", "08116", "08126", "08173", "08265", "08442"  };
			
	        String index[] = { "00494" };
	        
	        for (int i = 0; i < index.length; i++) {
	        	Thread.sleep(2000);
	        	getROESummary(index[i],"N");
	        }
	        
	        System.out.println("");
	        
	        for (int i = 0; i < index.length; i++) {
	        	Thread.sleep(2000);
	        	getEPSummary(index[i],"N");
	        	System.out.println("");
	        }
		} catch(Exception e){
			
		}
        
		return ;
	}

	public static String getFileContent(String fileName) throws IOException{
		BufferedReader br = null;
		String everything = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    everything = sb.toString();
		    
		} catch(Exception e){
			System.out.println(e);	
		} finally {
		    br.close();
		}
		
		return everything;
	}
	
	public static String getNetworkContent(String url) throws IOException{
		BufferedReader in = null;
		try {
			System.setProperty("https.proxyHost", "10.6.3.23");
	        System.setProperty("https.proxyPort", "3128");
			
			URL data = new URL(url);
	        
	        HttpsURLConnection con = (HttpsURLConnection) data.openConnection(); 
	        
	        in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		    
		} catch(Exception e){
			System.out.println(e);	
		} finally {
		}
		
		return in.readLine();
	}
	
	public static void getSummary(String index_nbr){
		String inputLine = null;
		
		SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try{
			inputLine = getNetworkContent("https://api.investtab.com/api/quote/"+index_nbr+":HK/earnings-summary");
	        
	        inputLine = "{\"dataset\":"+inputLine+"}";
	        
	        JSONObject json = new JSONObject(inputLine);
	        
	        JSONArray genreArray = (JSONArray) json.get("dataset");
	        
	        inputLine = getNetworkContent("https://api.investtab.com/api/quote/"+index_nbr+":HK/financial-ratios");
	        
	        inputLine = "{\"dataset\":"+inputLine+"}";
	        
	        JSONObject json2 = new JSONObject(inputLine);
	        
	        JSONArray genreArray2 = (JSONArray) json2.get("dataset");
	        
			// get the first genre
			JSONObject genre = null;
			JSONObject genre2 = null;
			
			BigDecimal avg = BigDecimal.ZERO; 
			
			for(int i = genreArray.length()-1, j = 0; i >= 0 && j < 5; i--){
				genre = (JSONObject) genreArray.get(i);
				genre2 = (JSONObject) genreArray2.get(i);
				
				if(genre.get("cover_period").toString().equals("12.0")){
					j++;
					
					avg = avg.add(new BigDecimal(genre2.get("ret_equity").toString()));
					System.out.print(myFormat.format(fromUser.parse(genre.get("as_of_date").toString())) + "\t");

					System.out.print(genre.get("np_growth").toString() + "\t"); //盈利增長

					System.out.print(genre.get("eps_growth").toString() + "\t"); //每股基本盈利增長

					System.out.println(genre2.get("ret_equity").toString()); //股東權益回報率
					
				}
			}
			
			
		} catch(Exception e){
			System.out.println(e);
			System.out.println(inputLine);
		}
	}
	
	public static void getROESummary(String index_nbr, String type){
		String inputLine = null;
		
		SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy");
		
		int year = 10;
		
		try{
			
			inputLine = getNetworkContent("https://api.investtab.com/api/quote/"+index_nbr+":HK/earnings-summary");
	        
	        inputLine = "{\"dataset\":"+inputLine+"}";
	        
	        JSONObject json = new JSONObject(inputLine);
	        
	        JSONArray genreArray = (JSONArray) json.get("dataset");
	        
	        inputLine = getNetworkContent("https://api.investtab.com/api/quote/"+index_nbr+":HK/financial-ratios");
	        
	        inputLine = "{\"dataset\":"+inputLine+"}";
	        
	        JSONObject json2 = new JSONObject(inputLine);
	        
	        JSONArray genreArray2 = (JSONArray) json2.get("dataset");
	        
			// get the first genre
			JSONObject genre = null;
			JSONObject genre2 = null;
			
			BigDecimal avg = BigDecimal.ZERO; 
			StringBuffer response = new StringBuffer();

			if(type == "Y"){
				response.append("["+index_nbr+"]\t");
				for(int i = genreArray.length()-1, j = 0; i >= 0 && j < year; i--){
					genre = (JSONObject) genreArray.get(i);
					
					if(genre.get("cover_period").toString().equals("12.0")){
						j++;
						
						response.append(myFormat.format(fromUser.parse(genre.get("as_of_date").toString())) + "\t");
	
					}
				}
			
				System.out.println(response.toString());
				
				response.delete(0, response.length());
			}
			
			
			
			response.append("["+index_nbr+"]\t");
			for(int i = genreArray.length()-1, j = 0; i >= 0 && j < year; i--){
				genre2 = (JSONObject) genreArray2.get(i);
				
				if(genre2.get("cover_period").toString().equals("12.0")){
					j++;
					
					response.append(genre2.get("ret_equity").toString() + "\t");

				}
			}
			
			System.out.println(response.toString());
			
			response.delete(0, response.length());
			
			
		} catch(Exception e){
			System.out.println(e);
			System.out.println(inputLine);
		}
	}
	
	
	public static void getEPSummary(String index_nbr, String type){
		String inputLine = null;
		
		SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy");
		
		int year = 10;
		
		try{
			
			inputLine = getNetworkContent("https://api.investtab.com/api/quote/"+index_nbr+":HK/earnings-summary");
	        
	        inputLine = "{\"dataset\":"+inputLine+"}";
	        
	        JSONObject json = new JSONObject(inputLine);
	        
	        JSONArray genreArray = (JSONArray) json.get("dataset");
	        
	        inputLine = getNetworkContent("https://api.investtab.com/api/quote/"+index_nbr+":HK/financial-ratios");
	        
	        inputLine = "{\"dataset\":"+inputLine+"}";
	        
	        JSONObject json2 = new JSONObject(inputLine);
	        
	        JSONArray genreArray2 = (JSONArray) json2.get("dataset");
	        
			// get the first genre
			JSONObject genre = null;
			JSONObject genre2 = null;
			
			BigDecimal avg = BigDecimal.ZERO; 
			StringBuffer response = new StringBuffer();

			if(type == "Y"){
				response.append("["+index_nbr+"]\t");
				for(int i = genreArray.length()-1, j = 0; i >= 0 && j < year; i--){
					genre = (JSONObject) genreArray.get(i);
					
					if(genre.get("cover_period").toString().equals("12.0")){
						j++;
						
						response.append(myFormat.format(fromUser.parse(genre.get("as_of_date").toString())) + "\t");
	
					}
				}
			
				System.out.println(response.toString());
				
				response.delete(0, response.length());
			}
			
			
			
			response.append("["+index_nbr+"]\t");
			for(int i = genreArray.length()-1, j = 0; i >= 0 && j < year; i--){
				genre = (JSONObject) genreArray.get(i);
				
				if(genre.get("cover_period").toString().equals("12.0")){
					j++;
					
					response.append(genre.get("np_growth").toString() + "\t");

				}
			}
			
			System.out.println(response.toString());
			response.delete(0, response.length());
			
			response.append("\t");
			for(int i = genreArray.length()-1, j = 0; i >= 0 && j < year; i--){
				genre = (JSONObject) genreArray.get(i);
				
				if(genre.get("cover_period").toString().equals("12.0")){
					j++;
					
					response.append(genre.get("eps_growth").toString() + "\t");

				}
			}
			
			System.out.println(response.toString());
			
			response.delete(0, response.length());
			
			
		} catch(Exception e){
			System.out.println(e);
			System.out.println(inputLine);
		}
	}
	
}
