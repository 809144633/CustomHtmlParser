package com.ehi.customhtmltag;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        String txt = "<custom><span style=\"color:1111;font-size:50px;background-color:#FFFF00\">测试内容<span style=\"color:#AAFFAF;font-size:25px\"><u>测试内容</u></span></span><font color='#CCFFCC'>网上搜</font></custom>";
        tv.setText(HtmlUtil.fromHtml(txt, this));
        btn = findViewById(R.id.btn);
        String txt2 = "<custom><p>话题故事内容，巴坎布副对戒多家分店。反馈到洛杉矶发动机弗兰克多家分" +
                "店发的，都快疯了接口及打开了辅导费的，f'k'd'j'l'k'f'j'd'f'd</p><p><span style=\"color: " +
                "#ff7e00; font-size: 20px;\">话题故事内容，巴坎布副对戒多家分店。反馈到洛杉矶发动机弗兰克多家分店发" +
                "的，都快疯了接口及打开了辅导费的，飞快的将离开房间大幅度。</span><span style=\"color: #27ad9a; font" +
                "-size: 16px;font-weight:bold\">话题故事内容，巴坎布副对戒多家分店。反馈到洛杉矶发动机弗兰克多家分店发的，" +
                "都快疯了接口及打开了辅导费的，飞快的将离开房间大幅度。</span><br><span style=\"color: #333333; font-size" +
                ": 25px;\">话题故事内容，巴坎布副对戒多家分店。反馈到洛杉矶发动机弗兰克多家分店发的，都快疯了接口及打开了辅导费的，飞快" +
                "的将离开房间大幅度。</span><br></p></custom>";
        String txt3 = "<custom><span>普通样式文本</span><br><span style=\"color: #ff7e00; font-size: 20px;\">黄色文字，字号偏大。</span>" +
                "<span style=\"color: #27ad9a; font-size: 20px;font-weight:bold\">" +
                "后面的绿色文字，字体加粗的。</span><br><del>中间的删除线文本</del></custom>";

        String txt4 = "<custom><span style=\"color: #E9B159; font-size: 12px;\">AA<span style=\"font-size: 25px;\">BB<span style=\"color: #FF3359; font-size: 12px;\">AA<span style=\"font-size: 25px;\">BB</span>DD</span></span>CC</span></custom>";
        String txt5 = "<custom><span style=\"color: #E9B159; font-size: 20px;\">AA<span style=\"font-size: 25px;\">BB</span>CC</span></custom>";
        String txt6 = "<custom><span style=\"color: #E9B159; font-size: 16px;\">AA</span><span style=\"font-size: 20px;\">BB</span></custom>";
        String txt7 = "<span style=\"color: #E9B159; background-color: #FF00FF;\">AA</span><span style=\"background-color: #FF0000;\">BB</span>";
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(HtmlUtil.fromHtml(txt7,MainActivity.this));
            }
        });

    }
}