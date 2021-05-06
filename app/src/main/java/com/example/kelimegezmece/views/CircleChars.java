package com.example.kelimegezmece.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.kelimegezmece.R;

import java.util.ArrayList;
import java.util.List;

public class CircleChars extends ConstraintLayout  {
    public  interface CircleCharsListener {
        void onTextReady(boolean touchUp);
        void onTextUpdate(String text);
    }

    //region default
    private String word;
    private int drawColor;
    private int textColor;
    private float strokeWidth;
    private int backgroundResource ;
    private int circleResource;
    private int circleSelectResource;
    private int radius;
    private int textSize;
//endregion

    private CircleCharsListener listener;
    private Paint paint;
    private Path path;
    private Context context;
    private List<String>  circleCharList,tempCircleCharList;
    private List<TextView> circleList;
    private ConstraintLayout.LayoutParams params;
    private ConstraintSet constraintSet;
    private List<Coordinat> coordinats;


    public CircleChars(Context context) {
        super(context);
        this.context=context;
        word="1234";
        drawColor=Color.WHITE;
        textColor=Color.WHITE;
        strokeWidth=20f;
        backgroundResource = R.drawable.new_games;
        circleResource = R.drawable.null_button_background;
        circleSelectResource=R.drawable.not_correct_games;
        radius = 180;
        textSize=50;
        init();
        invalidate();
    }

    public CircleChars(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        word="1234";
        drawColor=Color.WHITE;
        textColor=Color.WHITE;
        strokeWidth=20f;
        backgroundResource = R.drawable.new_games;
        circleResource = R.drawable.null_button_background;
        circleSelectResource=R.drawable.not_correct_games;
        radius = 180;
        textSize=50;
        init();
        invalidate();
    }


    private void init() {
        this.setBackgroundResource(backgroundResource);
        paint =new Paint(Paint.ANTI_ALIAS_FLAG);
        path =new Path();
        paint.setColor(drawColor);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        circleCharList=new ArrayList<>();
        tempCircleCharList= new ArrayList<>();
        circleList=new ArrayList<>();
        coordinats =new ArrayList<>();
        centerView();
        addCircleView();
        invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < coordinats.size()-1; i++) {
            canvas.drawLine(coordinats.get(i).getX(), coordinats.get(i).getY(),
                    coordinats.get(i+1).getX(), coordinats.get(i+1).getY(), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                for (TextView view : circleList){
                    if (searchTag(String.valueOf(view.getTag()))){
                        float viewX= view.getX();
                        float viewY= view.getY();
                        float viewHeight = view.getHeight();
                        float viewWidth = view.getWidth();
                        if (event.getX()>viewX+15 && event.getY()>viewY+15){
                            if (event.getX()<viewX+viewHeight-15 && event.getY()<viewY+viewWidth-15){
                                view.setBackgroundResource(circleSelectResource);
                                coordinats.add(new Coordinat(viewX+viewHeight/2,viewY+viewWidth/2));
                                /*startX = viewX+viewHeight/2;
                                startY = viewY+viewWidth/2;*/
                                if (listener!=null) listener.onTextUpdate(view.getText().toString());
                                int size= circleCharList.size();
                                for (int i = 0; i <size ; i++) {
                                    if (circleCharList.get(i).equals(view.getTag().toString())){
                                        circleCharList.remove(i) ;
                                        break;
                                    }
                                }
                                coordinats.add(new Coordinat(event.getX(),event.getY()));
                                invalidate();
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (circleCharList.size()>0){
                    coordinats.set(coordinats.size()-1 , new Coordinat( event.getX(), event.getY()));
                    invalidate();
                    for (TextView view : circleList){
                        if (searchTag(String.valueOf(view.getTag()))){
                            float viewX= view.getX();
                            float viewY= view.getY();
                            float viewHeight = view.getHeight();
                            float viewWidth = view.getWidth();
                            if (event.getX()>viewX+15 && event.getY()>viewY+15){
                                if (event.getX()<viewX+viewHeight-15 && event.getY()<viewY+viewWidth-15){
                                    view.setBackgroundResource(circleSelectResource);
                                    int size= circleCharList.size();
                                    for (int i = 0; i <size ; i++) {
                                        if (circleCharList.get(i).equals(view.getTag().toString())){
                                            circleCharList.remove(i);
                                            coordinats.add(new Coordinat( event.getX(), event.getY() ));
                                            coordinats.set(coordinats.size()-2 ,new Coordinat(viewX+viewHeight/2,viewY+viewWidth/2) ) ;
                                            break;
                                        }
                                    }
                                    if (listener!=null) listener.onTextUpdate(view.getText().toString());
                                }
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (coordinats.size()>1){
                    coordinats.set(coordinats.size()-1 , new Coordinat( event.getX(), event.getY()));
                    coordinats=new ArrayList<>();
                    if (listener!=null){
                        listener.onTextReady(true);
                    }
                    invalidate();
                }
                break;
            default:return false;
        }
        return true;
    }

    private boolean searchTag(String tag) {
        boolean flag=false;
        for (String i : circleCharList){
            if (tag.equals(i)){
                flag=true;
                return flag;
            }
        }
        return flag;
    }

    private void centerView() {
        TextView view = new TextView(context);
        view.setId(0);view.setHeight(1);view.setWidth(1);
        view.setBackgroundResource(R.color.beyaz);
        params = new ConstraintLayout.LayoutParams( ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        view.setLayoutParams(params);
        this.addView(view,0);
        constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.connect(view.getId(), ConstraintSet.LEFT, this.getId(), ConstraintSet.LEFT, 18);
        constraintSet.connect(view.getId(), ConstraintSet.RIGHT, this.getId(), ConstraintSet.RIGHT, 18);;
        constraintSet.connect(view.getId(), ConstraintSet.TOP, this.getId(), ConstraintSet.TOP, 18);
        constraintSet.connect(view.getId(), ConstraintSet.BOTTOM, this.getId(), ConstraintSet.BOTTOM, 18);;
        constraintSet.applyTo(this);
    }

    private void addCircleView() {
        int size = word.length();
        int degree =0;
        List<Integer> degreeList= new ArrayList<>();
        for (int index = 0; index < size ; index++) {
            if (index!=0) degree+=360/size;// First circle always 0 degree
            degreeList.add(degree);
            TextView tView = new TextView(context);
            tView.setId(index+1);tView.setText(String.valueOf(word.charAt(index)));tView.setTag(index+1);
            tView.setHeight(180); tView.setWidth(180);tView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            tView.setMaxHeight(180); tView.setMaxWidth(180);
            tView.setTextSize(textSize);
            tView.setTextColor(textColor);
            tView.setBackgroundResource(circleResource);
            params = new ConstraintLayout.LayoutParams( ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            this.addView(tView);
            tView.setLayoutParams(params);
            constraintSet = new ConstraintSet();
            constraintSet.clone(this);
            constraintSet.constrainCircle(tView.getId(),0,radius,degreeList.get(index));
            constraintSet.applyTo(this);
            circleList.add(tView);
            circleCharList.add(String.valueOf(index+1));
        }
        tempCircleCharList.addAll(circleCharList);
    }


    public void setCustomViewListener(CircleCharsListener listener)  {
        this.listener=listener;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
        circleCharList=new ArrayList<>();
        tempCircleCharList=new ArrayList<>();
        circleList=new ArrayList<>();
        this.removeAllViews();
        centerView();
        addCircleView();
        invalidate();
    }


    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public int getDrawColor() {
        return drawColor;
    }

    public void setDrawColor(int drawColor) {
        paint.setColor(drawColor); ;
        invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
    }

    public int getBackgroundResource() {
        return backgroundResource;
    }

    public void setBackgroundResource(int backgroundResource) {
        this.backgroundResource = backgroundResource;
        invalidate();
    }

    public int getCircleResource() {
        return circleResource;
    }

    public void setCircleResource(int circleResource) {
        this.circleResource = circleResource;
        invalidate();
    }

    public int getCircleSelectResource() {
        return circleSelectResource;
    }

    public void setCircleSelectResource(int circleSelectResource) {
        this.circleSelectResource = circleSelectResource;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }
    //endregion geter and setter
}
