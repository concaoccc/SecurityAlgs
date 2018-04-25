package SymmetricEncry;

//DES�ĺ��ĳ���ֻ��ɼ��ܺͽ��ܲ���
class DESCore {
	//����ӽ��ܵĽ��
	private byte[] result;
	/*
	 * ���캯��
	 * �˴�ȡ�ɣ�ÿһ��byteֻ��ʾ0����1�������ĸ���Կ���ǳ���λ64��byte����
	 * plaintext:64λ������
	 * key:64λ��Կ
	 * mode: string �࣬��ѡ��Ϊencry��decry���ֱ�����˼��ܺͽ��ܲ���
	 */
	public DESCore(byte[] text, byte[] key, String mode) {
		if(mode.equals("encry"))
		{
			result = encry(text, key);
		}
		else if(mode.equals("decry"))
		{
			result = decry(text, key);
		}
		else {
			System.out.println("����DES�㷨��ѡ���˲���ѡ���ģʽ��������");
		}
	}
	
	//���ش˴μ��ܽ��
	public byte[] getResult()
	{
		return result;
	}
	//IP���û����飬�������ĵ��û�
	public static int[] IP={
		58,50,42,34,26,18,10,2,  
		60,52,44,36,28,20,12,4,  
		62,54,46,38,30,22,14,6,  
		64,56,48,40,32,24,16,8,  
		57,49,41,33,25,17, 9,1,  
		59,51,43,35,27,19,11,3,  
		61,53,45,37,29,21,13,5,  
		63,55,47,39,31,23,15,7 
	};
	
	//��Կ��64λ���56λ
	public static int[] PC1={
		57,49,41,33,25,17,9,1,  
		58,50,42,34,26,18,10,2,  
		59,51,43,35,27,19,11,3,  
		60,52,44,36,63,55,47,39,  
		31,23,15,7,62,54,46,38,  
		30,22,14,6,61,53,45,37,  
		29,21,13,5,28,20,12,4 
	};
	
	//����ʱ������Կÿ��ѭ����λ�Ĵ���
	public static int[] encryMove={
		1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1
	};
	
	//����ʱ������Կÿ��ѭ����λ�Ĵ���
	public static int[] decryMove={
		0,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1
	};
	
	//��Կ��56���48
	public static int[] PC2={
		14,17,11,24,1,5,  
		3,28,15,6,21,10,  
		23,19,12,4,26,8,  
		16,7,27,20,13,2,  
		41,52,31,37,47,55,  
		30,40,51,45,33,48,  
		44,49,39,56,34,53,  
		46,42,50,36,29,32
	};
	//E����չ,��32λ��չ��64λ
	public static int[] E={
		32,1,2,3,4,5,  
		4,5,6,7,8,9,  
		8,9,10,11,12,13,  
		12,13,14,15,16,17,  
		16,17,18,19,20,21,  
		20,21,22,23,24,25,  
		24,25,26,27,28,29,  
		28,29,30,31,32,1 
	};
	
	//S��ѹ����6��4��
	public static int[][]S1={
		{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},  
		{0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},  
		{4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},  
		{15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13} 
	};
	
	public static int[][]S2={
		{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},  
		{3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},  
		{0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},  
		{13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}  
	};
	
	public static int[][] S3={
		{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},  
		{13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},  
		{13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},  
		{1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}
	};
	
	public static int[][] S4={
		{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},  
		{13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},  
		{10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},  
		{3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14} 
	};
	public static int[][] S5={
		{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},  
		{14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},  
		{4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},  
		{11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}  
	};
	public static int[][] S6={
		{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},  
		{10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},  
		{9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},  
		{4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}  
	};
	public static int[][] S7={
		{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},  
		{13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},  
		{1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},  
		{6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}  
	};
	public static int[][] S8={
		{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},  
		{1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},  
		{7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},  
		{2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}  
	};
	
	//��S�еĽ�������û�
	public static int[] P={
		16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,  
		2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25,  
	};
	//����IP�е�������
	public static int[] invIP={
		40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,  
		38,6,46,14,54,22,62,30,37,5,45,13,53,21,61,29,  
		36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,  
		34,2,42,10,50,18,58,26,33,1,41, 9,49,17,57,25, 
	};
	

	
	//���ܲ���
	private byte[] encry(byte[] plaintext, byte[] key)
	{
//		System.out.println("��ʼ���ܹ��̣�");
		byte[] currentData = new byte[64];
		byte[] currentKey = new byte[56];
		//���Ƚ���IP�в���
		currentData = getIPText(plaintext);
		//����Կ���ٵ�56λ
		currentKey = getPC1Text(key);
		//�õ���ǰ����������
		byte[] currentLeftData = getLeftData(currentData);
		byte[] currentRightData = getRightData(currentData);
		//�õ���ǰ��������Կ
		byte[] currentLeftKey = getLeftData(currentKey);
		byte[] currentRightKey = getRightData(currentKey);
		//���е�������16�ּ���
		for (int i = 0; i < 16; i++) {
			//TODO ����16�ֵ�������
			//����ѭ����Ϊ����������Ϊ��
//			System.out.printf("��ʼ��%d�ּ��ܣ�\n", i+1);
//			System.out.println("�������룺");
//			for (int j = 0; j < currentLeftData.length; j++) {
//				System.out.printf(" %d", currentLeftData[j]);
//			}
//			for (int j = 0; j < currentRightData.length; j++) {
//				System.out.printf(" %d", currentRightData[j]);
//			}
//			System.out.println();
			currentLeftKey = shift(currentLeftKey,encryMove[i] , 1);
			currentRightKey = shift(currentRightKey,encryMove[i] , 1);
			//���������ݽ��кϲ�
			byte[] usingKey = mergeKey(currentLeftKey, currentRightKey);
//			System.out.println("������Կ��");
//			for (int j = 0; j < usingKey.length; j++) {
//				System.out.printf(" %d", usingKey[j]);
//			}
//			System.out.println();
			//��һ�ε�leftֱ��Ϊǰһ�ε�right
			byte[] nextLeftData = currentRightData;
			//��32λ������չ��48λ
			byte[] expandData = expand(currentRightData);
			//����Կ�������
			expandData = XOR(expandData, usingKey);
			//S��ѡ��
			byte[] tmpData = select(expandData);
			//P�û�
			tmpData = p_change(tmpData);
			//���������õ������Ҳ�
			byte[] nextRightData = XOR(currentLeftData,tmpData);
			//���µ���һ��
			currentLeftData = nextLeftData;
			currentRightData = nextRightData;
//			System.out.println("���������");
//			for (int j = 0; j < currentLeftData.length; j++) {
//				System.out.printf(" %d", currentLeftData[j]);
//			}
//			for (int j = 0; j < currentRightData.length; j++) {
//				System.out.printf(" %d", currentRightData[j]);
//			}
//			System.out.println();
		}
		//�����Ҳ��ֺϲ�
		currentData = mergeData(currentLeftData, currentRightData);
		//����IP�˵�������
		currentData = invIPChange(currentData);
		return currentData;
	}
	
	//���ܲ���
	private byte[] decry(byte[] encrytext, byte[] key)
	{
//		byte[] currentData = new byte[64];
//		//TODO ��ɽ��ܲ���
//		return currentData;
		byte[] currentData = new byte[64];
		byte[] currentKey = new byte[56];
		//���Ƚ���IP�в���
		currentData = getIPText(encrytext);
		//����Կ���ٵ�56λ
		currentKey = getPC1Text(key);
		//�õ���ǰ����������
		byte[] currentLeftData = getLeftData(currentData);
		byte[] currentRightData = getRightData(currentData);
		//�õ���ǰ��������Կ
		byte[] currentLeftKey = getLeftData(currentKey);
		byte[] currentRightKey = getRightData(currentKey);
		//���е�������16�ּ���
		for (int i = 0; i < 16; i++) {
			//TODO ����16�ֵ�������
			//����ѭ����Ϊ����������Ϊ��
//			System.out.printf("��ʼ��%d�ֽ��ܣ�\n", i+1);
//			System.out.println("�������룺");
//			for (int j = 0; j < currentLeftData.length; j++) {
//				System.out.printf(" %d", currentLeftData[j]);
//			}
//			for (int j = 0; j < currentRightData.length; j++) {
//				System.out.printf(" %d", currentRightData[j]);
//			}
//			System.out.println();
			currentLeftKey = shift(currentLeftKey,decryMove[i] , -1);
			currentRightKey = shift(currentRightKey,decryMove[i] , -1);
			//���������ݽ��кϲ�
			byte[] usingKey = mergeKey(currentLeftKey, currentRightKey);
			//��һ�ε�leftֱ��Ϊǰһ�ε�right
			byte[] nextRightData = currentLeftData;
//			System.out.println("������Կ��");
//			for (int j = 0; j < usingKey.length; j++) {
//				System.out.printf(" %d", usingKey[j]);
//			}
//			System.out.println();
			//��32λ������չ��48λ
			byte[] expandData = expand(currentLeftData);
			//����Կ�������
			expandData = XOR(expandData, usingKey);
			//S��ѡ��
			byte[] tmpData = select(expandData);
			//P�û�
			tmpData = p_change(tmpData);
			//���������õ������Ҳ�
			byte[] nextLeftData = XOR(currentRightData,tmpData);
			//���µ���һ��
			currentLeftData = nextLeftData;
			currentRightData = nextRightData;
//			System.out.println("���������");
//			for (int j = 0; j < currentLeftData.length; j++) {
//				System.out.printf(" %d", currentLeftData[j]);
//			}
//			for (int j = 0; j < currentRightData.length; j++) {
//				System.out.printf(" %d", currentRightData[j]);
//			}
//			System.out.println();
		}
		//�����Ҳ��ֺϲ�
		currentData = mergeData(currentLeftData, currentRightData);
		//����IP�˵�������
		currentData = invIPChange(currentData);
		return currentData;
	}
	/*
	 * ��ȡ���ľ���IP��֮������ݣ� 64��byte����64byte��
	 */
	private byte[] getIPText(byte[] plaintext)
	{
		int dataLength = 64;
		byte[] dataBytes=new byte[dataLength];
		for (int i = 0; i < dataLength; i++) {
			//IP��ֵλ1~64,����Ҫ��һ
			dataBytes[i] = plaintext[IP[i]-1];
		}
		return dataBytes;
	}
	/*
	 * ��Կ����PC1�У�64����56��
	 */
	private byte[] getPC1Text(byte[] key)
	{
		int keyLength = 56;
		byte[] keyBytes = new byte[keyLength];
		for (int i = 0; i < keyLength; i++) {
			keyBytes[i] = key[PC1[i]-1];
		}
		return keyBytes;
	}
	
	/*
	 * �������ݵ�ǰ�벿��
	 */
	private byte[] getLeftData(byte[] data)
	{
		byte[] leftData = new byte[data.length/2];
		for (int i = 0; i < data.length/2; i++) {
			leftData[i] = data[i];
		}
		return leftData;
	}
	
	/*
	 * �������ݵĺ�벿��
	 */
	private byte[] getRightData(byte[] data)
	{
		byte[] rightData = new byte[data.length/2];
		for (int i = 0; i < data.length/2; i++) {
			rightData[i] = data[i+data.length/2];
		}
		return rightData;
	}
	
	/*
	 * ������Կ�ֻ�
	 * primaryData: ԭʼ����
	 * step:�ƶ��Ĳ���
	 * direction:�ƶ��ķ���,1Ϊ��-1Ϊ��
	 */
	private byte[] shift(byte[] primaryData, int step, int direction)
	{
		byte[] result = new byte[primaryData.length];
		if(direction>0)
		{
			//ѭ������
			for (int i = 0; i < result.length; i++) {
				result[i] = primaryData[(i-step+primaryData.length)%primaryData.length];
			}
		}
		else {
			//ѭ������
			for (int i = 0; i < result.length; i++) {
				result[i] = primaryData[(i+step)%primaryData.length];
			}
		}
		return result;
		
	}
	
	/*
	 * ���кϲ���Կ��ѡȡ���е�48λ
	 */
	private byte[] mergeKey(byte[] leftKey, byte[] rightKey)
	{
		//����ϲ����
		byte[] mergingKey = new byte[56];
		//���кϲ�
		for (int i = 0; i < leftKey.length; i++) {
			mergingKey[i] = leftKey[i];
		}
		for (int i = 0; i < rightKey.length; i++) {
			mergingKey[i+28] = rightKey[i];
		}
		//����Ժϲ�����ĳ�ȡ������48λ
		byte[] usingKey = new byte[48];
		//���г�ȡ,ʹ��PC2
		for (int i = 0; i < usingKey.length; i++) {
			usingKey[i] = mergingKey[PC2[i]-1];
		}
		return usingKey;
	}
	
	/*
	 * ʹ��E�ж��ұ����ݽ�����չ
	 */
	private byte[] expand(byte[] primaryData)
	{
		byte[] expanded = new byte[48];
		for (int i = 0; i < expanded.length; i++) {
			expanded[i] = primaryData[E[i]-1];
		}
		return expanded;
	}
	
	/*
	 * ����������
	 */
	private byte[] XOR(byte[] data1, byte[] data2)
	{
		byte[] result = new byte[data1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (data1[i]^data2[i]);
		}
		return result;
	}
	/*
	 * ����S�е�ѡ��
	 * ����Ϊ48λ
	 * ���Ϊ32λ
	 */
	private byte[] select(byte[] data)
	{
		byte[] result = new byte[32];
		//ǰ6��ʹ�õ�һ��ɳ��,��ΪS1 S2�ȵ����֣���֪��Ӧ����ôʹ��ѭ��
		int order =0;
		int row = data[0+order*6]*2+data[5+order*6];
		int col = data[1+order*6]*8+data[2+order*6]*4+data[3+order*6]*2+data[4+order*6];
		int tmp = S1[row][col];
		for (int i = 0; i < 4; i++) {
			result[i+order*4] = (byte) (tmp&1);
			tmp = tmp>>1;
		}
		order =1;
		row = data[0+order*6]*2+data[5+order*6];
		col = data[1+order*6]*8+data[2+order*6]*4+data[3+order*6]*2+data[4+order*6];
		tmp = S2[row][col];
		for (int i = 0; i < 4; i++) {
			result[i+order*4] = (byte) (tmp&1);
			tmp = tmp>>1;
		}
		order =2;
		row = data[0+order*6]*2+data[5+order*6];
		col = data[1+order*6]*8+data[2+order*6]*4+data[3+order*6]*2+data[4+order*6];
		tmp = S3[row][col];
		for (int i = 0; i < 4; i++) {
			result[i+order*4] = (byte) (tmp&1);
			tmp = tmp>>1;
		}
		order =3;
		row = data[0+order*6]*2+data[5+order*6];
		col = data[1+order*6]*8+data[2+order*6]*4+data[3+order*6]*2+data[4+order*6];
		tmp = S4[row][col];
		for (int i = 0; i < 4; i++) {
			result[i+order*4] = (byte) (tmp&1);
			tmp = tmp>>1;
		}
		order =4;
		row = data[0+order*6]*2+data[5+order*6];
		col = data[1+order*6]*8+data[2+order*6]*4+data[3+order*6]*2+data[4+order*6];
		tmp = S5[row][col];
		for (int i = 0; i < 4; i++) {
			result[i+order*4] = (byte) (tmp&1);
			tmp = tmp>>1;
		}
		order =5;
		row = data[0+order*6]*2+data[5+order*6];
		col = data[1+order*6]*8+data[2+order*6]*4+data[3+order*6]*2+data[4+order*6];
		tmp = S6[row][col];
		for (int i = 0; i < 4; i++) {
			result[i+order*4] = (byte) (tmp&1);
			tmp = tmp>>1;
		}
		order =6;
		row = data[0+order*6]*2+data[5+order*6];
		col = data[1+order*6]*8+data[2+order*6]*4+data[3+order*6]*2+data[4+order*6];
		tmp = S7[row][col];
		for (int i = 0; i < 4; i++) {
			result[i+order*4] = (byte) (tmp&1);
			tmp = tmp>>1;
		}
		order =7;
		row = data[0+order*6]*2+data[5+order*6];
		col = data[1+order*6]*8+data[2+order*6]*4+data[3+order*6]*2+data[4+order*6];
		tmp = S8[row][col];
		for (int i = 0; i < 4; i++) {
			result[i+order*4] = (byte) (tmp&1);
			tmp = tmp>>1;
		}
		return result;
	}
	
	/*
	 * ����P���û�
	 * 32����32��
	 */
	private byte[] p_change(byte[] data)
	{
		byte[] result = new byte[data.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = data[P[i]-1];
		}
		return result;
	}
	/*
	 * �������Ҷ����ݺϲ�
	 */
	private byte[] mergeData(byte[]data1, byte[]data2)
	{
		byte[] result = new byte[data1.length+data2.length];
		for (int i = 0; i < data1.length; i++) {
			result[i] = data1[i];
		}
		for (int i = 0; i < data2.length; i++) {
			result[i+data1.length] = data2[i];
		}
		return result;
	}
	/*
	 * ����IP�˵�������
	 */
	private byte[] invIPChange(byte[] data)
	{
		byte[] result = new byte[data.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = data[invIP[i]-1];
		}
		return result;
	}
}
