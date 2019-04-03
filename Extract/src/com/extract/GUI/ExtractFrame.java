package com.extract.GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import com.extract.Process.ExtractProcess;
import com.method.FileOperation.AES;
import com.method.FileOperation.FileRestore;
import com.method.PictureOpreation.ReadPicture;

public class ExtractFrame extends MyFrame {

	private static final long serialVersionUID = 1L;

	private ChImageFrame cf;

	private String pictureDirectory,picturePath;

	public ExtractFrame(){
		jb.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				cf = new ChImageFrame("载密图像文件选择窗口");
				picturePath = cf.getFilePath();
				pictureDirectory = cf.getFileDirectory();
				if(picturePath!=null){
					jtf.setText(picturePath);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				jb.setForeground(Color.ORANGE);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				jb.setForeground(Color.BLACK);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

		});

		jbok.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(picturePath==null){
					JOptionPane.showMessageDialog(null, "请选择载密图像！");
				}else if(jpf.getText().length()==0){
					JOptionPane.showMessageDialog(null, "请输入您的密码！");
				}else{
					String password = jpf.getText();

					//获取异常
					ReadPicture readpicture = new ReadPicture(picturePath,pictureDirectory);

					int[] picturelist = readpicture.getPicturelist();//获得嵌入文件图片的链式结构

					FileRestore filerestore = new FileRestore(picturelist);//文件从图片中还原

					byte[] file_enbytearray = filerestore.getFile_enbytearray();//获取加密文件的byte数组

					AES aes = new AES();

					aes.Decrypt(file_enbytearray, password);//试着破解，获取异常，赋值flag
					//获取异常


					if(aes.isFlag())	{
						JOptionPane.showMessageDialog(null, "密码错误，文件不能提取！");
						System.exit(0);
					}else{
						new ExtractProcess(picturePath,pictureDirectory,password);
						JOptionPane.showMessageDialog(null, "文件提取成功！");
						System.exit(0);
					}

				}

			}
		});

		this.setTitle("文件提取");

	}

}
