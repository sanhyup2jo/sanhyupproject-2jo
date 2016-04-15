package com.example.faceandme;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class clientInfo extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    int[] faceCOLOR = {Color.rgb(255,255,255),Color.rgb(0,0,0),Color.rgb(0,225,0),Color.rgb(255,0,0)};
    boolean[] check = {false, false, false, false, false, false};  //처음에 선택된거 안된거 확인할 변수
    int[] back = {0, 0, 0, 0, 0, 0};                   // 다시 선택할 경우 이전 이미지 선택 지워주기
    Drawable[] selected = {null,null,null,null,null,null};    //선택된 drawable저장
    int[] colors = {0,0};   //0은 머리 1은 얼굴색, 초기값 0번으로

    int gender;
    ImageView vi;
    Button clear;
    EditText name, year, mon, day;

    RadioGroup radioGroup;
    RadioButton man, women;

    Spinner hairColor, faceColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_info);
        init();
    }

    void init() {
        name = (EditText)findViewById(R.id.name);
        year = (EditText)findViewById(R.id.year);
        mon = (EditText)findViewById(R.id.mon);
        day = (EditText)findViewById(R.id.day);
        man = (RadioButton)findViewById(R.id.man);
        women = (RadioButton)findViewById(R.id.women);

        hairColor = (Spinner) findViewById(R.id.hiarColor);
        faceColor = (Spinner) findViewById(R.id.faceColor);

        ArrayList<String> hairColorArr = new ArrayList<String>();
        ArrayList<String> faceColorArr = new ArrayList<String>();
        hairColorArr.add("검정색");hairColorArr.add("갈색");hairColorArr.add("밝은갈색");hairColorArr.add("노란색");
        faceColorArr.add("");faceColorArr.add("");faceColorArr.add("");faceColorArr.add("");

        //헤어색
        ArrayAdapter<String> hairColorAd = new ArrayAdapter<String>(
                this, R.layout.gallery_item, hairColorArr) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setBackgroundColor(faceCOLOR[position]);
                if(position == 1)   //검정색이면 글씨색이 안보이니까 글자색 바꿔주야댐
                {
                    tv.setTextColor(Color.WHITE);
                }
                return view;
            }
        };

        hairColorAd.setDropDownViewResource(R.layout.gallery_item);
        hairColor.setAdapter(hairColorAd);

        hairColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //hair색상선택하면 처리
                hairColor.setBackgroundColor(faceCOLOR[position]);
                colors[0] = position;   //선택한 포지션(색깔)저장
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 얼굴색
        ArrayAdapter<String> faceColorad = new ArrayAdapter<String>(
                this, R.layout.gallery_item, faceColorArr) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setBackgroundColor(faceCOLOR[position]);
                return view;
            }
        };

        faceColorad.setDropDownViewResource(R.layout.gallery_item);
        faceColor.setAdapter(faceColorad);

        faceColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                faceColor.setBackgroundColor(faceCOLOR[position]);
                colors[1] = position;   //선택한 포지션(색깔)저장
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        clear = (Button) findViewById(R.id.clear);  //모든작업을 끝내고 나면
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //모든항목 다 작성했는지 확인. 안한곳있으면 거기로 포커스주기. 생년월일에 숫자만.
                if (name.getText().length() != 0 && year.getText().length() != 0 && mon.getText().length() != 0 && day.getText().length() != 0) {
                    if (isStringDouble(year.getText().toString()) && isStringDouble(mon.getText().toString()) && isStringDouble(day.getText().toString())) {
                        if (man.isChecked() == false && women.isChecked() == false) {
                            Toast.makeText(getApplicationContext(), "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
                        }else{
                        for (int i = 0; i < 6; i++) {
                            if (selected[i] != null) {
                                if (i == 5) {
                                    //여기서 디비에 하나하나 넣어주면 된다!!!!!!
                                    //name, year, mon, day, gender, colors[0~1], selected[0~6]
                                    //->이름,년,  월, 일,  성별,  머리색~얼굴색,선택한얼굴들
                                    finish();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "선택하지않은 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        }
                    } else {  //생년월일에 문자썼다.
                        Toast.makeText(getApplicationContext(), "생년월일을 잘못입력하셨습니다!", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "별명과 생년월일을 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
    }


    public static boolean isStringDouble(String s) {    //생년월일에서 들어온값이 숫자인지 확인하기위한 함수
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    void check(int t, int x) {  //얼굴선택 처리
            if (!check[x]) { //처음이면
                back[x] = t;       //나중에 눌리면 선택된거 풀어줘야되니까 이전꺼 id 저장
                vi = (ImageView) findViewById(t);
                vi.setImageResource(R.drawable.image_border);
                check[x] = !check[x];
                selected[x] = vi.getBackground();   //선택한 리소스
            } else {      //처음이 아니면
                ImageView back_img;
                back_img = (ImageView) findViewById(back[x]);
                back_img.setImageResource(R.drawable.image_noborder);   //이전선택 풀어주기

                back[x] = t;       //나중에 눌리면 선택된거 풀어줘야되니까 이전꺼 id 저장
                vi = (ImageView) findViewById(t);
                vi.setImageResource(R.drawable.image_border);
                selected[x] = vi.getBackground();   //선택한 리소스
            }
    }


    public void hairClick(View v){
        int t = v.getId();
        if(t == R.id.hair1 || t == R.id.hair2 || t == R.id.hair3 || t == R.id.hair4) {
          check(t,0);
        }else if(t == R.id.face1 || t == R.id.face2 || t == R.id.face3 || t == R.id.face4) {
           check(t,1);
        }else if(t == R.id.brow1 || t == R.id.brow2 || t == R.id.brow3 || t == R.id.brow4) {
            check(t,2);
        }else if(t == R.id.eye1 || t == R.id.eye2 || t == R.id.eye3 || t == R.id.eye4) {
            check(t,3);
        }else if(t == R.id.nose1 || t == R.id.nose2 || t == R.id.nose3 || t == R.id.nose4) {
            check(t,4);
        }else if(t == R.id.lip1 || t == R.id.lip2 || t == R.id.lip3 || t == R.id.lip4) {
            check(t,5);
        }
        //Toast.makeText(this, String.valueOf(t),Toast.LENGTH_SHORT).show();
    }

    //라디오 버튼 남자선택했는지 여자선택했는지 처리
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
       gender = checkedId;
    }
}










