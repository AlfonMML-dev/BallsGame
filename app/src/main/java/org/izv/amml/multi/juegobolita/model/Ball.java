package org.izv.amml.multi.juegobolita.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;

public class Ball implements Parcelable {

    public Paint paint;
    public RectF oval;

    public int[] direction = new int[]{1,1}; //direction modifier (-1,1)
    public int x,y,size;
    public int speed;

    public Ball(int x, int y, int size, int color,int speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.paint = new Paint();
        this.paint.setColor(color);
    }

    protected Ball(Parcel in) {
        oval = in.readParcelable(RectF.class.getClassLoader());
        direction = in.createIntArray();
        x = in.readInt();
        y = in.readInt();
        size = in.readInt();
        speed = in.readInt();
    }

    public static final Creator<Ball> CREATOR = new Creator<Ball>() {
        @Override
        public Ball createFromParcel(Parcel in) {
            return new Ball(in);
        }

        @Override
        public Ball[] newArray(int size) {
            return new Ball[size];
        }
    };

    public void move(Canvas canvas) {
        this.x += speed*direction[0];
        this.y += speed*direction[1];
        this.oval = new RectF(x-size/2,y-size/2,x+size/2,y+size/2);

        //Do we need to bounce next time?
        Rect bounds = new Rect();
        this.oval.roundOut(bounds); ///store our int bounds

        //This is what you're looking for ▼
        if(!canvas.getClipBounds().contains(bounds)){
            if(this.x-size<0 || this.x+size > canvas.getWidth()){
                direction[0] = direction[0] * (-1);
            }
            if(this.y-size<0 || this.y+size > canvas.getHeight()){
                direction[1] = direction[1] * (-1);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(oval, flags);
        dest.writeIntArray(direction);
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeInt(size);
        dest.writeInt(speed);
    }
}
