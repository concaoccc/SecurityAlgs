# 项目说明
本工程是东南大学网络空间学院黄杰老师课堂大作业，需要完成对称加密算法DES、AES，Hash算法SHA-1和MD5，非对称加密RSA,具体要求见[homework.pdf](https://github.com/deadfishlovecat/SecurityAlgs/blob/master/homework%20requirement/homework.pdf).

# 工程目录
```
.
+-- README.md                                                             % 当前文档
+-- LICENCE                                                               
+-- SecurityAlgs                         
|   +-- .settings
|   +-- .classpath
|   +-- .project
|   +-- src
|       +-- AlgsComb                                                     %算法组合，完成作业框图
|            +-- AlgsComb.java
|       +-- Asymmetricencry                                               % 非对称加密
|            +-- RSA.java                
|       +-- Hash                                                          % 摘要
|            +-- MD5.java                 
|            +-- SHA.java 
|       +-- Main                                                          %入口函数
|           +-- Main.java     
|       +-- SymmetricEncry                                                % 对称加密
|           +-- AES.java                  
|           +-- DES.java                  
|           +-- DESCore.java              
|           +-- util.java
|       +-- Test                                                          % 测试
|           +-- AESTest.java
|           +-- DESCoreTest.java
|           +-- DESTest.java
|           +-- MD5Test.java
|           +-- SHATest.java
|           +-- RSATest.java                                           
|           +-- AlgsComb.java                                            %算法框图
|           +-- MainUI.java                                                 %图形化界面
|           +-- Main.java                                                      %入口函数
|
|           +-- AlgsCombTest.java
|       +-- UI                                                           % swing实现的界面
|           +-- MainUI.java
+-- release                                                             %最后的发行版文件
|   +-- *.jar
+-- homework requirement
|   +-- homework.pdf                                                      % 作业要求
```
# 核心代码说明
<<<<<<< HEAD
### Main(入口函数)
- Main.java

工程的入口函数，调用即可生成最后界面

### UI(图形化界面)
- MainUI.java

### AlgsComb(算法框图的实现)
- AlgsComb
### AlgsComb(算法组合，实现框图)
- AlgsComb.java
  利用密码学算法接口实现整体的框图流程
### SymmetricEncry(对称加密)
- DESCore.java 
  
  DES算法的核心文件，进行DES一次加密解密操作，实现参考的博客包括[1](https://www.cnblogs.com/songwenlong/p/5944139.html)和[2](https://blog.csdn.net/qq_27570955/article/details/52442092)
- DES.java

对DESCore进行包装使用,密钥、明文和密文都是8*8bit
- util.java

保存AES中的各种盒
- AES.java

AES的核心加解密函数，密钥、明文和密文都是16*8bit, 参考的[代码](https://github.com/VinayakShukl/AES-256)
### Asymmetricencry(非对称加密)
- RSA.java

实现RSA中加解密函数，使用BigInteger库，参考的[代码](https://introcs.cs.princeton.edu/java/99crypto/RSA.java.html)
### Hash(Hash算法)
- MD5.java

MD5算法实现，对于任意长度的字符串生成16*8bit的摘要，参考[代码](https://baike.baidu.com/item/MD5%E5%8A%A0%E5%AF%86/5706230)
- SHA.java

SHA-1算法实现，对于任意长度的string生成长度位20*8bit的摘要
### Main(入口)
- Main.java
整体项目的接口

### Test(对加密代码进行测试)
- DESCoreTest.java

对DESCore.java进行测试
- DESTest.java

随机生成一组key和data,观察加解密后数据和原始数据是否相同
- AESTest.java

随机生成一组key和data,观察加解密后数据和原始数据是否相同
- MD5Test.java

任意生成一组数据，观察自己代码和java自带库中结果是否相同
- SHATest.java

任意生成一组数据，观察自己代码和java自带库中生成的摘要结果是否相同
- RSATest.java

任意生成一组数据，利用RSA进行加密和解密操作

-
=======
### UI(界面)
- MainUI.java
整体的界面
# 密码学算法API
- DES
```java
DES desTest = new DES();
//需要加密数据
byte[] data = new byte[8];
//密钥
byte[] key = new byte[8];
//加密
byte[] encrytext = desTest.encry(data, key);
//解密
byte[] result = desTest.decry(encrytext, key);
```
- AES
```java
//密钥
byte[] key = new byte[16];
//原始数据
byte[] data = new byte[16];
AES test = new AES(key);
//加密
byte[] entryText = test.encrypt(data);
//解密
byte[] result = test.decrypt(entryText);
```
- RSA
```java
//生成N位的随机数
int N = 500;
RSA key = new RSA(N);
//数据
byte[] data = new byte[40];
//加密
byte[] encrypt = key.encrypt(data);
//解密		      
byte[] decrypt = key.decrypt(encrypt);
```
- MD5
```java
//随机生成的数据
byte[] data = new byte[160];
StringBuilder dataChangeBuilder = new StringBuilder() ;
//转换成字符串
for(byte b : data){
    int bt = b&0xff;
    if(bt<16){
    	dataChangeBuilder.append(0);
    }
    dataChangeBuilder.append(Integer.toHexString(bt));
}
MD5 md5Test = new MD5();
//得到MD5
byte[] result = md5Test.getMD5(dataChangeBuilder.toString().getBytes());
```
- SHA-1 
```JAVA
//本地版本只能对string转化的byte进行计算sha
String data = "hello";
SHA sha = new SHA(data.getBytes());
//得到计算结果
byte[] result = sha.getSHA();
```
# 不足与展望
- sha-1算法没能正确实现，使用的系统提供的
- 界面比较简陋，固定大小，只实现了基础工程
- 类的设计有问题，比如对称密钥以及sha没有统一接口
- 算法组合的时候逻辑混乱，只是实现了基础功能
