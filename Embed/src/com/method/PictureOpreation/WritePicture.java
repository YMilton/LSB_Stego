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
		ArrayList<int[]> list = VectortoList(cpicture_list,height*width);//一维向量划分成A、R、G、B的链式结构
		
		int[] argblist = ListtoARGBVector(list);//A、R、G、B的链式结构转换成整合的argb一维向量
		
		int[][] argbmatrix = VectortoMatrix(argblist,width);//整合的ARGB转换成二维矩阵
		
		String picture = pictureDirectory + "/Embed_Picture."+extensions;
		
		createRGBImage(argbmatrix, picture, extensions);//图片的生成
		
	}
	

	public int[] cutArray(int[] binary_list, int begin, int end){//数组的截取
		int[] cutarray = new int[end-begin+1];
		
		for(int i=begin; i<=end; i++){
			cutarray[i-begin] = binary_list[i];
		}
		
		return cutarray;
	}
	
	
	/*一维向量转换成A、R、G、B的链式结构的方法*/
	public ArrayList<int[]> VectortoList(int[] vector, int length){
		ArrayList<int[]> list = new ArrayList<int[]>();
		
		for(int i=0; i<vector.length/length; i++){
	    list.add(cutArray(vector,i*length,(i+1)*length-1));
		}
		
		return list;
	}
	
	
	/*A、R、G、B的链式结构转换成整合的ARGB一维向量*/
	public int[] ListtoARGBVector(ArrayList<int[]> argblist){
		int[] argbvector = new int[argblist.get(0).length];
		
		for(int i =0; i<argbvector.length; i++){
			//25-32代表alpha通道，17-24代表red，9-16代表green、1-8代表blue
			argbvector[i] = argblist.get(3)[i]<<24 | argblist.get(2)[i]<<16 | argblist.get(1)[i]<<8 | argblist.get(0)[i];
		}
		
		return argbvector;
	}
	
	
	/*一维向量转换成二维矩阵的方法*/
	public int[][] VectortoMatrix(int[] vector,int width){
		
		int[][] matrix = new int[vector.length/width][width] ;
		
		int m = 0, n=0, k=0;
		
		while(k<vector.length){
			if(n == width){//当n等于图片的宽度时，转致下一行的存储
				n=0;
				m++;
			}
			matrix[m][n] = vector[k];
			n++;
			k++;
		}		
		return matrix;
	}
	
	/*图片生成的方法*/
	public void createRGBImage(int[][] matrix, String filepath,String format) throws IOException{
		int height = matrix.length;
        int width = matrix[0].length;     
 
        OutputStream outputfile = new FileOutputStream(new File(filepath));
        
        BufferedImage rgbImage = new BufferedImage(width, height,  BufferedImage.TYPE_INT_RGB);//图片缓冲区存储灰度图像
        	for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {    
                	rgbImage.setRGB(j, i, matrix[i][j]);
                }
            }   
        
        ImageIO.write(rgbImage, format, outputfile);
	}
	
}
