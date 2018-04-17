package SymmetricEncry;
//对DESCore的核心加解密进行测试
//将实际计算结果与Java自带的DES库进行比较
public class DESCoreTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] data = new byte[64];
		byte[] key = new byte[64];
		for (int i = 0; i < key.length; i++) {
			data[i] = (byte)1;
			key[i] = (byte)1;
		}
		DESCore desTest = new DESCore(data,key,"encry");
		byte[] result = desTest.getResult();
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]+" ");
		}
		System.out.println();
	}

}
