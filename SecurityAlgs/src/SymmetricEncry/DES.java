package SymmetricEncry;

public class DES {

	
	public DES() {
		// TODO Auto-generated constructor stub
	}
	public byte[] encry(byte[] data, byte[] key)
	{
		if( data.length != 8 || key.length != 8)
		{
			System.out.println("DES输入不正常！");
			System.exit(-1);
		}
		//将输入转成64bit
		byte[] changeData = change8to64(data);
		byte[] changeKey = change8to64(key);
		DESCore desCore = new DESCore(changeData, changeKey, "encry");
		byte[] result = desCore.getResult();
		result = change64to8(result);
		return result;
	}
	public byte[] decry(byte[] encrytext, byte[] key)
	{
		if( encrytext.length != 8 || key.length != 8)
		{
			System.out.println("DES输入不正常！");
			System.exit(-1);
		}
		//将输入转成64bit
		byte[] changeData = change8to64(encrytext);
		byte[] changeKey = change8to64(key);
		DESCore desCore = new DESCore(changeData, changeKey, "decry");
		byte[] result = desCore.getResult();
		result = change64to8(result);
		return result;
	}
	
	//将8bit转化成64bit
	public byte[] change8to64(byte[] data)
	{
		byte[] result = new byte[64];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length; j++) {
				result[i*8+j] =(byte)((data[i]>>j)&1);
			}
		}
		return result;
	}
	
	//将64bit转化成8比特
	public byte[] change64to8(byte[] data)
	{
		byte[] result = new byte[8];
		for (int i = 0; i < data.length/8; i++) {
			byte tmp = 0;
			for (int j = 0; j < 8; j++) {
				tmp += data[i*8+j]<<j;
			}
			result[i] = tmp;
		}
		return result;
	}
	/**
	 * @param args
	 */

}
