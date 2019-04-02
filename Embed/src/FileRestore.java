

/*�ļ��ĸ�ԭ������*/
public class FileRestore { 

	/*��ȡ����ķ�����begin�ǻ�ȡ�������ʼλ�ã�end�ǻ�ȡ����Ľ���λ��*/
	public int[] cutArray(int[] binary_list, int begin, int end){
		int[] cutarray = new int[end-begin+1];
		
		for(int i=begin; i<=end; i++){
			cutarray[i-begin] = binary_list[i];
		}
		
		return cutarray;
	}
	
	/*��byte�����cut*/
	public byte[] cutArray(byte[] binary_list, int begin, int end){
		byte[] cutarray = new byte[end-begin+1];
		
		for(int i=begin; i<=end; i++){
			cutarray[i-begin] = binary_list[i];
		}
		
		return cutarray;
	}
	
	/*�Ӷ����������л�ȡ�ļ��ĳ��ȵķ���*/
	public int getEncryptFileLength(int[] binary_length_list){
		int tmp = 0;
		int length=0;
		for(int i=1; i<binary_length_list.length; i++){
			tmp = tmp+(int)Math.pow(2,binary_length_list.length-i-1)*binary_length_list[i];
		}
		if(binary_length_list[0]==0){
			length = tmp;
		}else{
			length=-((int)Math.pow(2, 32) - tmp);
		}
		
		return length;
	}


	/*����������ת����byte�������ݵķ���*/
	public byte BinarytoByte(int[] arr){
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
	public byte[] getByteArray(int[] binarylist){
		int num = binarylist.length/8;
		
		byte[] arrbyte = new byte[num];
		
		for(int i=0; i<num; i++){
			int[] templist = cutArray(binarylist, i*8,(i+1)*8-1);
			arrbyte[i] = BinarytoByte(templist);
		}
		
		return arrbyte;
	}
	
	/*�Ӷ������л�ȡ�ļ��ĺ�׺��*/
	public String getFileExtensions(int[] binarylist){
		byte[] bytes = getByteArray(binarylist);
		int k=0;
		for(int i =0; i<bytes.length; i++){
			if(bytes[i]==0){
				k=i;break;
			}
		}
		byte[] arrbytes=cutArray(bytes,0,k-1);
		
       String name = new String(arrbytes);
       
        return name;
	}
	
	/*�ж����ݵ���ż�Եķ���*/
	public int Parity(int data){
		int result;
		
		if(data%2==0){
			result=0;
		}else{
			result=1;
		}
		return result;
	}
	
	public int[] getBinary(int[] partlist){
		int[] binarylist = new int[partlist.length];
		
		for(int i=0; i<partlist.length; i++){
			binarylist[i] = Parity(partlist[i]);
		}
		
		return binarylist;
	}
	
}
