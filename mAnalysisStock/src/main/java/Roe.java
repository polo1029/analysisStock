import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;



public class Roe {
	
	public static void main(String[] args) {
		try{
			System.setProperty("https.proxyHost", "10.6.3.23");
	        System.setProperty("https.proxyPort", "3128");
	        
	        int loop_time = 2000;
	        
	        /*String index_[] = {  "00084", "00097", "00104", "00113", "00121", "00128", "00162", "00178", "00244", "00280", 
	        		"00289", "00312", "00398", "00493", "00602", "00647", "00653", "00693", "00720", "00759", 
	        		"00814", "00825", "00831", "00848", "00881", "00887", "00891", "00970", "00974", "00980", 
	        		"00984", "00990", "00999", "01139", "01150", "01173", "01212", "01255", "01268", "01280", 
	        		"01293", "01353", "01365", "01373", "01386", "01388", "01470", "01700", "01728", "01771", 
	        		"01828", "01929", "02136", "02213", "03308", "03368", "03389", "03669", "03813", "03836", 
	        		"06288", "06808", "08048", "08116", "08126", "08173", "08265", "08442"  };*/
			
        	//String index[] = {  "00084", "00097", "00104", "00113", "00121", "00700" };
	        
	        String index[] = getAllStockIndex_v2();
	        
	        ArrayList<String> tmp_index = new ArrayList<String>();
	        
	        boolean isPrint = true;
	        
	        for (int i = 0; i < index.length; i++) {
	        	Thread.sleep(loop_time);
	        	isPrint = getROESummary(index[i],"N",(float) 15.0);
	        	
	        	if(!isPrint){
	        		continue;
	        	}
	        	
	        	tmp_index.add(index[i]);
	        }
	        
	        if(isPrint){
		        System.out.println("");
		        
		        for (int i = 0; i < tmp_index.size(); i++) {
		        //for (int i = 0; i < index.length; i++) {	
		        	Thread.sleep(loop_time);
		        	getEPSummary(tmp_index.get(i),"N");
		        	//getEPSummary(index[i],"N");
		        	System.out.println("");
		        }
	        }
		} catch(Exception e){
			
		}
        
		return ;
	}

	public static String[] getAllStockIndex(){
		String[] index = { "00001", "00002", "00003", "00004", "00005", "00006", "00007", "00008", "00009", "00010", 
    		"00011", "00012", "00013", "00014", "00015", "00016", "00017", "00018", "00019", "00020", 
    		"00021", "00022", "00023", "00024", "00025", "00026", "00027", "00028", "00029", "00030", 
    		"00031", "00032", "00033", "00034", "00035", "00036", "00037", "00038", "00039", "00040", 
    		"00041", "00042", "00043", "00044", "00045", "00046", "00047", "00048", "00050", "00051", 
    		"00052", "00053", "00054", "00055", "00056", "00057", "00058", "00059", "00060", "00061", 
    		"00062", "00063", "00064", "00065", "00066", "00067", "00068", "00069", "00070", "00071", 
    		"00072", "00073", "00075", "00076", "00077", "00078", "00079", "00080", "00081", "00082", 
    		"00083", "00084", "00085", "00086", "00087", "00088", "00089", "00090", "00091", "00092", 
    		"00093", "00094", "00095", "00096", "00097", "00098", "00099", "00100", "00101", "00102", 
    		"00103", "00104", "00105", "00106", "00107", "00108", "00109", "00110", "00111", "00112", 
    		"00113", "00114", "00115", "00116", "00117", "00118", "00119", "00120", "00121", "00122", 
    		"00123", "00124", "00125", "00126", "00127", "00128", "00129", "00130", "00131", "00132", 
    		"00133", "00135", "00136", "00137", "00138", "00139", "00141", "00142", "00143", "00144", 
    		"00145", "00146", "00147", "00148", "00149", "00151", "00152", "00153", "00154", "00155", 
    		"00156", "00157", "00158", "00159", "00160", "00161", "00162", "00163", "00164", "00165", 
    		"00166", "00167", "00168", "00169", "00170", "00171", "00172", "00173", "00174", "00175", 
    		"00176", "00177", "00178", "00179", "00180", "00181", "00182", "00183", "00184", "00185", 
    		"00186", "00187", "00188", "00189", "00190", "00191", "00193", "00194", "00195", "00196", 
    		"00197", "00198", "00199", "00200", "00201", "00202", "00204", "00205", "00206", "00207", 
    		"00208", "00209", "00210", "00211", "00212", "00213", "00214", "00215", "00216", "00217", 
    		"00218", "00219", "00220", "00221", "00222", "00223", "00224", "00225", "00226", "00227", 
    		"00228", "00229", "00230", "00231", "00232", "00233", "00234", "00235", "00236", "00237", 
    		"00238", "00239", "00240", "00241", "00242", "00243", "00244", "00245", "00246", "00247", 
    		"00248", "00250", "00251", "00252", "00253", "00254", "00255", "00256", "00257", "00258", 
    		"00259", "00260", "00261", "00262", "00263", "00264", "00265", "00266", "00267", "00268", 
    		"00269", "00270", "00271", "00272", "00273", "00274", "00275", "00276", "00277", "00278", 
    		"00279", "00280", "00281", "00282", "00283", "00285", "00286", "00287", "00288", "00289", 
    		"00290", "00291", "00292", "00293", "00294", "00295", "00296", "00297", "00298", "00299", 
    		"00300", "00303", "00305", "00306", "00307", "00308", "00309", "00310", "00311", "00312", 
    		"00313", "00315", "00316", "00317", "00318", "00319", "00320", "00321", "00322", "00323", 
    		"00326", "00327", "00328", "00329", "00330", "00331", "00332", "00333", "00334", "00335", 
    		"00336", "00337", "00338", "00339", "00340", "00341", "00342", "00343", "00345", "00346", 
    		"00347", "00348", "00350", "00351", "00352", "00353", "00354", "00355", "00356", "00357", 
    		"00358", "00359", "00360", "00361", "00362", "00363", "00364", "00365", "00366", "00367", 
    		"00368", "00369", "00370", "00371", "00372", "00373", "00374", "00375", "00376", "00377", 
    		"00378", "00379", "00380", "00381", "00382", "00383", "00384", "00385", "00386", "00387", 
    		"00388", "00389", "00390", "00391", "00392", "00393", "00395", "00396", "00397", "00398", 
    		"00399", "00400", "00401", "00402", "00403", "00404", "00405", "00406", "00408", "00410", 
    		"00411", "00412", "00413", "00416", "00417", "00418", "00419", "00420", "00422", "00423", 
    		"00425", "00426", "00428", "00430", "00431", "00432", "00433", "00434", "00435", "00436", 
    		"00438", "00439", "00440", "00442", "00444", "00445", "00449", "00450", "00451", "00455", 
    		"00456", "00458", "00459", "00460", "00462", "00464", "00465", "00467", "00468", "00469", 
    		"00471", "00472", "00474", "00475", "00476", "00477", "00479", "00480", "00482", "00483", 
    		"00484", "00485", "00486", "00487", "00488", "00489", "00491", "00493", "00494", "00495", 
    		"00496", "00497", "00498", "00499", "00500", "00503", "00505", "00506", "00508", "00509", 
    		"00510", "00511", "00512", "00513", "00515", "00517", "00518", "00519", "00520", "00521", 
    		"00522", "00524", "00525", "00526", "00527", "00528", "00529", "00530", "00531", "00532", 
    		"00533", "00535", "00536", "00538", "00539", "00540", "00542", "00543", "00544", "00546", 
    		"00547", "00548", "00549", "00550", "00551", "00552", "00553", "00554", "00555", "00556", 
    		"00557", "00558", "00559", "00560", "00563", "00564", "00565", "00566", "00567", "00568", 
    		"00569", "00570", "00571", "00572", "00573", "00574", "00575", "00576", "00577", "00578", 
    		"00579", "00580", "00581", "00582", "00583", "00585", "00586", "00587", "00588", "00589", 
    		"00590", "00591", "00592", "00593", "00595", "00596", "00598", "00599", "00600", "00601", 
    		"00602", "00603", "00604", "00605", "00606", "00607", "00608", "00609", "00610", "00611", 
    		"00612", "00613", "00616", "00617", "00618", "00619", "00620", "00621", "00622", "00623", 
    		"00625", "00626", "00627", "00628", "00629", "00630", "00631", "00632", "00633", "00635", 
    		"00636", "00637", "00638", "00639", "00640", "00641", "00642", "00643", "00645", "00646", 
    		"00647", "00648", "00650", "00651", "00653", "00655", "00656", "00657", "00658", "00659", 
    		"00660", "00661", "00662", "00663", "00665", "00666", "00668", "00669", "00670", "00672", 
    		"00673", "00674", "00675", "00676", "00677", "00678", "00679", "00680", "00681", "00682", 
    		"00683", "00684", "00685", "00686", "00687", "00688", "00689", "00690", "00691", "00692", 
    		"00693", "00694", "00695", "00696", "00697", "00698", "00699", "00700", "00701", "00702", 
    		"00703", "00704", "00705", "00706", "00707", "00708", "00709", "00710", "00711", "00712", 
    		"00713", "00715", "00716", "00717", "00718", "00719", "00720", "00721", "00722", "00723", 
    		"00724", "00725", "00726", "00727", "00728", "00729", "00730", "00731", "00732", "00733", 
    		"00735", "00736", "00737", "00738", "00743", "00745", "00746", "00747", "00750", "00751", 
    		"00752", "00753", "00754", "00755", "00756", "00757", "00758", "00759", "00760", "00761", 
    		"00762", "00763", "00764", "00765", "00766", "00767", "00768", "00769", "00770", "00771", 
    		"00773", "00775", "00776", "00777", "00778", "00787", "00789", "00794", "00798", "00799", 
    		"00800", "00801", "00802", "00803", "00804", "00805", "00806", "00808", "00809", "00810", 
    		"00811", "00812", "00813", "00814", "00815", "00816", "00817", "00818", "00819", "00820", 
    		"00821", "00822", "00823", "00825", "00826", "00827", "00828", "00829", "00830", "00831", 
    		"00832", "00833", "00834", "00835", "00836", "00837", "00838", "00839", "00840", "00841", 
    		"00842", "00844", "00845", "00846", "00847", "00848", "00850", "00851", "00852", "00853", 
    		"00854", "00855", "00856", "00857", "00858", "00859", "00860", "00861", "00862", "00863", 
    		"00864", "00865", "00866", "00867", "00868", "00869", "00871", "00872", "00873", "00874", 
    		"00875", "00876", "00877", "00878", "00880", "00881", "00882", "00883", "00884", "00885", 
    		"00886", "00887", "00888", "00889", "00891", "00893", "00894", "00895", "00896", "00897", 
    		"00898", "00899", "00900", "00901", "00902", "00903", "00904", "00905", "00906", "00907", 
    		"00908", "00909", "00910", "00911", "00912", "00913", "00914", "00915", "00916", "00917", 
    		"00918", "00919", "00921", "00922", "00923", "00925", "00926", "00927", "00928", "00929", 
    		"00930", "00931", "00932", "00933", "00934", "00935", "00936", "00938", "00939", "00940", 
    		"00941", "00943", "00945", "00947", "00948", "00950", "00951", "00952", "00953", "00954", 
    		"00956", "00958", "00959", "00960", "00963", "00966", "00967", "00968", "00969", "00970", 
    		"00973", "00974", "00975", "00976", "00978", "00979", "00980", "00981", "00982", "00983", 
    		"00984", "00985", "00986", "00987", "00988", "00989", "00990", "00991", "00992", "00993", 
    		"00994", "00995", "00996", "00997", "00998", "00999", "01000", "01001", "01002", "01003", 
    		"01004", "01005", "01006", "01007", "01008", "01009", "01010", "01011", "01013", "01019", 
    		"01020", "01021", "01022", "01023", "01025", "01026", "01027", "01028", "01029", "01030", 
    		"01031", "01033", "01035", "01036", "01037", "01038", "01039", "01041", "01043", "01044", 
    		"01045", "01046", "01047", "01048", "01049", "01050", "01051", "01052", "01053", "01055", 
    		"01057", "01058", "01059", "01060", "01061", "01062", "01063", "01064", "01065", "01066", 
    		"01068", "01069", "01070", "01071", "01072", "01073", "01075", "01076", "01079", "01080", 
    		"01082", "01083", "01085", "01086", "01087", "01088", "01089", "01090", "01091", "01093", 
    		"01094", "01096", "01097", "01098", "01099", "01100", "01101", "01102", "01103", "01104", 
    		"01105", "01106", "01107", "01108", "01109", "01110", "01111", "01112", "01113", "01114", 
    		"01115", "01116", "01117", "01118", "01120", "01121", "01122", "01123", "01124", "01125", 
    		"01126", "01127", "01128", "01129", "01130", "01131", "01132", "01133", "01135", "01136", 
    		"01137", "01138", "01139", "01140", "01141", "01142", "01143", "01145", "01146", "01148", 
    		"01149", "01150", "01151", "01152", "01155", "01157", "01159", "01160", "01161", "01163", 
    		"01164", "01165", "01166", "01168", "01169", "01170", "01171", "01172", "01173", "01174", 
    		"01175", "01176", "01177", "01178", "01180", "01181", "01182", "01184", "01185", "01186", 
    		"01187", "01188", "01189", "01190", "01191", "01192", "01193", "01194", "01195", "01196", 
    		"01197", "01198", "01199", "01200", "01201", "01202", "01203", "01205", "01206", "01207", 
    		"01208", "01210", "01211", "01212", "01213", "01215", "01216", "01217", "01218", "01219", 
    		"01220", "01221", "01222", "01223", "01224", "01225", "01226", "01227", "01228", "01229", 
    		"01230", "01231", "01232", "01233", "01234", "01235", "01236", "01237", "01238", "01239", 
    		"01240", "01241", "01243", "01245", "01246", "01247", "01249", "01250", "01251", "01252", 
    		"01253", "01255", "01257", "01258", "01259", "01260", "01262", "01263", "01265", "01266", 
    		"01268", "01269", "01270", "01271", "01272", "01273", "01275", "01277", "01278", "01280", 
    		"01281", "01282", "01285", "01288", "01289", "01290", "01292", "01293", "01296", "01297", 
    		"01298", "01299", "01300", "01301", "01302", "01303", "01305", "01308", "01310", "01312", 
    		"01313", "01314", "01315", "01316", "01317", "01318", "01319", "01321", "01322", "01323", 
    		"01326", "01327", "01328", "01329", "01330", "01332", "01333", "01335", "01336", "01338", 
    		"01339", "01340", "01341", "01345", "01347", "01348", "01349", "01353", "01355", "01357", 
    		"01358", "01359", "01360", "01361", "01362", "01363", "01365", "01366", "01367", "01368", 
    		"01369", "01370", "01371", "01372", "01373", "01375", "01378", "01380", "01381", "01382", 
    		"01383", "01385", "01386", "01387", "01388", "01389", "01390", "01393", "01395", "01396", 
    		"01397", "01398", "01399", "01400", "01415", "01418", "01419", "01420", "01421", "01426", 
    		"01428", "01430", "01431", "01432", "01438", "01439", "01443", "01446", "01447", "01448", 
    		"01450", "01452", "01456", "01458", "01459", "01460", "01461", "01462", "01466", "01468", 
    		"01469", "01470", "01476", "01478", "01480", "01483", "01486", "01488", "01492", "01495", 
    		"01496", "01498", "01499", "01500", "01508", "01509", "01513", "01515", "01518", "01520", 
    		"01522", "01523", "01526", "01527", "01528", "01530", "01532", "01533", "01536", "01538", 
    		"01539", "01543", "01547", "01548", "01549", "01551", "01552", "01555", "01556", "01557", 
    		"01558", "01559", "01560", "01561", "01565", "01566", "01568", "01569", "01570", "01571", 
    		"01572", "01573", "01575", "01577", "01578", "01579", "01580", "01581", "01583", "01585", 
    		"01586", "01588", "01589", "01591", "01596", "01599", "01600", "01606", "01608", "01609", 
    		"01610", "01611", "01612", "01613", "01616", "01617", "01618", "01619", "01622", "01623", 
    		"01626", "01627", "01628", "01629", "01630", "01631", "01632", "01633", "01635", "01636", 
    		"01637", "01638", "01639", "01647", "01649", "01655", "01656", "01658", "01660", "01661", 
    		"01662", "01663", "01666", "01667", "01668", "01669", "01673", "01676", "01678", "01679", 
    		"01680", "01681", "01682", "01683", "01685", "01689", "01693", "01695", "01698", "01699", 
    		"01700", "01702", "01708", "01717", "01718", "01728", "01733", "01738", "01766", "01768", 
    		"01771", "01776", "01777", "01778", "01786", "01788", "01798", "01799", "01800", "01803", 
    		"01808", "01811", "01812", "01813", "01816", "01818", "01819", "01822", "01823", "01826", 
    		"01828", "01829", "01830", "01831", "01833", "01836", "01838", "01848", "01856", "01858", 
    		"01862", "01863", "01866", "01868", "01878", "01880", "01881", "01882", "01883", "01884", 
    		"01885", "01886", "01888", "01889", "01893", "01898", "01899", "01900", "01908", "01910", 
    		"01913", "01918", "01919", "01928", "01929", "01932", "01938", "01958", "01962", "01963", 
    		"01966", "01968", "01970", "01972", "01979", "01980", "01982", "01985", "01986", "01988", 
    		"01989", "01991", "01993", "01998", "01999", "02000", "02001", "02002", "02005", "02006", 
    		"02007", "02008", "02009", "02010", "02011", "02012", "02014", "02016", "02017", "02018", 
    		"02020", "02023", "02028", "02030", "02031", "02033", "02038", "02039", "02066", "02068", 
    		"02078", "02080", "02083", "02086", "02088", "02098", "02099", "02100", "02111", "02112", 
    		"02113", "02118", "02120", "02121", "02123", "02128", "02133", "02136", "02138", "02166", 
    		"02168", "02178", "02183", "02186", "02188", "02193", "02196", "02198", "02199", "02200", 
    		"02202", "02203", "02208", "02211", "02212", "02213", "02218", "02221", "02222", "02223", 
    		"02226", "02228", "02229", "02233", "02236", "02238", "02239", "02255", "02266", "02268", 
    		"02269", "02277", "02278", "02280", "02281", "02282", "02283", "02286", "02288", "02289", 
    		"02293", "02298", "02299", "02300", "02302", "02303", "02307", "02308", "02309", "02310", 
    		"02312", "02313", "02314", "02317", "02318", "02319", "02320", "02322", "02323", "02324", 
    		"02326", "02327", "02328", "02329", "02330", "02331", "02333", "02336", "02338", "02339", 
    		"02340", "02341", "02342", "02343", "02345", "02348", "02349", "02355", "02356", "02357", 
    		"02358", "02362", "02366", "02368", "02369", "02371", "02378", "02379", "02380", "02382", 
    		"02383", "02386", "02388", "02389", "02393", "02398", "02399", "02468", "02488", "02588", 
    		"02600", "02601", "02607", "02608", "02611", "02618", "02623", "02626", "02628", "02633", 
    		"02638", "02662", "02666", "02668", "02669", "02678", "02686", "02688", "02689", "02698", 
    		"02699", "02700", "02722", "02727", "02728", "02738", "02768", "02777", "02778", "02788", 
    		"02789", "02799", "02800", "02801", "02802", "02805", "02808", "02811", "02816", "02817", 
    		"02818", "02819", "02821", "02822", "02823", "02824", "02825", "02827", "02828", "02829", 
    		"02830", "02832", "02833", "02834", "02835", "02836", "02838", "02839", "02840", "02841", 
    		"02842", "02843", "02844", "02846", "02847", "02848", "02863", "02866", "02868", "02869", 
    		"02877", "02878", "02880", "02882", "02883", "02886", "02888", "02889", "02898", "02899", 
    		"02901", "02903", "02904", "02905", "02908", "02909", "02910", "02911", "02912", "02913", 
    		"02914", "02915", "02916", "02917", "02918", "02919", "02920", "02921", "02922", "02923", 
    		"02924", "02925", "02926", "02927", "02928", "02929", "02930", "02931", "02932", "02933", 
    		"02934", "02935", "02936", "02937", "02938", "02939", "02940", "02941", "02942", "02943", 
    		"02944", "02945", "02946", "02947", "02948", "02949", "02950", "02951", "02952", "02953", 
    		"02954", "02955", "02956", "02957", "02959", "02960", "02961", "02963", "02964", "02965", 
    		"02966", "02967", "02968", "02969", "02970", "02971", "02972", "02973", "02974", "02975", 
    		"02976", "02977", "02978", "02979", "02980", "02981", "02982", "02983", "02984", "02985", 
    		"02986", "02987", "02988", "02989", "02990", "02991", "02993", "02996", "02997", "02998", 
    		"02999", "03001", "03002", "03004", "03005", "03006", "03007", "03008", "03009", "03010", 
    		"03011", "03012", "03013", "03015", "03016", "03017", "03019", "03020", "03021", "03024", 
    		"03025", "03026", "03027", "03029", "03031", "03032", "03035", "03036", "03037", "03039", 
    		"03040", "03041", "03043", "03045", "03046", "03048", "03049", "03050", "03051", "03052", 
    		"03054", "03055", "03056", "03057", "03060", "03061", "03062", "03063", "03064", "03065", 
    		"03066", "03069", "03070", "03071", "03072", "03073", "03074", "03075", "03076", "03078", 
    		"03081", "03082", "03084", "03085", "03086", "03087", "03089", "03090", "03091", "03092", 
    		"03095", "03097", "03098", "03099", "03100", "03101", "03102", "03105", "03106", "03107", 
    		"03110", "03115", "03116", "03117", "03118", "03119", "03120", "03121", "03122", "03124", 
    		"03126", "03127", "03128", "03129", "03132", "03134", "03135", "03136", "03137", "03139", 
    		"03140", "03141", "03143", "03145", "03146", "03147", "03149", "03150", "03153", "03155", 
    		"03156", "03157", "03160", "03161", "03162", "03165", "03167", "03170", "03171", "03175", 
    		"03177", "03180", "03188", "03199", "03300", "03301", "03303", "03306", "03308", "03311", 
    		"03313", "03315", "03318", "03320", "03322", "03323", "03326", "03328", "03329", "03330", 
    		"03331", "03332", "03333", "03335", "03336", "03337", "03339", "03344", "03355", "03360", 
    		"03363", "03366", "03368", "03369", "03377", "03378", "03380", "03382", "03383", "03386", 
    		"03389", "03393", "03395", "03396", "03398", "03399", "03600", "03606", "03608", "03618", 
    		"03623", "03626", "03628", "03633", "03636", "03638", "03639", "03663", "03666", "03668", 
    		"03669", "03678", "03683", "03686", "03688", "03689", "03698", "03699", "03708", "03709", 
    		"03728", "03737", "03768", "03773", "03777", "03778", "03788", "03789", "03799", "03800", 
    		"03808", "03813", "03816", "03818", "03822", "03823", "03828", "03833", "03836", "03838", 
    		"03839", "03848", "03866", "03868", "03869", "03882", "03883", "03886", "03888", "03889", 
    		"03893", "03898", "03899", "03900", "03903", "03908", "03918", "03933", "03939", "03948", 
    		"03958", "03963", "03966", "03968", "03969", "03983", "03988", "03989", "03993", "03996", 
    		"03998", "03999", "04331", "04332", "04333", "04335", "04336", "04337", "04338", "04362", 
    		"04363", "04370", "04601", "04603", "04604", "04605", "06030", "06033", "06038", "06066", 
    		"06068", "06088", "06099", "06108", "06113", "06116", "06118", "06122", "06123", "06128", 
    		"06133", "06136", "06138", "06139", "06161", "06163", "06166", "06168", "06169", "06178", 
    		"06183", "06188", "06189", "06196", "06198", "06199", "06210", "06230", "06288", "06388", 
    		"06808", "06816", "06818", "06822", "06823", "06826", "06828", "06830", "06833", "06836", 
    		"06837", "06838", "06839", "06858", "06863", "06865", "06866", "06868", "06869", "06878", 
    		"06880", "06881", "06882", "06883", "06886", "06888", "06889", "06893", "06896", "06898", 
    		"06899", "07200", "07202", "07205", "07210", "07221", "07222", "07225", "07228", "07230", 
    		"07231", "07242", "07250", "07255", "07261", "07267", "07288", "07300", "07302", "07311", 
    		"07312", "07315", "07321", "07322", "07326", "07328", "07331", "07335", "07336", "07341", 
    		"07362", "07388", "08001", "08002", "08003", "08005", "08006", "08007", "08008", "08009", 
    		"08011", "08012", "08013", "08016", "08017", "08018", "08019", "08020", "08021", "08022", 
    		"08023", "08025", "08026", "08027", "08028", "08029", "08030", "08031", "08032", "08033", 
    		"08035", "08036", "08037", "08038", "08039", "08041", "08045", "08046", "08047", "08048", 
    		"08049", "08050", "08051", "08052", "08053", "08055", "08056", "08057", "08058", "08059", 
    		"08060", "08061", "08062", "08063", "08066", "08067", "08068", "08069", "08070", "08071", 
    		"08072", "08073", "08075", "08076", "08077", "08078", "08079", "08080", "08081", "08082", 
    		"08083", "08085", "08086", "08087", "08088", "08089", "08090", "08091", "08092", "08093", 
    		"08095", "08096", "08097", "08098", "08099", "08100", "08101", "08102", "08103", "08106", 
    		"08107", "08108", "08109", "08111", "08112", "08113", "08115", "08116", "08117", "08119", 
    		"08120", "08121", "08122", "08123", "08125", "08126", "08127", "08128", "08129", "08130", 
    		"08131", "08132", "08133", "08135", "08137", "08138", "08139", "08140", "08141", "08142", 
    		"08143", "08145", "08147", "08148", "08149", "08150", "08152", "08153", "08155", "08156", 
    		"08157", "08158", "08159", "08160", "08161", "08162", "08163", "08165", "08166", "08167", 
    		"08169", "08170", "08171", "08172", "08173", "08175", "08176", "08177", "08178", "08179", 
    		"08180", "08181", "08182", "08183", "08185", "08186", "08187", "08188", "08189", "08190", 
    		"08191", "08192", "08193", "08195", "08196", "08197", "08198", "08200", "08201", "08202", 
    		"08203", "08205", "08206", "08207", "08209", "08210", "08211", "08212", "08213", "08215", 
    		"08216", "08217", "08218", "08220", "08221", "08222", "08225", "08226", "08227", "08228", 
    		"08229", "08230", "08231", "08232", "08233", "08235", "08236", "08237", "08238", "08239", 
    		"08240", "08242", "08243", "08245", "08246", "08247", "08248", "08249", "08250", "08251", 
    		"08252", "08253", "08255", "08256", "08257", "08258", "08260", "08261", "08262", "08265", 
    		"08266", "08267", "08268", "08269", "08270", "08271", "08272", "08273", "08277", "08278", 
    		"08279", "08280", "08281", "08282", "08283", "08286", "08290", "08291", "08292", "08293", 
    		"08295", "08296", "08297", "08299", "08300", "08301", "08305", "08306", "08307", "08308", 
    		"08309", "08310", "08311", "08312", "08315", "08316", "08317", "08318", "08319", "08320", 
    		"08321", "08325", "08326", "08327", "08328", "08329", "08331", "08333", "08336", "08337", 
    		"08340", "08341", "08342", "08343", "08345", "08346", "08347", "08348", "08349", "08351", 
    		"08352", "08353", "08355", "08356", "08358", "08359", "08360", "08361", "08362", "08363", 
    		"08365", "08366", "08368", "08369", "08370", "08372", "08373", "08375", "08376", "08377", 
    		"08379", "08380", "08381", "08383", "08385", "08387", "08388", "08389", "08390", "08392", 
    		"08393", "08397", "08398", "08399", "08400", "08405", "08407", "08409", "08410", "08411", 
    		"08412", "08413", "08415", "08416", "08417", "08420", "08421", "08423", "08425", "08427", 
    		"08428", "08431", "08432", "08439", "08442", "08446", "08452", "08455", "08460", "08462", 
    		"08463", "08465", "08469", "08471", "08472", "08481", "08552", "08553", "08555", "08556", 
    		"08557", "08558", "08559", "08560", "08561", "08563", "08565", "08566", "08567", "08568", 
    		"08569", "08570", "08571", "08573", "08575", "08576", "08577", "08578", "08579", "08580", 
    		"08581", "08582", "08583", "08585", "08586", "08587", "08588", "09010", "09074", "09115", 
    		"09146", "09155", "09170", "09834", "09836", "09846", "09847"  };
		
		return index;
	}
	
	public static String[] getAllStockIndex_v2(){
		String[] index = { 
				"00008", "00043", "00052", "00151", "00303", "00345", "00388", "00477", "00536", "00688", 
				"00700", "00773", "00831", "00837", "00867", "00884", "00950", "01044", "01061", "01114", 
				"01127", "01128", "01169", "01177", "01197", "01212", "01234", "01240", "01253", "01260", 
				"01297", "01316", "01345", "01349", "01363", "01382", "01385", "01500", "01539", "01556", 
				"01568", "01683", "01819", "01830", "01882", "01928", "01982", "01999", "02018", "02020", 
				"02030", "02033", "02098", "02111", "02128", "02202", "02282", "02283", "02293", "02313", 
				"02328", "02333", "02382", "02393", "02669", "03311", "03380", "03618", "03709", "03898", 
				"03966", "03968", "06033", "06116", "06388", "06822", "06869", "08008", "08138"
		};
		
		return index;
	}
	
	public static String[] getBuleStockIndex(){
		String[] index = { "00001", "00002", "00003", "00004", "00005", "00006", "00011", "00012", "00016", "00017", 
        		"00019", "00023", "00027", "00066", "00083", "00101", "00135", "00144", "00151", "00175", 
        		"00267", "00293", "00386", "00388", "00688", "00700", "00762", "00823", "00836", "00857", 
        		"00883", "00939", "00941", "00992", "01038", "01044", "01088", "01109", "01113", "01299", 
        		"01398", "01928", "02018", "02318", "02319", "02388", "02628", "03328", "03988" };
		
		return index;
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
	
	public static boolean getROESummary(String index_nbr, String type, float compare_val){
		String inputLine = null;
		
		SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy");
		
		boolean notFit = false;
		String tmpStr;
		int year = 10;
		int count = 0;
		
		try{
	        
	        inputLine = getNetworkContent("https://api.investtab.com/api/quote/"+index_nbr+":HK/financial-ratios");
	        
	        inputLine = "{\"dataset\":"+inputLine+"}";
	        
	        JSONObject json2 = new JSONObject(inputLine);
	        
	        JSONArray genreArray2 = (JSONArray) json2.get("dataset");
	        
			JSONObject genre2 = null;
			
			StringBuffer response = new StringBuffer();

			if(type == "Y"){
				
				inputLine = getNetworkContent("https://api.investtab.com/api/quote/"+index_nbr+":HK/earnings-summary");
		        
		        inputLine = "{\"dataset\":"+inputLine+"}";
		        
		        JSONObject json = new JSONObject(inputLine);
		        
		        JSONArray genreArray = (JSONArray) json.get("dataset");
				
				JSONObject genre = null;
				
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
			for(int i = genreArray2.length()-1, j = 0; i >= 0 && j < year; i--){
				
				try{
					genre2 = (JSONObject) genreArray2.get(i);
					
					if(genre2.get("cover_period").toString().equals("12.0")){
						j++;
						
						tmpStr = genre2.get("ret_equity").toString();
						
						try{
							if( j <= 5 && ( tmpStr == "null" || Float.parseFloat(tmpStr) < compare_val)){
								notFit = true;
								break;
							}
						} catch(Exception e){}
						
						response.append(tmpStr + "\t");
						
						count++;
					}
					
				} catch (Exception e){
					
				}
				
			}
			
			if(!notFit && count >= 6){
				System.out.println(response.toString());
			}
			
			response.delete(0, response.length());
			
		} catch(Exception e){
			System.out.println(e);
			System.out.println(inputLine);
		}
		
		return !notFit;
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
	        
	        /*inputLine = getNetworkContent("https://api.investtab.com/api/quote/"+index_nbr+":HK/financial-ratios");
	        
	        inputLine = "{\"dataset\":"+inputLine+"}";
	        
	        JSONObject json2 = new JSONObject(inputLine);
	        
	        JSONArray genreArray2 = (JSONArray) json2.get("dataset");*/
	        
			// get the first genre
			JSONObject genre = null;
			//JSONObject genre2 = null;
			
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
