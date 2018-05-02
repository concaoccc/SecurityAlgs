package Test;

import SymmetricEncry.DES;

public class DESTest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DES desTest = new DES();
		byte[] data = new byte[8];
		byte[] key = new byte[8];
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
		byte[] encrytext = desTest.encry(data, key);
		for (int i = 0; i < encrytext.length; i++) {
			System.out.print(encrytext[i]+" ");
		}
		System.out.println();
		System.out.println("解密：");
		byte[] result = desTest.decry(encrytext, key);
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]+" ");
		}
	}
}
