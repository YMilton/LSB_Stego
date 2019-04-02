

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

/*读取图片的方法类*/
public class ReadPicture {

	public String isPictureExist(Scanner inputPicturePath ){//输入文件路径并检测文件是否存在，若存在并返回路径
		
		System.out.println("请输入载体图片所在的路径：");
		
		String picturePath=inputPicturePath.next(); //从控制台窗口读取当前行输入的信息
		
		File mypicture=new File(picturePath);//传递输入的文件的路径
		
		while(!mypicture.exists()){
			
			System.out.println("载体图片不存在,请重新输入：");
			
			picturePath=inputPicturePath.next(); //从控制台窗口读取当前行输入的信息
			
			mypicture=new File(picturePath);//传递输入的文件的路径
		}
		
		return picturePath;//返回文件路径
		
	}

	public int[][] getImageRGB(String picturePath) {//获取图片每个像素点值的方法，传递文件路径

        File filename  = new File(picturePath);

        int[][] result = null;

        try {

             BufferedImage bufImg = ImageIO.read(filename);//图像的读取以及存储到图像数据缓冲区
             
             System.out.println(bufImg.getType());
             
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
		
		int[] alphalist= new int[arrimage.length*arrimage[0].length];
		int[] rlist = alphalist;
		int[] glist = alphalist;
		int[] blist = alphalist;
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
	
	public int[] ListtoVector(ArrayList<int[]> list){
		int[] vector = new int[list.size()*list.get(0).length];
		
		for(int i=0; i<list.size(); i++){
			for(int j=0; j<list.get(0).length; j++){
				vector[i*list.get(0).length + j] = list.get(i)[j];
			}
		}
		
		return vector;
	}
	
	public int[] getImageIntRGBList(int[][] arrimage){//图片一维数组获得
		int[] rgblist= new int[arrimage.length*arrimage[0].length];
		
		for (int i = 0; i < arrimage.length; i++) {

            for (int j = 0; j < arrimage[i].length; j++) {

                 rgblist[i*arrimage[i].length+j]=arrimage[i][j];

            }
            
       }
		
		return rgblist;
	}
	
	public void printPictureRGB(int[][] rgbPicture){//图像数组rgb值的输出
		for (int i = 0; i < rgbPicture.length; i++) {

            for (int j = 0; j < rgbPicture[0].length; j++) {

                  System.out.printf("%-6d",rgbPicture[i][j]);
            }
            System.out.println();
       }
	}

	
}
