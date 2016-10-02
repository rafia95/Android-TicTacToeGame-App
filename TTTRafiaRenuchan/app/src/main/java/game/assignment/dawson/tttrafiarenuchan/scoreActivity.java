package game.assignment.dawson.tttrafiarenuchan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class scoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Bundle bun = getIntent().getExtras();

        int numWinsP1 = bun.getInt("p1Wins");
        int numLosesP1 = bun.getInt("p1Loses");
        int numWinsP2 = bun.getInt("p2Wins");
        int numLosesP2 = bun.getInt("p2Loses");

        int numTies =  bun.getInt("Ties");

        ((TextView)findViewById(R.id.p1WinsInfo)).setText(numWinsP1 + "");
        ((TextView)findViewById(R.id.p1LosesInfo)).setText(numLosesP1 + "");
        ((TextView)findViewById(R.id.p2WinsInfo)).setText(numWinsP2 + "");
        ((TextView)findViewById(R.id.p2LosesInfo)).setText(numLosesP2+ "");
        ((TextView)findViewById(R.id.scoreTiesLbl)).setText(numTies+ "");


    }
}
