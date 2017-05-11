package x224;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

class MyShape{
	public static final int SHAPE_I = 0;
	public static final int SHAPE_J = 1;
	public static final int SHAPE_L = 2;
	public static final int SHAPE_O = 3;
	public static final int SHAPE_S = 4;
	public static final int SHAPE_T = 5;
	public static final int SHAPE_Z = 6;
	
	public static final Color borderColor = Color.black;
	private boolean isTurning = false;
	//4 items, each item has 2 positions x, y
	int currPos[][] = new int[4][2];
	int t,v;//Type And Vec

}

public class MyController implements Runnable {
	private int row,col;
	private final int INIT_COL = 5;
	private final int INIT_V = 0;
	private final int INIT_QUEUE_SIZE = 4;
	private final int FASTEST_SPEED = 100;
	
	private final int ROW = 0;
	private final int COL = 1;
	private boolean isStopped = false;
	public  boolean isPaused = false;
	private int score = 0;
	
	private JLabel scoreLabel;
	private JLayeredPane nxtShapePane;

	private JLabel boxUI[][];
	private MyModel core;
	private MyShape currShape ;
	private java.util.Queue<MyShape> qe = new LinkedList<MyShape>(); //Shape queue;
	private int interval = 1000;
	
	public void setStopped(boolean stopped) {
		this.isStopped = stopped;
	}

	public MyController(JLabel[][] boxUI, int row, int col) {
//		super();
		this.boxUI = boxUI;
		this.row = row;
		this.col = col;
		//initialize the core box area
		//maybe showing area is different from model area
		core = new MyModel(row,col);
	}
	//current UI is drawed according to myModel
	private void drawUI(){
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				boxUI[i][j].setBackground(core.getBoxColor(i,j));
				
				if( core.getBox(i, j) ){
					boxUI[i][j].setBorder(BorderFactory.createLineBorder(MyShape.borderColor));
				}else{
					boxUI[i][j].setBorder(null);
				}
			}
		}
		/**/
		if( isStopped ){
			return;
		}
		
		scoreLabel.setText("Score: " + score*10);
//		ImageIcon img1 = new ImageIcon(this.getClass().getClassLoader().getResource
//				("img/" + String.valueOf(qe.peek().t) + ".jpg"));
		
//		nxtShapePane.getGraphics().drawImage(img1.getImage(), 0, 0, null);
	 
	}
	
	

	public boolean movLeft(){
		for(int i=0; i<4; i++){
			if(currShape.currPos[i][1] - 1 < 0){
				return false;
			}
			if( core.getBox(currShape.currPos[i][0], currShape.currPos[i][1] - 1 ) ){
				return false;
			}
		}
		
		for(int i=0; i<4; i++){
			currShape.currPos[i][1]--; // Y
		}
		return true;
	}
	
	public boolean movRight(){
		for(int i=0; i<4; i++){
			if(currShape.currPos[i][1] + 1 >= col){
				return false;
			}
			if( core.getBox(currShape.currPos[i][0], currShape.currPos[i][1] + 1 ) ){
				return false;
			}
		}
		
		for(int i=0; i<4; i++){
			if(currShape.currPos[i][1] + 1 > col-1 ){
				return false;
			}
		}
		
		for(int i=0; i<4; i++){
			currShape.currPos[i][1]++; // Y
		}
		return true;
	}
		
	public boolean movDown(){
		for(int i=0; i<4; i++){
			if( currShape.currPos[i][0] >= this.row - 1 ){
				return false;
			}
			if( core.getBox(currShape.currPos[i][0]+1, currShape.currPos[i][1]) ){
					return false;
			}
		}
		
		for(int i=0; i<4; i++){
			currShape.currPos[i][0]++; // row++
		}
		return true;
	}

	public boolean movUp(){
		switch(currShape.t){
		case MyShape.SHAPE_O:
			break;
		case MyShape.SHAPE_I:
			transformCoords( 0 , false);
			break;
		case MyShape.SHAPE_J:
			transformCoords( 2 , false);
			break;
		case MyShape.SHAPE_L:
			transformCoords( 2 , false);
			break;
		case MyShape.SHAPE_S:
			transformCoords( 3 , false);
			break;
		case MyShape.SHAPE_T:
			transformCoords( 2 , false);
			break;
		case MyShape.SHAPE_Z:
			transformCoords( 3 , false);
			break; 
		}
		return true;
	}
	
	/*That's it! Really sucks. I have to check high school textbooks 
	 * because i just forget how to transform the coordinates...haha
	 * Well, the formula is :
	 * p2x = baseX +(should be '-' when it is deasil) (p1y-baseY)
	 * p2y = baseY -(should be '+' when it is deasil) (p1x-baseX) 
	 */
	private boolean transformCoords(int basePoint, boolean isDeasil){
		int tmpXY[][] = new int[4][2];
		int triedTimes = 0;
		
		if( basePoint >=4 || basePoint<0 ){
			return false;
		}
		for(int i=0; i<4; i++){
			if( triedTimes > 1 ){
				return false;
			}
			if( i== basePoint ){
				tmpXY[i][1] = currShape.currPos[basePoint][1];
				tmpXY[i][0] = currShape.currPos[basePoint][0];
				continue;
			}
			
			if( isDeasil ){
				tmpXY[i][1] = currShape.currPos[basePoint][1] - 
				(currShape.currPos[i][0] - 
				currShape.currPos[basePoint][0]);
				
				tmpXY[i][0] = currShape.currPos[basePoint][0] +
				(currShape.currPos[i][1] -
				currShape.currPos[basePoint][1]);
			}else{
				tmpXY[i][1] = currShape.currPos[basePoint][1] + 
				(currShape.currPos[i][0] - 
				currShape.currPos[basePoint][0]);
				
				tmpXY[i][0] = currShape.currPos[basePoint][0] -
				(currShape.currPos[i][1] -
				currShape.currPos[basePoint][1]);
			}
			
			if(tmpXY[i][1] >= col || tmpXY[i][1] < 0 || tmpXY[i][0] < 0 || tmpXY[i][0] >= row){
				if( triedTimes > 1){
					return false;
				}else{
					i = -1;
					isDeasil = !isDeasil;
					triedTimes++;
					continue;
				}
			}
			
			if( core.getBox(tmpXY[i][0], tmpXY[i][1]) ){
				return false;
			}
		}
		
		for(int i=0; i<4; i++){
			currShape.currPos[i][0] = tmpXY[i][0];
			currShape.currPos[i][1] = tmpXY[i][1];
		}
		return true;
	}

	public synchronized boolean turnShape(int KeyCode){
		if( isStopped || isPaused ){
			return false;
		}
		
		updateModel();
		switch(KeyCode){
		case KeyEvent.VK_LEFT:
			movLeft();
			break;
		case KeyEvent.VK_RIGHT:
			movRight();
			break;
		case KeyEvent.VK_DOWN:
			movDown();
			break;
		case KeyEvent.VK_UP:
			movUp();
			break;
		}
		updateModel();
		drawUI();
		return true;
	}

	private void clearAll(){
		for(int i=0; i<row; i++){
			core.clearRow(i);
		}
		currShape = null;
		qe.removeAll(qe);
		drawUI();
		this.setStopped(false);
		this.isPaused = false;
	}
	
	@Override 
	public void run() {
		pumpShapeQueue();
		while(true){
			if( isStopped ){
				clearAll();
				return ;
			}
			
			if( isPaused ){
				try {
					Thread.sleep(100);
					continue;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				if( !movDown() ){
					fixModel();
					score +=eraseLines();
					updateSpeed();
					this.drawUI();
					pumpShapeQueue();
					if( lostTheGame() ){
						this.setStopped(true);
					}
				}

				updateModel();
				this.drawUI();
				Thread.sleep(interval);
				updateModel();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private int eraseLines(){
		boolean flag ;
		
		LinkedList<Integer> lines = new LinkedList<Integer>();
		//find erased line and add it to lines
		for(int i=row-1; i>=0; i--){
			flag = true;
			for(int j=0; j<col; j++){
				if( !core.isFilled(i, j) ){
					flag = false;
					break;
				}
			}
			if( flag ){
				lines.add(i);
			}
		}
		
		Collections.sort(lines);
		//clear the erased line
		for(int i=0; i<lines.size(); i++){
			core.clearRow(lines.get(i));
		}
		//if no erased line, return 0
		if( lines.size() == 0 ){
			return 0;
		}
		
		int j=0;
		//move line to the below erased line, from the last 2 line
		for(int i=row-2; i>=0; i--){
			if( core.isBlankRow(i) ){
				continue;
			}else{
				j=i;
				while( core.isBlankRow(j+1) ){
					core.movToNextRow(j);
					core.clearRow(j);
					j++;
				}
			}
		}
		
		return lines.size();
	}
	
	private boolean lostTheGame(){
		for(int i=0; i< col; i++){
			if( core.getBox(0, i) ){
				JOptionPane.showMessageDialog(null, "You Lost the Game~! Score is:" + score);
				return true;
			}
		}
		return false;
	}
	
	private boolean pumpShapeQueue(){
		currShape = null;
		Random rnd = new Random(System.currentTimeMillis());
		int t = 0;
		while( qe.size() < INIT_QUEUE_SIZE ){
			t = Math.abs(rnd.nextInt()%7);
			qe.offer(makeShape( t, INIT_V ));
		}
		
		currShape = qe.poll();
		return true;
	}

	/*
	 *Update model so DrawUI() will works!
	 *After DrawUI() is done , model will be restored because setBox() just reverses the value; 
	 */
	private synchronized boolean updateModel(){
		for(int i=0; i<4; i++){
			core.setBox(currShape.currPos[i][0], currShape.currPos[i][1], currShape.t);
		}
		return true;
	}
	
	private boolean fixModel(){
		for(int i=0; i<4; i++){
			core.fixBox(currShape.currPos[i][0], currShape.currPos[i][1], currShape.t);
		}
		return true;
	}
	
	private boolean updateSpeed(){
		if( this.interval == FASTEST_SPEED ){
			return false;
		}else{
			this.interval = 1000 - 100 * (this.score  / 10) ;
			return true;
		}
	}
	
	private MyShape makeShape(int t, int v){
		MyShape shp = new MyShape();
		shp.t = t;
		shp.v = v;
		
		switch(t){
		case MyShape.SHAPE_O:
			shp.currPos[0][ROW] = 0;
			shp.currPos[0][COL] = INIT_COL;
			shp.currPos[1][ROW] = 0;
			shp.currPos[1][COL] = INIT_COL+1;
			shp.currPos[2][ROW] = 1;
			shp.currPos[2][COL] = INIT_COL;
			shp.currPos[3][ROW] = 1;
			shp.currPos[3][COL] = INIT_COL+1;
			break;
		case MyShape.SHAPE_I:
			shp.currPos[0][ROW] = 0;
			shp.currPos[0][COL] = INIT_COL;
			shp.currPos[1][ROW] = 0;
			shp.currPos[1][COL] = INIT_COL+1;
			shp.currPos[2][ROW] = 0;
			shp.currPos[2][COL] = INIT_COL+2;
			shp.currPos[3][ROW] = 0;
			shp.currPos[3][COL] = INIT_COL+3;
			break;
		case MyShape.SHAPE_T:
			shp.currPos[0][ROW] = 0;
			shp.currPos[0][COL] = INIT_COL+1;
			shp.currPos[1][ROW] = 1;
			shp.currPos[1][COL] = INIT_COL;
			shp.currPos[2][ROW] = 1;
			shp.currPos[2][COL] = INIT_COL+1;
			shp.currPos[3][ROW] = 1;
			shp.currPos[3][COL] = INIT_COL+2;
			break;
		case MyShape.SHAPE_L:
			shp.currPos[0][ROW] = 0;
			shp.currPos[0][COL] = INIT_COL;
			shp.currPos[1][ROW] = 1;
			shp.currPos[1][COL] = INIT_COL;
			shp.currPos[2][ROW] = 2;
			shp.currPos[2][COL] = INIT_COL;
			shp.currPos[3][ROW] = 2;
			shp.currPos[3][COL] = INIT_COL+1;
			break;
		case MyShape.SHAPE_J:
			shp.currPos[0][ROW] = 0;
			shp.currPos[0][COL] = INIT_COL+1;
			shp.currPos[1][ROW] = 1;
			shp.currPos[1][COL] = INIT_COL+1;
			shp.currPos[2][ROW] = 2;
			shp.currPos[2][COL] = INIT_COL+1;
			shp.currPos[3][ROW] = 2;
			shp.currPos[3][COL] = INIT_COL;
			break;
		case MyShape.SHAPE_Z:
			shp.currPos[0][ROW] = 0;
			shp.currPos[0][COL] = INIT_COL;
			shp.currPos[1][ROW] = 0;
			shp.currPos[1][COL] = INIT_COL+1;
			shp.currPos[2][ROW] = 1;
			shp.currPos[2][COL] = INIT_COL+1;
			shp.currPos[3][ROW] = 1;
			shp.currPos[3][COL] = INIT_COL+2;
			break;
		case MyShape.SHAPE_S:
			shp.currPos[0][ROW] = 0;
			shp.currPos[0][COL] = INIT_COL+1;
			shp.currPos[1][ROW] = 0;
			shp.currPos[1][COL] = INIT_COL+2;
			shp.currPos[2][ROW] = 1;
			shp.currPos[2][COL] = INIT_COL;
			shp.currPos[3][ROW] = 1;
			shp.currPos[3][COL] = INIT_COL+1;
			break;
		}
		return shp;
	}
	
	public void setScoreLabel(JLabel scoreLabel) {
		this.scoreLabel = scoreLabel;
	}
	
	public void setnxtShapePane(JLayeredPane nxtShape) {
		this.nxtShapePane = nxtShape;
	}
}
