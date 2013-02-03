
import game.GameConsole;
import java.awt.Color;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Tom
 */
public class Figure {

    public static final int[][] squareI = {
        {4, 0, 5, 0, 6, 0, 7, 0},
        {4, 0, 4, 1, 4, 2, 4, 3},
        {1, 0, 2, 0, 3, 0, 4, 0},
        {4, -3, 4, -2, 4, -1, 4, 0}
    };
    public static final int[][] squareJ = {
        {4, 0, 4, 1, 5, 1, 6, 1},
        {4, 0, 4, 1, 4, 2, 5, 0},
        {3, 0, 4, 0, 5, 0, 5, 1},
        {4, 0, 5, 0, 5, -1, 5, -2}
    };
    public static final int[][] squareL = {
        {4, 1, 5, 1, 6, 1, 6, 0},
        {4, 0, 4, 1, 4, 2, 5, 2},
        {2, 0, 2, 1, 3, 0, 4, 0},
        {3, -2, 4, -2, 4, -1, 4, 0}
    };
    public static final int[][] squareO = {
        {4, 0, 4, 1, 5, 0, 5, 1},
        {4, 0, 4, 1, 5, 0, 5, 1},
        {4, 0, 4, 1, 5, 0, 5, 1},
        {4, 0, 4, 1, 5, 0, 5, 1}
    };
    public static final int [][] squareS ={
        {2,1,3,1,3,0,4,0},
        {2,0,2,1,3,1,3,2},  //注意这边S的顺时针变化与逆时针变换暂定一样
        {2,1,3,1,3,0,4,0},
        {2,0,2,1,3,1,3,2}
    };
    public static final int [][] squareT={
        {4,0,3,1,4,1,5,1},
        {4,0,4,1,4,2,5,1},
        {3,0,4,0,4,1,5,0},
        {3,1,4,0,4,1,4,2}
    };
    public static final int [][] squareZ={
        {3,0,4,0,4,1,5,1},
        {3,1,3,2,4,0,4,1},
        {3,0,4,0,4,1,5,1},
        {3,1,3,2,4,0,4,1}
    };
    int[][] rotation;
    int rotationIndex;
    int currentX;
    int currentY; //current coordinates of the figure
    int tempX;
    int tempY;
    Color c;
    

    public Figure(int type, Board[][] tF) {
        switch (type) {
            case 0:
                rotation = squareI;
                c=Color.blue;
                break;
            case 1:
                rotation = squareJ;
                c=Color.cyan;
                break;
            case 2:
                rotation = squareL;
                c=Color.orange;
                break;
            case 3:
                rotation = squareO;
                c=Color.yellow;
                break;
            case 4:
                rotation =squareS;
                c=Color.green;
                break;
            case 5:
                rotation =squareT;
                c=Color.magenta;
                break;
            case 6:
                rotation =squareZ;
                c=Color.red;
                break;
        }
        // tetrisFigure = tF;
        update(tF, true);
    }

    boolean update(Board[][] tF1, boolean flag) {
        int[] x = new int[4];
        int[] y = new int[4];
        if (flag) {
            for (int i = 0; i < 4; i++) {
                x[i] = currentX + rotation[rotationIndex][i * 2];
                y[i] = currentY + rotation[rotationIndex][i * 2 + 1];
            }
            if (checkAvail(tF1, x, y)) {
                for (int i = 0; i < 4; i++) {
                    tF1[y[i]][x[i]].paintColor(this.c);
                }
                return true;
            }
            return false;

        } else {
            for (int i = 0; i < 4; i++) {
                x[i] = currentX + rotation[rotationIndex][i * 2];
                y[i] = currentY + rotation[rotationIndex][i * 2 + 1];
                tF1[y[i]][x[i]].removeSquare();

            }
            return true;
        }
    }
    boolean isGameOver(){
        if (this.currentY==0)
            return true;
        return false;
    }
    boolean moveLeft(Board[][] tF1) {
        update(tF1, false);
        tempX = currentX;
        //tempY = currentY;
        currentX -= 1;
        if (update(tF1, true)) {
            return true;
        }
        currentX = tempX;
        update(tF1, true);
        return false;
    }

    boolean moveRight(Board[][] tF1) {

        update(tF1, false);
        tempX = currentX;
        //tempY = currentY;
        currentX += 1;
        if (update(tF1, true)) {
            return true;
        }
        currentX = tempX;
        update(tF1, true);
        return false;
    }

    boolean moveDown(Board[][] tF1) {
        update(tF1, false);
        //tempX = currentX;
        tempY = currentY;
        currentY += 1;
        if (update(tF1, true)) {
            return true;
        }
        currentY = tempY;
        update(tF1, true);
        return false;
    }

    boolean moveAllWayDown(Board[][] tF1) {
        while (moveDown(tF1)) {
        }
        return false;
    }

    boolean rotateClockwise(Board[][] tF1) {
        int tempIndex = rotationIndex;
        update(tF1, false);
        rotationIndex = (rotationIndex + 1) % 4;
        if (update(tF1, true)) {
            return true;
        }
        rotationIndex = tempIndex;
        update(tF1, true);
        return false;
    }

    boolean rotateAntiClockwise(Board[][] tF1) {
        int tempIndex = rotationIndex;
        update(tF1, false);
        rotationIndex = (rotationIndex + 3) % 4;
        if (update(tF1, true)) {
            return true;
        }
        rotationIndex = tempIndex;
        update(tF1, true);
        return false;
    }

    boolean inBoard(int x, int y) {
        if (x >= 10 || x < 0 || y >= 20 || y < 0) {
            return false;
        }
        return true;
    }

    boolean checkAvail(Board[][] tF1, int[] x, int[] y) {
        for (int i = 0; i < 4; i++) {
            if (inBoard(x[i], y[i]) && !tF1[y[i]][x[i]].hasSquare)
                ; else {
                return false;
            }
        }
        return true;
    }
}
