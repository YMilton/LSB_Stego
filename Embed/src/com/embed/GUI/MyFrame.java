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
		
		jl1 = new JLabel("待载密图像路径：");
		jl1.setFont(new Font("楷体",Font.BOLD,16));
		jl1.setBounds(50, 50, 150, 25);
		add(jl1);
		
		jtf1 = new JTextField("请选择载密图像的路径......");
		jtf1.setFont(new Font("华文仿宋",Font.ITALIC,13));
		jtf1.setBounds(180, 50, 240, 25);
		add(jtf1);
		
		jb1 = new JButton("浏览");
		jb1.setBounds(350, 90, 70, 20);
		jb1.setFont(new Font("楷体",Font.BOLD,15));
		add(jb1);
		
		jl2 = new JLabel("秘密文件路径：");
		jl2.setFont(new Font("楷体",Font.BOLD,16));
		jl2.setBounds(60, 150, 150, 25);
		add(jl2);
		
		jtf2 = new JTextField("请选择秘密文件的路径......");
		jtf2.setFont(new Font("华文仿宋",Font.ITALIC,13));
		jtf2.setBounds(180, 150, 240, 25);
		add(jtf2);
		
		jb2 = new JButton("浏览");
		jb2.setBounds(350, 190, 70, 20);
		jb2.setFont(new Font("楷体",Font.BOLD,15));
		add(jb2);
		
		jl3 = new JLabel("请输入您的密码：");
		jl3.setFont(new Font("楷体",Font.BOLD,16));
		jl3.setBounds(60, 250, 150, 25);
		
		jpf1 = new JPasswordField();
		
		jpf1.setBounds(200, 250, 180, 25);
		
		jl4 = new JLabel("请确定您的密码：");
		jl4.setFont(new Font("楷体",Font.BOLD,16));
		jl4.setBounds(60, 320, 150, 25);
		
		jpf2 = new JPasswordField();
		
		jpf2.setBounds(200,320, 180, 25);
		
		jbok = new JButton("确定");
		
		jbok.setBounds(350, 400, 70, 35);
		jbok.setFont(new Font("楷体",Font.BOLD,15));
		
		add(jl3);
		add(jl4);
		add(jpf1);
		add(jpf2);
		add(jbok);
		
		this.setSize(450, 500);
		this.setVisible(true);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setResizable(false);//窗体大小不可改变		
		this.setLocationRelativeTo(null);//居中
		
	}

}
