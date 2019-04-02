import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Test6 {

	public static void main(String[] args) throws IOException {
		int[] alist = {255,255,255,255,255,255,255,255,255,255};
		int[] rlist = {11,12,13,14,15,16,17,18,19,20};
		int[] glist = {21,22,23,24,25,26,27,28,29,30};
		int[] blist = {31,32,33,34,35,36,37,38,39,40};
		
		ArrayList<int[]> list = new ArrayList<int[]>();
		
		list.add(alist);
		list.add(rlist);
		list.add(glist);
		list.add(blist);
		
		int[] rgb = ListtoARGBVector(list);
		
        ColorModel cm = ColorModel.getRGBdefault();
		
		for(int i=0; i<rgb.length; i++){
			System.out.println(cm.getBlue(rgb[i])+"  "+ cm.getGreen(rgb[i])+"  "+cm.getRed(rgb[i])+"  "+cm.getAlpha(rgb[i]));
		}
		
		int[][] m = VectortoMatrix(rgb,5);
		
		
		System.out.println();
		for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
            	System.out.println(m[i][j]);
            	
            	System.out.println(cm.getBlue(m[i][j])+"  "+ cm.getGreen(m[i][j])+"  "+cm.getRed(m[i][j])+"  "+cm.getAlpha(m[i][j]));
            	
            }
            System.out.println();
        }
		
		
		createRGBImage(m,"F:/test.bmp","bmp");
		
		
		int[][] m1 = getImageRGB("F:/test.bmp");
		
		for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
            	
            	System.out.print(m1[i][j]+"  ");
            }
            System.out.println();
        }
		
		ArrayList<int[]> rgblist = getImageRGBList(m1);
		
		for(int j=0; j<rgblist.size(); j++){
			for(int i=0; i<rgblist.get(j).length; i++){
				System.out.print(rgblist.get(j)[i]+" ");
			}
			System.out.println();
		}
		
	}
	
	public static void createRGBImage(int[][] matrix, String filepath,String format) throws IOException{
		int height = matrix.length;
        int width = matrix[0].length;     
 
        OutputStream outputfile = new FileOutputStream(new File(filepath));
        
        BufferedImage rgbImage = new BufferedImage(width, height,  BufferedImage.TYPE_INT_RGB);//图片缓冲区存储灰度图像
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
            	System.out.print(matrix[i][j]+" ");
            	rgbImage.setRGB(j, i, matrix[i][j]);
            }
            System.out.println();
        }
        
        ImageIO.write(rgbImage, format, outputfile);
	}
	
	public static int[][] getImageRGB(String picturePath) {//获取图片每个像素点值的方法，传递文件路径

        File filename  = new File(picturePath);

        int[][] result = null;

        try {

             BufferedImage bufImg = ImageIO.read(filename);//图像的读取以及存储到图像数据缓冲区

             int height = bufImg.getHeight();

             int width = bufImg.getWidth();

             result = new int[height][width];

             for (int i = 0; i < height; i++) {

                  for (int j = 0; j < width; j++) {
                        /*java中RGB获得时，第一位是代表矩阵的列数，第二位代表矩阵的行数，而二维数组第一位代表矩阵的行数，第二位代表矩阵的列数*/
                        result[i][j] = bufImg.getRGB(j,i) ;//只需要整型的低八位，因为图像的rgb值是0-255；getRGB(int x, int y, int rgb)

                  }
                  
             }

        } catch (IOException e) {
             e.printStackTrace();
        }
        return result;
  }
	
	public static int[] cutArray(int[] binary_list, int begin, int end){//截取数组的方法
		int[] cutarray = new int[end-begin+1];
		
		for(int i=begin; i<=end; i++){
			cutarray[i-begin] = binary_list[i];
		}
		
		return cutarray;
	}
	
	/*vector是图片4byte数据的链式结构，length是图片高度与宽度的乘积*/
	public static ArrayList<int[]> VectortoList(int[] vector, int length){//数组转换成链式结构
		ArrayList<int[]> list = new ArrayList<int[]>();
		
		for(int i=0; i<vector.length/length; i++){
	    list.add(cutArray(vector,i*length,(i+1)*length-1));
		}
		
		return list;
	}
	
	public static int[] ListtoARGBVector(ArrayList<int[]> argblist){//把4byte的argb数组整合为一维的rgb数组
		int[] intargb_vector = new int[argblist.get(0).length];
		
		for(int i=0; i<argblist.get(0).length; i++){
			intargb_vector[i] = argblist.get(0)[i]<<24 | argblist.get(1)[i]<<16 | argblist.get(2)[i]<<8 | argblist.get(3)[i];
		}
		
		return intargb_vector;
	}
	
public static int[][] VectortoMatrix(int[] vector,int width){//一维数组转换成二维数组
		
		int[][] matrix = new int[vector.length/width][width] ;//定义数组的宽和高
		
		int m = 0, n=0, k=0;
		
		while(k<vector.length){
			if(n == width){
				n=0;
				m++;
			}
			matrix[m][n] = vector[k];
			n++;
			k++;
		}		
		return matrix;
	}


public static ArrayList<int[]> getImageRGBList(int[][] arrimage){//图片一维数组获得
	ArrayList<int[]> argblist= new ArrayList<int[]>();
	ColorModel cm = ColorModel.getRGBdefault();
	
	int[] alphalist= new int[arrimage.length*arrimage[0].length];
	int[] rlist =  new int[arrimage.length*arrimage[0].length];
	int[] glist =  new int[arrimage.length*arrimage[0].length];
	int[] blist =  new int[arrimage.length*arrimage[0].length];
	for (int i = 0; i < arrimage.length; i++) {

        for (int j = 0; j < arrimage[i].length; j++) {
        	int alpha = cm.getAlpha(arrimage[i][j]);
        	int r = cm.getRed(arrimage[i][j]);
        	int g = cm.getGreen(arrimage[i][j]);
        	int b = cm.getBlue(arrimage[i][j]);
        	
        	alphalist[i*arrimage[i].length+j]=alpha;
            rlist[i*arrimage[i].length+j]=r;
            glist[i*arrimage[i].length+j]=g;
            blist[i*arrimage[i].length+j]=b;
        }
        
   }
	argblist.add(blist);	
	argblist.add(glist);
	argblist.add(rlist);
    argblist.add(alphalist);			
		
	return argblist;
}

}
