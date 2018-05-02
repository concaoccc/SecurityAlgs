package Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Hash.SHA;

public class SHATest {
	public static void main(String[] args) {

        //Getting the word
        String data = "hello wosdjkfhasjkfhjkasdhksdjkfhsjkdahfj,hadsjkfhasjkdhfajksahdfjkhasdjkfhasjkdhfjkasdhfjkfjasklfj;askf;laskflakjsjl;fasjkfhkajshfjasdhrld!";
        //Converting the word to binary
        SHA sha = new SHA(data.getBytes());
        byte[] result = sha.getSHA();
        System.out.println("�Լ���SHA��");
        System.out.println(new String(result));
        MessageDigest sh;
        try {
			sh = MessageDigest.getInstance("SHA");
			//�õ�һ������ϵͳĬ�ϵ��ֽڱ����ʽ���ֽ�����
			byte[] btInput =data.getBytes();
	    	//�Եõ����ֽ�������д���
	    	sh.update(btInput);
	    	//���й�ϣ���㲢���ؽ��
	    	byte[] btResult = sh.digest();
	    	StringBuffer sb = new StringBuffer();
	    	for(byte b : btResult){
	    		int bt = b&0xff;
	    		if(bt<16){
	    			sb.append(0);
	    		}
	    		sb.append(Integer.toHexString(bt));
	    	}
	    	System.out.println("ϵͳ�汾��");
	    	System.out.println( sb.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
