package com.extract.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ChImageFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private String FilePath;//文件的绝对路径返回
	
	private String FileDirectory;//文件所在目录返回

	public ChImageFrame(String framename){
		
		JFileChooser jfc = new JFileChooser();//文件选择器
		
		FileNameExtensionFilter filter  = new FileNameExtensionFilter("BMP,PNG File","bmp","png");
		
		jfc.setFileFilter(filter);//文件的过滤
		
		jfc.setApproveButtonText("选择");
		
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
