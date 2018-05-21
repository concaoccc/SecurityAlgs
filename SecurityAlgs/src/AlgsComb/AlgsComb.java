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
	//算法组合，直接给UI进行调用
	//完成秘钥生成，加密，解密
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
	//初始向量
	private byte[] IV;
	private RSA keyA;
	private RSA keyB;
	//记录数据补全的数据
	int moreByte;
	//加密后秘钥的长度
	int EntryKeyLength;
	//原始数据长度
	int PrimaryDatalength;
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
		 keyA = new RSA(RSAKeyLength);
		 publicKeyA = keyA.modulus.toString();
		 privateKeyA = keyA.privateKey.toString();
		 keyB =  new RSA(RSAKeyLength);
		 publicKeyB = keyB.modulus.toString();
		 privateKeyB = keyB.privateKey.toString();
		//首先生成一组对称秘钥
		 symKeyByte = getKey();
		
	}
	
	//获取秘钥
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
			//使用string进行生成
			byte[] tmp = this.symKey.getBytes();
			//判断byte的数据是否超出
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
	
	//用户A的操作
	public byte[] encodeProcess(String data)
	{
		//生成HASH
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
	    	//对得到的字节数组进行处理
	    	sh.update(primary);
	    	//进行哈希计算并返回结果
	    	shaData = sh.digest();
		}
		else {
			shaData =  new MD5().getMD5(primary);
		}
		//用自己的私钥进行签名
		byte[] signature = keyA.encrypt(shaData);
		PrimaryDatalength = data.length();
		//将签名加到最后
		byte[] signaturedData = byteMerger(data.getBytes(), signature);
//		System.out.println("签名数据比较:");
//		for (int i = 0; i < signaturedData.length; i++) {
//			System.out.print(signaturedData[i]+" ");
//		}
		//对签名和原始数据进行加密
		byte[] EncrySinaturedData = SymEncry(signaturedData);
		byte[] EncryKey = keyB.encrypt(symKeyByte);
		EntryKeyLength = EncryKey.length;
		byte[] result = byteMerger(EncrySinaturedData, EncryKey);
		
		return result;
	}
	
	//用户B的操作
	public boolean decodeProcess(byte[] encryData)
	{
		//首先划分出加密后的秘钥
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
	    	//对得到的字节数组进行处理
	    	sh.update(primaryData);
	    	//进行哈希计算并返回结果
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
	//使用对称秘钥加密不同模式不同模式
	  public byte[] SymEncry(byte[] data)
	  {
		  
		  //对秘钥进行分组
		  byte[] encryText = null;
		  int num = 1;
		  int needLength;
		  if (this.symAlgs == "DES")
		  {
			  //首先进行数据填充
			  needLength = 8;
			  byte[] tmpIV = IV;
			  DES des = new DES();
			  data = addData(data, needLength);
			  while (num * needLength <= data.length) {
				  byte[] tmpData = new byte[needLength];
				  for (int i = 0; i < tmpData.length; i++) {
					  tmpData[i] = data[i+(num-1)*needLength];
				  }
				  //此次加密结果
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
			  //首先进行数据填充
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
				  //此次加密结果
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
	  
	 //使用对称秘钥解密不同模式
	  public byte[] SymDecry(byte[] data)
	  {
		//对秘钥进行分组
		  byte[] decryText = null;
		  int num = 1;
		  int needLength;
		  if (this.symAlgs == "DES")
		  {
			  //首先进行数据填充
			  needLength = 8;
			  byte[] tmpIV = IV;
			  DES des = new DES();
			  while (num * needLength <= data.length) {
				  byte[] tmpData = new byte[needLength];
				  for (int i = 0; i < tmpData.length; i++) {
					  tmpData[i] = data[i+(num-1)*needLength];
				  }
				  
				  //此次加密结果
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
			  //首先进行数据填充
			  needLength = 16;
			  byte[] tmpIV = IV;
			  AES test = new AES(symKeyByte);
			  while (num * needLength <= data.length) {
				  byte[] tmpData = new byte[needLength];
				  for (int i = 0; i < tmpData.length; i++) {
					  tmpData[i] = data[i+(num-1)*needLength];
				  }
				  num++;
				  //此次加密结果
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
	//对byte[]进行组合
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
	  //byte[]进行数据填充
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
	//byte[]的异或
	  public byte[] XOR(byte[]data1, byte[] data2)
	  {
		  byte[] result = new byte[data1.length];
		  for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (data1[i]^data2[i]);
		  }
		  return result;
	  }
	  //显示秘钥
	public void showKey()
	{
		System.out.println("第一组公钥");
		System.out.println(publicKeyA);
		System.out.println("第一组私钥");
		System.out.println(privateKeyA);
		System.out.println("第二组公钥");
		System.out.println(publicKeyB);
		System.out.println("第二组私钥");
		System.out.println(privateKeyB);
		System.out.println("对称秘钥:");
		System.out.println(new String(symKeyByte));
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
