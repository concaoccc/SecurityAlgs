package AlgsComb;

import Asymmetricencry.RSA;

public class AlgsComb {
	//�㷨��ϣ�ֱ�Ӹ�UI���е���
	//�����Կ���ɣ����ܣ�����
	private String symAlgs = "DES";
	private String symmode = "CBC";
	private String shaAlgs = "SHA-1";
	private String keySelect = "generate";
	private String symKey = "";
	private String RSALength = "200";
	private String publicKeyA="";
	private String publicKeyB="";
	
	
	//��ʼ������
	public AlgsComb() {
		// TODO Auto-generated constructor stub
	}
	// �޸Ĳ���
	public void changeParm(String symAlgs, String symmode, String shaAlgs, String keySelect, String symKey, String RSALength)
	{
		this.symAlgs = symAlgs;
		this.symmode = symmode;
		this.shaAlgs = shaAlgs;
		this.keySelect = keySelect;
		this.symKey = symKey;
		this.RSALength = RSALength;
	}
	//��ʼ������Կ
	public void initKey()
	{
		//������������RSA
		int RSAKeyLength = Integer.parseInt(RSALength);
		 RSA keyA = new RSA(RSAKeyLength);
		 RSA keyB =  new RSA(RSAKeyLength);
		//��������һ��Գ���Կ
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
