package com.example.jianhua.game2048;

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
 * Created by jianhua on 2015/8/16.
 */
public class GameView  extends GridLayout {

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }
    public GameView(Context context) {
        super(context);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    private void initGameView(){
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
       setOnTouchListener(new View.OnTouchListener() {
           private float startX, startY, offsetX, offsetY;

           @Override
           public boolean onTouch(View v, MotionEvent event) {
               switch (event.getAction()) {
                   case MotionEvent.ACTION_DOWN:
                       startX = event.getX();
                       startY = event.getY();
                       break;
                   case MotionEvent.ACTION_UP:
                       offsetX = event.getX() - startX;
                       offsetY = event.getY() - startY;
                       if (Math.abs(offsetX) > Math.abs(offsetY)) {
                           if (offsetX < -5) {
                               System.out.println("Left");
                               swipeLeft();

                           } else if (offsetX > 5) {
                               System.out.println("Right");
                               swipeRight();

                           }
                       } else {
                           if (offsetY < -5) {
                               System.out.println("UP");
                               swipeUp();


                           } else if (offsetY > 5) {
                               System.out.println("down");
                               swipeDown();

                           }
                       }

                       break;


               }

               return true;
           }
       });
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w,h)-10)/4;
        addCards(cardWidth, cardWidth);
        startGame();
    }


    private  void addCards(int cardWidth,int cardHeight){
        Card c;
        for(int y = 0;y<4;y++){
            for(int x=0;x<4;x++){
                c=new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);
                cardMap[x][y]=c;
            }
        }


    }

    private void startGame(){

        MainActivity.getMainActivity().clearScore();

        for(int y=0;y<4;y++){
            for(int x= 0;x<4;x++){
                cardMap[x][y].setNum(0);
            }
        }
        addRandonNum();
        addRandonNum();


    }

    private  void addRandonNum(){
        emptyPoints.clear();
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
               if(cardMap[x][y].getNum()<=0){
                   emptyPoints.add(new Point(x,y));
               }
            }
        }

        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardMap[p.x][p.y].setNum(Math.random() > 0.2 ? 2: 4);


    }


    private  void swipeLeft(){
        boolean merge = false;

        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                for(int x1=x+1;x1<4;x1++){
                    if(cardMap[x1][y].getNum()>0){
                        if(cardMap[x][y].getNum()<=0){
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            x--;
                            merge = true;

                        }
                        else if(cardMap[x][y].equals(cardMap[x1][y])){
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);
                            cardMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;

                    }


                }


            }


        }
         if(merge){
             addRandonNum();
             checkComplete();
         }


    }
    private  void swipeRight(){
        boolean merge = false;
        for(int y=0;y<4;y++) {
            for (int x = 3; x > 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardMap[x1][y].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x1][y].setNum(0);
                            x++;
                            merge = true;

                        } else if (cardMap[x][y].equals(cardMap[x1][y])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;


                    }


                }

            }

        }
        if(merge){
            addRandonNum();
            checkComplete();
        }

        }
    private  void swipeDown(){
           boolean  merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >=0; y--) {
                for (int y1 = y -1; y1 >=0; y1--) {
                    if (cardMap[x][y1].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y++;
                            merge = true;

                        } else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;

                    }

                }


            }

        }



       if(merge){
           addRandonNum();
           checkComplete();
       }



    }
    private  void swipeUp() {
         boolean merge=false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardMap[x][y1].getNum() > 0) {
                        if (cardMap[x][y].getNum() <= 0) {
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y1].setNum(0);
                            y--;
                            merge = true;

                        } else if (cardMap[x][y].equals(cardMap[x][y1])) {
                            cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
                            cardMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
                            merge = true;
                        }
                        break;

                    }


                }


            }

        }
        if(merge){
            addRandonNum();
            checkComplete();
        }
    }

    private void checkComplete(){

        boolean complete = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardMap[x][y].getNum()==0||
                        (x>0&&cardMap[x][y].equals(cardMap[x-1][y]))||
                        (x<3&&cardMap[x][y].equals(cardMap[x+1][y]))||
                        (y>0&&cardMap[x][y].equals(cardMap[x][y-1]))||
                        (y<3&&cardMap[x][y].equals(cardMap[x][y+1]))) {

                    complete = false;
                    break ALL;
                }
            }
        }

        if (complete) {
            new AlertDialog.Builder(getContext())
                    .setTitle("你好")
                    .setMessage("游戏结束")
                    .setPositiveButton("重来", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startGame();
                        }
                    }).show();
        }

    }


    private  Card[][] cardMap= new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();
}
