/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotrunner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jainendra
 */
public class DotRunner {
    Screen screen;
    public DotRunner() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                screen = new Screen(DotRunner.this);
                screen.setVisible(true);
                screen.setSize(1024, 768);
            }
        });
    }

    
    public static void main(String[] args) {
        DotRunner dotRunner = new DotRunner();
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(DotRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Catch the Thief\n\nWASD - Police\nUp/Down/Left/Right - Theif");
        dotRunner.start();
    }
    
    Car player1,player2;
    void step() {
        player1.step();
        player2.step();
        if(player2.isNear(player1)) {
            JOptionPane.showMessageDialog(null, "Thief Caught!!!");
            System.exit(0);
        }
        if(screen!=null){
            screen.repaint((int)player1.x-50, (int)player1.y-50, 100, 100);
            screen.repaint((int)player2.x-50, (int)player2.y-50, 100, 100);
            
        }
    }
    private void start() {
        player1 = new Car(-30,100,Color.WHITE,screen.getWidth(),screen.getHeight());
        player1.speed = player1.MAXSPEED;
        player2 = new Car(100,200,Color.BLACK,screen.getWidth(),screen.getHeight());
        new Thread() {

            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(60);
                        step();
                    } catch (InterruptedException ex) {
                    
                    }
                }
            }
            
        }.start();
        KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if(e.getID() ==KeyEvent.KEY_PRESSED) {
                    if(e.getKeyCode() == KeyEvent.VK_RIGHT) player1.turnRight();
                    if(e.getKeyCode() == KeyEvent.VK_LEFT)   player1.turnLeft();
                    if(e.getKeyCode() == KeyEvent.VK_UP)      player1.run();
                    if(e.getKeyCode() == KeyEvent.VK_DOWN)  player1.brakeStart();
                    if(e.getKeyCode() == KeyEvent.VK_D) player2.turnRight();
                    if(e.getKeyCode() == KeyEvent.VK_A)   player2.turnLeft();
                    if(e.getKeyCode() == KeyEvent.VK_W)      player2.run();
                    if(e.getKeyCode() == KeyEvent.VK_S)  player2.brakeStart();
                  
                }
                if(e.getID() ==KeyEvent.KEY_RELEASED) {
                    if(e.getKeyCode() == KeyEvent.VK_UP)   player1.stop();
                    if(e.getKeyCode() == KeyEvent.VK_RIGHT) player1.turnStop();
                    if(e.getKeyCode() == KeyEvent.VK_LEFT) player1.turnStop();
                     if(e.getKeyCode() == KeyEvent.VK_DOWN) player1.brakeStop();
                   
                      if(e.getKeyCode() == KeyEvent.VK_W)   player2.stop();
                    if(e.getKeyCode() == KeyEvent.VK_D) player2.turnStop();
                    if(e.getKeyCode() == KeyEvent.VK_A) player2.turnStop();
                     if(e.getKeyCode() == KeyEvent.VK_S) player2.brakeStop();
                   
                }
                
                return false;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
    }

    
    void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, screen.getSize().width, screen.getSize().height);
        screen.namePrint(g);
        player1.draw(g);
        player2.draw(g);
        
    }
    
}
