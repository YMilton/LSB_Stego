package com.embed.GUI;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ChFileFrame extends JFrame {

   private static final long serialVersionUID = 1L;
	
	private String FilePath;//�ļ��ľ���·������

	public ChFileFrame(String framename){
		
		JFileChooser jfc = new JFileChooser();//�ļ�ѡ����
		
		jfc.setApproveButtonText("ѡ��");
		
		jfc.setDialogTitle(framename);
		
        int op = jfc.showOpenDialog(jfc);
		
		if(op==JFileChooser.APPROVE_OPTION){
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
	
}
