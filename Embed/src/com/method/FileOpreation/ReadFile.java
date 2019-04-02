package com.method.FileOpreation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*文件读取方法类*/
public class ReadFile {
	
	private int[] binarylist;
	
	public ReadFile(String filePath,String password){
		AES aes = new AES();
		
        byte[] filebytes = toByteArray(filePath);//调用ReadFile类的toByteArray方法得到byte类型数组
		
		byte[] encryptarr= aes.Encrypt(filebytes, password);//对以byte形式读取的文件进行DES加密
				
		//文件二进制流的获得
	    int[] binaryfilelist = getFileBinary(encryptarr);

	   //文件数组长度二进制流的获得
	   int[] binarylengthlist  = getLengthBinary(binaryfilelist.length);
	  
	   //文件后缀名二进制流的获得
	   int[] binaryextensionslist = getExtensionsBinary(filePath);	
	   
	   //得到最终的二进制数组链
	   this.binarylist = convergeBinaryList(binarylengthlist, binaryextensionslist, binaryfilelist);
	   				
	}
	
	public int[] getBinarylist() {
		return binarylist;
	}
	
	
	public byte[] toByteArray(String filePath){//读取文件得到byte数组
		byte[] buffer = null;  
		try{
			File file = new File(filePath);  
			FileInputStream fis = new FileInputStream(file);  
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
			byte[] b = new byte[1000]; 
			int n;  
			while ((n = fis.read(b)) != -1) { 
				bos.write(b, 0, n);  
			}  
			fis.close();  
			bos.close();  
			buffer = bos.toByteArray();  
		}catch (FileNotFoundException e) {
			e.printStackTrace();  
		} catch (IOException e) {  
		    e.printStackTrace();  
		}  
		 return buffer;  
	}
	
	
	public int[] InttoBinary(int num){//转换成8位二进制数，用以对每个像素的转化
		int[] binary_arr=new int[8];
		for(int i=0; i<8; i++){
			binary_arr[i]=1&(num>>(7-i));//每右移一位进行和1 相与
		}
		return binary_arr;
	}
	
	public int[] getLengthBinary(int num){//转换成32位的二进制数，用于记录读取文件的byte数组的长度
		int[] binary_arr=new int[32];
		for(int i=0; i<32; i++){
			binary_arr[i]=1&(num>>(31-i));//每右移一位进行和1 相与
		}
		return binary_arr;
	}
	
	/*获得文件后缀名的二进制链的方法*/
	public int[] getExtensionsBinary(String filePath){
		int[] binaryextensionslist = new int[80];
		
		File file = new File(filePath);
		
		String extensions = file.getName().substring(file.getName().lastIndexOf(".")+1);//获得文件后缀名
		
		int num = extensions.toCharArray().length;//后缀名字符长度
		
		byte[] arrbyte = new byte[num];
		
		for(int i=0; i<num; i++){
			arrbyte[i] = (byte)extensions.toCharArray()[i];//后缀名强制转换成byte
		}
		
		for(int i=0; i<arrbyte.length; i++){
			int[] tmp =  InttoBinary(arrbyte[i]);
			for(int j=0; j<8; j++){
				binaryextensionslist[i*8+j] = tmp[j];
			}
		}
		
		return binaryextensionslist;
	}
	
	/*获得文件的二进制链的方法*/
	public int[] getFileBinary(byte[] arrbyte){//arrbyte是byte的一维数组
		
		int[] binaryfileList=new int[arrbyte.length*8];
		
		for(int i=0; i<arrbyte.length; i++){
			
			int[] tmparr=InttoBinary(arrbyte[i]);
			
			for(int j=0; j<8; j++){
				binaryfileList[i*8+j]=tmparr[j];
			}
			
		}
		return binaryfileList;
	}
	
	//添加文件数组长度到二进制流数组，把文件数组长度添加在最终二进制数字的前32位
	public int[] convergeBinaryList(int[] binarylengthlist, int[] binaryextensionslist, int[] binaryfilelist){
		int[] binarylist = new int[binaryfilelist.length+32+80];
		
		for(int i=0; i<binarylist.length; i++){
			if(i<32){//装文件数组长度值
				binarylist[i] = binarylengthlist[i];
			}
			if(i>=32 && i<112){//装文件后缀名二进制流
				binarylist[i]=binaryextensionslist[i-32];
			}
			if(i>=112){//装文件二进制流
				binarylist[i]=binaryfilelist[i-112];
			}
								
		}
		
		return binarylist;
		
	}
	
}
