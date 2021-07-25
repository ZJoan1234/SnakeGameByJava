package com.company;

import javax.swing.*;
import java.awt.*;

public class StartGame {
    public static void main(String[] args){
        //创建窗体
        JFrame jf =new JFrame();
        jf.setTitle("TESTSnake by ZJoan");
        int width= Toolkit.getDefaultToolkit().getScreenSize().width;
        int height= Toolkit.getDefaultToolkit().getScreenSize().height;
        jf.setBounds((width-1100)/2,(height-1100)/2,1100,1100);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //创建面板
        GamePanel gp = new GamePanel();
        jf.add(gp);
        jf.setVisible(true);
    }
}
