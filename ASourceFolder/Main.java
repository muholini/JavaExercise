import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

	public static void main(String[] args) throws Exception {
		File aFile = new File("TextWithLinks");

	   	Validator aCheck = new Validator();
	    Fetch fData = new Fetch();

	   	String regex = "((http|https):/{2})+(([0-9a-z+&@#/%?=~\\-_|!.,;]*[0-9a-z+&@#/%?=~\\-_|!.,;])(.com|.org|.at))((\\/([~0-9a-zA-Z+&@/%~\\-_|!,;]+))?([0-9a-zA-Z+&@/%~\\-_|!,;]+)?)?";
	   	Pattern patt = Pattern.compile(regex);

	    try(BufferedReader br = new BufferedReader(new FileReader(aFile))) {
	      for(String line; (line = br.readLine()) != null; ) {
	      	try {
	          Matcher matcher = patt.matcher(line);
	          	if (matcher.find()){
				 	aCheck.validateString(matcher.group());
				}
		      } catch (Exception e) {}
	      }
	    }catch(Exception e){}

	    for(String link : aCheck.getList()){
	      try{
	        fData.fetchData(link);
	      }catch(Exception e){}
	    }
	    
	    for(int i = 1; i <= fData.getNumOfLinks(); i++){
	    	String md5 = fData.getMD5Checksum("Link"+i);
	    	byte[] encodedBuilder = Base64.getEncoder().encode(md5.getBytes());
	    	System.out.println(md5);
	    	System.out.println(encodedBuilder);
	    }
	    
	    //System.out.println(fData.getBase64List());
	    //System.out.println(fData.getChecksumList());
	}
	
}
