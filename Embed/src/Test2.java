import java.awt.image.ColorModel;
import java.io.IOException;


public class Test2 {

	public static void main(String[] args) throws IOException {
		
		ReadPicture readpicture = new ReadPicture();
		
		int[][] arr1 = readpicture.getImageRGB("F:/new.bmp");
		int[][] arr2 = readpicture.getImageRGB("F:/Tiffy.bmp");
		
		int[] arrlist1 = readpicture.getImageIntRGBList(arr1);
		int[] arrlist2 = readpicture.getImageIntRGBList(arr2);
		
		ColorModel cm=ColorModel.getRGBdefault();
		
		for(int i=112 ; i<500; i++){
			int rgb1 = arrlist1[i];
			int rgb2 = arrlist2[i];
			
			int alpha1=cm.getAlpha(rgb1);
			int alpha2=cm.getAlpha(rgb2);
			
	        int red1=cm.getRed(rgb1);
	        int red2=cm.getRed(rgb2);
	        
	        int green1=cm.getGreen(rgb1);
	        int green2=cm.getGreen(rgb2);
	        
		    int blue1=cm.getBlue(rgb1);
		    int blue2=cm.getBlue(rgb2);
		    System.out.println(alpha2+" "+red2+" "+green2+" "+blue2+"\t\t"+alpha1+" "+red1+" "+green1+" "+blue1);
		}
	}

}
