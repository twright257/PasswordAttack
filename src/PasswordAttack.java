import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.imageio.IIOException;

/*
 * Tyler Wright
 * Feb. 8, 2016
 * PasswordAttack uses a supplied password dictionary, runs the words from the file through an MD5
 * hash function and compares the values to those of known hash values, printing the unencrypted word 
 * as well as the time taken to find it. 
 */

public class PasswordAttack {
	private String filePath = "passwords.txt"; //path to dictionary file
	//variables of hash values to match on 
	private HashMap passwords = new HashMap(); 
	private String pass0 = "6f047ccaa1ed3e8e05cde1c7ebc7d958"; 
	private String pass1 = "275a5602cd91a468a0e10c226a03a39c"; 
	private String pass2 = "b4ba93170358df216e8648734ac2d539"; 
	private String pass3 = "dc1c6ca00763a1821c5af993e0b6f60a";
	private String pass4 = "8cd9f1b962128bd3d3ede2f5f101f4fc";
	private String pass5 = "554532464e066aba23aee72b95f18ba2";
	//hash value array 
	private String[] passHash = {pass0, pass1, pass2, pass3, pass4, pass5};
	
	public PasswordAttack() {
		for (String p : passHash) {
			passwords.put(p,  1); 
		}
	}

	//read through dictionary file and pass words to MD5. Print if match 
	public void processFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line;//each dictionary word 
		    long startTime = System.nanoTime(); //start timer 
		    while ((line = br.readLine()) != null) {
		       String md5Value = MD5(line);
		       if (passwords.containsKey(md5Value)) {
		    	   System.out.println("The Password for Hash Value " + md5Value + " is: " + line);
	    		   System.out.println("It took " + ((System.nanoTime() - startTime) / 1000000000.0) + " seconds to find the password.\n");
		       }
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//convert input string to MD5 hex hash
	public String MD5(String md5) {
		try {
			//very handy way to get MD5 bytes 
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] inputBytes = md.digest(md5.getBytes(Charset.forName("UTF8")));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < inputBytes.length; ++i) {
				sb.append(Integer.toHexString((inputBytes[i] & 0xFF) | 0x100).substring(1, 3)); //pad with zeros and make 2 digit hex
			}
			return sb.toString();
		}catch(NoSuchAlgorithmException e){
			// TODO Auto-generated catch block
			return null; 
		}
	}

	public static void main(String[] args) {
		PasswordAttack p = new PasswordAttack(); 
		p.processFile();
	}
}
