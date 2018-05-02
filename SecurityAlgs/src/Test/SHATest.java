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
        System.out.println("自己的SHA：");
        System.out.println(new String(result));
        MessageDigest sh;
        try {
			sh = MessageDigest.getInstance("SHA");
			//得到一个操作系统默认的字节编码格式的字节数组
			byte[] btInput =data.getBytes();
	    	//对得到的字节数组进行处理
	    	sh.update(btInput);
	    	//进行哈希计算并返回结果
	    	byte[] btResult = sh.digest();
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
