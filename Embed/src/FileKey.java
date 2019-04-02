

import java.io.BufferedReader;//缓冲区类
import java.io.File;
import java.io.FileReader; //用于读取字符流
import java.io.IOException;//失败或中断的 I/O 操作生成的异常的通用类
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/*文件加密方法类*/
public class FileKey {
	
	
public String isFileExist(Scanner inputFilePath){//输入文件路径并检测文件是否存在，若存在并返回路径
		
		System.out.println("请输入密码所在的路径：");
		
		String filePath=inputFilePath.next(); //从控制台窗口读取当前行输入的信息
		
		File myfile=new File(filePath);//传递输入的文件的路径
		
		while(!myfile.exists()){
			
			System.out.println("密码文件不存在,请重新输入：");
			
			filePath=inputFilePath.next(); //从控制台窗口读取当前行输入的信息
			
			myfile=new File(filePath);//传递输入的文件的路径
		}
		
		return filePath;//返回文件路径
		
	}
	
	public String ReadKey(String filePath){//从文件读取密码的方法
		String key=null;
        BufferedReader br=null;//指定缓冲区为空	
		try{
			br=new BufferedReader(new FileReader(filePath));//缓冲区存储的是指定文件
			key=br.readLine();//读取文件的一行
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(br!=null){
					br.close();
					br=null;
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return key;
	}

	public byte[] Encrypt(byte[] datasource, String password) { //对数据的加密方法             
		try{ 
        SecureRandom random = new SecureRandom();  //构造一个实现默认随机数算法的安全随机数生成器 (RNG)
        
        DESKeySpec desKey = new DESKeySpec(password.getBytes()); 
        //创建一个密匙工厂，然后用它把DESKeySpec转换成 
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
        SecretKey securekey = keyFactory.generateSecret(desKey);  
        //Cipher对象实际完成加密操作  
        Cipher cipher = Cipher.getInstance("DES");  
        //用密匙初始化Cipher对象  
        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);  
        //现在，获取数据并加密  
        //正式执行加密操作  
        return cipher.doFinal(datasource);  
        }catch(Throwable e){
        	e.printStackTrace();  
        	}
		return null;  
		}
	
	public byte[] Decrypt(byte[] src, String password) throws Exception {//对数据的解密方法
        // DES算法要求有一个可信任的随机数源  
        SecureRandom random = new SecureRandom();  
        // 创建一个DESKeySpec对象  
        DESKeySpec desKey = new DESKeySpec(password.getBytes());  
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
        // 将DESKeySpec对象转换成SecretKey对象  
        SecretKey securekey = keyFactory.generateSecret(desKey);  
        // Cipher对象实际完成解密操作  
        Cipher cipher = Cipher.getInstance("DES");  
        // 用密匙初始化Cipher对象  
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);  
        // 真正开始解密操作  
        return cipher.doFinal(src);  
        }

	}
