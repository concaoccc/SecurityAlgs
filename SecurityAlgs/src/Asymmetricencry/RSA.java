package Asymmetricencry;

import java.math.BigInteger;
import java.security.SecureRandom;
    

public class RSA {
   private final static BigInteger one      = new BigInteger("1");
   private final static SecureRandom random = new SecureRandom();

   public BigInteger privateKey;
   public BigInteger publicKey;
   public BigInteger modulus;

   // generate an N-bit (roughly) public and private key
   public RSA(int N) {
      BigInteger p = BigInteger.probablePrime(N, random);
      BigInteger q = BigInteger.probablePrime(N, random);
      BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

      modulus    = p.multiply(q);                                  
      publicKey  = new BigInteger("65537");     // common value in practice = 2^16 + 1
      privateKey = publicKey.modInverse(phi);
   }


   public byte[] encrypt(byte[] data ) {
	   byte[] used = new byte[data.length+1];
	   used[0] = 0x11;
	   for (int i = 1; i < used.length; i++) {
		used[i] = data[i-1];
	}
	   BigInteger message = new BigInteger(used);
      return message.modPow(publicKey, modulus).toByteArray();
   }

   public byte[] decrypt(byte[] data) {
	  BigInteger encrypted = new BigInteger(data);
	  BigInteger tmpResult = encrypted.modPow(privateKey, modulus);
      byte[] tmp = tmpResult.toByteArray();
      byte[] result = new byte[tmp.length-1];
      for (int i = 0; i < result.length; i++) {
		result[i] = tmp[i+1];
      }
      return result;
   }
}
