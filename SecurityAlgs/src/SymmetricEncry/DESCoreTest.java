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
		//随机生成数据和秘钥
		for (int i = 0; i < key.length; i++) {
			double dataRandom = Math.random();
			data[i] = (byte)(dataRandom>0.5 ? 1:0);
			double keyRandom = Math.random();
			key[i] = (byte)(keyRandom>0.5 ? 1:0);
		}
		System.out.println("原始数据:");
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i]+" ");
		}
		System.out.println();
		DESCore desEncryTest = new DESCore(data,key,"encry");
		byte[] encryresult = desEncryTest.getResult();
		System.out.println("加密结果:");
		for (int i = 0; i < encryresult.length; i++) {
			System.out.print(encryresult[i]+" ");
		}
		System.out.println();
		DESCore desDecryTest = new DESCore(encryresult, key, "decry"); 
		byte[] decryResult = desDecryTest.getResult();
		System.out.println("解密结果：");
		for (int i = 0; i < decryResult.length; i++) {
			System.out.print(decryResult[i]+" ");
		}
		System.out.println();
		boolean right = true;
		for (int i = 0; i < decryResult.length; i++) {
			if (data[i] != decryResult[i])
			{
				right = false;
				break;
			}
		}
		if (right)
		{
			System.out.println("此次DES测试正确！");
		}
		else
		{
			System.out.println("此次DES测试错误！");
		}
	}

}
