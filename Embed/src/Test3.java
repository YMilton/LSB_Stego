import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test3 {
 
    public static void main(String[] args) {
    	
       byte[] arr = toByteArray("F:/a.pdf");
       
       AES key = new AES();
       
       byte[] enarr = key.Encrypt(arr, "1234567890");
       
       File file =  new File("F:/a.pdf");
       
       String extensions = file.getName().substring(file.getName().lastIndexOf(".")+1);
       
       ReadFile readfile = new ReadFile();
       
      int[]  binary =  readfile.getFileBinary(enarr);
      
      FileRestore filerestore = new FileRestore();
      
      byte[] enarr1 = filerestore.getByteArray(binary);
      
      byte[] dearr1 = key.Decrypt(enarr1, "1234567890");
       
       getFile(dearr1,"F:","new."+extensions);
    	
    }
    
    public static byte[] toByteArray(String filePath){//读取文件得到byte数组
		byte[] buffer = null;  
		try{
			File file = new File(filePath);  
			FileInputStream fis = new FileInputStream(file);  
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
			byte[] b = new byte[1000];  
			int n;  
			while ((n = fis.read(b)) != -1) { 
				bos.write(b, 0, n);  
			}  
			fis.close();  
			bos.close();  
			buffer = bos.toByteArray();  
		}catch (FileNotFoundException e) {
			e.printStackTrace();  
		} catch (IOException e) {  
		    e.printStackTrace();  
		}  
		 return buffer;  
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
    
}
