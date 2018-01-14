/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotrunner;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author jelly
 */
public class Car {
    double x,y;
    int screenWid,screenHei;
    double speed;
    double direction;
    static final int MAXSPEED = 10;
    double acc = 0;
    double frictionalAccelation = 0.1;
    Color color;
    Car(double gx,double gy,Color c,int w,int h )
    {
        screenHei = h;
        screenWid = w;
        color = c;
        x = gx;
        y = gy;
        speed = 0;
        direction = 0;
    } 
    void run() {
        acc = 0.3;
//        System.err.println("RUN");
//        speed = MAXSPEED;
    }
    void stop() {
        acc = 0;
//        speed = 0;
    }
    void turnRight() {
        turn = -1;
    }
    void turnLeft() {
        turn = 1;
    }
    
    void step() {
        speed += acc;
        speed -= (frictionalAccelation+brakeAcc);
        if(turn!=0) {
            if(speed>MAXSPEED/2) {
                double nextSpeed = speed*0.9 + (MAXSPEED/2)*0.1;
                speed = nextSpeed;
            }
        }
        if(speed>MAXSPEED)
            speed = MAXSPEED;
        if(speed<0)
            speed = 0;
        
        direction += turn*10;
        x += speed*Math.cos(direction * Math.PI / 180);
        y -= speed*Math.sin(direction * Math.PI / 180);
        if(x< -30)
            x = 30+screenWid;
        if(x> 30+screenWid)
            x = -30;
        if(y< -30)
            y = 30+screenHei;
        if(y> 30+screenHei)
            y = -30;
        
        
    }
    
    int timer = 0;
    int radius = 30;
    boolean isNear(Car other) {
        double d = Math.sqrt((x-other.x)*(x-other.x)+(y-other.y)*(y-other.y));
        if(d<radius)
            return true;
        return false;
        
    }
    void draw(Graphics g) {
        
        timer++;
        if(timer==10)
            timer = 0;
        g.setColor(color);
        if(color==Color.BLACK) {
            g.setColor(Color.WHITE);
            g.drawOval((int)x-radius, (int)y-radius, 2*radius, 2*radius);
            g.setColor(Color.BLACK);
            if(timer>3 && timer<=7)
                g.setColor(Color.RED);
            else if(timer>7)
                g.setColor(Color.BLUE);
        }
        int xPoints[] = new int[4];
        int yPoints[] = new int[4];
        xPoints[0] = (int)(x +  20*Math.cos((direction+150)*Math.PI/180));
        yPoints[0] = (int)(y - 20*Math.sin((direction+150)*Math.PI/180));
        xPoints[1] = (int)(x );
        yPoints[1] = (int)(y);
        xPoints[2] = (int)(x +  20*Math.cos((direction+210)*Math.PI/180));
        yPoints[2] = (int)(y - 20*Math.sin((direction+210)*Math.PI/180));
        xPoints[3] = (int)(x +  20*Math.cos((direction)*Math.PI/180));
        yPoints[3] = (int)(y - 20*Math.sin((direction)*Math.PI/180));
        
        g.fillPolygon(xPoints, yPoints, 4);
    }
    int turn = 0;

    void turnStop() {
        turn = 0;
    }
    double brakeAcc = 0;
    void brakeStop() {
        brakeAcc = 0;
    }
    void brakeStart() {
        brakeAcc = 0.5;
    }
    
}
