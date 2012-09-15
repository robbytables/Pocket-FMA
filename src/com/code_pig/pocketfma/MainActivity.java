package com.code_pig.pocketfma;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

/**
 * Main activity: shows controls, sends intents to {@link MusicService} for handling.
 * 
 * @author Robby Grodin (grodin.robby@gmail.com) 2012
 **/
public class MainActivity extends Activity implements OnClickListener {
	static final String TAG = "MainActivity";
	Button playButton;
	Button pauseButton;
	Button stopButton;
	Button skipButton;
	Button rewindButton;
	Button enterButton;
	EditText searchBar;
	
	/**
	 * Initializes the {@link MusicService} and sets up event listeners.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate() called");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        playButton = (Button) findViewById(R.id.playbutton);
        pauseButton = (Button) findViewById(R.id.pausebutton);
        skipButton = (Button) findViewById(R.id.skipbutton);
        rewindButton = (Button) findViewById(R.id.rewindbutton);
        stopButton = (Button) findViewById(R.id.stopbutton);
        enterButton = (Button) findViewById(R.id.enterbutton);
        searchBar = (EditText) findViewById(R.id.searchbar);

        playButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        skipButton.setOnClickListener(this);
        rewindButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        enterButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View target) {
		Log.i(TAG, "onClick() called, target = " + target.toString());
		if (target == playButton) {
			startService(new Intent(MusicService.ACTION_PLAY));
		} else if (target == pauseButton) {
			startService(new Intent(MusicService.ACTION_PAUSE));
		} else if (target == skipButton) {
			startService(new Intent(MusicService.ACTION_SKIP));
		} else if (target == rewindButton) {
			startService(new Intent(MusicService.ACTION_REWIND));
		} else if (target == stopButton) {
			startService(new Intent(MusicService.ACTION_STOP));
		} else if (target == enterButton) {
			Intent i = new Intent(MusicService.ACTION_QUERY);
			i.putExtra("query", searchBar.getText().toString());
			startService(i);
		}
	}
}