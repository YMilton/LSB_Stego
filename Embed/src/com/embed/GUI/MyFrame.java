package com.embed.GUI;

import java.awt.Font;
import javax.swing.*;

public class MyFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel jl1,jl2,jl3,jl4;
	
	protected JTextField jtf1,jtf2;
	
	protected JButton jb1,jb2,jbok;
	
	protected JPasswordField jpf1,jpf2;
	
	public MyFrame(){
	setLayout(null);
		
		jl1 = new JLabel("������ͼ��·����");
		jl1.setFont(new Font("����",Font.BOLD,16));
		jl1.setBounds(50, 50, 150, 25);
		add(jl1);
		
		jtf1 = new JTextField("��ѡ������ͼ���·��......");
		jtf1.setFont(new Font("���ķ���",Font.ITALIC,13));
		jtf1.setBounds(180, 50, 240, 25);
		add(jtf1);
		
		jb1 = new JButton("���");
		jb1.setBounds(350, 90, 70, 20);
		jb1.setFont(new Font("����",Font.BOLD,15));
		add(jb1);
		
		jl2 = new JLabel("�����ļ�·����");
		jl2.setFont(new Font("����",Font.BOLD,16));
		jl2.setBounds(60, 150, 150, 25);
		add(jl2);
		
		jtf2 = new JTextField("��ѡ�������ļ���·��......");
		jtf2.setFont(new Font("���ķ���",Font.ITALIC,13));
		jtf2.setBounds(180, 150, 240, 25);
		add(jtf2);
		
		jb2 = new JButton("���");
		jb2.setBounds(350, 190, 70, 20);
		jb2.setFont(new Font("����",Font.BOLD,15));
		add(jb2);
		
		jl3 = new JLabel("�������������룺");
		jl3.setFont(new Font("����",Font.BOLD,16));
		jl3.setBounds(60, 250, 150, 25);
		
		jpf1 = new JPasswordField();
		
		jpf1.setBounds(200, 250, 180, 25);
		
		jl4 = new JLabel("��ȷ���������룺");
		jl4.setFont(new Font("����",Font.BOLD,16));
		jl4.setBounds(60, 320, 150, 25);
		
		jpf2 = new JPasswordField();
		
		jpf2.setBounds(200,320, 180, 25);
		
		jbok = new JButton("ȷ��");
		
		jbok.setBounds(350, 400, 70, 35);
		jbok.setFont(new Font("����",Font.BOLD,15));
		
		add(jl3);
		add(jl4);
		add(jpf1);
		add(jpf2);
		add(jbok);
		
		this.setSize(450, 500);
		this.setVisible(true);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setResizable(false);//�����С���ɸı�		
		this.setLocationRelativeTo(null);//����
		
	}

}
