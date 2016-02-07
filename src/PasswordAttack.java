import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.IIOException;

public class PasswordAttack {
	private String filePath = "passwords.txt"; 
	private String pass1 = "275a5602cd91a468a0e10c226a03a39c"; 
	private String pass2 = "b4ba93170358df216e8648734ac2d539"; 
	private String pass3 = "dc1c6ca00763a1821c5af993e0b6f60a";
	private String pass4 = "8cd9f1b962128bd3d3ede2f5f101f4fc";
	private String pass5 = "554532464e066aba23aee72b95f18ba2";
	private String[] passHash = {pass1, pass2, pass3, pass4, pass5};

	public void processFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line;
		    long startTime = System.nanoTime(); 
		    while ((line = br.readLine()) != null) {
		       String md5Value = MD5(line);
		       for (String p : passHash) {
		    	   if (p.equals(md5Value)) {
		    		   System.out.println("The Password for Hash Value " + p + " is: " + line);
		    		   System.out.println("It took " + ((System.nanoTime() - startTime) / 1000000000.0) + " seconds to find the password.\n");
		    	   }
		       }
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String MD5(String md5) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] inputBytes = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < inputBytes.length; ++i) {
				sb.append(Integer.toHexString((inputBytes[i] & 0xFF) | 0x100).substring(1, 3));
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
		//System.out.println(p.MD5("s"));
	}
}
