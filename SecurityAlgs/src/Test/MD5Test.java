package Test;

import Hash.MD5;

public class MD5Test {
	public static void main(String[] args){
    	MD5 md5Test = new MD5();
    	byte[] result = md5Test.getMD5(".".getBytes());
    	System.out.println(new String(result));
    }
}
