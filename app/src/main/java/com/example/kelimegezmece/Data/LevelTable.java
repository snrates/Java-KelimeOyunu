package com.example.kelimegezmece.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class LevelTable {

    private int level;
    private boolean gecildiMi;
    private String harfler;
    private String cevaplar;
    private String isim;
    private int sure;
    private int puan;
    private static final int FINAL_ROW=5;
    private static final int FINAL_COLUMN=6;
    private String matris[][]= new String[FINAL_ROW][FINAL_COLUMN];
    private List<String> cevapList;
    public LevelTable() {

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isGecildiMi() {
        return gecildiMi;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public void setGecildiMi(boolean gecildiMi) {
        this.gecildiMi = gecildiMi;
    }

    public String getHarfler() {
        return harfler;
    }

    public void setHarfler(String harfler) {
        this.harfler = harfler;
    }

    public String getCevaplar() {
        return cevaplar;
    }

    public List<String> getCevapList(){
        /*cevapList = new ArrayList<>();
        StringTokenizer  tok = new StringTokenizer(this.cevaplar,"-");
        while (tok.hasMoreTokens()){
            StringTokenizer  tempTok = new StringTokenizer(tok.nextToken(),"*");
            cevapList.add(tempTok.nextToken());
        }*/
        return cevapList;
    }

    public void setCevapList(List<String> cevapList) {
        this.cevapList = cevapList;
    }

    public void setCevaplar(String cevaplar) {
        cevapList = new ArrayList<>();
        StringTokenizer  tok = new StringTokenizer(cevaplar,"-");
        while (tok.hasMoreTokens()){
            int index=0;
            StringTokenizer  tempTok = new StringTokenizer(tok.nextToken(),"*");
            while (tempTok.hasMoreTokens()){
                if (index!=0){
                    StringTokenizer  tempTokTwo = new StringTokenizer(tempTok.nextToken(),"_");
                    int i = Integer.parseInt(tempTokTwo.nextToken());
                    int j = Integer.parseInt(tempTokTwo.nextToken());
                    this.matris[i][j] = tempTokTwo.nextToken();
                }else{
                    cevapList.add(tempTok.nextToken());
                }
                index++;
            }
        }
        this.cevaplar = cevaplar;
    }

    public String[][] getMAtris(){
     /*   String matris[][] = new String[FINAL_ROW][FINAL_COLUMN];
        StringTokenizer tokenizer = new StringTokenizer(this.matrisKonumlar,"-");
        while (tokenizer.hasMoreTokens()){
            int index=0,i=0,j=0;
            String harf="";
            StringTokenizer tempTokenizer = new StringTokenizer(tokenizer.nextToken(),"_");
            while (tempTokenizer.hasMoreTokens()){
                if (index==0) i=Integer.parseInt(tempTokenizer.nextToken());
                else if (index==1) j = Integer.parseInt(tempTokenizer.nextToken());
                else if (index==2) harf=tempTokenizer.nextToken();
                index++;
            }
            matris[i][j]=harf;
        }*/
        return this.matris;
    }


    public int getSure() {
        return sure;
    }

    public void setSure(int sure) {
        this.sure = sure;
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }

    public String toString(){
        String data="";
        data+=getHarfler();
        return data;
    }
}

