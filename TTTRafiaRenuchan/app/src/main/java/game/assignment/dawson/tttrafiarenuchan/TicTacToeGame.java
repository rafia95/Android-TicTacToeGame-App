package game.assignment.dawson.tttrafiarenuchan;

/**
 * Created by Renuchan on 9/25/2016.
 */

import java.util.Random;

public class TicTacToeGame {

    private int[] boardOccupied;
    private int playersNumber;

    public TicTacToeGame()
    {
        boardOccupied = new int[9];
        playersNumber = 1;
    }


    //0-8 (index of boardOccupied)

    public boolean setPosition(int index)
    {
        //player 1 if true
        if(playersNumber % 2 != 0)
            boardOccupied[index] = 1;
        else
            boardOccupied[index] = 2;

        boolean win = checkForWinner();

        playersNumber++;
        return win;
    }

    private boolean checkForWinner()
    {
        int whichPlayerIsIt = 0;

        if (playersNumber % 2 != 0)
            whichPlayerIsIt = 1;
        else
            whichPlayerIsIt = 2;

        if (checkRows(whichPlayerIsIt))
            return true;

        if (checkColumn(whichPlayerIsIt))
            return true;

        if (checkDiagonal(whichPlayerIsIt))
            return true;

        return false;

    }

    /*
     * This method will check if a row is filled with a players pieces
     */
    private boolean checkRows(int whichPlayerIsIt)
    {

        // check row 1
        if ((boardOccupied[0] == whichPlayerIsIt) && (boardOccupied[1] == whichPlayerIsIt) &&
                (boardOccupied[2] == whichPlayerIsIt))
        {
            return true;
        }

        // check row 2
        if ((boardOccupied[3] == whichPlayerIsIt) && (boardOccupied[4] == whichPlayerIsIt) &&
                (boardOccupied[5] == whichPlayerIsIt))
        {
            return true;
        }

        // check row 3
        if ((boardOccupied[6] == whichPlayerIsIt) && (boardOccupied[7] == whichPlayerIsIt) &&
                (boardOccupied[8] == whichPlayerIsIt))
        {
            return true;
        }

        return false;

    }

    /*
     * This method will check if a column is filled with a players pieces
     */
    private boolean checkColumn(int whichPlayerIsIt)
    {
        // check column 1
        if ((boardOccupied[0] == whichPlayerIsIt) && (boardOccupied[3] == whichPlayerIsIt) &&
                (boardOccupied[6] == whichPlayerIsIt))
        {
            return true;
        }

        // check column 2
        if ((boardOccupied[1] == whichPlayerIsIt) && (boardOccupied[4] == whichPlayerIsIt) &&
                (boardOccupied[7] == whichPlayerIsIt))
        {
            return true;
        }

        // check column 3
        if ((boardOccupied[2] == whichPlayerIsIt) && (boardOccupied[5] == whichPlayerIsIt) &&
                (boardOccupied[8] == whichPlayerIsIt))
        {
            return true;
        }

        return false;
    }


    /*
     * This method will check if a diagonal is filled with a players pieces
     */
    private boolean checkDiagonal(int whichPlayerIsIt)
    {
        // check diagonal 1
        if ((boardOccupied[0] == whichPlayerIsIt) && (boardOccupied[4] == whichPlayerIsIt) &&
                (boardOccupied[8] == whichPlayerIsIt))
        {
            return true;
        }

        // check diagonal 2
        if ((boardOccupied[2] == whichPlayerIsIt) && (boardOccupied[4] == whichPlayerIsIt) &&
                (boardOccupied[6] == whichPlayerIsIt))
        {
            return true;
        }

        return false;
    }

    public int AIMove()
    {
        boolean valid = false;
        int computersMove = 0;

        while (!valid)
        {
            Random generate = new Random();
            computersMove = (int)(Math.random() * 8) + 1;

            if (boardOccupied[computersMove] == 0)
                valid = true;
        }// close loop

        return computersMove;
    }

    public int getPlayersNumber()
    {
        return playersNumber;
    }

    public void setPlayersNumber(int num)
    {
        playersNumber = num;
    }

    public int[] getBoardOccupied()
    {
        return boardOccupied;
    }

    public void setBoardOccupied(int[] broadOcc)
    {
        boardOccupied = broadOcc;
    }



}
