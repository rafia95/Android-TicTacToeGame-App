package game.assignment.dawson.tttrafiarenuchan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This Activity will drive the majority of the game
 *
 * @author Renuchan T, Rafia A
 *
 */
public class TicTacToe extends AppCompatActivity {

    private ImageButton[] pictureBox;
    private TicTacToeGame game;
    private boolean multiplayer;

    private int numWinsP1 = 0;
    private int numLosesP1 = 0;
    private int numWinsP2 = 0;
    private int numLosesP2 = 0;
    private int numTies = 0;

    private int numTurns = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        pictureBox = new ImageButton[9];
        multiplayer = false;
        game = new TicTacToeGame();

        pictureBox[0] = ((ImageButton) findViewById(R.id.imageButton0));
        pictureBox[1] = ((ImageButton) findViewById(R.id.imageButton1));
        pictureBox[2] = ((ImageButton) findViewById(R.id.imageButton2));
        pictureBox[3] = ((ImageButton) findViewById(R.id.imageButton3));
        pictureBox[4] = ((ImageButton) findViewById(R.id.imageButton4));
        pictureBox[5] = ((ImageButton) findViewById(R.id.imageButton5));
        pictureBox[6] = ((ImageButton) findViewById(R.id.imageButton6));
        pictureBox[7] = ((ImageButton) findViewById(R.id.imageButton7));
        pictureBox[8] = ((ImageButton) findViewById(R.id.imageButton8));

        if(savedInstanceState != null)
            onResortGame(savedInstanceState);

        SharedPreferences pref = getPreferences(MODE_PRIVATE);

        numWinsP1 = pref.getInt("p1WinsDisk", numWinsP1);
        numLosesP1 = pref.getInt("p1LosesDisk", numLosesP1);
        numWinsP2 = pref.getInt("p2WinsDisk", numWinsP2);
        numLosesP2 = pref.getInt("p2LosesDisk", numLosesP2);
        numTurns = pref.getInt("TiesDisk", numTies);


    }

    /**
     This method deals with all actions required after 1 click

     @param v        Gui element object clicked
     */
    public void onClick(View v)
    {
        //the number of the box selected
        String boxSelected = getResources().getResourceName(v.getId());

        int playersMove = Integer.parseInt(boxSelected.charAt(boxSelected.length() - 1) + "");

        boolean win;

        if(game.getPlayersNumber() % 2 != 0) //player 1 plays
        {
            win = enterMove(playersMove);

            if (!multiplayer && !win && numTurns < 9) {
                win = enterMove(game.AIMove()); //will make a move and inc the playerNumber, this if will only run
            }
        }
        else // this will only be invoked if a real person is also playing
            win = enterMove(playersMove);

        if(numTurns == 9 && !win) {

            numTies++;
            Toast.makeText(this, R.string.gameTie, Toast.LENGTH_SHORT).show();
            disableBoard();
        }

    }

    private boolean enterMove(int index)
    {
        boolean win = game.setPosition(index);
        changeImage(index);

        if(win) {

            int lastPlayerPlayed = game.getPlayersNumber() - 1;

            if(lastPlayerPlayed % 2 != 0) {
                numWinsP1++;
                numLosesP2++;
                Toast.makeText(this, R.string.p1WinMsg, Toast.LENGTH_SHORT).show();
            }
            else if(lastPlayerPlayed % 2 == 0) {
                numWinsP2++;
                numLosesP1++;
                Toast.makeText(this, R.string.p1LoseMsg, Toast.LENGTH_SHORT).show();
            }
            disableBoard();
            saveScoreToDisk();

        }

        numTurns++;
        return win;
    }

    private void changeImage(int index)
    {
        int draw;
        int playerThatPlayed = game.getPlayersNumber() - 1;

        if(playerThatPlayed % 2 != 0)
            draw = R.drawable.pic1;
        else
            draw = R.drawable.pic2;

        pictureBox[index].setImageResource(draw);
        pictureBox[index].setClickable(false);
    }
    private void disableBoard()
    {
        for(ImageButton ele : pictureBox)
            ele.setClickable(false);
    }

    /**
     This method resets the current game

     @param v        Gui element object clicked
     */
    public void onReset(View v)
    {
        Drawable pic = getDrawable(R.drawable.pic3);

        for(ImageButton img: pictureBox) {

            img.setImageDrawable(pic);
            img.setClickable(true);
        }

        game = new TicTacToeGame();
        numTurns = 0;
    }

    /**
     This method opens an information activity

     @param v        Gui element object clicked
     */
    public void onAbout(View v){
        Intent i = new Intent(this, aboutActivity.class);
        startActivity(i);
    }

    /**
     This method resets all points

     @param v        Gui element object clicked
     */
    public void onZero(View v){
        numWinsP1 = 0;
        numLosesP1 = 0;
        numWinsP2 = 0;
        numLosesP2 = 0;

        onReset(null);
    }

    /**
     This method displays the score of the game

     @param v        Gui element object clicked
     */
    public void onScore(View v){
        Intent i = new Intent(this, scoreActivity.class);

        i.putExtra("p1Wins", numWinsP1);
        i.putExtra("p1Loses", numLosesP1);
        i.putExtra("p2Wins", numWinsP2);
        i.putExtra("p2Loses", numLosesP2);
        i.putExtra("Ties", numTies);

        startActivity(i);

    }

    /**
     This method toggles between human and droid and resets the game

     @param v        Gui element object clicked
     */
    public void onPlay(View v){
        //switch between modes

        TextView view = (TextView) findViewById(R.id.vsLbl);

        if(multiplayer) {
            multiplayer = false;
            view.setText(R.string.pVsAi);
        }
        else {
            multiplayer = true;
            view.setText(R.string.pVp);
        }
        onReset(null);
    }


    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);

        bundle.putInt("p1WinsSave", numWinsP1);
        bundle.putInt("p1LosesSave", numLosesP1);
        bundle.putInt("p2WinsSave", numWinsP2);
        bundle.putInt("p2LosesSave", numLosesP2);
        bundle.putInt("TiesSave", numTies);

        bundle.putInt("playerCounterSave", game.getPlayersNumber());
        bundle.putInt("numTurnsSave", numTurns);
        bundle.putIntArray("gameBoardSave", game.getBoardOccupied());

    }

    private void onResortGame(Bundle bun)
    {
        numWinsP1 = bun.getInt("p1WinsSave");
        numLosesP1 = bun.getInt("p1LosesSave");
        numWinsP2 = bun.getInt("p2WinsSave");
        numLosesP2 = bun.getInt("p2LosesSave");
        numTurns = bun.getInt("numTurnsSave");
        numTies = bun.getInt("TiesSave");

        game = new TicTacToeGame();
        game.setPlayersNumber(bun.getInt("playerCounterSave"));
        game.setBoardOccupied(bun.getIntArray("gameBoardSave"));

        reDrawBroad();
    }

    private void reDrawBroad()
    {
        int[] board = game.getBoardOccupied();

        Drawable img0 = getDrawable(R.drawable.pic3);
        Drawable img1 = getDrawable(R.drawable.pic1);
        Drawable img2 = getDrawable(R.drawable.pic2);


        for(int i = 0; i < board.length; i++)
        {
            if(board[i] == 0)
                pictureBox[i].setImageDrawable(img0);
            else if (board[i] == 1){
                pictureBox[i].setImageDrawable(img1);
                pictureBox[i].setClickable(false);
            }
            else{
                pictureBox[i].setImageDrawable(img2);
                pictureBox[i].setClickable(false);
            }

        }

        TextView view = (TextView)findViewById(R.id.vsLbl);

        if(multiplayer)
            view.setText(R.string.pVp);
        else
            view.setText(R.string.pVsAi);

    }

    /**
     This method saves game info to disk
     */
    public void saveScoreToDisk()
    {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("p1WinsDisk", numWinsP1);
        editor.putInt("p1LosesDisk", numLosesP1);
        editor.putInt("p2WinsDisk", numWinsP2);
        editor.putInt("p2LosesDisk", numLosesP2);
        editor.putInt("TiesDisk", numTies);

        editor.commit();

    }

}
