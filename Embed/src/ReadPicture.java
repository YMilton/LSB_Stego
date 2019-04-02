

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

/*��ȡͼƬ�ķ�����*/
public class ReadPicture {

	public String isPictureExist(Scanner inputPicturePath ){//�����ļ�·��������ļ��Ƿ���ڣ������ڲ�����·��
		
		System.out.println("����������ͼƬ���ڵ�·����");
		
		String picturePath=inputPicturePath.next(); //�ӿ���̨���ڶ�ȡ��ǰ���������Ϣ
		
		File mypicture=new File(picturePath);//����������ļ���·��
		
		while(!mypicture.exists()){
			
			System.out.println("����ͼƬ������,���������룺");
			
			picturePath=inputPicturePath.next(); //�ӿ���̨���ڶ�ȡ��ǰ���������Ϣ
			
			mypicture=new File(picturePath);//����������ļ���·��
		}
		
		return picturePath;//�����ļ�·��
		
	}

	public int[][] getImageRGB(String picturePath) {//��ȡͼƬÿ�����ص�ֵ�ķ����������ļ�·��

        File filename  = new File(picturePath);

        int[][] result = null;

        try {

             BufferedImage bufImg = ImageIO.read(filename);//ͼ��Ķ�ȡ�Լ��洢��ͼ�����ݻ�����
             
             System.out.println(bufImg.getType());
             
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
	
	public int[] getImageIntRGBList(int[][] arrimage){//ͼƬһά������
		int[] rgblist= new int[arrimage.length*arrimage[0].length];
		
		for (int i = 0; i < arrimage.length; i++) {

            for (int j = 0; j < arrimage[i].length; j++) {

                 rgblist[i*arrimage[i].length+j]=arrimage[i][j];

            }
            
       }
		
		return rgblist;
	}
	
	public void printPictureRGB(int[][] rgbPicture){//ͼ������rgbֵ�����
		for (int i = 0; i < rgbPicture.length; i++) {

            for (int j = 0; j < rgbPicture[0].length; j++) {

                  System.out.printf("%-6d",rgbPicture[i][j]);
            }
            System.out.println();
       }
	}

	
}
