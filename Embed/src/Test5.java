
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Test5 {

	public static void main(String[] args) throws Exception{
		
		/*加密文件二进制流的获得*/
		ReadFile readfile = new ReadFile();
		
		byte[] arr = readfile.toByteArray("F:/secret.docx");
		
		AES filekey = new AES();
		
		byte[] encryptarr = filekey.Encrypt(arr, "rgb4567890");

		int[] barr = readfile.getFileBinary(encryptarr);
		
		
		
		
		File file=new File("F:\\exp1");
		String Images[];
		Images=file.list();
		
		for(int i=0; i<Images.length;i++){
			String image="F:\\exp1\\"+Images[i];
			
			
			/*彩色图片整合的一维向量的获得*/
			ArrayList<int[]> rgblist = getImageRGBList(getImageRGB(image));
		
			int[] rgbvector = ListtoVector(rgblist);
		
			
			/*加密二进制流嵌入图片*/
			int[] nrgbvector = Embed_Area(rgbvector,barr);
	       
			
			/*图片二维矩阵的生成*/
			ArrayList<int[]> nrgblist = VectortoList(nrgbvector,getImageRGB(image).length*getImageRGB(image)[0].length);
					
			int[] nrgbvector1= ListtoARGBVector(nrgblist);
			
			int[][] matrix = ARGBVectortoMatrix(nrgbvector1,getImageRGB(image)[0].length);
			//matrix是嵌入加密文件的二维数组
			
			String extensions  = selectExtensions();
			
			String str = "F:\\exp2\\"+Images[i].substring(0,Images[i].lastIndexOf("."))+"."+extensions;
			
			createRGBImage(matrix, str, extensions);
			
		}
		
		
	
		
		
		
	}
	
	
	public static String selectExtensions(){
		Random rd = new Random();
		
		String extensions = "png";
		
		int opt = rd.nextInt(2);
		
		switch(opt){
		case 0: extensions = "bmp";break;
		case 1: extensions = "png";break;
		}
		
		return extensions;
	}
	
	
	
	public static int[] createRGBVector(int num){
		int[] vector = new int[num];
		
		Random ran = new Random();
		
		for(int i=0; i<num; i++){
			vector[i] = ran.nextInt(256);
		}
		
		return vector;
	}
	
	
	
	
	/*二进制数组转换成byte整型数据的方法*/
	public static byte BinarytoByte(int[] arr){
		byte num=0;
		int tmp=arr[1]*64+arr[2]*32+arr[3]*16+arr[4]*8+arr[5]*4+arr[6]*2+arr[7];
		if(arr[0]==0){
			num=(byte)tmp;
		}else{
			num=(byte)-(128-tmp);//负数数以补码的形式存在的
		}
		return num;
	}
	
	/*从二进制数字链中获取byte数组的方法*/
	public static byte[] getByteArray(int[] binarylist){
		int num = binarylist.length/8;
		
		byte[] arrbyte = new byte[num];
		
		for(int i=0; i<num; i++){
			int[] templist = cutArray(binarylist, i*8,(i+1)*8-1);
			arrbyte[i] = BinarytoByte(templist);
		}
		
		return arrbyte;
	}
	
	public static int[] cutArray(int[] binary_list, int begin, int end){
		int[] cutarray = new int[end-begin+1];
		
		for(int i=begin; i<=end; i++){
			cutarray[i-begin] = binary_list[i];
		}
		
		return cutarray;
	}
	
	public static void getFile(byte[] bfile, String filePath,String fileName) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath+"/"+fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
	}
	
	public static int Parity(int data){//判断数据的奇偶性
		int result;
		
		if(data%2==0){
			result=0;
		}else{
			result=1;
		}
		
		return result;
	}
	
	/*prgb图像rgb值，fileb文件加密二进制流，条件是R[ X[k+1] + 1 ][ X[k] ] > R[ X[k+1] + 1 ][ X[k+2] ]*/
	public static int Embed_Function1(int gray,int fileb){
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
	public static int Embed_Function2(int gray,int fileb){
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
	public static int Embed_Function3(int gray,int fileb){
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
	public static int Embed_Container(int x, int y){
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
	public static int[][] getRecordMatrix(int[] picture_graylist){
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
	public static int[] Embed_Area(int[] picture_graylist, int[] binary_list){
		
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
                       
                        result[i][j] = bufImg.getRGB(j,i);//只需要整型的低八位，因为图像的rgb值是0-255；getRGB(int x, int y, int rgb)

                  }
                  
             }

        } catch (IOException e) {
             e.printStackTrace();
        }
        return result;
  }
	
	public static int[] getImageIntRGBList(int[][] arrimage){//图片一维数组获得
		int[] rgblist= new int[arrimage.length*arrimage[0].length];
		
		for (int i = 0; i < arrimage.length; i++) {

            for (int j = 0; j < arrimage[i].length; j++) {

                 rgblist[i*arrimage[i].length+j]=arrimage[i][j];

            }
            
       }
		
		return rgblist;
	}
	
	public static ArrayList<int[]> getImageRGBList(int[][] arrimage){//图片一维数组获得
		ArrayList<int[]> argblist= new ArrayList<int[]>();
		ColorModel cm = ColorModel.getRGBdefault();
		
		int[] alphalist= new int[arrimage.length*arrimage[0].length];
		int[] rlist = new int[arrimage.length*arrimage[0].length];
		int[] glist = new int[arrimage.length*arrimage[0].length];
		int[] blist = new int[arrimage.length*arrimage[0].length];
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
	
	public static int[] ListtoVector(ArrayList<int[]> list){
		int[] vector = new int[list.size()*list.get(0).length];
		
		for(int i=0; i<list.size(); i++){
			for(int j=0; j<list.get(0).length; j++){
				vector[i*list.get(0).length + j] = list.get(i)[j];
			}
		}
		
		return vector;
	}
	
	public static ArrayList<int[]> VectortoList(int[] vector, int length){//数组转换成链式结构
		ArrayList<int[]> list = new ArrayList<int[]>();
		
		for(int i=0; i<vector.length/length; i++){
	    list.add(cutArray(vector,i*length,(i+1)*length-1));
		}
		
		return list;
	}
	
	public static int[] ListtoARGBVector(ArrayList<int[]> argblist){
		int[] argbvector = new int[argblist.get(0).length];
		
		for(int i =0; i<argbvector.length; i++){
			argbvector[i] = argblist.get(3)[i]<<24 | argblist.get(2)[i]<<16 | argblist.get(1)[i]<<8 | argblist.get(0)[i];
		}
		
		return argbvector;
	}
	
public static int[][] ARGBVectortoMatrix(int[] vector,int width){//一维数组转换成二维数组
		
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
	

public static void createRGBImage(int[][] matrix, String filepath,String format) throws IOException{
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
