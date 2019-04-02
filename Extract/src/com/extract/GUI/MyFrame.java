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
		
		jl1 = new JLabel("载密图像路径：");
		jl1.setFont(new Font("楷体",Font.BOLD,16));
		jl1.setBounds(60, 100, 150, 25);
		add(jl1);
		
		jtf = new JTextField("请选择载密图像的路径......");
		jtf.setFont(new Font("华文仿宋",Font.ITALIC,13));
		jtf.setBounds(180, 100, 240, 25);
		add(jtf);
		
		jb = new JButton("浏览");
		jb.setBounds(350, 150, 70, 20);
		jb.setFont(new Font("楷体",Font.BOLD,15));
		add(jb);
		
		jl2 = new JLabel("请输入您的密码：");
		jl2.setFont(new Font("楷体",Font.BOLD,16));
		jl2.setBounds(60, 200, 150, 25);
		
		jpf = new JPasswordField();
		jpf.setBounds(190, 200, 235, 25);
		
		jbok = new JButton("确定");
		jbok.setBounds(350, 280, 70, 35);
		jbok.setFont(new Font("楷体",Font.BOLD,15));
		
		add(jl2);
		add(jpf);
		add(jbok);
		
		this.setSize(450, 400);
		this.setVisible(true);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setResizable(false);//窗体大小不可改变		
		this.setLocationRelativeTo(null);//居中
		
	}

}
