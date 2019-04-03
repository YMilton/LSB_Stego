package com.extract.Process;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.method.FileOperation.AES;
import com.method.FileOperation.FileRestore;
import com.method.PictureOpreation.ReadPicture;

public class ExtractProcess {


    public ExtractProcess(String picturePath,String pictureDirectory,String password){

        ReadPicture readpicture = new ReadPicture(picturePath,pictureDirectory);

        int[] picturelist = readpicture.getPicturelist();//获得嵌入文件图片的链式结构

        FileRestore filerestore = new FileRestore(picturelist);//文件从图片中还原

        byte[] file_enbytearray = filerestore.getFile_enbytearray();//获取加密文件的byte数组

        AES aes = new AES();

        byte[] file_bytearray = aes.Decrypt(file_enbytearray, password);

        String file_extensions = filerestore.getFile_extensions();

        getFile(file_bytearray,pictureDirectory,("Extract_File."+file_extensions));

    }

    public void getFile(byte[] bfile, String filePath,String fileName) {//文件的写入
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+"\\"+fileName);
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
