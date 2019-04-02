package com.extract.GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import com.extract.Process.ExtractProcess;
import com.method.FileOpreation.AES;
import com.method.FileOpreation.FileRestore;
import com.method.PictureOpreation.ReadPicture;

public class ExtractFrame extends MyFrame {
	
	private static final long serialVersionUID = 1L;

	private ChImageFrame cf;
	
	private String pictureDirectory,picturePath;
	
	public ExtractFrame(){
		jb.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				cf = new ChImageFrame("����ͼ���ļ�ѡ�񴰿�");
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
					JOptionPane.showMessageDialog(null, "��ѡ������ͼ��");
				}else if(jpf.getText().length()==0){
					JOptionPane.showMessageDialog(null, "�������������룡");
				}else{
					String password = jpf.getText();					
					
					//��ȡ�쳣
					ReadPicture readpicture = new ReadPicture(picturePath,pictureDirectory);
					
					int[] picturelist = readpicture.getPicturelist();//���Ƕ���ļ�ͼƬ����ʽ�ṹ
					
					FileRestore filerestore = new FileRestore(picturelist);//�ļ���ͼƬ�л�ԭ
					
					byte[] file_enbytearray = filerestore.getFile_enbytearray();//��ȡ�����ļ���byte����
					
					AES aes = new AES();
					
					aes.Decrypt(file_enbytearray, password);//�����ƽ⣬��ȡ�쳣����ֵflag
					//��ȡ�쳣
				
					
					if(aes.isFlag())	{
						JOptionPane.showMessageDialog(null, "��������ļ�������ȡ��");
						System.exit(0);
					}else{
						new ExtractProcess(picturePath,pictureDirectory,password);
						JOptionPane.showMessageDialog(null, "�ļ���ȡ�ɹ���");
						System.exit(0);
					}
					
				}			
					
			}
		});
		
		this.setTitle("�ļ���ȡ");
		
	}

}
