package com.embed.Process;

import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

import com.method.PictureOpreation.WritePicture;

/*文件的嵌入阶段类*/
public class EmbedProcess {
	//cpicture_list加密链式向量
	//height高度
   //width宽度
    //extensions图片后缀名
    //pictureDirectory图片所在的路径
	public EmbedProcess(int[] binary_list,int[] picture_list,int height,int width,String extensions,String pictureDirectory) throws IOException{
		//加密阶段的构造器
		//binary_list添加数组长度的二进制数组链,picture_list提取图片的rgb数组链
		
		int[] cpicture_list = Embed_Area(picture_list, binary_list);//嵌入的图片数组链
		
		if(extensions.equals("bmp") || extensions.equals("gif") || extensions.equals("png")  ){
	    	   new WritePicture(cpicture_list , height, width,extensions,pictureDirectory);
	       }else{
	    	   extensions = selectExtensions();
	    	   new WritePicture(cpicture_list , height, width,extensions,pictureDirectory);
	       }
			
	       JOptionPane.showMessageDialog(null, "文件嵌入成功！");
	       System.exit(0);    		
	}
	
	public String selectExtensions(){//随机选择后缀名
		Random rd = new Random();
		
		String extensions = "png";
		
		int opt = rd.nextInt(2);
		
		switch(opt){
		case 0: extensions = "bmp";break;
		case 1: extensions = "png";break;
		}
		
		return extensions;
	}

	
	public int Parity(int data){//判断数据的奇偶性
		int result;
		
		if(data%2==0){
			result=0;
		}else{
			result=1;
		}
		
		return result;
	}
	
	/*prgb图像rgb值，fileb文件加密二进制流，条件是R[ X[k+1] + 1 ][ X[k] ] > R[ X[k+1] + 1 ][ X[k+2] ]*/
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
	
	/*prgb图像rgb值，fileb文件加密二进制流，条件是R[ X[k+1] + 1 ][ X[k] ] < R[ X[k+1] + 1 ][ X[k+2] */
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
	
	/*prgb图像rgb值，fileb文件加密二进制流，条件是R[ X[k+1] + 1 ][ X[k] ] = R[ X[k+1] + 1 ][ X[k+2]*/
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
				
				int opt = ran.nextInt(2);//产生平均分布的01随机码
				
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

	
	/*嵌入容器，判断大小，等于返回0，小于返回-1，大于返回1*/
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

	/*记录矩阵的生成，picture_graylist代表图片的链式灰度矩阵*/
	public int[][] getRecordMatrix(int[] picture_graylist){
		int[][] record_matrix=new int[256][256];//记录矩阵
		
		for(int i=0; i<picture_graylist.length-1; i++){
			record_matrix[ picture_graylist[i] ][ picture_graylist[i+1] ]+=1;
		}
		
		return record_matrix;
	}
	
	 /******************
	 * 嵌入的关键算法：  
	 * picture_list是以INT_ARGB的数组链；ubyte_list是BYTE_GRAY的数组链，像素值在0-255变化；
	 * record_matrix是记录显示点变化的矩阵； binary_list待嵌入的二进制流
	 * *****************/
	public int[] Embed_Area(int[] picture_graylist, int[] binary_list){
		
		int[] new_picture_graylist = picture_graylist;
		
		int[][] record_matrix=getRecordMatrix(picture_graylist);//记录矩阵
		
		for(int index=0; index<binary_list.length; index++){
			
			int m = 0,n = 0;
			if(picture_graylist[ index ] <=253 && picture_graylist[ index+1 ] <=254){
				m = record_matrix[ picture_graylist[ index+1 ]+1 ][ picture_graylist[ index ] ];  //记录矩阵的位置R[ X[k+1] + 1 ][ X[k] ]
				n = record_matrix[ picture_graylist[ index+1 ]+1 ][ picture_graylist[ index ]+2 ];  //记录矩阵的位置 R[ X[k+1] + 1 ][ X[k+2 ]
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
				if( index>=0 && index<binary_list.length-1){//起始位置和中间位置时记录矩阵的改变
					record_matrix[ picture_graylist[index] ][ picture_graylist[index+1] ] -= 1;//记录矩阵减一
							
					record_matrix[ picture_graylist[index]+1 ][ picture_graylist[index+1]]  +=1;//记录矩阵加一
				}
				
				if(index>0 && index<=binary_list.length-1){//末尾位置和中间位置时记录矩阵的改变
					record_matrix[ picture_graylist[index-1] ][ picture_graylist[index] ] -= 1 ;
					
					record_matrix[ picture_graylist[index-1] ][ picture_graylist[index]+1 ] += 1 ;
				}
					
			}	

			if(new_picture_graylist[index] - picture_graylist[index]  == -1){
				if( index>=0 && index<binary_list.length-1){//起始位置和中间位置时记录矩阵的改变
					record_matrix[ picture_graylist[index] ][ picture_graylist[index+1] ] -= 1;//记录矩阵减一
							
					record_matrix[ picture_graylist[index]-1 ][ picture_graylist[index+1]]  +=1;//记录矩阵加一
				}
				
				if(index>0 && index<=binary_list.length-1){//末尾位置和中间位置时记录矩阵的改变
					record_matrix[ picture_graylist[index-1] ][ picture_graylist[index] ] -= 1 ;
					
					record_matrix[ picture_graylist[index-1] ][ picture_graylist[index]-1 ] += 1 ;
				}
				
			}
			
		}
		
		return new_picture_graylist;
		
	}
	
	
	
	
}
