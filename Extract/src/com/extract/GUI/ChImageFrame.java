package com.extract.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ChImageFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private String FilePath;//�ļ��ľ���·������
	
	private String FileDirectory;//�ļ�����Ŀ¼����

	public ChImageFrame(String framename){
		
		JFileChooser jfc = new JFileChooser();//�ļ�ѡ����
		
		FileNameExtensionFilter filter  = new FileNameExtensionFilter("BMP,PNG File","bmp","png");
		
		jfc.setFileFilter(filter);//�ļ��Ĺ���
		
		jfc.setApproveButtonText("ѡ��");
		
		jfc.setDialogTitle(framename);
		
        int op = jfc.showOpenDialog(jfc);
		
		if(op==JFileChooser.APPROVE_OPTION){
			this.FileDirectory=jfc.getCurrentDirectory().getAbsolutePath();
			this.FilePath = jfc.getSelectedFile().getPath();
			this.dispose();
		}
		if(op==JFileChooser.CANCEL_OPTION){
			this.dispose();
		}
	}
	
	public String getFilePath(){
		return FilePath;
	}
	
	public String getFileDirectory(){
		return FileDirectory;
	}
}
