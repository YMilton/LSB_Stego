package com.method.PictureOperation;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/*��ȡͼƬ�ķ�����*/
public class ReadPicture {
	
	private int[] picturelist;//ͼƬһά����
	
	private String extensions;//ͼƬ��׺��

	private int height;//ͼƬ�߶�
	
	private int width;//ͼƬ���
	
	private String pictureDirectory;//ͼƬ�ļ����ڵ�Ŀ¼
	
	public ReadPicture(String picturePath,String pictureDirectory){
		
		 //ͼƬ���ض�ά����
	    int[][] pictureMartix=getImageRGB(picturePath);
	    
	    //ͼƬ��A��R��G��B�ֱ�洢Ϊһά�������ʽ�ṹ
	    ArrayList<int[]> picturergbList = getImageRGBList(pictureMartix);
	    
	    //ARGB����ʽת����һά������
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

	public int[][] getImageRGB(String picturePath) {//��ȡͼƬÿ�����ص�ֵ�ķ����������ļ�·��

        File filename  = new File(picturePath);

        int[][] result = null;

        try {

             BufferedImage bufImg = ImageIO.read(filename);//ͼ��Ķ�ȡ�Լ��洢��ͼ�����ݻ�����

             int height = bufImg.getHeight();

             int width = bufImg.getWidth();

             result = new int[height][width];

             for (int i = 0; i < height; i++) {

                  for (int j = 0; j < width; j++) {
                        /*java��RGB���ʱ����һλ�Ǵ��������������ڶ�λ������������������ά�����һλ���������������ڶ�λ������������*/
                        result[i][j] = bufImg.getRGB(j,i) ;//ֻ��Ҫ���͵ĵͰ�λ����Ϊͼ���rgbֵ��0-255��getRGB(int x, int y, int rgb)

                  }
                  
             }

        } catch (IOException e) {
             e.printStackTrace();
        }
        return result;
  }
	
	public ArrayList<int[]> getImageRGBList(int[][] arrimage){//ͼƬһά������
		ArrayList<int[]> argblist= new ArrayList<int[]>();
		ColorModel cm = ColorModel.getRGBdefault();
		
		//A��R��G��B��һά����
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
	
	public int[] ListtoVector(ArrayList<int[]> list){//��ʽת��Ϊһά����
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
		
		String extensions = file.getName().substring(file.getName().lastIndexOf(".")+1);//����ļ���׺��
		
		return extensions;
	}
	
}
