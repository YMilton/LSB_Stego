package com.extract.GUI;

import java.awt.Font;
import javax.swing.*;

public class MyFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel jl1,jl2;
	
	protected JTextField jtf;
	
	protected JButton jb,jbok;
	
	protected JPasswordField jpf,jpf2;
	
	public MyFrame(){
	setLayout(null);
		
		jl1 = new JLabel("����ͼ��·����");
		jl1.setFont(new Font("����",Font.BOLD,16));
		jl1.setBounds(60, 100, 150, 25);
		add(jl1);
		
		jtf = new JTextField("��ѡ������ͼ���·��......");
		jtf.setFont(new Font("���ķ���",Font.ITALIC,13));
		jtf.setBounds(180, 100, 240, 25);
		add(jtf);
		
		jb = new JButton("���");
		jb.setBounds(350, 150, 70, 20);
		jb.setFont(new Font("����",Font.BOLD,15));
		add(jb);
		
		jl2 = new JLabel("�������������룺");
		jl2.setFont(new Font("����",Font.BOLD,16));
		jl2.setBounds(60, 200, 150, 25);
		
		jpf = new JPasswordField();
		jpf.setBounds(190, 200, 235, 25);
		
		jbok = new JButton("ȷ��");
		jbok.setBounds(350, 280, 70, 35);
		jbok.setFont(new Font("����",Font.BOLD,15));
		
		add(jl2);
		add(jpf);
		add(jbok);
		
		this.setSize(450, 400);
		this.setVisible(true);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setResizable(false);//�����С���ɸı�		
		this.setLocationRelativeTo(null);//����
		
	}

}
