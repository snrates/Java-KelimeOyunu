package com.example.kelimegezmece;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.kelimegezmece.Data.DbGame;
import com.example.kelimegezmece.Data.LevelTable;

import java.util.List;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    //region myData
        String myData="AMÇ-MAÇ*1_2_M*2_2_A*3_2_Ç-ÇAM*2_1_Ç*2_2_A*2_3_M\n" +
            "UŞT-ŞUT*1_2_Ş*2_2_U*3_2_T-TUŞ*3_2_T*3_3_U*3_4_Ş\n" +
            "AKŞ-AŞK*2_2_A*2_3_Ş*2_4_K-KAŞ*0_3_K*1_3_A*2_3_Ş\n" +
            "NEŞE-NEŞE*2_1_N*2_2_E*2_3_Ş*2_4_E-ŞEN*1_2_Ş*2_2_E*3_2_N\n" +
            "ACAM-CAM*1_4_C*2_4_A*3_4_M-AMCA*1_2_A*1_3_M*1_4_C*1_5_A-AMA*0_3_A*1_3_M*2_3_A\n" +
            "İMÜT-ÜMİT*0_1_Ü*0_2_M*0_3_İ*0_4_T-TİM*2_2_T*2_3_İ*2_4_M-MİT*0_2_M*1_2_İ*2_2_T-TÜM*0_4_T*1_4_Ü*2_4_M\n" +
            "ARYÜ-RÜYA*1_1_R*1_2_Ü*1_3_Y*1_4_A-RAY*1_1_R*2_1_A*3_1_Y-YAR*3_1_Y*3_2_A*3_3_R\n" +
            "KOŞU-KOŞU*0_2_K*1_2_O*2_2_Ş*3_2_U-ŞOK*2_2_Ş*2_3_O*2_4_K-KUŞ*2_4_K*3_4_U*4_4_Ş\n" +
            "NABİ-ANİ*2_4_A*3_4_N*4_4_İ-ABİ*1_1_A*2_1_B*3_1_İ-BİNA*2_1_B*2_2_İ*2_3_N*2_4_A-BİN*0_3_B*1_3_İ*2_3_N\n" +
            "YIPA-YAPI*2_1_Y*2_2_A*2_3_P*2_4_I-AYI*0_4_A*1_4_Y*2_4_I-AYIP*1_1_A*2_1_Y*3_1_I*4_1_P-PAY*4_1_P*4_2_A*4_3_Y\n" +
            "İDÇN-ÇİN*2_4_Ç*3_4_İ*4_4_N-DİN*0_3_D*1_3_İ*2_3_N-DİNÇ*2_1_D*2_2_İ*2_3_N*2_4_Ç\n" +
            "AMAD-DAM*2_2_D*3_2_A*4_2_M-DAMA*0_4_D*1_4_A*2_4_M*3_4_A-ADA*0_3_A*0_4_D*0_5_A-ADAM*2_1_A*2_2_D*2_3_A*2_4_M-AMA*0_1_A*1_1_M*2_1_A\n" +
            "RMAG-GRAM*1_1_G*1_2_R*1_3_A*1_4_M-GAR*1_1_G*2_1_A*3_1_R-GAM*0_3_G*1_3_A*2_3_M\n" +
            "KIAT-KITA*1_1_K*1_2_I*1_3_T*1_4_A-ATIK*0_3_A*1_3_T*2_3_I*3_3_K-KATI*1_1_K*2_1_A*3_1_T*4_1_I-TAKI*3_1_T*3_2_A*3_3_K*3_4_I\n" +
            "AMDO-MODA*2_1_M*2_2_O*2_3_D*2_4_A-ODA*2_2_O*3_2_D*4_2_A-DAM*0_1_D*1_1_A*2_1_M\n" +
            "ELİÇ-ÇİLE*0_2_Ç*1_2_İ*2_2_L*3_2_E-ELÇİ*3_2_E*3_3_L*3_4_Ç*3_5_İ-ÇİL*2_5_Ç*3_5_İ*4_5_L-İLE*2_3_İ*3_3_L*4_3_E-İLÇE*0_0_İ*0_1_L*0_2_Ç*0_3_E\n" +
            "YBOA-BOYA*2_1_B*2_2_O*2_3_Y*2_4_A-BOY*0_3_B*1_3_O*2_3_Y-BAY*1_4_B*2_4_A*3_4_Y-OYA*2_2_O*3_2_Y*4_2_A-OBA*1_3_O*1_4_B*1_5_A\n" +
            "ACEZ-ECZA*1_1_E*1_2_C*1_3_Z*1_4_A-EZA*0_3_E*1_3_Z*2_3_A-CEZA*1_2_C*2_2_E*3_2_Z*4_2_A-CAZ*4_1_C*4_2_A*4_3_Z\n" +
            "TÜÖSĞ-SÖĞÜT*0_1_S*1_1_Ö*2_1_Ğ*3_1_Ü*4_1_T-ÖĞÜT*1_1_Ö*1_2_Ğ*1_3_Ü*1_4_T-SÜT*0_3_S*1_3_Ü*2_3_T\n" +
            "ABZYE-BEYAZ*2_1_B*2_2_E*2_3_Y*2_4_A*2_5_Z-YAZ*2_3_Y*3_3_A*4_3_Z-BEZ*0_5_B*1_5_E*2_5_Z-BAY*1_4_B*2_4_A*3_4_Y-BEY*2_1_B*3_1_E*4_1_Y\n" +
            "SAĞON-SOĞAN*0_4_S*1_4_O*2_4_Ğ*3_4_A*4_4_N-SAĞ*2_2_S*2_3_A*2_4_Ğ-SON*2_2_S*3_2_O*4_2_N\n" +
            "ÇEİKB-BEKÇİ*0_3_B*1_3_E*2_3_K*3_3_Ç*4_3_İ-ÇEK*1_1_Ç*2_1_E*3_1_K-KEÇİ*3_1_K*3_2_E*3_3_Ç*3_4_İ\n" +
            "JNOTE-JETON*0_3_J*1_3_E*2_3_T*3_3_O*4_3_N-TON*0_5_T*1_5_O*2_5_N-OJE*1_1_O*1_2_J*1_3_E-NOT*0_1_N*1_1_O*2_1_T-NET*4_3_N*4_4_E*4_5_T-JET*0_3_J*0_4_E*0_5_T-TEN*2_3_T*2_4_E*2_5_N     \n" +
            "KKEÖP-KÖPEK*0_2_K*1_2_Ö*2_2_P*3_2_E*4_2_K-PEK*2_2_P*2_3_E*2_4_K-KEP*3_1_K*3_2_E*3_3_P-KEK*0_2_K*0_3_E*0_4_K-KÖK*0_4_K*1_4_Ö*2_4_K\n" +
            "NİZOB-BOZ*1_4_B*2_4_O*3_4_Z-BİZON*2_1_B*2_2_İ*2_3_Z*2_4_O*2_5_N-BİZ*0_3_B*1_3_İ*2_3_Z-BİN*2_1_B*3_1_İ*4_1_N\n" +
            "YRZAA-YAZAR*0_3_Y*1_3_A*2_3_Z*3_3_A*4_3_R-AYAZ*0_2_A*0_3_Y*0_4_A*0_5_Z-AZAR*3_1_A*3_2_Z*3_3_A*3_4_R-YARA*1_2_Y*1_3_A*1_4_R*1_5_A-AYAR*4_0_A*4_1_Y*4_2_A*4_3_R\n" +
            "PAİLV-VALİ*0_3_V*1_3_A*2_3_L*3_3_İ-PİLAV*2_1_P*2_2_İ*2_3_L*2_4_A*2_5_V-PİL*2_1_P*3_1_İ*4_1_L-LAV*0_5_L*1_5_A*2_5_V";

    //endregion
    DbGame db;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    List<LevelTable> leveller;
    int first=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        CreateAllLevelScreen();

    }
    private void CreateAllLevelScreen() {
        leveller= db.getAllLevel();
        int sutun=3,satir,level=0;
        boolean newLevel = true;
        if(leveller.size()%3==0) satir=leveller.size()/3;
        else satir=(leveller.size()/3)+1;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        TableLayout table =  findViewById(R.id.table_leveller);
        for (int tempSatir=0;tempSatir<satir;tempSatir++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for (int tempSutun=0;tempSutun<sutun;tempSutun++){
                final int FINAL_SUTUN = sutun;
                final int FINAL_SATIR = satir;
                tableRow.setPadding(3,6,3,6);
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setTag(leveller.get(level).getLevel()+"");
                button.setTextColor(Color.WHITE);
                if (leveller.get(level).isGecildiMi()){
                    button.setBackgroundResource(R.drawable.correct_games);
                    button.setText(leveller.get(level).getLevel()+"\n"+leveller.get(level).getIsim()+"\n"+leveller.get(level).getPuan());
                }else{
                    if (newLevel){
                        first=level;
                        button.setBackgroundResource(R.drawable.new_games);
                        button.setText(leveller.get(level).getLevel()+"\n"+leveller.get(level).getHarfler());
                        newLevel=false;
                    }else{
                        button.setBackgroundResource(R.drawable.not_correct_games);
                        button.setText(leveller.get(level).getLevel()+"\n");
                    }
                }
                button.setWidth(size.x/3);
                button.setHeight(300);
                button.setTextSize(25);
                level++;
                button.setGravity(Gravity.CENTER);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("beyter "+ (v.getTag()));
                        GoGame(Integer.parseInt(String.valueOf(v.getTag())));
                    }
                });
                tableRow.addView(button);
            }
        }

    }

    private void GoGame(int tag) {

        if (db.getLevel(tag).isGecildiMi() || first==tag-1){
            Intent intent = new Intent(this, GameScreen.class);
            intent.putExtra("level",tag);
            startActivity(intent);
            this.finish();
        }else{
            Toast.makeText(this, "hoppp dedik", Toast.LENGTH_SHORT).show();
        }
    }

    public void init(){
        db= new DbGame(getApplicationContext());
        sharedPref= this.getPreferences(Context.MODE_PRIVATE);
        editor=sharedPref.edit();
        if (sharedPref.getInt("readData",1)==1){
            ReadData();
            editor.putInt("readData",0);
        }
    }

    private void ReadData() {
        StringTokenizer tokenizerLevels = new StringTokenizer(myData,"\n");
       // StringTokenizer tokenizerCevap = new StringTokenizer(mCevaplar,"\n");
        int level =1;
        while (tokenizerLevels.hasMoreTokens()) {
            LevelTable table= new LevelTable();
            StringTokenizer tok = new StringTokenizer(tokenizerLevels.nextToken(),"-");
            String cevaplar="";
            boolean flag=true;
            while (tok.hasMoreTokens()){
                if (flag) {
                    table.setHarfler(tok.nextToken());
                    flag=false;
                }
                else cevaplar+="-"+tok.nextToken();
            }
            table.setLevel(level);
            table.setGecildiMi(false);
            table.setCevaplar(cevaplar.substring(1,cevaplar.length()));
            table.setIsim("");
           // table.setMatrisKonumlar(tokenizerCevap.nextToken());
            table.setSure(-1);
            table.setPuan(0);
            db.addLevel(table);
            level++;
        }

    }

}
