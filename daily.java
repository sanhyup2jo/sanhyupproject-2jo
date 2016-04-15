package com.example.faceandme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

public class daily extends AppCompatActivity {

    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily);
        init();


    }

    void init(){
        table = (TableLayout)findViewById(R.id.table);


    }

    public void btnClick(View v){
        CheckRow cr = new CheckRow(this);

        table.addView(cr, TableRow.LayoutParams.MATCH_PARENT);


    }

}
