package com.embed.Process;

import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

import com.method.PictureOpreation.WritePicture;

/*�ļ���Ƕ��׶���*/
public class EmbedProcess {
	//cpicture_list������ʽ����
	//height�߶�
   //width���
    //extensionsͼƬ��׺��
    //pictureDirectoryͼƬ���ڵ�·��
	public EmbedProcess(int[] binary_list,int[] picture_list,int height,int width,String extensions,String pictureDirectory) throws IOException{
		//���ܽ׶εĹ�����
		//binary_list������鳤�ȵĶ�����������,picture_list��ȡͼƬ��rgb������
		
		int[] cpicture_list = Embed_Area(picture_list, binary_list);//Ƕ���ͼƬ������
		
		if(extensions.equals("bmp") || extensions.equals("gif") || extensions.equals("png")  ){
	    	   new WritePicture(cpicture_list , height, width,extensions,pictureDirectory);
	       }else{
	    	   extensions = selectExtensions();
	    	   new WritePicture(cpicture_list , height, width,extensions,pictureDirectory);
	       }
			
	       JOptionPane.showMessageDialog(null, "�ļ�Ƕ��ɹ���");
	       System.exit(0);    		
	}
	
	public String selectExtensions(){//���ѡ���׺��
		Random rd = new Random();
		
		String extensions = "png";
		
		int opt = rd.nextInt(2);
		
		switch(opt){
		case 0: extensions = "bmp";break;
		case 1: extensions = "png";break;
		}
		
		return extensions;
	}

	
	public int Parity(int data){//�ж����ݵ���ż��
		int result;
		
		if(data%2==0){
			result=0;
		}else{
			result=1;
		}
		
		return result;
	}
	
	/*prgbͼ��rgbֵ��fileb�ļ����ܶ���������������R[ X[k+1] + 1 ][ X[k] ] > R[ X[k+1] + 1 ][ X[k+2] ]*/
	public  int Embed_Function1(int gray,int fileb){
		int result = gray;
		if(Parity(gray)!=fileb){
			if(gray>=0 && gray<255){
				result = gray + 1;
			}
			if(gray==255){
				result = gray - 1;
			}
		}
		return result;
	}
	
	/*prgbͼ��rgbֵ��fileb�ļ����ܶ���������������R[ X[k+1] + 1 ][ X[k] ] < R[ X[k+1] + 1 ][ X[k+2] */
	public int Embed_Function2(int gray,int fileb){
		int result = gray;
		if(Parity(gray)!=fileb){
			if(gray>0 && gray<=255){
				result = gray - 1;
			}
			if(gray==0){
				result = gray + 1;
			}
		}
		return result;
	}
	
	/*prgbͼ��rgbֵ��fileb�ļ����ܶ���������������R[ X[k+1] + 1 ][ X[k] ] = R[ X[k+1] + 1 ][ X[k+2]*/
	public int Embed_Function3(int gray,int fileb){
		int result = gray;
		if(Parity(gray)!=fileb){
			if(gray==255){
				result = gray - 1;
			}
			if(gray==0){
				result = gray + 1;
			}
			if(gray>0 && gray<255){
				Random ran = new Random();
				
				int opt = ran.nextInt(2);//����ƽ���ֲ���01�����
				
				if(opt == 0){
					result = gray - 1;
				}
				if(opt==1){
					result = gray + 1;
				}
				
			}
		}
		return result;
	}

	
	/*Ƕ���������жϴ�С�����ڷ���0��С�ڷ���-1�����ڷ���1*/
	public int Embed_Container(int x, int y){
		int output = 0;
		
		if(x==y){
			output = 0;
		}
		
		if(x>y){
			output = 1;
		}
		
		if(x<y){
			output = -1;
		}
		
		return output;
	}

	/*��¼��������ɣ�picture_graylist����ͼƬ����ʽ�ҶȾ���*/
	public int[][] getRecordMatrix(int[] picture_graylist){
		int[][] record_matrix=new int[256][256];//��¼����
		
		for(int i=0; i<picture_graylist.length-1; i++){
			record_matrix[ picture_graylist[i] ][ picture_graylist[i+1] ]+=1;
		}
		
		return record_matrix;
	}
	
	 /******************
	 * Ƕ��Ĺؼ��㷨��  
	 * picture_list����INT_ARGB����������ubyte_list��BYTE_GRAY��������������ֵ��0-255�仯��
	 * record_matrix�Ǽ�¼��ʾ��仯�ľ��� binary_list��Ƕ��Ķ�������
	 * *****************/
	public int[] Embed_Area(int[] picture_graylist, int[] binary_list){
		
		int[] new_picture_graylist = picture_graylist;
		
		int[][] record_matrix=getRecordMatrix(picture_graylist);//��¼����
		
		for(int index=0; index<binary_list.length; index++){
			
			int m = 0,n = 0;
			if(picture_graylist[ index ] <=253 && picture_graylist[ index+1 ] <=254){
				m = record_matrix[ picture_graylist[ index+1 ]+1 ][ picture_graylist[ index ] ];  //��¼�����λ��R[ X[k+1] + 1 ][ X[k] ]
				n = record_matrix[ picture_graylist[ index+1 ]+1 ][ picture_graylist[ index ]+2 ];  //��¼�����λ�� R[ X[k+1] + 1 ][ X[k+2 ]
			}
			
			if(Embed_Container(m,n)==1){
				new_picture_graylist[ index ] = Embed_Function1(picture_graylist[ index ], binary_list[ index ]);
			}
			
			if(Embed_Container(m,n)==-1){
				new_picture_graylist[ index ] = Embed_Function2(picture_graylist[ index ], binary_list[ index ]);
			}
			
			if(Embed_Container(m,n)==0){
				new_picture_graylist[ index ] = Embed_Function3(picture_graylist[ index ], binary_list[ index ]);
			}
			
			if(new_picture_graylist[index] - picture_graylist[index] == 1){
				if( index>=0 && index<binary_list.length-1){//��ʼλ�ú��м�λ��ʱ��¼����ĸı�
					record_matrix[ picture_graylist[index] ][ picture_graylist[index+1] ] -= 1;//��¼�����һ
							
					record_matrix[ picture_graylist[index]+1 ][ picture_graylist[index+1]]  +=1;//��¼�����һ
				}
				
				if(index>0 && index<=binary_list.length-1){//ĩβλ�ú��м�λ��ʱ��¼����ĸı�
					record_matrix[ picture_graylist[index-1] ][ picture_graylist[index] ] -= 1 ;
					
					record_matrix[ picture_graylist[index-1] ][ picture_graylist[index]+1 ] += 1 ;
				}
					
			}	

			if(new_picture_graylist[index] - picture_graylist[index]  == -1){
				if( index>=0 && index<binary_list.length-1){//��ʼλ�ú��м�λ��ʱ��¼����ĸı�
					record_matrix[ picture_graylist[index] ][ picture_graylist[index+1] ] -= 1;//��¼�����һ
							
					record_matrix[ picture_graylist[index]-1 ][ picture_graylist[index+1]]  +=1;//��¼�����һ
				}
				
				if(index>0 && index<=binary_list.length-1){//ĩβλ�ú��м�λ��ʱ��¼����ĸı�
					record_matrix[ picture_graylist[index-1] ][ picture_graylist[index] ] -= 1 ;
					
					record_matrix[ picture_graylist[index-1] ][ picture_graylist[index]-1 ] += 1 ;
				}
				
			}
			
		}
		
		return new_picture_graylist;
		
	}
	
	
	
	
}
