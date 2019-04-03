package com.method.FileOperation;

/*文件的复原方法类*/
public class FileRestore {
	private String file_extensions;

	private byte[] file_enbytearray;

	public FileRestore(int[] picturelist){
		int[] binary_file_length = getBinary(cutArray(picturelist,0,31));//加密文件二进制流长度的获得

		int[] binary_extensions_list = getBinary(cutArray(picturelist,32,111));//文件后缀名二进制流的获得

		int encrypt_file_length = getEncryptFileLength(binary_file_length);//长度二进制转换成int

		int[] binary_file_list = getBinary(cutArray(picturelist,112,112+encrypt_file_length-1));//加密文件二进制流获得

//		System.out.println(binary_file_list.length);
//
//		for(int i=0; i<100; i++){
//			System.out.print(binary_file_list[i]+"  ");
//		}
//		System.out.println();

		this.file_extensions = getFileExtensions(binary_extensions_list);//文件的后缀名

		this.file_enbytearray = 	getByteArray(binary_file_list);//加密文件bytes

	}


	public String getFile_extensions() {
		return file_extensions;
	}


	public byte[] getFile_enbytearray() {
		return file_enbytearray;
	}


	/*获取数组的方法，begin是获取数组的起始位置，end是获取数组的结束位置*/
	public int[] cutArray(int[] binary_list, int begin, int end){
		int[] cutarray = new int[end-begin+1];

		for(int i=begin; i<=end; i++){
			cutarray[i-begin] = binary_list[i];
		}

		return cutarray;
	}

	/*对byte数组的cut*/
	public byte[] cutArray(byte[] binary_list, int begin, int end){
		byte[] cutarray = new byte[end-begin+1];

		for(int i=begin; i<=end; i++){
			cutarray[i-begin] = binary_list[i];
		}

		return cutarray;
	}

	/*从二进制数组中获取文件的长度的方法*/
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


	/*二进制数组转换成byte整型数据的方法*/
	public byte BinarytoByte(int[] arr){
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
	public byte[] getByteArray(int[] binarylist){
		int num = binarylist.length/8;

		byte[] arrbyte = new byte[num];

		for(int i=0; i<num; i++){
			int[] templist = cutArray(binarylist, i*8,(i+1)*8-1);
			arrbyte[i] = BinarytoByte(templist);
		}

		return arrbyte;
	}

	/*从二进制中获取文件的后缀名*/
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

	/*判断数据的奇偶性的方法*/
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
