package Test;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//��java�Դ��Ľ��бȽ�
import Hash.MD5;

public class MD5Test {
	public static void main(String[] args){
//		String data = "hello world!";
//        System.out.println(data);
		byte[] data = new byte[160];
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) ((Math.random()-0.5)*256);
		}
		StringBuilder dataChangeBuilder = new StringBuilder() ;
		for(byte b : data){
    		int bt = b&0xff;
    		if(bt<16){
    			dataChangeBuilder.append(0);
    		}
    		dataChangeBuilder.append(Integer.toHexString(bt));
    	}
    	MD5 md5Test = new MD5();
    	byte[] result = md5Test.getMD5(dataChangeBuilder.toString().getBytes());
    	System.out.println("�Դ���MD5:");
    	System.out.println(new String(result));
    	MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			//�õ�һ������ϵͳĬ�ϵ��ֽڱ����ʽ���ֽ�����
			byte[] btInput = dataChangeBuilder.toString().getBytes();
	    	//�Եõ����ֽ�������д���
	    	md.update(btInput);
	    	//���й�ϣ���㲢���ؽ��
	    	byte[] btResult = md.digest();
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
