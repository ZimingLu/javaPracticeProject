package x224;

import java.awt.Color;

class box {
	public boolean isFilled;
	public boolean isDead;
	public Color color;
}

public class MyModel {
	private box gbox[][];
	private int row,col;
	public  final Color BLANK_BOX = Color.BLACK;
	private Color colorMap[] = {
			new Color(0,240,240).brighter(),
			new Color(0,0,240).brighter(),
			new Color(240,160,0).brighter(),
			new Color(240,240,0).brighter(),
			new Color(0,240,0).brighter(),
			new Color(160,0,240).brighter(),
			new Color(240,0,0).brighter()
			};

	public MyModel(int nRow, int nCol) {
		row = nRow;
		col = nCol;
		gbox = new box[nRow][nCol];
		
		for(int i=0; i<nRow; i++){
			for(int j=0; j<nCol; j++){
				gbox[i][j] = new box();
				gbox[i][j].isFilled = false;
				gbox[i][j].isDead = false;
				gbox[i][j].color = BLANK_BOX;
			}
		}
	}
	
	boolean movToNextRow(int row){
		for(int i=0; i<col; i++){
			gbox[row+1][i].color = gbox[row][i].color;
			gbox[row+1][i].isDead = gbox[row][i].isDead;
			gbox[row+1][i].isFilled = gbox[row][i].isFilled;

			gbox[row][i].isFilled = false;
			gbox[row][i].isDead = false;
			gbox[row][i].color = BLANK_BOX;
			
		}
		return true;
	}
	
	boolean isBlankRow(int row){
		boolean flag = true;
		if( row >= this.row ){
			return false;
		}
		
		for(int i=0; i<col; i++){
			if( gbox[row][i].isFilled ){
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	boolean clearRow(int row){
		for(int i=0; i<col; i++){
			gbox[row][i].isFilled = false;
			gbox[row][i].isDead = false;
			gbox[row][i].color = BLANK_BOX;
		}
		return true;
	}
	
	boolean getBox(int row, int col){
		if( row > this.row || col > this.col ){
			return true;
		}

		if(gbox[row][col].isFilled){
			return true;
		}else{
			return false;
		}
	}
	
	boolean isDead(int row, int col){
		if(gbox[row][col].isDead){
			return true;
		}
		return false;
	}
	
	Color getBoxColor(int row, int col){
		return gbox[row][col].color;
	}
	
	void setBoxColor(int row, int col,Color c){
		gbox[row][col].color = c;
	}
	
	void fixBox(int row, int col, int t){
		gbox[row][col].isFilled = true;
		gbox[row][col].isDead = true;
		gbox[row][col].color = colorMap[t];
	}
	
	void setBox(int row, int col, int t){
		gbox[row][col].isFilled = !gbox[row][col].isFilled ;
		if( gbox[row][col].isFilled == false ){
			gbox[row][col].color = BLANK_BOX;
			//gbox[row][col].isDead = false;
		}else{
			gbox[row][col].color = colorMap[t];
		}
	}
}
