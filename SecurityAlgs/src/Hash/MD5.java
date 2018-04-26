package Hash;

public class MD5{
	//�ĸ���ʼ����
	private final int A=0x67452301;
    private final int B=0xefcdab89;
    private final int C=0x98badcfe;
    private final int D=0x10325476;
    //���浱ǰ��A/B/C/D
    private int currentA;
    private int currentB;
    private int currentC;
    private int currentD;
    
    //��ʼ�����캯��
    public MD5() {
		// TODO Auto-generated constructor stub
    	currentA = A;
    	currentB = B;
    	currentC = C;
    	currentD = D;
	}
    /*
     * ԭʼ��K
     */
    private final int K[]={
            0xd76aa478,0xe8c7b756,0x242070db,0xc1bdceee,
            0xf57c0faf,0x4787c62a,0xa8304613,0xfd469501,0x698098d8,
            0x8b44f7af,0xffff5bb1,0x895cd7be,0x6b901122,0xfd987193,
            0xa679438e,0x49b40821,0xf61e2562,0xc040b340,0x265e5a51,
            0xe9b6c7aa,0xd62f105d,0x02441453,0xd8a1e681,0xe7d3fbc8,
            0x21e1cde6,0xc33707d6,0xf4d50d87,0x455a14ed,0xa9e3e905,
            0xfcefa3f8,0x676f02d9,0x8d2a4c8a,0xfffa3942,0x8771f681,
            0x6d9d6122,0xfde5380c,0xa4beea44,0x4bdecfa9,0xf6bb4b60,
            0xbebfbc70,0x289b7ec6,0xeaa127fa,0xd4ef3085,0x04881d05,
            0xd9d4d039,0xe6db99e5,0x1fa27cf8,0xc4ac5665,0xf4292244,
            0x432aff97,0xab9423a7,0xfc93a039,0x655b59c3,0x8f0ccc92,
            0xffeff47d,0x85845dd1,0x6fa87e4f,0xfe2ce6e0,0xa3014314,
            0x4e0811a1,0xf7537e82,0xbd3af235,0x2ad7d2bb,0xeb86d391};
    /*
     *����λ����
     */
     private final int s[]={7,12,17,22,7,12,17,22,7,12,17,22,7,
         12,17,22,5,9,14,20,5,9,14,20,5,9,14,20,5,9,14,20,
         4,11,16,23,4,11,16,23,4,11,16,23,4,11,16,23,6,10,
         15,21,6,10,15,21,6,10,15,21,6,10,15,21};
     
    public byte[] getMD5(byte[] source)
    {
    	int strByte[] = add(new String(source));
    	for(int i=0;i<strByte.length/16;i++){
            int num[]=new int[16];
            for(int j=0;j<16;j++){
                num[j]=strByte[i*16+j];
            }
            MainLoop(num);
         }
    	String resultString = changeHex(currentA)+changeHex(currentB)+changeHex(currentC)+changeHex(currentD);
    	return resultString.getBytes();
    }
    
    //��亯��
    private int[] add(String str){
    	//��512λ��64���ֽ�Ϊһ�飬8�ֽڱ�ʾ���鳤��
        int num=((str.length()+8)/64)+1;
      //64/4=16��������16������
        int strByte[]=new int[num*16];
        for(int i=0;i<num*16;i++){
            strByte[i]=0;
        }
        int i;
        for(i=0;i<str.length();i++){
            strByte[i>>2]|=str.charAt(i)<<((i%4)*8);//һ�������洢�ĸ��ֽڣ�С����
        }
        strByte[i>>2]|=0x80<<((i%4)*8);//β�����1
        //����ԭ����ĳ���
        strByte[num*16-2]=str.length()*8;
        return strByte;
    }
    
    /*
     *�ƶ�һ��λ��
     */
     private int shift(int a,int s){
         return(a<<s)|(a>>>(32-s));//���Ƶ�ʱ�򣬸�λһ��Ҫ���㣬�����ǲ������λ
     }
     /*
      *�������16�����ַ���
      */
      private String changeHex(int a){
          String str="";
          for(int i=0;i<4;i++){
              str+=String.format("%2s", Integer.toHexString(((a>>i*8)%(1<<8))&0xff)).replace(' ', '0');
   
          }
          return str;
      }
     /*
     *��ѭ��
     */
     private void MainLoop(int M[]){
         int F,g;
         int a = currentA;
         int b = currentB;
         int c = currentC;
         int d = currentD;
         //ȫ����Ҫѭ��64��
         for(int i = 0; i < 64; i ++){
             if(i<16){
                 F=(b&c)|((~b)&d);
                 g=i;
             }else if(i<32){
                 F=(d&b)|((~d)&c);
                 g=(5*i+1)%16;
             }else if(i<48){
                 F=b^c^d;
                 g=(3*i+5)%16;
             }else{
                 F=c^(b|(~d));
                 g=(7*i)%16;
             }
             int tmp=d;
             d=c;
             c=b;
             b=b+shift(a+F+K[i]+M[g],s[i]);
             a=tmp;
         }
         currentA += a;
         currentB += b;
         currentC += c;
         currentD += d;
      
     }
    public static void main(String[] args){
    	MD5 md5Test = new MD5();
    	byte[] result = md5Test.getMD5(".".getBytes());
    	System.out.println(new String(result));
    }
}
