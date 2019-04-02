

import java.io.BufferedReader;//��������
import java.io.File;
import java.io.FileReader; //���ڶ�ȡ�ַ���
import java.io.IOException;//ʧ�ܻ��жϵ� I/O �������ɵ��쳣��ͨ����
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/*�ļ����ܷ�����*/
public class FileKey {
	
	
public String isFileExist(Scanner inputFilePath){//�����ļ�·��������ļ��Ƿ���ڣ������ڲ�����·��
		
		System.out.println("�������������ڵ�·����");
		
		String filePath=inputFilePath.next(); //�ӿ���̨���ڶ�ȡ��ǰ���������Ϣ
		
		File myfile=new File(filePath);//����������ļ���·��
		
		while(!myfile.exists()){
			
			System.out.println("�����ļ�������,���������룺");
			
			filePath=inputFilePath.next(); //�ӿ���̨���ڶ�ȡ��ǰ���������Ϣ
			
			myfile=new File(filePath);//����������ļ���·��
		}
		
		return filePath;//�����ļ�·��
		
	}
	
	public String ReadKey(String filePath){//���ļ���ȡ����ķ���
		String key=null;
        BufferedReader br=null;//ָ��������Ϊ��	
		try{
			br=new BufferedReader(new FileReader(filePath));//�������洢����ָ���ļ�
			key=br.readLine();//��ȡ�ļ���һ��
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

	public byte[] Encrypt(byte[] datasource, String password) { //�����ݵļ��ܷ���             
		try{ 
        SecureRandom random = new SecureRandom();  //����һ��ʵ��Ĭ��������㷨�İ�ȫ����������� (RNG)
        
        DESKeySpec desKey = new DESKeySpec(password.getBytes()); 
        //����һ���ܳ׹�����Ȼ��������DESKeySpecת���� 
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
        SecretKey securekey = keyFactory.generateSecret(desKey);  
        //Cipher����ʵ����ɼ��ܲ���  
        Cipher cipher = Cipher.getInstance("DES");  
        //���ܳ׳�ʼ��Cipher����  
        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);  
        //���ڣ���ȡ���ݲ�����  
        //��ʽִ�м��ܲ���  
        return cipher.doFinal(datasource);  
        }catch(Throwable e){
        	e.printStackTrace();  
        	}
		return null;  
		}
	
	public byte[] Decrypt(byte[] src, String password) throws Exception {//�����ݵĽ��ܷ���
        // DES�㷨Ҫ����һ�������ε������Դ  
        SecureRandom random = new SecureRandom();  
        // ����һ��DESKeySpec����  
        DESKeySpec desKey = new DESKeySpec(password.getBytes());  
        // ����һ���ܳ׹���
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
        // ��DESKeySpec����ת����SecretKey����  
        SecretKey securekey = keyFactory.generateSecret(desKey);  
        // Cipher����ʵ����ɽ��ܲ���  
        Cipher cipher = Cipher.getInstance("DES");  
        // ���ܳ׳�ʼ��Cipher����  
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);  
        // ������ʼ���ܲ���  
        return cipher.doFinal(src);  
        }

	}
