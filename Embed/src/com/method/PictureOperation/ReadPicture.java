package com.method.PictureOperation;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/*读取图片的方法类*/
public class ReadPicture {
	
	private int[] picturelist;//图片一维向量
	
	private String extensions;//图片后缀名

	private int height;//图片高度
	
	private int width;//图片宽度
	
	private String pictureDirectory;//图片文件所在的目录
	
	public ReadPicture(String picturePath,String pictureDirectory){
		
		 //图片像素二维矩阵
	    int[][] pictureMartix=getImageRGB(picturePath);
	    
	    //图片的A、R、G、B分别存储为一维矩阵的链式结构
	    ArrayList<int[]> picturergbList = getImageRGBList(pictureMartix);
	    
	    //ARGB的链式转换成一维的向量
	    this.picturelist = ListtoVector(picturergbList);
	    
	    this.extensions=getExtensions(picturePath);
	    
	    this.height = pictureMartix.length;
	    
	    this.width = pictureMartix[0].length;
	    
	    this.pictureDirectory = pictureDirectory;
	}
	

	public int getHeight() {
		return height;
	}


	public int getWidth() {
		return width;
	}
	
	public int[] getPicturelist() {
		return picturelist;
	}
	
	public String getExtensions() {
		return extensions;
	}
	
	public String getPictureDirectory() {
		return pictureDirectory;
	}

	public int[][] getImageRGB(String picturePath) {//获取图片每个像素点值的方法，传递文件路径

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
	
	public ArrayList<int[]> getImageRGBList(int[][] arrimage){//图片一维数组获得
		ArrayList<int[]> argblist= new ArrayList<int[]>();
		ColorModel cm = ColorModel.getRGBdefault();
		
		//A、R、G、B的一维向量
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
	
	public int[] ListtoVector(ArrayList<int[]> list){//链式转换为一维向量
		int[] vector = new int[list.size()*list.get(0).length];
		
		for(int i=0; i<list.size(); i++){
			for(int j=0; j<list.get(0).length; j++){
				vector[i*list.get(0).length + j] = list.get(i)[j];
			}
		}
		
		return vector;
	}
	
	public String getExtensions(String filePath){
				
		File file = new File(filePath);
		
		String extensions = file.getName().substring(file.getName().lastIndexOf(".")+1);//获得文件后缀名
		
		return extensions;
	}
	
}
