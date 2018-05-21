package Test;

import AlgsComb.AlgsComb;

public class AlgsCombTest {

	//加密模式测试
	public static void SysModeTest()
	{
		AlgsComb test = new AlgsComb();
		String symAlgs = "AES";
		String symmode = "OFB";
		String shaAlgs = "SHA-1";
		String keySelect = "generate";
		String symKey = "";
		String RSALength = "200";
		byte[] data = new byte[100];
		for (int i = 0; i < data.length; i++) {
			data[i]= (byte) ((Math.random()-0.5)*256);
		}
		test.changeParm(symAlgs,symmode, shaAlgs, keySelect, symKey, RSALength);
		test.initKey();
		byte[] tmp = test.SymEncry(data);
		byte[] result = test.SymDecry(tmp);
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i]+" ");
		}
		System.out.println();
		System.out.println(data.length);
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]+" ");
		}
		System.out.println();
		System.out.println(result.length);
		for (int i = 0; i < result.length; i++) {
			if(data[i]!= result[i])
			{
				System.out.println("发生错误！");
				return;
			}
		}
		System.out.println("完全正确！");
	}
	
	/*
	 * 对整体进行测试
	 */
	public static void CombTest()
	{
		AlgsComb test = new AlgsComb();
		String symAlgs = "AES";
		String symmode = "OFB";
		String shaAlgs = "SHA-1";
		String keySelect = "generate";
		String symKey = "";
		String RSALength = "200";
		String data = "caocongcong zhen de hao shuai!!!!!!!!!!!!!!!!";
		test.changeParm(symAlgs,symmode, shaAlgs, keySelect, symKey, RSALength);
		test.initKey();
		byte[] tmp = test.encodeProcess(data);
		boolean result = test.decodeProcess(tmp);
		if(result)
		{
			System.out.println("此次测试成功！");
		}
		else {
			System.out.println("此次测试失败！");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CombTest();
	}

}
