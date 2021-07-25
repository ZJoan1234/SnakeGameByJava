package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/*
 *创建面板,自动调用paintComponent
 */
public class GamePanel extends JPanel {
    //int类型数组定义蛇的xy轴坐标
    int[] snakeX =new int[200];
    int[] snakeY =new int[200];
    int length;
    String direction;
    Boolean isStart =false;//默认游戏停止状态
    Timer timer;
    int foodX;
    int foodY;
    int score;
    boolean isdead = false;
    public void init(){
        length = 3;
        //初始化坐标
        snakeX[0]=175;
        snakeY[0]=475;
        snakeX[1]=150;
        snakeY[1]=475;
        snakeX[2]=125;
        snakeY[2]=475;
        direction="R";
        foodX = 600;
        foodY = 400;
        score = 0;
    }
    public GamePanel(){
        init();
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {//监听键盘按压
                super.keyPressed(e);
                int keyCode=e.getKeyCode();
//                System.out.println(keyCode);
                if(keyCode == KeyEvent.VK_SPACE){ //监听空格
                    if(isdead){
                        init();
                        isdead = false;
                    }else {
                        isStart = !isStart;
                        repaint();//重绘
                    }
                }
                if(keyCode == KeyEvent.VK_UP){ //监听向上箭头
                    direction = "U";
                }
                if(keyCode == KeyEvent.VK_DOWN){ //监听向下箭头
                    direction = "D";
                }
                if(keyCode == KeyEvent.VK_LEFT){ //监听向左箭头
                    direction = "L";
                }
                if(keyCode == KeyEvent.VK_RIGHT){ //监听向右箭头
                    direction = "R";
                }

            }
        });
        timer = new Timer(200, new ActionListener() {
            //事件监听
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isStart && isdead == false){
                    //后一节身子移动到前一节身子
                    for (int i= length-1;i>0;i--){
                        snakeX[i]=snakeX[i-1];
                        snakeY[i]=snakeY[i-1];
                    }
                    //动头
                    if(direction == "R"){
                        snakeX[0] += 25;
                    }
                    if("L".equals(direction)){
                        snakeX[0] -= 25;
                    }
                    if("U".equals(direction)){
                        snakeY[0] -= 25;
                    }
                    if("D".equals(direction)){
                        snakeY[0] += 25;
                    }
                    //防止蛇超出边界
                    if(snakeX[0]>1100){
                        snakeX[0]=25;
                    }
                    if(snakeX[0]<25){
                        snakeX[0]=1100;
                    }
                    if(snakeY[0]<220){
                        snakeY[0]=1050;
                    }
                    if(snakeY[0]>1050){
                        snakeY[0]=220;
                    }
                    //检查碰撞动作:蛇头坐标=食物坐标
                    if(snakeX[0] == foodX && snakeY[0] == foodY){
                        length++;
                        //食物坐标改变，随机生成25倍数
                        foodX = ((int) (Math.random()*28)+4)*25;  //[100,800]=[4,32]*25  [0.0,1.0)->[0.0,32.0)->[0,43]->[1,44]
                        foodY =(new Random().nextInt(23)+14)*25;    //[350,900]=[14,36]*25 [0,22]+14
                        score += 10;
                    }
                    //死亡判定
                    for (int i = 1;i <length;i++){
                        if(snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]){
                            isdead =true;
                        }
                    }
                    repaint();//重绘
                }
            }
        });
        //启动定时器
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(201, 184, 184));
        Images.headerImg.paintIcon(this,g,10,10);
        g.setColor(new Color(238, 225, 225));
        g.fillRect(10,230,1100,800);

        //画蛇
        if("R".equals(direction)){
            Images.rightImg.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        if("L".equals(direction)){
            Images.leftImg.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        if("U".equals(direction)){
            Images.upImg.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        if("D".equals(direction)){
            Images.downImg.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
//        Images.bodyImg.paintIcon(this,g,snakeX[1],snakeY[1]);
//        Images.bodyImg.paintIcon(this,g,snakeX[2],snakeY[2]);
        for(int i = 1;i<length;i++){
            Images.bodyImg.paintIcon(this,g,snakeX[i],snakeY[i]);
        }

        //加提示
        if (isStart == false){
            g.setColor(new Color(158, 112, 112));
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("点击空格开始游戏",400,300);
        }

        //画食物
        Images.foodImg.paintIcon(this,g,foodX,foodY);

        //画积分
        g.setColor(new Color(165, 151, 151));
        g.setFont(new Font("微软雅黑",Font.BOLD,20));
        g.drawString("积分："+score,800,40);

        //画死亡状态
        if(isdead){
            g.setColor(new Color(177, 41, 41));
            g.setFont(new Font("微软雅黑",Font.BOLD,30));
            g.drawString("游戏结束，按下空格重新开始游戏",330,500);
        }
    }
}
