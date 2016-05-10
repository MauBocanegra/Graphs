package maurobocanegra.easypays;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import maurobocanegra.easypays.CustomViews.LineGraph;
import maurobocanegra.easypays.CustomViews.PerceCard;

public class Shower extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shower);

        /*
        LineGraph lineGraph = (LineGraph)findViewById(R.id.lineGraph1);
        lineGraph.setData(
                new int[][]{{3, 2, 6, 5, 1, 2, 7, 4, 3, 8}, {2, 1, 5, 4, 0, 1, 6, 3, 2, 7}, {4, 3, 7, 6, 2, 3, 8, 5, 4, 9}}
        );
        lineGraph.setColors(
                new int[]{R.color.colorAccent, R.color.colorPrimary, R.color.Cyan500}
        );
        lineGraph.setShadows(true);

        PerceCard perceS = (PerceCard)findViewById(R.id.percentageView1);
        PerceCard perceM = (PerceCard)findViewById(R.id.percentageView2);
        PerceCard perceL = (PerceCard)findViewById(R.id.percentageView3);

        perceS.setData(new int[]{45}); perceS.setColors(new int[]{R.color.Cyan500});
        perceM.setData(new int[]{60}); perceM.setColors(new int[]{R.color.Purple500});
        perceL.setData(new int[]{85}); perceL.setColors(new int[]{R.color.colorAccent});

        perceS.setTextView((TextView) findViewById(R.id.textView_perce1));
        perceM.setTextView((TextView)findViewById(R.id.textView_perce2));
        perceL.setTextView((TextView)findViewById(R.id.textView_perce3));
        */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shower, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
