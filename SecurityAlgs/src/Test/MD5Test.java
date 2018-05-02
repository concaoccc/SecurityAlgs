package Test;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//与java自带的进行比较
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
    	System.out.println("自带的MD5:");
    	System.out.println(new String(result));
    	MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			//得到一个操作系统默认的字节编码格式的字节数组
			byte[] btInput = dataChangeBuilder.toString().getBytes();
	    	//对得到的字节数组进行处理
	    	md.update(btInput);
	    	//进行哈希计算并返回结果
	    	byte[] btResult = md.digest();
	    	StringBuffer sb = new StringBuffer();
	    	for(byte b : btResult){
	    		int bt = b&0xff;
	    		if(bt<16){
	    			sb.append(0);
	    		}
	    		sb.append(Integer.toHexString(bt));
	    	}
	    	System.out.println("系统版本：");
	    	System.out.println( sb.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
