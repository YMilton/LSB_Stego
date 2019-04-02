package com.embed.GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.embed.Process.EmbedProcess;
import com.method.FileOpreation.ReadFile;
import com.method.PictureOpreation.ReadPicture;

public class EmbedFrame extends MyFrame {
	
	private static final long serialVersionUID = 1L;
	
	private String pictureDirectory,picturePath,filePath,Key;

	private ChImageFrame cf1;
	private ChFileFrame cf2;

	public EmbedFrame(){
		
		jb1.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				cf1 = new ChImageFrame("ͼ���ļ�ѡ�񴰿�");
				picturePath = cf1.getFilePath();
				pictureDirectory = cf1.getFileDirectory();
				if(picturePath!=null){
					jtf1.setText(picturePath);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				jb1.setForeground(Color.ORANGE);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				jb1.setForeground(Color.BLACK);
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
				
		jb2.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				cf2 = new ChFileFrame("�����ļ�ѡ�񴰿�");
				filePath = cf2.getFilePath();
				if(filePath!=null){
					jtf2.setText(filePath);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				jb2.setForeground(Color.ORANGE);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				jb2.setForeground(Color.BLACK);
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
				if(picturePath==null || filePath==null){
					JOptionPane.showMessageDialog(null, "·��Ϊ�գ���ѡ��·����");
				}
				else if(jpf1.getText().length()==0){
					JOptionPane.showMessageDialog(null, "����Ϊ�գ����������룡");
				}else if(jpf2.getText().length()==0){
					JOptionPane.showMessageDialog(null, "���ٴ��������룡");
				}else if(!jpf1.getText().equals(jpf2.getText())){
					JOptionPane.showMessageDialog(null, "�������벻һ�£����������룡");
					jpf1.setText("");
					jpf2.setText("");
				}else{//���ܽ׶�
					Key = jpf1.getText();//AES��������
					
					//filePath  //picturePath 	//pictureDirectory					
				    
				    ReadPicture readpicture=new ReadPicture(picturePath,filePath);
				    
				    int alistlength = readpicture.getHeight()*readpicture.getWidth();
						
					ReadFile readfile=new ReadFile(filePath,Key);
						
					if(readfile.getBinarylist().length >= (readpicture.getPicturelist().length - alistlength)){//�жϼ����ļ����������Ƿ����Ƕ��ͼƬ�ڣ�alistlength��alphͨ�����鳤��
						
						JOptionPane.showMessageDialog(null, "�ļ�������Ƕ�룬��ѡ�����ظ����ͼƬ��");
						
						jtf1.setText("��ѡ������ͼ���·��......");
						jtf2.setText("��ѡ�������ļ���·��......");
						
						jpf1.setText("");
						jpf2.setText("");
						
					}else{
						int[] binarylist = readfile.getBinarylist();//�ļ����յĶ�������������
						
						int[] picturelist = readpicture.getPicturelist();//ͼƬ���������
						
						int picture_height = readpicture.getHeight();//ͼƬ�߶�
						
						int picture_width = readpicture.getWidth();//ͼƬ���
						
						String extensions = readpicture.getExtensions();//�ļ���׺��
						
						try {
							new EmbedProcess(binarylist,picturelist,picture_height,picture_width,extensions,pictureDirectory);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}					
				}
			}
			
		});
		
		this.setTitle("�ļ�Ƕ��");
		
	}
	
	public String getPictureDirectory() {
		return pictureDirectory;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getKey() {
		return Key;
	}

}