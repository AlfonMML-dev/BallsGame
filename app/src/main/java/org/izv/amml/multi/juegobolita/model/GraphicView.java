package org.izv.amml.multi.juegobolita.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class GraphicView extends View {
    public static ArrayList<Ball> balls = new ArrayList<>();

    public static ArrayList<Integer> colors = new ArrayList<>();

    public static int numColors = 0;
    public static int numBalls = 0;

    public GraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static void createBalls(){
        if(!balls.isEmpty()){
            balls.removeAll(balls);
        }
        fillColors();
        int counter = 0;
        while(counter < numBalls){
            int[] ballBounds = setBallBounds();
            //Add a new ball to the view
            balls.add(new Ball(ballBounds[0], ballBounds[1], ballBounds[2], ballBounds[3], ballBounds[4]));
            counter++;
            Log.v("GraphicView", "counter = " + counter + ", numBalls = " + numBalls);
        }
    }

    private static void fillColors(){
        if(!colors.isEmpty()){
            colors.removeAll(colors);
        }
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
    }

    private static int[] setBallBounds(){
        int maxSpeed = numColors == 2 ? 30 : (numColors == 3 ? 30 : 40);
        int minSpeed = 10;
        int x = (int) (Math.random() * 500) + 40;
        int y = (int) (Math.random() * 500) + 40;
        int size = 100;
        int color = colors.get((int) (Math.random() * numColors));
        int speed = (int) (Math.random() * (maxSpeed-minSpeed)) + 15;
        String logV = "x = " + x + ", y = " + y + ", color = " + color + ", speed = " + speed;
        Log.v("GraphicView", logV);
        return new int[]{x, y, size, color, speed};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * No se puede desde aquí llamar al método createBalls(), porque crea bolas hasta que
         * termine el contador.
         */
        //Draw the balls
        for(Ball ball : balls){
            //Move first
            ball.move(canvas);
            //Draw them
            canvas.drawOval(ball.oval,ball.paint);
        }
        invalidate(); // See note
    }
}
