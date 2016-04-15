package com.example.faceandme;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by 동현 on 2016-04-13.
 */
public class CheckRow extends TableRow {

    TextView tv;
    RadioButton rbtn1;
    RadioButton rbtn2;
    RadioGroup group;

    public CheckRow(Context context) {
        super(context);
        tv = new TextView(context);
        rbtn1 = new RadioButton(context);
        rbtn2 = new RadioButton(context);
        group = new RadioGroup(context);
        tv.setText("질문");
        group.setOrientation(TableRow.HORIZONTAL);
        group.addView(rbtn1);
        group.addView(rbtn2);

        this.addView(tv);
        this.addView(group);


    }
}
