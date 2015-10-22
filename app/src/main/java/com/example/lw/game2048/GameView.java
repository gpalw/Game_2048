package com.example.lw.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lw on 2015/9/11.
 */
public class GameView extends GridLayout {
    final static int GAME_WEI_HEI=4;
    final static int GAME_BACKCOLOR=0xffbbada0;

    private boolean ismove=false;

    //设置二维数组
    private Card[][] cardsMap=new Card[GAME_WEI_HEI][GAME_WEI_HEI];

    public GameView(Context context) {

        super(context);
        initGameView();
    }


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }


    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    //初始化
    private void initGameView(){
        setColumnCount(GAME_WEI_HEI);
        setBackgroundColor(GAME_BACKCOLOR);

        //设置触摸监听
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;//初始的X,Y.偏移的X,Y

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //设置点击屏幕的时候X,Y值
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //计算偏移量
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        //计算绝对值来判断上下还是左右
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else if (offsetX > 5) {
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                            } else if (offsetY > 5) {
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }
    //宽高发生改变的时候

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth =(Math.min(w,h)-10)/4;
        addCard(cardWidth,cardWidth);
        startGame();
    }


    private List<Point> emptyPoints= new ArrayList<Point>();
    //添加卡片
    private void addCard(int cardWidth,int cardHeight){
        Card c;
        for (int y=0;y<GAME_WEI_HEI;y++){
            for (int x=0;x<GAME_WEI_HEI;x++){
                c=new Card(getContext());
                c.setNum(0);
                //添加到当前GAMEVIEW
                addView(c,cardWidth,cardHeight);

                cardsMap[x][y]=c;
            }
        }
    }
    public void startGame(){
        MainActivity.getMainActivity().clearScore();
        for (int y=0;y<GAME_WEI_HEI;y++) {
            for (int x = 0; x < GAME_WEI_HEI; x++) {
                cardsMap[x][y].setNum(0);
            }
        }
        addRandonNum();//增加一个
        addRandonNum();//增加一个





    }
    //添加随机数
    private void addRandonNum(){
        emptyPoints.clear();
        for (int y=0;y<GAME_WEI_HEI;y++) {
            for (int x = 0; x < GAME_WEI_HEI; x++) {
                if (cardsMap[x][y].getNum()<=0){
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        //添加随机数
        Point p =emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }
    //设置左移方法
    private void swipeLeft(){
        for (int y=0;y<GAME_WEI_HEI;y++){
            for (int x=0;x<GAME_WEI_HEI;x++){
                for (int x1=x+1;x1<GAME_WEI_HEI;x1++){
                    if (cardsMap[x1][y].getNum()>0){        //如果这个方向为空则移到这个位置
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;
                            ismove=true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])){  //如果2个数相等则合并
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            ismove=true;
                        }  break;
                    }
                }
            }
           } if (ismove){addRandonNum();ismove=false;checkComplete();}

    }
    //设置右移方法
    private void swipeRight(){
        for (int y=0;y<GAME_WEI_HEI;y++){
            for (int x=3;x>=0;x--){
                for (int x1=x-1;x1>=0;x1--){
                    if (cardsMap[x1][y].getNum()>0){        //如果这个方向为空则移到这个位置
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            ismove=true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])){  //如果2个数相等则合并
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);

                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            ismove=true;
                        }break;
                    }
                }
            }
        }if (ismove){addRandonNum();ismove=false;checkComplete();}
    }
    //设置上移方法
    private void swipeUp(){
        for (int x=0;x<GAME_WEI_HEI;x++){
            for (int y=0;y<GAME_WEI_HEI;y++){
                for (int y1=y+1;y1<GAME_WEI_HEI;y1++){
                    if (cardsMap[x][y1].getNum()>0){        //如果这个方向为空则移到这个位置
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            ismove=true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])){  //如果2个数相等则合并
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            ismove=true;
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                        }break;
                    }
                }

        } }if (ismove){addRandonNum();ismove=false;checkComplete();}
    }
    //设置下移方法
    private void swipeDown(){
        for (int x=0;x<GAME_WEI_HEI;x++){
            for (int y=3;y>=0;y--){
                for (int y1=y-1;y1>=0;y1--){
                    if (cardsMap[x][y1].getNum()>0){        //如果这个方向为空则移到这个位置
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            ismove=true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])){  //如果2个数相等则合并
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            ismove=true;
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                        }break;
                    }
                }
            }
        }
        if (ismove){addRandonNum();ismove=false;checkComplete();}
    }
    private void checkComplete(){
        boolean complete=true;
        ALL:
        for (int y=0;y<GAME_WEI_HEI;y++){
            for (int x=0;x<GAME_WEI_HEI;x++){
                if (cardsMap[x][y].getNum()==0||
                        (x>1&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))
                        ){
                    complete=false;
                    break ALL;
                }
            }
        }
        if (complete){
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
        }

    }

