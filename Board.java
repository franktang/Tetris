
import java.awt.Color;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Tom
 */
public class Board {

    int x;
    int y; //for the position of each square
    Color squareColor;
    boolean hasSquare;

    
    Board(int i,int j){
        y=i;
        x=j;
    }

    void paintColor(Color c) {
        hasSquare = true;
        squareColor = c;
    }

    void removeSquare() {
        hasSquare = false;
        squareColor = null;
    }
    
    int getCorX(){
        return x;
    }
    
    int getCorY(){
        return y;
    }
    
    Color getColor(){
        return squareColor;
    }
}
