package com.method.FileOperation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*�ļ���ȡ������*/
public class ReadFile {
	
	private int[] binarylist;
	
	public ReadFile(String filePath,String password){
		AES aes = new AES();
		
        byte[] filebytes = toByteArray(filePath);//����ReadFile���toByteArray�����õ�byte��������
		
		byte[] encryptarr= aes.Encrypt(filebytes, password);//����byte��ʽ��ȡ���ļ�����DES����
				
		//�ļ����������Ļ��
	    int[] binaryfilelist = getFileBinary(encryptarr);

	   //�ļ����鳤�ȶ��������Ļ��
	   int[] binarylengthlist  = getLengthBinary(binaryfilelist.length);
	  
	   //�ļ���׺�����������Ļ��
	   int[] binaryextensionslist = getExtensionsBinary(filePath);	
	   
	   //�õ����յĶ�����������
	   this.binarylist = convergeBinaryList(binarylengthlist, binaryextensionslist, binaryfilelist);
	   				
	}
	
	public int[] getBinarylist() {
		return binarylist;
	}
	
	
	public byte[] toByteArray(String filePath){//��ȡ�ļ��õ�byte����
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
	
	
	public int[] InttoBinary(int num){//ת����8λ�������������Զ�ÿ�����ص�ת��
		int[] binary_arr=new int[8];
		for(int i=0; i<8; i++){
			binary_arr[i]=1&(num>>(7-i));//ÿ����һλ���к�1 ����
		}
		return binary_arr;
	}
	
	public int[] getLengthBinary(int num){//ת����32λ�Ķ������������ڼ�¼��ȡ�ļ���byte����ĳ���
		int[] binary_arr=new int[32];
		for(int i=0; i<32; i++){
			binary_arr[i]=1&(num>>(31-i));//ÿ����һλ���к�1 ����
		}
		return binary_arr;
	}
	
	/*����ļ���׺���Ķ��������ķ���*/
	public int[] getExtensionsBinary(String filePath){
		int[] binaryextensionslist = new int[80];
		
		File file = new File(filePath);
		
		String extensions = file.getName().substring(file.getName().lastIndexOf(".")+1);//����ļ���׺��
		
		int num = extensions.toCharArray().length;//��׺���ַ�����
		
		byte[] arrbyte = new byte[num];
		
		for(int i=0; i<num; i++){
			arrbyte[i] = (byte)extensions.toCharArray()[i];//��׺��ǿ��ת����byte
		}
		
		for(int i=0; i<arrbyte.length; i++){
			int[] tmp =  InttoBinary(arrbyte[i]);
			for(int j=0; j<8; j++){
				binaryextensionslist[i*8+j] = tmp[j];
			}
		}
		
		return binaryextensionslist;
	}
	
	/*����ļ��Ķ��������ķ���*/
	public int[] getFileBinary(byte[] arrbyte){//arrbyte��byte��һά����
		
		int[] binaryfileList=new int[arrbyte.length*8];
		
		for(int i=0; i<arrbyte.length; i++){
			
			int[] tmparr=InttoBinary(arrbyte[i]);
			
			for(int j=0; j<8; j++){
				binaryfileList[i*8+j]=tmparr[j];
			}
			
		}
		return binaryfileList;
	}
	
	//����ļ����鳤�ȵ������������飬���ļ����鳤����������ն��������ֵ�ǰ32λ
	public int[] convergeBinaryList(int[] binarylengthlist, int[] binaryextensionslist, int[] binaryfilelist){
		int[] binarylist = new int[binaryfilelist.length+32+80];
		
		for(int i=0; i<binarylist.length; i++){
			if(i<32){//װ�ļ����鳤��ֵ
				binarylist[i] = binarylengthlist[i];
			}
			if(i>=32 && i<112){//װ�ļ���׺����������
				binarylist[i]=binaryextensionslist[i-32];
			}
			if(i>=112){//װ�ļ���������
				binarylist[i]=binaryfilelist[i-112];
			}
								
		}
		
		return binarylist;
		
	}
	
}
