
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
		
		System.out.println("\n��ƥ��ĸ������ڣ�"+c);
		
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
	
	public static int Parity(int data){//�ж����ݵ���ż��
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
			System.out.println("1������û�иı�");
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
			System.out.println("2������û�иı�");
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
				
				int opt = ran.nextInt(2);//����ƽ���ֲ���01�����
				
				if(opt == 0){
					result = gray - 1;
				}else{
					result = gray + 1;
				}
				
			}
		}
		
		if(Parity(result)!=fileb){
			System.out.println("\n"+result+"  "+fileb);
			System.out.println("3������û�иı�");
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
	public static int[] Embed_Area(int[] picture_list, int[] binary_list){
		
		int[] new_picture_list = picture_list;
		
		int[][] record_matrix=getRecordMatrix(picture_list);//��¼����
		
		for(int index=0; index<binary_list.length; index++){
			int m = 0,n = 0;
			if(picture_list[ index ] <=253 && picture_list[ index+1 ] <=254){
				m = record_matrix[ picture_list[ index+1 ]+1 ][ picture_list[ index ] ];  //��¼�����λ��R[ X[k+1] + 1 ][ X[k] ]
				n = record_matrix[ picture_list[ index+1 ]+1 ][ picture_list[ index ]+2 ];  //��¼�����λ�� R[ X[k+1] + 1 ][ X[k+2 ]
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
				if( index>=0 && index<binary_list.length-1){//��ʼλ�ú��м�λ��ʱ��¼����ĸı�
					record_matrix[ picture_list[index] ][ picture_list[index+1] ] -= 1;//��¼�����һ
							
					record_matrix[ picture_list[index]+1 ][ picture_list[index+1]]  +=1;//��¼�����һ
				}
				
				if(index>0 && index<=binary_list.length-1){//ĩβλ�ú��м�λ��ʱ��¼����ĸı�
					record_matrix[ picture_list[index-1] ][ picture_list[index] ] -= 1 ;
					
					record_matrix[ picture_list[index-1] ][ picture_list[index]+1 ] += 1 ;
				}
					
			}	

			if(new_picture_list[index] - picture_list[index]  == -1){
				if( index>=0 && index<binary_list.length-1){//��ʼλ�ú��м�λ��ʱ��¼����ĸı�
					record_matrix[ picture_list[index] ][ picture_list[index+1] ] -= 1;//��¼�����һ
							
					record_matrix[ picture_list[index]-1 ][ picture_list[index+1]]  +=1;//��¼�����һ
				}
				
				if(index>0 && index<=binary_list.length-1){//ĩβλ�ú��м�λ��ʱ��¼����ĸı�
					record_matrix[ picture_list[index-1] ][ picture_list[index] ] -= 1 ;
					
					record_matrix[ picture_list[index-1] ][ picture_list[index]-1 ] += 1 ;
				}
				
			}
			
		}
		
		return new_picture_list;
		
	}
}
