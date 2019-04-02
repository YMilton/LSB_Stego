package com.method.PictureOpreation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class WritePicture {
	
	
	public WritePicture(int[] cpicture_list, int height, int width, String extensions,String pictureDirectory) throws IOException{
		ArrayList<int[]> list = VectortoList(cpicture_list,height*width);//һά�������ֳ�A��R��G��B����ʽ�ṹ
		
		int[] argblist = ListtoARGBVector(list);//A��R��G��B����ʽ�ṹת�������ϵ�argbһά����
		
		int[][] argbmatrix = VectortoMatrix(argblist,width);//���ϵ�ARGBת���ɶ�ά����
		
		String picture = pictureDirectory + "/Embed_Picture."+extensions;
		
		createRGBImage(argbmatrix, picture, extensions);//ͼƬ������
		
	}
	

	public int[] cutArray(int[] binary_list, int begin, int end){//����Ľ�ȡ
		int[] cutarray = new int[end-begin+1];
		
		for(int i=begin; i<=end; i++){
			cutarray[i-begin] = binary_list[i];
		}
		
		return cutarray;
	}
	
	
	/*һά����ת����A��R��G��B����ʽ�ṹ�ķ���*/
	public ArrayList<int[]> VectortoList(int[] vector, int length){
		ArrayList<int[]> list = new ArrayList<int[]>();
		
		for(int i=0; i<vector.length/length; i++){
	    list.add(cutArray(vector,i*length,(i+1)*length-1));
		}
		
		return list;
	}
	
	
	/*A��R��G��B����ʽ�ṹת�������ϵ�ARGBһά����*/
	public int[] ListtoARGBVector(ArrayList<int[]> argblist){
		int[] argbvector = new int[argblist.get(0).length];
		
		for(int i =0; i<argbvector.length; i++){
			//25-32����alphaͨ����17-24����red��9-16����green��1-8����blue
			argbvector[i] = argblist.get(3)[i]<<24 | argblist.get(2)[i]<<16 | argblist.get(1)[i]<<8 | argblist.get(0)[i];
		}
		
		return argbvector;
	}
	
	
	/*һά����ת���ɶ�ά����ķ���*/
	public int[][] VectortoMatrix(int[] vector,int width){
		
		int[][] matrix = new int[vector.length/width][width] ;
		
		int m = 0, n=0, k=0;
		
		while(k<vector.length){
			if(n == width){//��n����ͼƬ�Ŀ��ʱ��ת����һ�еĴ洢
				n=0;
				m++;
			}
			matrix[m][n] = vector[k];
			n++;
			k++;
		}		
		return matrix;
	}
	
	/*ͼƬ���ɵķ���*/
	public void createRGBImage(int[][] matrix, String filepath,String format) throws IOException{
		int height = matrix.length;
        int width = matrix[0].length;     
 
        OutputStream outputfile = new FileOutputStream(new File(filepath));
        
        BufferedImage rgbImage = new BufferedImage(width, height,  BufferedImage.TYPE_INT_RGB);//ͼƬ�������洢�Ҷ�ͼ��
        	for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {    
                	rgbImage.setRGB(j, i, matrix[i][j]);
                }
            }   
        
        ImageIO.write(rgbImage, format, outputfile);
	}
	
}
