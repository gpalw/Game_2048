package com.example.lw.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by lw on 2015/9/11.
 */
public class Card extends FrameLayout {
    final static int GAME_TXTBACKGROUND_COLOR=0x33ffffff;
    private int num=0;

    public Card(Context context) {

        super(context);

        label=new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(GAME_TXTBACKGROUND_COLOR);
        label.setGravity(Gravity.CENTER);
        LayoutParams lp=new LayoutParams(-1,-1);

        lp.setMargins(10,10,0,0);
        addView(label, lp);
        setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num<=0) {
        label.setText("");
        }else {
            label.setText(num+"");
        }
    }


    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }
    private TextView label;
}
