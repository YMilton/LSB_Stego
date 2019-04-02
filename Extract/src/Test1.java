

public class Test1 {

	public static void main(String[] args) {
        
		int a = -115;
		
		int[] arr = BytetoBinary(a);
		
		for(int i=0; i<arr.length; i++){
			System.out.print(arr[i]+" ");
		}
		
		byte b = BinarytoByte(arr);
		
		System.out.println("\n"+b);
		
        int a1 = 13;
		
		int[] arr1 = BytetoBinary(a1);
		
		for(int i=0; i<arr1.length; i++){
			System.out.print(arr1[i]+" ");
		}
		
		byte b1 = BinarytoByte(arr1);
		
		System.out.println("\n"+b1);
	}
	
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
	
	public static int[] BytetoBinary(int num){//ת����8λ�������������Զ�ÿ�����ص�ת��
		int[] binary_arr=new int[8];
		for(int i=0; i<8; i++){
			binary_arr[i]=1&(num>>(7-i));//ÿ����һλ���к�1 ����
		}
		return binary_arr;
	}
	
}
