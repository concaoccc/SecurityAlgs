package AlgsComb;

import Asymmetricencry.RSA;

public class AlgsComb {
	//算法组合，直接给UI进行调用
	//完成秘钥生成，加密，解密
	private String symAlgs = "DES";
	private String symmode = "CBC";
	private String shaAlgs = "SHA-1";
	private String keySelect = "generate";
	private String symKey = "";
	private String RSALength = "200";
	private String publicKeyA="";
	private String publicKeyB="";
	
	
	//初始化函数
	public AlgsComb() {
		// TODO Auto-generated constructor stub
	}
	// 修改参数
	public void changeParm(String symAlgs, String symmode, String shaAlgs, String keySelect, String symKey, String RSALength)
	{
		this.symAlgs = symAlgs;
		this.symmode = symmode;
		this.shaAlgs = shaAlgs;
		this.keySelect = keySelect;
		this.symKey = symKey;
		this.RSALength = RSALength;
	}
	//开始生成秘钥
	public void initKey()
	{
		//首先生成两组RSA
		int RSAKeyLength = Integer.parseInt(RSALength);
		 RSA keyA = new RSA(RSAKeyLength);
		 RSA keyB =  new RSA(RSAKeyLength);
		//首先生成一组对称秘钥
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
