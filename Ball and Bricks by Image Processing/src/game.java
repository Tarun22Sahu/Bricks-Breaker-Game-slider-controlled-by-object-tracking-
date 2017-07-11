import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;



public class game extends JPanel implements MouseListener,MouseMotionListener, KeyListener, ActionListener {
	private boolean play =false;
	private int score = 0;
	private int totalbricks = 21;
	private Timer time ,time2;
	private int sliderX = 310;
	private int ballPosX=(int) ((740*(Math.random()))+240);
	private int ballPosY = (int) ((230*(Math.random()))+400);
	private int ballXdir = (int) ((Math.random())-2);
	private int ballYdir = (int) ((Math.random())-3);
	private int speed = 8;
	private bricks t;
	private int o=0;
	
	private int j=23;
	private int level = 1;
	private Point p = new Point();
	//private boolean check=true;
	private int a=ballPosX,b=ballPosY,c=ballXdir,d=ballYdir,e;
	public game(){
		t = new bricks(3 , 7);

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		time = new  Timer(speed ,this);
		time.start();

	}

	public void paint(Graphics g){
		// background
		g.setColor(Color.black);
		g.fillRect(1,1,1280,720);
		t.draw((Graphics2D)g);


		//border
		g.setColor(Color.yellow);
		g.fillRect(0,0,3,720);
		g.fillRect(0,0,1280,3);
		g.fillRect(1277,0,3,720);
		//slider
		g.setColor(Color.green);
		g.fillRect(sliderX,680,100,8);
		//scores
		g.setColor(Color.white);
		g.setFont(new Font("serif" , Font.BOLD ,25 ));
		g.drawString("SCORE"+ score ,620 , 30);
		//level board
		g.setColor(Color.white);
		g.setFont(new Font("serif" , Font.BOLD ,25 ));
		g.drawString("level"+ level ,120 , 30);
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX,ballPosY,20,20);
		if(ballPosY >705){
			play=false;
			ballXdir=0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif" , Font.BOLD ,30 ));
			g.drawString("GAME OVER , SCORES :"+score ,280 , 360);
			g.setColor(Color.red);
			g.setFont(new Font("serif" , Font.BOLD ,30 ));
			g.drawString("Press Enter TO RESTART" ,280 , 410);}
			if(totalbricks==0){
				play=false;
				ballXdir=0;
				ballYdir = 0;
				g.setColor(Color.red);
				g.setFont(new Font("serif" , Font.BOLD ,30 ));
				g.drawString("Press k TO go to next level" ,280 , 410);
				g.drawString("Press enter TO replay current level" ,280 , 460);
}

	}


	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

@Override
public void keyPressed(KeyEvent e) {
	if(e.getKeyCode()==KeyEvent.VK_RIGHT){
		if(sliderX>1160){
			sliderX=1160;
		}
		else {
			moveRight();
		}
	}
	if(e.getKeyCode()==KeyEvent.VK_LEFT){
		if(sliderX<10){
			sliderX=10;
		}
		else {
			moveLeft();
		}
	}

	if(e.getKeyCode()==KeyEvent.VK_ENTER){
		if(!play){
			play = true;
			ballPosX=(int) (1100*(Math.random()));
			ballPosY=(int) ((230*(Math.random()))+400);
			ballXdir=-c;
			ballYdir=d;
			c=-c;
			score=0;
			totalbricks=21;
			t= new bricks(3,7);
		repaint();
		}
	}
	if(e.getKeyCode()==KeyEvent.VK_K){
		if(!play){
			if(totalbricks==0) {
			speed=speed-2;
			level++;
			play = true;
			ballPosX=(int) (1100*(Math.random()));
			ballPosY=(int) ((230*(Math.random()))+400);
			ballXdir=-c;
			ballYdir=d;
			c=-c;
			totalbricks=21;
			score=0;
			t= new bricks(3,7);
			time2 = new  Timer(speed ,this);
			time.stop();
			time2.start();

		repaint();
			}
		}
	}
}

private void moveLeft() {
	play=true;
	sliderX -=20;
}

private void moveRight() {
	play = true;
	sliderX+=20;
}

@Override
public void actionPerformed(ActionEvent e) {
//	time = new  Timer(speed ,this);
//	time.start();
	if(play){
		if(new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(sliderX,680,100,8)))
		{
			ballYdir = -ballYdir;
		}


		A: for(int i=0;i<t.map.length;i++){
			for(int j=0;j<t.map[0].length;j++){
				if(t.map[i][j] > 0){
					int brickX = j*t.brickwidth+80;
					int brickY = i*t.brickheight+50;
					int brickwidth = t.brickwidth;
					int brickheight = t.brickheight;
					Rectangle rect = new Rectangle(brickX,brickY,brickwidth,brickheight);
					Rectangle ballrect = new Rectangle(ballPosX,ballPosY,20,20);
					if(ballrect.intersects(rect)){
						t.setbrickValue(0 , i , j);
						score+=5;
						totalbricks--;
						if(ballPosX+19 <=rect.x || ballPosX+1>=rect.x+rect.width){
							ballXdir = -ballXdir;

						}else {
							ballYdir = -ballYdir;
						}
						break A;
					}

				}

			}
		}
		ballPosX += ballXdir;
		ballPosY += ballYdir;
		if(ballPosX<0)ballXdir= -ballXdir;
		if(ballPosY<0)ballYdir= -ballYdir;
		if(ballPosX>1250)ballXdir= -ballXdir;

	}
	repaint();
}

@Override
public void mouseClicked(MouseEvent arg0) {
	if(o==0) {
	play= true;
	o=1;
	}
	else if (o==1){
		play=false;
		o=0;
	}
}

@Override
public void mouseEntered(MouseEvent arg0) {}

@Override
public void mouseExited(MouseEvent arg0) {}

@Override
public void mouseReleased(MouseEvent e) {}


@Override
public void mousePressed(MouseEvent e) {}

@Override
public void mouseDragged(MouseEvent arg0) {}

@Override
public void mouseMoved(MouseEvent e) {
	//System.out.println(e.getXOnScreen());
	
	if(sliderX>1160){
		sliderX=1160;
	}
	else if(sliderX<10){
		sliderX=10;
	}
	else {
	sliderX = e.getXOnScreen();
	}
}
}

