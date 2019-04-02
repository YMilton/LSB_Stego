
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
		
		/*�����ļ����������Ļ��*/
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
			
			
			/*��ɫͼƬ���ϵ�һά�����Ļ��*/
			ArrayList<int[]> rgblist = getImageRGBList(getImageRGB(image));
		
			int[] rgbvector = ListtoVector(rgblist);
		
			
			/*���ܶ�������Ƕ��ͼƬ*/
			int[] nrgbvector = Embed_Area(rgbvector,barr);
	       
			
			/*ͼƬ��ά���������*/
			ArrayList<int[]> nrgblist = VectortoList(nrgbvector,getImageRGB(image).length*getImageRGB(image)[0].length);
					
			int[] nrgbvector1= ListtoARGBVector(nrgblist);
			
			int[][] matrix = ARGBVectortoMatrix(nrgbvector1,getImageRGB(image)[0].length);
			//matrix��Ƕ������ļ��Ķ�ά����
			
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
	
	
	
	
	/*����������ת����byte�������ݵķ���*/
	public static byte BinarytoByte(int[] arr){
		byte num=0;
		int tmp=arr[1]*64+arr[2]*32+arr[3]*16+arr[4]*8+arr[5]*4+arr[6]*2+arr[7];
		if(arr[0]==0){
			num=(byte)tmp;
		}else{
			num=(byte)-(128-tmp);//�������Բ������ʽ���ڵ�
		}
		return num;
	}
	
	/*�Ӷ������������л�ȡbyte����ķ���*/
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
            if(!dir.exists()&&dir.isDirectory()){//�ж��ļ�Ŀ¼�Ƿ����  
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
	
	public static int Parity(int data){//�ж����ݵ���ż��
		int result;
		
		if(data%2==0){
			result=0;
		}else{
			result=1;
		}
		
		return result;
	}
	
	/*prgbͼ��rgbֵ��fileb�ļ����ܶ���������������R[ X[k+1] + 1 ][ X[k] ] > R[ X[k+1] + 1 ][ X[k+2] ]*/
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
	
	/*prgbͼ��rgbֵ��fileb�ļ����ܶ���������������R[ X[k+1] + 1 ][ X[k] ] < R[ X[k+1] + 1 ][ X[k+2] */
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
	
	/*prgbͼ��rgbֵ��fileb�ļ����ܶ���������������R[ X[k+1] + 1 ][ X[k] ] = R[ X[k+1] + 1 ][ X[k+2]*/
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

	/*��¼��������ɣ�picture_graylist����ͼƬ����ʽ�ҶȾ���*/
	public static int[][] getRecordMatrix(int[] picture_graylist){
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
	public static int[] Embed_Area(int[] picture_graylist, int[] binary_list){
		
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
	
	public static int[][] getImageRGB(String picturePath) {//��ȡͼƬÿ�����ص�ֵ�ķ����������ļ�·��

        File filename  = new File(picturePath);

        int[][] result = null;

        try {

             BufferedImage bufImg = ImageIO.read(filename);//ͼ��Ķ�ȡ�Լ��洢��ͼ�����ݻ�����

             int height = bufImg.getHeight();

             int width = bufImg.getWidth();

             result = new int[height][width];

             for (int i = 0; i < height; i++) {

                  for (int j = 0; j < width; j++) {
                       
                        result[i][j] = bufImg.getRGB(j,i);//ֻ��Ҫ���͵ĵͰ�λ����Ϊͼ���rgbֵ��0-255��getRGB(int x, int y, int rgb)

                  }
                  
             }

        } catch (IOException e) {
             e.printStackTrace();
        }
        return result;
  }
	
	public static int[] getImageIntRGBList(int[][] arrimage){//ͼƬһά������
		int[] rgblist= new int[arrimage.length*arrimage[0].length];
		
		for (int i = 0; i < arrimage.length; i++) {

            for (int j = 0; j < arrimage[i].length; j++) {

                 rgblist[i*arrimage[i].length+j]=arrimage[i][j];

            }
            
       }
		
		return rgblist;
	}
	
	public static ArrayList<int[]> getImageRGBList(int[][] arrimage){//ͼƬһά������
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
	
	public static ArrayList<int[]> VectortoList(int[] vector, int length){//����ת������ʽ�ṹ
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
	
public static int[][] ARGBVectortoMatrix(int[] vector,int width){//һά����ת���ɶ�ά����
		
		int[][] matrix = new int[vector.length/width][width] ;//��������Ŀ�͸�
		
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
    
    BufferedImage rgbImage = new BufferedImage(width, height,  BufferedImage.TYPE_INT_RGB);//ͼƬ�������洢�Ҷ�ͼ��
    	for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {    
            	rgbImage.setRGB(j, i, matrix[i][j]);
            }
        }   
    
    ImageIO.write(rgbImage, format, outputfile);
}

	
}
