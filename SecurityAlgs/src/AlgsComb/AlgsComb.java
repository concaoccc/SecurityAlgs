package AlgsComb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.text.JTextComponent.KeyBinding;

import Asymmetricencry.RSA;
import Hash.MD5;
import Hash.SHA;
import SymmetricEncry.AES;
import SymmetricEncry.DES;

public class AlgsComb {
	//�㷨��ϣ�ֱ�Ӹ�UI���е���
	//�����Կ���ɣ����ܣ�����
	private String symAlgs = "DES";
	private String symmode = "CBC";
	private String shaAlgs = "SHA-1";
	private String keySelect = "generate";
	private String symKey = "";
	private String RSALength = "200";
	
	public String publicKeyA="";
	public String publicKeyB="";
	public String privateKeyA="";
	public String privateKeyB="";
	
	private byte[] symKeyByte;
	//��ʼ����
	private byte[] IV;
	private RSA keyA;
	private RSA keyB;
	//��¼���ݲ�ȫ������
	int moreByte;
	//���ܺ���Կ�ĳ���
	int EntryKeyLength;
	//ԭʼ���ݳ���
	int PrimaryDatalength;
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
		 keyA = new RSA(RSAKeyLength);
		 publicKeyA = keyA.modulus.toString();
		 privateKeyA = keyA.privateKey.toString();
		 keyB =  new RSA(RSAKeyLength);
		 publicKeyB = keyB.modulus.toString();
		 privateKeyB = keyB.privateKey.toString();
		//��������һ��Գ���Կ
		 symKeyByte = getKey();
		
	}
	
	//��ȡ��Կ
	private byte[] getKey()
	{
		int needLength;
		if (this.symAlgs == "DES")
		{
			needLength = 8;
		}
		else {
			needLength = 16;
		}
		IV = new byte[needLength];
		for (int i = 0; i < IV.length; i++) {
			IV[i]= (byte) ((Math.random()-0.5)*256);
		}
		byte[] key = new byte[needLength];
		if (this.keySelect == "generate")
		{
			for (int i = 0; i < key.length; i++) {
				key[i]= (byte) ((Math.random()-0.5)*256);
			}
		}
		else {
			//ʹ��string��������
			byte[] tmp = this.symKey.getBytes();
			//�ж�byte�������Ƿ񳬳�
			if (tmp.length >= needLength)
			{
				for (int i = 0; i < needLength; i++) {
					key[i] = tmp[i];
				}
			}
			else {
				for (int i = 0; i < tmp.length; i++) {
					key[i] = tmp[i];
				}
				for (int j = tmp.length; j < needLength; j++) {
					key[j] = (byte) ((Math.random()-0.5)*256);
				}
			}
			
		}
		return key;
	}
	
	//�û�A�Ĳ���
	public byte[] encodeProcess(String data)
	{
		//����HASH
		byte[] shaData;
		byte[] primary = data.getBytes();
		if (shaAlgs == "SHA-1")
		{
			MessageDigest sh = null;
			try {
				sh = MessageDigest.getInstance("SHA");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	//�Եõ����ֽ�������д���
	    	sh.update(primary);
	    	//���й�ϣ���㲢���ؽ��
	    	shaData = sh.digest();
		}
		else {
			shaData =  new MD5().getMD5(primary);
		}
		//���Լ���˽Կ����ǩ��
		byte[] signature = keyA.encrypt(shaData);
		PrimaryDatalength = data.length();
		//��ǩ���ӵ����
		byte[] signaturedData = byteMerger(data.getBytes(), signature);
//		System.out.println("ǩ�����ݱȽ�:");
//		for (int i = 0; i < signaturedData.length; i++) {
//			System.out.print(signaturedData[i]+" ");
//		}
		//��ǩ����ԭʼ���ݽ��м���
		byte[] EncrySinaturedData = SymEncry(signaturedData);
		byte[] EncryKey = keyB.encrypt(symKeyByte);
		EntryKeyLength = EncryKey.length;
		byte[] result = byteMerger(EncrySinaturedData, EncryKey);
		
		return result;
	}
	
	//�û�B�Ĳ���
	public boolean decodeProcess(byte[] encryData)
	{
		//���Ȼ��ֳ����ܺ����Կ
		byte[] encryKey = getEncryKey(encryData, EntryKeyLength);
		byte[] UsedKey = keyB.decrypt(encryKey);
		byte[] EncrySinaturedData = new byte[encryData.length - EntryKeyLength];
		for (int i = 0; i < encryData.length - EntryKeyLength; i++) {
			EncrySinaturedData[i] = encryData[i];
		}
		byte[] SinaturedData = SymDecry(EncrySinaturedData);
		byte[] primaryData = new byte[PrimaryDatalength];
		for (int i = 0; i < primaryData.length; i++) {
			primaryData[i] = SinaturedData[i];
		}
		byte[] EncrySignature = new byte[SinaturedData.length - PrimaryDatalength];
		for (int i = 0; i < EncrySignature.length; i++) {
			EncrySignature[i] = SinaturedData[PrimaryDatalength+i];
		}
		
		byte[] signature = keyA.decrypt(EncrySignature);
		byte[] shaData;
		if (shaAlgs == "SHA-1")
		{
			MessageDigest sh = null;
			try {
				sh = MessageDigest.getInstance("SHA");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	//�Եõ����ֽ�������д���
	    	sh.update(primaryData);
	    	//���й�ϣ���㲢���ؽ��
	    	shaData = sh.digest();
		}
		else {
			shaData =  new MD5().getMD5(primaryData);
		}
		for (int i = 0; i < shaData.length; i++) {
			if(signature[i] != shaData[i])
			{
				return false;
			}
		}
		return true;	
	}
	
	private byte[] getEncryKey(byte[] encryData, int length)
	{
		byte[] result = new byte[length];
		int index =0;
		for (int i = encryData.length - length; i < encryData.length; i++) {
			result[index] = encryData[i];
			index++;
		}
		return result;
	}
	//ʹ�öԳ���Կ���ܲ�ͬģʽ��ͬģʽ
	  public byte[] SymEncry(byte[] data)
	  {
		  
		  //����Կ���з���
		  byte[] encryText = null;
		  int num = 1;
		  int needLength;
		  if (this.symAlgs == "DES")
		  {
			  //���Ƚ����������
			  needLength = 8;
			  byte[] tmpIV = IV;
			  DES des = new DES();
			  data = addData(data, needLength);
			  while (num * needLength <= data.length) {
				  byte[] tmpData = new byte[needLength];
				  for (int i = 0; i < tmpData.length; i++) {
					  tmpData[i] = data[i+(num-1)*needLength];
				  }
				  //�˴μ��ܽ��
				  if (symmode == "CBC")
				  {
					  byte[] tmpInput= XOR(tmpIV, tmpData);
					  byte[] tmpResult = des.encry(tmpInput, symKeyByte);
					  tmpIV = tmpResult;
					  encryText = byteMerger(encryText, tmpResult);
				  }
				  else if(symmode == "CFB")
				  {
					  byte[] tmpInput= tmpIV;
					  byte[] tmpResult = des.encry(tmpInput, symKeyByte);
					  tmpResult = XOR(tmpResult, tmpData);
					  tmpIV = tmpResult;
					  encryText = byteMerger(encryText, tmpResult);
					  
				  }
				  else {
					  byte[] tmpInput= tmpIV;
					  byte[] tmpResult = des.encry(tmpInput, symKeyByte);
					  tmpIV = tmpResult;
					  tmpResult = XOR(tmpResult, tmpData);
					  encryText = byteMerger(encryText, tmpResult);
				}
				num++;
			}
		  }
		  else {
			  //���Ƚ����������
			  needLength = 16;
			  byte[] tmpIV = IV;
			  AES test = new AES(symKeyByte);
			  data = addData(data, needLength);
			  while (num * needLength <= data.length) {
				  byte[] tmpData = new byte[needLength];
				  for (int i = 0; i < tmpData.length; i++) {
					  tmpData[i] = data[i+(num-1)*needLength];
				  }
				  num++;
				  //�˴μ��ܽ��
				  if (symmode == "CBC")
				  {
					  byte[] tmpInput= XOR(tmpIV, tmpData);
					  byte[] tmpResult = test.encrypt(tmpInput);
					  tmpIV = tmpResult;
					  encryText = byteMerger(encryText, tmpResult);
				  }
				  else if(symmode == "CFB")
				  {
					  byte[] tmpInput= tmpIV;
					  byte[] tmpResult = test.encrypt(tmpInput);
					  tmpResult = XOR(tmpResult, tmpData);
					  tmpIV = tmpResult;
					  encryText = byteMerger(encryText, tmpResult);
				  }
				  else {
					  byte[] tmpInput= tmpIV;
					  byte[] tmpResult = test.encrypt(tmpInput);
					  tmpIV = tmpResult;
					  tmpResult = XOR(tmpResult, tmpData);
					  encryText = byteMerger(encryText, tmpResult);
				}
			}
		}
		  return encryText;
	  }
	  
	 //ʹ�öԳ���Կ���ܲ�ͬģʽ
	  public byte[] SymDecry(byte[] data)
	  {
		//����Կ���з���
		  byte[] decryText = null;
		  int num = 1;
		  int needLength;
		  if (this.symAlgs == "DES")
		  {
			  //���Ƚ����������
			  needLength = 8;
			  byte[] tmpIV = IV;
			  DES des = new DES();
			  while (num * needLength <= data.length) {
				  byte[] tmpData = new byte[needLength];
				  for (int i = 0; i < tmpData.length; i++) {
					  tmpData[i] = data[i+(num-1)*needLength];
				  }
				  
				  //�˴μ��ܽ��
				  if (symmode == "CBC")
				  {
					  byte[] tmpInput = tmpData;
					  byte[] tmpResult = des.encry(tmpInput, symKeyByte);
					  byte[] tmpDecry = XOR(tmpResult, tmpIV);
					  tmpIV = tmpInput;
					  decryText = byteMerger(decryText, tmpDecry);
				  }
				  else if(symmode == "CFB")
				  {
					  byte[] tmpInput= tmpIV;
					  byte[] tmpResult = des.encry(tmpInput, symKeyByte);
					  tmpResult = XOR(tmpResult, tmpData);
					  tmpIV = tmpData;
					  decryText = byteMerger(decryText, tmpResult);
				  }
				  else {
					  byte[] tmpInput= tmpIV;
					  byte[] tmpResult = des.encry(tmpInput, symKeyByte);
					  tmpIV = tmpResult;
					  tmpResult = XOR(tmpResult, tmpData);
					  decryText = byteMerger(decryText, tmpResult);
				}
				num++;
			}
		  }
		  else {
			  //���Ƚ����������
			  needLength = 16;
			  byte[] tmpIV = IV;
			  AES test = new AES(symKeyByte);
			  while (num * needLength <= data.length) {
				  byte[] tmpData = new byte[needLength];
				  for (int i = 0; i < tmpData.length; i++) {
					  tmpData[i] = data[i+(num-1)*needLength];
				  }
				  num++;
				  //�˴μ��ܽ��
				  if (symmode == "CBC")
				  {
					  byte[] tmpInput = tmpData;
					  byte[] tmpResult = test.decrypt(tmpInput);
					  byte[] tmpDecry = XOR(tmpResult, tmpIV);
					  tmpIV = tmpInput;
					  decryText = byteMerger(decryText, tmpDecry);
				  }
				  else if(symmode == "CFB")
				  {
					  byte[] tmpInput= tmpIV;
					  byte[] tmpResult =  test.encrypt(tmpInput);
					  tmpResult = XOR(tmpResult, tmpData);
					  tmpIV = tmpData;
					  decryText = byteMerger(decryText, tmpResult);
				  }
				  else {
					  byte[] tmpInput= tmpIV;
					  byte[] tmpResult =  test.encrypt(tmpInput);
					  tmpIV = tmpResult;
					  tmpResult = XOR(tmpResult, tmpData);
					  decryText = byteMerger(decryText, tmpResult);
				}
			}
		}
		  byte[] finalDecryText = new byte[decryText.length - moreByte];
		  for (int i = 0; i < finalDecryText.length; i++) {
			finalDecryText[i] = decryText[i];
		}
		return finalDecryText;
	  }
	//��byte[]�������
	  public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){
		  if(byte_1==null)
		  {
			  return byte_2;
		  }
		  if(byte_2 == null)
		  {
			  return byte_1;
		  }
	      byte[] byte_3 = new byte[byte_1.length+byte_2.length];  
	      System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);  
	      System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);  
	      return byte_3;  
	    }
	  //byte[]�����������
	  public byte[] addData(byte[] data, int needlength)
	  {
		  byte[] result;
		  int length = data.length;
		  if (length % needlength == 0)
		  {
			  moreByte = 0;
			  return data;
		  }
		  else
		  {
			  int need = needlength - length%needlength;
			  moreByte = need;
			  result = new byte[length + need];
			  for (int i = 0; i < length; i++) {
				result[i] = data[i];
			}
			  for (int i = length; i < length+need; i++) {
				result[i] = 1;
			}
			  return result;
		  }
	  }
	//byte[]�����
	  public byte[] XOR(byte[]data1, byte[] data2)
	  {
		  byte[] result = new byte[data1.length];
		  for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (data1[i]^data2[i]);
		  }
		  return result;
	  }
	  //��ʾ��Կ
	public void showKey()
	{
		System.out.println("��һ�鹫Կ");
		System.out.println(publicKeyA);
		System.out.println("��һ��˽Կ");
		System.out.println(privateKeyA);
		System.out.println("�ڶ��鹫Կ");
		System.out.println(publicKeyB);
		System.out.println("�ڶ���˽Կ");
		System.out.println(privateKeyB);
		System.out.println("�Գ���Կ:");
		System.out.println(new String(symKeyByte));
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
