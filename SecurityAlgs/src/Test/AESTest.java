package Test;

import SymmetricEncry.AES;

public class AESTest {
	public static void main(String[] args)
    {
    	byte[] key = new byte[16];
    	byte[] data = new byte[16];
    	System.out.println("原始数据:");
		for (int i = 0; i < data.length; i++) {
			data[i]= (byte) ((Math.random()-0.5)*256);
			System.out.print(data[i]+" ");
		}
		System.out.println();
		System.out.println("密钥：");
		for (int i = 0; i < data.length; i++) {
			key[i]= (byte) ((Math.random()-0.5)*256);
			System.out.print(key[i]+" ");
		}
		System.out.println();
		System.out.println("密文：");
        AES test = new AES(key);
        byte[] entryText = test.encrypt(data);
        for (int i = 0; i < entryText.length; i++) {
			System.out.print(entryText[i]+" ");
		}
        System.out.println();
        byte[] result = test.decrypt(entryText);
        System.out.println("解密：");
        for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]+" ");
		}
        
    }
}
