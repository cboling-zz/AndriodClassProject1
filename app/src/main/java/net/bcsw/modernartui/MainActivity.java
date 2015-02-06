package net.bcsw.modernartui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity
{
    static private final String MOMA_URL = "http://MoMA.org";

    LinearLayout topLeftLayout;
    LinearLayout bottomLeftLayout;
    LinearLayout topRightLayout;
    LinearLayout middleRightLayout;
    LinearLayout bottomRightLayout;

    ArrayList<LinearLayout> layoutList = new ArrayList<LinearLayout>();

    // For use with app chooser
    static private final String CHOOSER_TEXT = "Load " + MOMA_URL + " with:";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup variables to our layouts

        topLeftLayout = (LinearLayout) findViewById(R.id.leftTopSecondLevelColumn);
        bottomLeftLayout = (LinearLayout) findViewById(R.id.leftBottomSecondLevelColumn);
        topRightLayout = (LinearLayout) findViewById(R.id.rightTopSecondLevelColumn);
        middleRightLayout = (LinearLayout) findViewById(R.id.rightMiddleSecondLevelColumn);
        bottomRightLayout = (LinearLayout) findViewById(R.id.rightBottomSecondLevelColumn);

        layoutList.add(topLeftLayout);
        layoutList.add(bottomLeftLayout);
        layoutList.add(topRightLayout);
        layoutList.add(middleRightLayout);
        layoutList.add(bottomRightLayout);

        for (LinearLayout item : layoutList)
        {
            Drawable drawable = item.getBackground();
            if (drawable instanceof ColorDrawable)
            {
                int defaultColor = ((ColorDrawable)drawable).getColor();
                item.setTag(defaultColor);
            }
            else
            {
                item.setTag((int)0xFFFFFF);
            }
        }
        // Setup seek bar handler

        SeekBar seekbar = (SeekBar)findViewById(R.id.seekBar);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                for (LinearLayout item : layoutList)
                {
                    int color = (int)item.getTag();

                    if (color != -1)
                    {
                        color = color << progress;
                        item.setBackgroundColor(color);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_information)
        {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            // Hook up buttons

            Button visitMomaButton = (Button)dialog.findViewById(R.id.visitMOMA);
            visitMomaButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MOMA_URL));

                    //  Create a chooser intent, for choosing which Activity will carry out the
                    // baseIntent

                    Intent chooserIntent = Intent.createChooser(baseIntent, CHOOSER_TEXT);

                    // Start the chooser Activity, using the chooser intent

                    if (baseIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(chooserIntent);
                    }
                }
            });

            Button notNowButton = (Button)dialog.findViewById(R.id.notNow);
            notNowButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                }
            });
            // Now show it
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
