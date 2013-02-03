/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tom
 */
import game.GameConsole;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tetris {

    Board[][] gameTetris;
    GameConsole gc;
    boolean gameOver;
    Random randomGenerator;
    int currentScore;
    static int highScore;
    int clearedLine;
    int currentSpeed;

    Tetris() {
        gameTetris = new Board[20][10];
        gc = GameConsole.getInstance();
        gc.show();
        randomGenerator=new Random();
        currentSpeed=5;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                gameTetris[i][j] = new Board(i, j);
            }
        }
    }

    boolean isLineFull(Board[] tetrisLine) {
        int i = 0;
        while (i < 10) {
            if (tetrisLine[i].hasSquare) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    void eraseLine(Board[] tetrisLine) {
        for (int i = 0; i < 10; i++) {
            tetrisLine[i].removeSquare();
        }
        clearedLine++;
        
    }
    void moveDownLine(Board[][] tF,int k){
        for (int i=k-1;i>=0;i--){
            for (int j=9;j>=0;j--){
                if (gameTetris[i][j].hasSquare){
                    Color c=gameTetris[i][j].getColor();
                    gameTetris[i][j].removeSquare();
                    
                    gameTetris[i+1][j].paintColor(c);
                }
            }
        }
    }
    
    void addScore(int score){
        currentScore+=score;
    }
    
    void displayText(){
        //gc.clear();
        gc.drawText(5,10,"Current Speed Level: "+(6-currentSpeed));
        gc.drawText(5, 20,"No.of lines cleared: "+clearedLine );
        gc.drawText(5, 30, "Highest Score: "+highScore);
        gc.drawText(5,40,"Current Score: "+currentScore);
        //gc.update();
    }
    void run() {
        while (!gameOver) {
            
             for (int k = 0; k < 20; k++) {
                if (isLineFull(gameTetris[k])) {
                    eraseLine(gameTetris[k]);
                    this.addScore(100);
                    moveDownLine(gameTetris,k);
                    continue;
                }
            }
             
             int randomNumber=randomGenerator.nextInt(7);
             Figure fig = new Figure(randomNumber, gameTetris);
             this.addScore(10);
             
           
            while (true) {
                gc.idle(currentSpeed*100);
                gc.clear();
                this.displayText();
                for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (gameTetris[i][j].hasSquare) {
                            gc.drawSquare(j * 20, i * 20, gameTetris[i][j].getColor());
                        }
                    }
                }
                gc.update();

                int key = gc.getPressedKey();
                if (key == KeyEvent.VK_LEFT) {
                    if (!fig.moveLeft(gameTetris)) {
                        continue;
                    }
                }
                if (key == KeyEvent.VK_RIGHT) {
                    if (!fig.moveRight(gameTetris)) {
                        continue;
                    }
                }
                if (key == KeyEvent.VK_DOWN) {
                    if (!fig.moveAllWayDown(gameTetris)) {
                        continue;
                    }
                }
                if (key == KeyEvent.VK_SPACE) {
                    if (!fig.rotateClockwise(gameTetris)) {
                        continue;
                    }
                }
                if (key == KeyEvent.VK_UP) {
                    if (!fig.rotateAntiClockwise(gameTetris)) {
                        continue;
                    }
                }
                if (!fig.moveDown(gameTetris)) {
                    if (fig.isGameOver()){
                        gameOver=true;
                        Font bigFont = new Font("Arial",Font.BOLD,20);
                        gc.drawText(40, 200, "GAME OVER", bigFont, Color.white);
                        gc.update();
                    }
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Tetris Te = new Tetris();
        Te.run();
    }
}
