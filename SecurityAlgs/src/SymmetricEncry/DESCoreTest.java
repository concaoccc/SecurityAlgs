package SymmetricEncry;
//��DESCore�ĺ��ļӽ��ܽ��в���
//��ʵ�ʼ�������Java�Դ���DES����бȽ�
public class DESCoreTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] data = new byte[64];
		byte[] key = new byte[64];
		for (int i = 0; i < key.length; i++) {
			data[i] = (byte)1;
			key[i] = (byte)1;
		}
		DESCore desTest = new DESCore(data,key,"encry");
		byte[] result = desTest.getResult();
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]+" ");
		}
		System.out.println();
	}

}
