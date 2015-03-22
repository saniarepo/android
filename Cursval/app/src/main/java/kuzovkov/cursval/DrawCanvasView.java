package kuzovkov.cursval;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by sania on 3/21/2015.
 */
public class DrawCanvasView extends View {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    private static final int MARGIN = 80;
    private static final int PADDING = 10;


    private ShapeDrawable mDravable;
    private String valuteName;
    private List<CBR_ParserXML.Curs> curses;
    private float deltaY;


    public DrawCanvasView(Context context){
        super(context);
        setFocusable(true);
        mDravable = new ShapeDrawable();
    }

    /*метод запускающий рисование*/
    public void drawDiagram(String valuteName, List<CBR_ParserXML.Curs> curses){
        this.curses = curses;
        this.valuteName = valuteName;
        mDravable = new ShapeDrawable();
        invalidate();
    }

    /*перерисовка графического объекта*/
    @Override
    protected void onDraw(Canvas canvas){
        Paint p = new Paint();
        float x1,x2,y1,y2;

        /*вычисление смещения по оси Y*/
        deltaY = (HEIGHT-MARGIN-PADDING)/curses.size();

        /*вычисление максимального и минимального курса*/
        float maxCurs = 0;
        float minCurs = 10000000;
        for(CBR_ParserXML.Curs curs : curses){
            float currCurs = curs.getCurs()/curs.getNominal();
            if (maxCurs < currCurs){
                maxCurs = currCurs;
            }
            if (minCurs > currCurs){
                minCurs = currCurs;
            }
        }
        int count = 0;

        /*Отрисовка диаграммы*/
        for (CBR_ParserXML.Curs curs : curses){
            x1 = MARGIN;
            y1 = MARGIN + PADDING + deltaY * count;
            y2 = y1;
            float currCurs = curs.getCurs()/curs.getNominal();
            x2 = MARGIN+PADDING+(currCurs-minCurs)/(maxCurs-minCurs)*(WIDTH - MARGIN - PADDING);
            count++;
            p.setColor(Color.RED);
            canvas.drawLine(x1, y1, x2, y2, p);
            p.setColor(Color.BLACK);
            canvas.drawText(curs.getDate(),0, y1, p);
        }

        /*отрисовка осей*/
        /*ось курсов*/
        x1 = MARGIN;
        y1 = MARGIN;
        x2 = WIDTH;
        y2 = MARGIN;
        p.setColor(Color.BLACK);
        canvas.drawLine(x1,y1,x2,y2,p);
        /*ось дат*/
        x1 = MARGIN;
        y1 = MARGIN;
        x2 = MARGIN;
        y2 = HEIGHT;
        canvas.drawLine(x1,y1,x2,y2,p);
        p.setColor(Color.BLUE);
        p.setTextSize(20.0f);
        canvas.drawText(getResources().getString(R.string.curses), WIDTH-40, MARGIN/2 ,p);
        canvas.drawText(getResources().getString(R.string.dates), MARGIN+10, HEIGHT ,p);
        /*уровни*/
        p.setColor(Color.GRAY);
        p.setAlpha(100);
        canvas.drawLine(MARGIN+PADDING, MARGIN-5, MARGIN+PADDING, HEIGHT, p);
        canvas.drawLine(WIDTH, MARGIN-5, WIDTH, HEIGHT, p);
        p.setColor(Color.BLACK);
        p.setTextSize(15.0f);
        canvas.drawText(new Float(minCurs).toString(), MARGIN+PADDING-10, MARGIN-10, p);
        canvas.drawText(new Float(maxCurs).toString(), WIDTH-10, MARGIN-10, p);
        p.setColor(Color.MAGENTA);
        p.setTextSize(25.0f);
        canvas.drawText(valuteName, MARGIN, MARGIN/2, p);


        mDravable.draw(canvas);
    }

}
