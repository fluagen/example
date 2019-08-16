# 1. Byte数组与十六进制字符串互转

Java中byte用二进制表示占用8位，而我们知道16进制的每个字符需要用4位二进制位来表示。
所以我们就可以把每个byte转换成两个相应的16进制字符，即把byte的高4位和低4位分别转换成相应的16进制字符H和L，并组合起来得到byte转换到16进制字符串的结果`new String(H) + new String(L)`。
 
同理，相反的转换也是将两个16进制字符转换成一个byte，原理同上。
 
根据以上原理，我们就可以将byte[] 数组转换为16进制字符串了，当然也可以将16进制字符串转换为byte[]数组了。  

# 2. BASE64加密解密

Base64是网络上最常见的用于传输8Bit字节代码的编码方式之一，大家可以查看RFC2045～RFC2049，上面有MIME的详细规范。Base64编码可用于在HTTP环境下传递较长的标识信息。例如，在Java Persistence系统Hibernate中，就采用了Base64来将一个较长的唯一标识符（一般为128-bit的UUID）编码为一个字符串，用作HTTP表单和HTTP GET URL中的参数。在其他应用程序中，也常常需要把二进制数据编码为适合放在URL（包括隐藏表单域）中的形式。此时，采用Base64编码不仅比较简短，同时也具有不可读性，即所编码的数据不会被人用肉眼所直接看到。

RFC2045还规定每行位76个字符，每行末尾需添加一个回车换行符，即便是最后一行不够76个字符，也要加换行符。

## 2.1 实现原理

Base64实际上是对二进制码做分组转换操作
1.每3个8位二进制码位一组，转换为4个6位二进制码为一组（不足6位时地位补0）。3个8位二进制码和4个6位二进制码长度都是24位。
2.对获得的4个6位二进制码补位，每个6位二进制码添加两位高位0，组成4个8位二进制码。
3.将获得的4个8位二进制码转换为4个十进制码。
4.将获得的十进制码转换为Base64字符表中对应的字符。

示例：

**1. 字符串“A”，进行Base64编码**

```
字符                A
ASCII码           65
二进制码          01000001
 
4个6位二进制码 010000          010000
4个8位二进制码 00010000       00010000
十进制码          16                 16
字符表映射码     Q                  Q                   =                 =
```
**字符串“A”经过Base64编码后得到字符串“QQ==”。**

结果出现了两个等号。很显然，当原文的二进制码长度不足24位，最终转换为十进制时也不足4项，这时就需要用等号补位。
将Base64编码后的字符串最多会有2个等号，这时因为：
余数 = 原文字节数 MOD 3。

**2. 字符串“密”，进行Base64编码**
```
对其使用UTF-8编码等到Byte数组{-27，-81，-122}，
 
字符                密
UTF-8编码        -27                -81               -122
二进制码          11100101       10101111       10000110
4个6位二进制码 111001          011010           111110          000110
4个8位二进制码 00111001       00011010       00111110       00000110
十进制码          57                 26                  62                6
字符表映射码     5                   a                   +                 G
```
**字符串“密”经过Base64编码后得到字符串“5a+G”。**


## 2.2 对照表

```
索引	对应字符	索引	对应字符	索引	对应字符	索引	对应字符
0	A	17	R	34	i	51	z
1	B	18	S	35	j	52	0
2	C	19	T	36	k	53	1
3	D	20	U	37	l	54	2
4	E	21	V	38	m	55	3
5	F	22	W	39	n	56	4
6	G	23	X	40	o	57	5
7	H	24	Y	41	p	58	6
8	I	25	Z	42	q	59	7
9	J	26	a	43	r	60	8
10	K	27	b	44	s	61	9
11	L	28	c	45	t	62	+
12	M	29	d	46	u	63	/
13	N	30	e	47	v	 	 
14	O	31	f	48	w	 	 
15	P	32	g	49	x	 	 
16	Q	33	h	50	y	 

```

# 3. 消息摘要

消息摘要（Message Digest）又称为数字摘要(Digital Digest)。
它是一个唯一对应一个消息或文本的固定长度的值，它由一个单向Hash加密函数对消息进行作用而产生。如果消息在途中改变了，则接收者通过对收到消息的新产生的摘要与原摘要比较，就可知道消息是否被改变了。因此消息摘要保证了消息的完整性。消息摘要采用单向Hash 函数将需加密 的明文"摘要"成一串128bit的密文，这一串密文亦称为数字指纹(Finger Print)，它有固定的长度，且不同的明文摘要成密文，其结果总是不同的，而同样的明文其摘要必定一致 。这样这串摘要便可成为验证明文是否是"真身"的"指纹"了。 

HASH函数的抗冲突性使得如果一段明文稍有变化，哪怕只更改该段落的一个字母，通过哈希算法作用后都将产生不同的值。而HASH算法的单向性使得要找到到哈希值相同的两个不 同的输入消息，在计算上是不可能的。所以数据的哈希值，即消息摘要，可以检验数据的完整性。哈希函数的这种对不同的输入能够生成不同的值的特性使得无法找到两个具有相同哈希值的输入。因此，如果两个文档经哈希转换后成为相同的值，就可以肯定它们是同一文档。 所以，当希望有效地比较两个数据块时，就可以比较它们的哈希值。例如，可以通过比较邮件发送前和发送后的哈希值来验证该邮件在传递时是否修改。 

## 3.1 消息摘要算法

消息摘要算法的主要特征是加密过程不需要密钥，并且经过加密的数据无法被解密，只有输入相同的明文数据经过相同的消息摘要算法才能得到相同的密文。消息摘要算法不存在 密钥的管理与分发问题，适合于分布式网络相同上使用。由于其加密计算的工作量相当可观，所以以前的这种算法通常只用于数据量有限的情况下的加密，例如计算机的口令就是 用不可逆加密算法加密的。近年来，随着计算机相同性能的飞速改善，加密速度不再成为限制这种加密技术发展的桎梏，因而消息摘要算法应用的领域不断增加。

**消息摘要算法的特点**

1. 无论输入的消息有多长，计算出来的消息摘要的长度总是固定的。
2. 消息摘要看起来是“随机的”。这些比特看上去是胡乱的杂凑在一起的。
3. 一般地，只要输入的消息不同，对其进行摘要以后产生的摘要消息也必不相同；但相同的输入必会产生相同的输出。
4. 消息摘要函数是无陷门的单向函数，即只能进行正向的信息摘要，而无法从摘要中恢复出任何的消息，甚至根本就找不到任何与原信息相关的信息。
5. 好的摘要算法，无法找到两条消息，是它们的摘要相同。


**现有的消息摘要算法**

消息摘要算法包含MD、SHA和MAC三大系列，常用于验证数据的完整性，是数据签名算法的核心算法。 

MAC与MD和SHA不同，MAC是含有密钥的散列函数算法，我们也常把MAC称为HMAC。

**JDK对消息摘要算法的支持**

JDK6支持

MD2/MD5/SHA/SHA256/SHA384/SHA512/HmacMD5/HmacSHA1/HmacSHA256/HmacSHA384/HmacSHA512

# 4. 对称加密算法DES
数据加密算法（Data Encryption Algorithm，DEA）是一种对称加密算法，很可能是使用最广泛的密钥系统，特别是在保护金融数据的安全中，最初开发的DEA是嵌入硬件中的。通常，自动取款机（Automated Teller Machine，ATM）都使用DEA。它出自IBM的研究工作，IBM也曾对它拥有几年的专利权，但是在1983年已到期后，处于公有范围中，允许在特定条件下可以免除专利使用费而使用。1977年被美国政府正式采纳。
 
1998年后实用化DES破译机的出现彻底宣告DES算法已不具备安全性，1999年NIST颁布新标准，规定DES算法只能用于遗留加密系统，但不限制使用DESede算法。当今DES算法正是推出历史舞台，AES算法称为他的替代者。(详见： Java 加密解密之对称加密算法AES )
 
## 4.1 加密原理

DES 使用一个 56 位的密钥以及附加的 8 位奇偶校验位，产生最大 64 位的分组大小。这是一个迭代的分组密码，使用称为 Feistel 的技术，其中将加密的文本块分成两半。使用子密钥对其中一半应用循环功能，然后将输出与另一半进行“异或”运算；接着交换这两半，这一过程会继续下去，但最后一个循环不交换。DES 使用 16 个循环，使用异或，置换，代换，移位操作四种基本运算。
 
## 4.2 JDK对DES算法的支持

密钥长度：56位 (其实是64位，还需要一个8 位奇偶校验位)，换算成字节就是8个字节

工作模式：ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128

填充方式：Nopadding/PKCS5Padding/ISO10126Padding/

# 5. 对称加密算法AES
 
密码学中的高级加密标准（Advanced Encryption Standard，AES），又称Rijndael加密法，是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用。经过五年的甄选流程，高级加密标准由美国国家标准与技术研究院 （NIST）于2001年11月26日发布于FIPS PUB 197，并在2002年5月26日成为有效的标准。2006年，高级加密标准已然成为对称密钥加密中最流行的算法之一。该算法为比利时密码学家Joan Daemen和Vincent Rijmen所设计，结合两位作者的名字，以Rijndael之命名之，投稿高级加密标准的甄选流程。（Rijdael的发音近于 "Rhinedoll"。）
 
AES是美国国家标准技术研究所NIST旨在取代DES的21世纪的加密标准。
AES的基本要求是，采用对称分组密码体制，密钥长度的最少支持为128、192、256，分组长度128位，算法应易于各种硬件和软件实现。1998年NIST开始AES第一轮分析、测试和征集，共产生了15个候选算法。1999年3月完成了第二轮AES2的分析、测试。2000年10月2日美国政府正式宣布选中比利时密码学家Joan Daemen 和 Vincent Rijmen 提出的一种密码算法RIJNDAEL 作为 AES. 　　在应用方面，尽管DES在安全上是脆弱的，但由于快速DES芯片的大量生产，使得DES仍能暂时继续使用，为提高安全强度，通常使用独立密钥的三级DES。但是DES迟早要被AES代替。流密码体制较之分组密码在理论上成熟且安全，但未被列入下一代加密标准。 　　

AES加密数据块和密钥长度可以是128比特、192比特、256比特中的任意一个。

AES加密有很多轮的重复和变换。大致步骤如下：

1、密钥扩展（KeyExpansion）;

2、初始轮（Initial Round）;

3、重复轮（Rounds），每一轮又包括：SubBytes、ShiftRows、MixColumns、AddRoundKey;

4、最终轮（Final Round），最终轮没有MixColumns。
 
## 5.1 JDK对AESede算法的支持

密钥长度：128位、192位、256位

工作模式：ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128

填充方式：Nopadding/PKCS5Padding/ISO10126Padding/

# 非对称加密RSA

非对称加密，指的是加、解密使用不同的密钥，一把作为公开的公钥，另一把作为私钥。公钥加密的信息，只有私钥才能解密。反之，私钥加密的信息，只有公钥才能解密。

JAVA6开始支持RSA算法，RSA算法可以用于数据加密和数字签名，相对于DES/AES等对称加密算法，他的速度要慢的多。

## 优劣

**优势：**

安全性更高，公钥是公开的，私钥是自己保存的，不需要将私钥给别人。

**劣势：**

加密和解密花费时间长、速度慢，只适合对少量数据进行加密。 

## 特点

**1.密钥长度**

目前主流密钥长度至少都是1024bit以上，低于1024bit的密钥已经不建议使用（安全问题）。

主流可选值：1024、2048、3072、4096...

**2. 密文的长度等于密钥长度**

生成密文的长度等于密钥长度。密钥长度越大，生成密文的长度也就越大，加密的速度也就越慢，而密文也就越难被破解掉。著名的"安全和效率总是一把双刃剑"定律，在这里展现的淋漓尽致。我们必须通过定义密钥的长度在"安全"和"加解密效率"之间做出一个平衡的选择。

**3. 生成密文的长度和明文长度无关，但明文长度不能超过密钥长度**

不管明文长度是多少，RSA 生成的密文长度总是固定的，但是明文长度不能超过密钥长度。

Java 默认的 RSA 加密实现不允许明文长度超过密钥长度减去 11 (单位是字节，也就是 byte)

>也就是说，如果我们定义的密钥 (我们可以通过 java.security.KeyPairGenerator.initialize(int keysize) 来定义密钥长度)长度为 1024(单位是位，也就是 bit)，生成的密钥长度就是 1024位 / 8位/字节 = 128字节，
>那么我们需要加密的明文长度不能超过 128字节 - 11 字节 = 117字节。
>也就是说，我们最大能将 117 字节长度的明文进行加密，否则会出问题(抛诸如 javax.crypto.IllegalBlockSizeException: Data must not be longer than 53 bytes 的异常)。
>BC 提供的加密算法能够支持到的 RSA 明文长度最长为密钥长度。

**4. Java 默认的 RSA 实现 "RSA/None/PKCS1Padding" 要求最小密钥长度为 512 位**

Java 默认的 RSA 实现 "RSA/None/PKCS1Padding" 要求最小密钥长度为 512 位(否则会报 java.security.InvalidParameterException: RSA keys must be at least 512 bits long 异常)，也就是说生成的密钥、密文长度最小为 64 个字节。

可以通过调整算法提供者来减小密文长度：

```java
Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
keyGen.initialize(128);
```

这样得到密文长度为 128 位(16 个字节)，但是密钥小于1024bit是不安全的。

**5. 每次生成的密文都不一致**

一个优秀的加密必须每次生成的密文都不一致，即使每次你的明文一样、使用同一个公钥，因为这样才能把明文信息更安全地隐藏起来。

Java 默认的 RSA 实现是 `RSA/None/PKCS1Padding`

使用`Cipher cipher = Cipher.getInstance("RSA")`生成密文，密文总是不一致

为什么 Java 默认的 RSA 实现每次生成的密文都不一致呢，即使每次使用同一个明文、同一个公钥？

这是因为 RSA 的 `PKCS1Padding` 方案在加密前对明文信息进行了随机数填充。


Bouncy Castle 的默认 RSA 实现是 `RSA/None/NoPadding`。

你可以使用BC的实现让同一个明文、同一个公钥每次生成同一个密文，但是你必须意识到你这么做付出的代价是什么。
比如，你可能使用 RSA 来加密传输，但是由于你的同一明文每次生成的同一密文，攻击者能够据此识别到同一个信息都是何时被发送。

```java
Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
final Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
```

## 注意事项

**1. 加密的系统不要具备解密的功能，否则 RSA 可能不太合适**

公钥加密，私钥解密。加密的系统和解密的系统分开部署，加密的系统不应该同时具备解密的功能，这样即使黑客攻破了加密系统，他拿到的也只是一堆无法破解的密文数据。否则的话，你就要考虑你的场景是否有必要用 RSA 了。

**2. `byte[].toString()` 返回的实际上是内存地址，不是将数组的实际内容转换为 `String`**

**警惕 toString 陷阱：Java 中数组的 toString() 方法返回的并非数组内容，它返回的实际上是数组存储元素的类型以及数组在内存的位置的一个标识。**

大部分人跌入这个误区而不自知，包括一些写了多年 Java 的老鸟。比如这篇博客 [《How To Convert Byte[] Array To String In Java》](https://www.mkyong.com/java/how-do-convert-byte-array-to-string-in-java/)中的代码

```java

public class TestByte
{    
	public static void main(String[] argv) {
 
		    String example = "This is an example";
		    byte[] bytes = example.getBytes();
 
		    System.out.println("Text : " + example);
		    System.out.println("Text [Byte Format] : " + bytes);
		    System.out.println("Text [Byte Format] : " + bytes.toString());
 
		    String s = new String(bytes);
		    System.out.println("Text Decryted : " + s);
 
 
	}
}
```

```
输出：
Text : This is an example
Text [Byte Format] : [B@187aeca
Text [Byte Format] : [B@187aeca
Text Decryted : This is an example
```

以及这篇博客[《RSA Encryption Example》](https://javadigest.wordpress.com/2012/08/26/rsa-encryption-example/)中的代码
```java
final byte[] cipherText = encrypt(originalText, publicKey);
System.out.println("Encrypted: " +cipherText.toString());
```

```
输出：
[B@4c3a8ea3
```

这些输出其实都是字节数组在内存的位置的一个标识，而不是作者所认为的字节数组转换成的字符串内容。
如果我们对密钥以 `byte[].toString() `进行持久化存储或者和其他一些字符串打 json 传输，那么密钥的解密者得到的将只是一串毫无意义的字符，当他解码的时候很可能会遇到 `"javax.crypto.BadPaddingException"` 异常。

**3. 字符串用以保存文本信息，字节数组用以保存二进制数据**

`java.lang.String` 保存明文，`byte数组`保存二进制密文，在 `java.lang.String` 和 `byte[]` 之间不应该具备互相转换。

如果你确实必须得使用 `java.lang.String` 来持有这些二进制数据的话，最安全的方式是使用 Base64(推荐 Apache 的 commons-codec 库的 org.apache.commons.codec.binary.Base64)

```java
      // use String to hold cipher binary data
      Base64 base64 = new Base64(); 
      String cipherTextBase64 = base64.encodeToString(cipherText);
      
      // get cipher binary data back from String
      byte[] cipherTextArray = base64.decode(cipherTextBase64);
```

**4. Cipher 是有状态的，而且是线程不安全的**

`javax.crypto.Cipher` 是有状态的，不要把 Cipher 当做一个静态变量，除非你的程序是单线程的，也就是说你能够保证同一时刻只有一个线程在调用 Cipher。
否则你可能会遇到 `java.lang.ArrayIndexOutOfBoundsException: too much data for RSA block` 异常。

遇见这个异常，你需要先确定你给 Cipher 加密的明文(或者需要解密的密文)是否过长；排除掉明文(或者密文)过长的情况，你需要考虑是不是你的 Cipher 线程不安全了。

