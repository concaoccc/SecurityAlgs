package Test;
import java.math.BigInteger;
import Asymmetricencry.RSA;
public class RSATest {
	   public static void main(String[] args) {
		      int N = 500;
		      RSA key = new RSA(N);
		      //防止出现负数，在最前面加入0 
		      byte[] data = new byte[40];
		      for (int i = 1; i < data.length; i++) {
		    	  data[i]= (byte) ((Math.random()-0.5)*256);
				}
		      byte[] encrypt = key.encrypt(data);
		      
		      byte[] decrypt = key.decrypt(encrypt);
		      System.out.println("message   = " + new BigInteger(data));
		      System.out.println("decrypted = " + new BigInteger(decrypt));
		   } 
}
