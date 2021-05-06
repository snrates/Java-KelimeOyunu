package com.example.kelimegezmece;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.example.kelimegezmece.Data.LevelTable;
import com.example.kelimegezmece.views.CircleChars;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class GameScreenHelper {
    private TableLayout table,bottomTable;
    private CircleChars circleChars;
    private Context context;
    private static final int FINAL_ROW=5;
    private static final int FINAL_COlUMN=6;
    private Button[][] buttons=new Button[FINAL_ROW][FINAL_COlUMN];
   // Button[][] buttomButtons;

    private LevelTable nowLevel;
    private TextView textView;
    public GameScreenHelper(TableLayout table, CircleChars circleChars, Context context, LevelTable nowLevel, TextView textView) {
       this.table=table;
       this.context=context;
       this.nowLevel=nowLevel;
       this.circleChars=circleChars;
       this.textView=textView;
       createScreen();
       createAgainTable();
    }

    private void createScreen() {
        for (int row=0;row<FINAL_ROW;row++){
            TableRow tableRow = new TableRow(context);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for (int column = 0; column <FINAL_COlUMN ; column++) {
                Button button = new Button(context);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setTextColor(Color.WHITE);
                button.setTextSize(50);
                button.setGravity(Gravity.CENTER);
                if (nowLevel.getMAtris()[row][column]!=null){
                    button.setBackgroundResource(R.drawable.null_button_background);
                }else{
                    button.setVisibility(View.INVISIBLE);
                }
                buttons[row][column]=button;
                tableRow.addView(button);
            }
        }
    }

    public boolean control(String cevap){
        boolean response=false;
        List<String> temp = nowLevel.getCevapList();
        System.out.println("beyter+ "+temp.size());
        for(String item: nowLevel.getCevapList()){
            if (cevap.equals(item)) {
                temp.remove(item);
                response=true;
                break;
            }
        }
        if (response){
            HarfleriYerlestir(cevap);
            System.out.println("beyter+ "+temp.size());
            nowLevel.setCevapList(temp);
        }
        return response;
    }
    private void HarfleriYerlestir(String cevap) {
        StringTokenizer tok = new StringTokenizer(nowLevel.getCevaplar(),"-");
        while (tok.hasMoreTokens()){
            StringTokenizer  tempTok = new StringTokenizer(tok.nextToken(),"*");
            if (cevap.equals(tempTok.nextToken())){
                while (tempTok.hasMoreElements()) {
                    StringTokenizer  tempTokTwo = new StringTokenizer(tempTok.nextToken(),"_");
                    int i = Integer.parseInt(tempTokTwo.nextToken());
                    int j = Integer.parseInt(tempTokTwo.nextToken());
                    buttons[i][j].setText(tempTokTwo.nextToken());
                    buttons[i][j].setBackgroundResource(R.drawable.correct_button_background);
                }
            }
        }
    }


    public void createAgainTable() {
        //buttomButtons= new Button[3][3];
        String harfler = karistir(nowLevel.getHarfler());
        circleChars.setWord(harfler);
    }

    private void tikla(String tag) {
        textView.setText(textView.getText()+tag);
    }

    private String karistir(String harfler) {
        char[] temp=harfler.toCharArray();
        Random random= new Random();
        String data="";
        List<Character> characterList =new ArrayList<>();
        for (char c : temp) characterList.add(c);
        int size = characterList.size();
        for (int i = 0; i <size ; i++) {
            int k = random.nextInt(characterList.size());
            data+=characterList.get(k);
            characterList.remove(k);
        }
        return data;
    }

    public void deleteText(String word) {
        textView.setText("");
        circleChars.setWord(word);
    }
}
