package com.example.kelimegezmece;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kelimegezmece.Data.DbGame;
import com.example.kelimegezmece.Data.LevelTable;
import com.example.kelimegezmece.views.CircleChars;

public class GameScreen extends AppCompatActivity {

    DbGame db;
    GameScreenHelper gameHelper;
    LevelTable nowLevel;
    TableLayout gameTable,bottomTable;
    TextView cevapText,lvlText,sureText,hatalıHamleText;
    CircleChars circleChars;
    int hatalıHamle=0,sure=100,index=0;
    String isimData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        imit();

        circleChars.setCustomViewListener(new CircleChars.CircleCharsListener() {
            @Override
            public void onTextReady(boolean touchUp) {
                if (touchUp) {
                    okButton();
                }
            }
            @Override
            public void onTextUpdate(String text) {
                cevapText.setText(cevapText.getText()+text);
            }
        });

    }

    private void imit() {
        db = new DbGame(this);
        Bundle gelenVeri=getIntent().getExtras();
        nowLevel =db.getLevel(gelenVeri.getInt("level",1));
        gameTable = findViewById(R.id.table_game);
        cevapText=findViewById(R.id.txt_k_cevap);
        lvlText=findViewById(R.id.txt_lvl);
        lvlText.setText("Level\n"+nowLevel.getLevel());
        sureText=findViewById(R.id.sure_txt);
        sureText.setText("Süre\n"+ sure +"sn");
        hatalıHamleText=findViewById(R.id.hatali_hamle_text);
        hatalıHamleText.setText("Hatalı Hamle\n"+hatalıHamle);
        cevapText.setText("");
        circleChars=findViewById(R.id.xxx);
        gameHelper=new GameScreenHelper(gameTable,circleChars,this,nowLevel,cevapText);
        zamanBasla();
    }

    public void okButton(){
          if (gameHelper.control(String.valueOf(cevapText.getText()))) {
              if (nowLevel.getCevapList().size()==0){
                  oyunBitir();
              }else{
                  circleChars.setWord(circleChars.getWord());
                  cevapText.setText("");
              }
          }else{
              hatalıHamle++;
              if (hatalıHamle==3){
                  oyunBitirBasarisiz();
              }else {
                  hatalıHamleText.setText("Hatalı Hamle\n"+hatalıHamle);
                  gameHelper.deleteText(circleChars.getWord());
              }
          }
    }

    private void oyunBitir() {
        Toast.makeText(this, "Kazandın", Toast.LENGTH_SHORT).show();
        int puan ;
        if (sure<80) puan= 100-( (hatalıHamle*10 )+(80-sure));
        else puan = 100-hatalıHamle*10;
        if (puan>nowLevel.getLevel()) getDialogName(puan);
        else codeTekrar();
    }

    private void getDialogName(int puan) {
        nowLevel.setPuan(puan);
        final AlertDialog.Builder mydialog = new AlertDialog.Builder(this);
        mydialog.setTitle("Tebrikler Adını Gir..");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        ////////////KAYNAK https://stackoverflow.com/questions/2461824/how-to-programmatically-set-maxlength-in-android-textview
                        int maxLength = 6;
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(maxLength);
                        input.setFilters(fArray);
        //////////////////////////////////////////////////////
        mydialog.setView(input);
        mydialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nowLevel.setIsim(input.getText().toString());
                dialogInterface.cancel();
                codeTekrar();
            }
        });
        mydialog.show();
        mydialog.setCancelable(false);

    }
    public void codeTekrar(){
        index=1000;
        if (nowLevel.getSure()==-1 || nowLevel.getSure()>100-sure) nowLevel.setSure(100-sure);
        if (!nowLevel.isGecildiMi()) nowLevel.setGecildiMi(true);
        db.updateLevel(nowLevel);
        Intent intent = new Intent(GameScreen.this, MainActivity.class);
        startActivity(intent);
        GameScreen.this.finish();
    }
    private void oyunBitirBasarisiz() {
        Toast.makeText(this, "Başarısız Oyun", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
    private void zamanBasla() {
        final Thread timer = new Thread() {
            public void run() {
                try {
                    while (index < 100) {
                        sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sureText.setText("Süre\n" + --sure + " sn");
                                if (index == 99) {
                                    oyunBitirBasarisiz();
                                }
                            }
                        });
                        index++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }

    public void refreshButton(View view){
        gameHelper.createAgainTable();
        cevapText.setText("");
    }
}
