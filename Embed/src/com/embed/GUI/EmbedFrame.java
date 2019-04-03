package com.embed.GUI;

import com.embed.Process.EmbedProcess;
import com.method.FileOperation.ReadFile;
import com.method.PictureOperation.ReadPicture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;


public class EmbedFrame extends MyFrame {

    private static final long serialVersionUID = 1L;

    private String pictureDirectory, picturePath, filePath, Key;

    private ChImageFrame cf1;

    private ChFileFrame cf2;

    public EmbedFrame() {

        jb1.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {

                cf1 = new ChImageFrame("图像文件选择窗口");

                picturePath = cf1.getFilePath();

                pictureDirectory = cf1.getFileDirectory();

                if (picturePath != null) {

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


        jb2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent arg0) {

                cf2 = new ChFileFrame("秘密文件选择窗口");

                filePath = cf2.getFilePath();

                if (filePath != null) {

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


        jbok.addActionListener(new ActionListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (picturePath == null || filePath == null) {

                    JOptionPane.showMessageDialog(null, "路径为空，请选择路径！");

                } else if (jpf1.getText().length() == 0) {

                    JOptionPane.showMessageDialog(null, "密码为空，请输入密码！");

                } else if (jpf2.getText().length() == 0) {

                    JOptionPane.showMessageDialog(null, "请再次输入密码！");

                } else if (!jpf1.getText().equals(jpf2.getText())) {

                    JOptionPane.showMessageDialog(null, "两次密码不一致，请重新输入！");

                    jpf1.setText("");

                    jpf2.setText("");

                } else {//加密阶段

                    Key = jpf1.getText();//AES加密密码


                    //filePath  //picturePath 	//pictureDirectory

                    ReadPicture readpicture = new ReadPicture(picturePath, filePath);

                    int alistlength = readpicture.getHeight() * readpicture.getWidth();

                    ReadFile readfile = new ReadFile(filePath, Key);

                    if (readfile.getBinarylist().length >= (readpicture.getPicturelist().length - alistlength)) {//判断加密文件二进制流是否可以嵌入图片内，alistlength是alph通道数组长度

                        JOptionPane.showMessageDialog(null, "文件过大不能嵌入，请选择像素更大的图片！");

                        jtf1.setText("请选择载密图像的路径......");

                        jtf2.setText("请选择秘密文件的路径......");

                        jpf1.setText("");

                        jpf2.setText("");

                    } else {

                        int[] binarylist = readfile.getBinarylist();//文件最终的二进制流数组链

                        int[] picturelist = readpicture.getPicturelist();//图片数组链获得

                        int picture_height = readpicture.getHeight();//图片高度

                        int picture_width = readpicture.getWidth();//图片宽度

                        String extensions = readpicture.getExtensions();//文件后缀名

                        try {

                            new EmbedProcess(binarylist, picturelist, picture_height, picture_width, extensions, pictureDirectory);

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();

                        }

                    }

                }

            }

        });

        this.setTitle("文件嵌入");

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