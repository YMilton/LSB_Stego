
import java.util.Random;

public class Test {

	public static void main(String[] args) {
		
		int[] arr = createRGBVector(4196352);
		
		
		int[] barr = createBinaryVector(532864);
		
		
		
		int[] arr1 = Embed_Area(arr,barr);
		
	    int[] barr1 = new int[barr.length];
	    
		for(int i=0; i<barr1.length; i++){
			barr1[i]=Parity(arr1[i]);
		}
		
		
		int c=0;
		for(int i=0; i<barr.length; i++){
			if(barr1[i]!=barr[i]){
				c++;
			}
		}
		
		System.out.println("\n不匹配的个数等于："+c);
		
	}
	
	public static int[] createBinaryVector(int num){
		int[] vector = new int[num];
		
		Random ran = new Random();
		
		for(int i=0; i<num; i++){
			vector[i] = ran.nextInt(2);
		}
		
		return vector;
	}
	
	
	public static int[] createRGBVector(int num){
		int[] vector = new int[num];
		
		Random ran = new Random();
		
		for(int i=0; i<num; i++){
			vector[i] = ran.nextInt(256);
		}
		
		return vector;
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
	
	
	
	
	
	public static int Embed_Function1(int gray,int fileb){
		int result=gray;
		if(Parity(gray)!=fileb){
			if(gray>=0 && gray<255){
				result = gray + 1;
			}
			if(gray==255){
				result = gray - 1;
			}
		}
		
		if(Parity(result)!=fileb){
			System.out.println("\n"+result+"  "+fileb);
			System.out.println("1、像素没有改变");
		}
		
		return result;
	}
	
	public static int Embed_Function2(int gray,int fileb){
		int result=gray;
		if(Parity(gray)!=fileb){
			if(gray>0 && gray<=255){
				result = gray - 1;
			}
			if(gray==0){
				result = gray + 1;
			}
		}
		
		if(Parity(result)!=fileb){
			System.out.println("\n"+result+"  "+fileb);
			System.out.println("2、像素没有改变");
		}
		
		return result;
	}
	
	public static int Embed_Function3(int gray,int fileb){
		int result=gray;
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
				}else{
					result = gray + 1;
				}
				
			}
		}
		
		if(Parity(result)!=fileb){
			System.out.println("\n"+result+"  "+fileb);
			System.out.println("3、像素没有改变");
		}
		
		return result;
	}
	
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
	public static int[] Embed_Area(int[] picture_list, int[] binary_list){
		
		int[] new_picture_list = picture_list;
		
		int[][] record_matrix=getRecordMatrix(picture_list);//记录矩阵
		
		for(int index=0; index<binary_list.length; index++){
			int m = 0,n = 0;
			if(picture_list[ index ] <=253 && picture_list[ index+1 ] <=254){
				m = record_matrix[ picture_list[ index+1 ]+1 ][ picture_list[ index ] ];  //记录矩阵的位置R[ X[k+1] + 1 ][ X[k] ]
				n = record_matrix[ picture_list[ index+1 ]+1 ][ picture_list[ index ]+2 ];  //记录矩阵的位置 R[ X[k+1] + 1 ][ X[k+2 ]
			}
			
			if(Embed_Container(m,n)==1){
				new_picture_list[ index ] = Embed_Function1(picture_list[ index ], binary_list[ index ]);
			}
			
			if(Embed_Container(m,n)==-1){
				new_picture_list[ index ] = Embed_Function2(picture_list[ index ], binary_list[ index ]);
			}
			
			if(Embed_Container(m,n)==0){
				new_picture_list[ index ] = Embed_Function3(picture_list[ index ], binary_list[ index ]);
			}
			
			if(new_picture_list[index] - picture_list[index] == 1){
				if( index>=0 && index<binary_list.length-1){//起始位置和中间位置时记录矩阵的改变
					record_matrix[ picture_list[index] ][ picture_list[index+1] ] -= 1;//记录矩阵减一
							
					record_matrix[ picture_list[index]+1 ][ picture_list[index+1]]  +=1;//记录矩阵加一
				}
				
				if(index>0 && index<=binary_list.length-1){//末尾位置和中间位置时记录矩阵的改变
					record_matrix[ picture_list[index-1] ][ picture_list[index] ] -= 1 ;
					
					record_matrix[ picture_list[index-1] ][ picture_list[index]+1 ] += 1 ;
				}
					
			}	

			if(new_picture_list[index] - picture_list[index]  == -1){
				if( index>=0 && index<binary_list.length-1){//起始位置和中间位置时记录矩阵的改变
					record_matrix[ picture_list[index] ][ picture_list[index+1] ] -= 1;//记录矩阵减一
							
					record_matrix[ picture_list[index]-1 ][ picture_list[index+1]]  +=1;//记录矩阵加一
				}
				
				if(index>0 && index<=binary_list.length-1){//末尾位置和中间位置时记录矩阵的改变
					record_matrix[ picture_list[index-1] ][ picture_list[index] ] -= 1 ;
					
					record_matrix[ picture_list[index-1] ][ picture_list[index]-1 ] += 1 ;
				}
				
			}
			
		}
		
		return new_picture_list;
		
	}
}
