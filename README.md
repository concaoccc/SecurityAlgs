# SecurityAlgs
本工程是东南大学网络空间学院黄杰老师课堂大作业，需要完成对称加密算法DES、AES，Hash算法SHA-1和MD5，非对称加密RSA,具体要求见[homework.pdf](https://github.com/deadfishlovecat/SecurityAlgs/blob/master/homework%20requirement/homework.pdf).

# 工程目录
### SecurityAlgs/src/SymmetricEncry(对称加密)
- DESCore.java 
  DES算法的核心文件，进行DES一次加密解密操作，实现参考的博客包括[1](https://www.cnblogs.com/songwenlong/p/5944139.html)和[2](https://blog.csdn.net/qq_27570955/article/details/52442092)
  ```java
  //加密调用方法
  //data和key为byte[64]
  DESCore desEncryTest = new DESCore(data,key,"encry");
	byte[] encryresult = desEncryTest.getResult();
  //解密调用方法
  DESCore desDecryTest = new DESCore(encryresult, key, "decry"); 
	byte[] decryResult = desDecryTest.getResult();
  ```
- DESCoreTest.java
  对DESCore.java进行测试，随机生成一组key和data,观察加解密后数据和原始数据是否相同
- DES.java
对DESCore进行包装
```java
//加密方法调用
DES desTest = new DES();
//64位的数据
byte[] data = new byte[8];
//64位的数据
byte[] key = new byte[8];
```
- AES.java
### SecurityAlgs/src/Asymmetricencry(非对称加密)
- RSA.java
### SecurityAlgs/src/Hash(Hash算法)
- MD5.java
- SHA.java

### Test(对加密代码进行测试)
- DESCoreTest.java
对DESCore.java进行测试，并将测试结果与调用java自带安全库的方法进行比较验证正确性。
