


public class Test4 {
    public static void main(String[] args){
    	int[] arr = {123,234,124,45,24,67,90,56,34,23,12,90,90,90,90,78,78,78,34,23,23,34,34,12,31,23,123,};
    	
    	int[][] R = getRecordMatrix(arr);
    	
    	int sum = 0;
    	for(int i=0; i<R.length; i++){
    		for(int j=0; j<R[0].length; j++){
    			//System.out.print(R[i][j]+" ");
    			sum+=R[i][j];
    		}
    	}
    	System.out.println(arr.length-1);
    	System.out.println(sum);
    }
    
    public static int[][] getRecordMatrix(int[] picture_graylist){
		int[][] record_matrix=new int[256][256];//¼ÇÂ¼¾ØÕó
		
		for(int i=0; i<picture_graylist.length-1; i++){
			record_matrix[ picture_graylist[i] ][ picture_graylist[i+1] ]+=1;
		}
		
		return record_matrix;
	}
}
	


