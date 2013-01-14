package com.code_pig.pocketfma;

import java.io.InputStream;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
	TextView trackName;
	TextView artistName;
	TextView albumName;
	ImageView trackArt;
	
	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context c, Intent i) {
			setTrackInfo((TrackItem) i.getExtras().get("TrackItem"));
		}
	};
	private IntentFilter filter = new IntentFilter("com.code_pig.pocketfma.action.UPDATE_UI");
	Boolean receiverIsRegistered = false;
	
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
        trackName = (TextView) findViewById(R.id.tracktext);
        artistName = (TextView) findViewById(R.id.artisttext);
        albumName = (TextView) findViewById(R.id.albumtext);
        trackArt = (ImageView) findViewById(R.id.trackart);
        
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
	
	public void setTrackInfo(TrackItem track){
		trackName.setText(track.getTrackName());
		artistName.setText("by " + track.getArtistName());
		albumName.setText("off " + track.getAlbumName());
		if(!track.getTrackArt().equals("null")) {
			new DownloadImageTask(trackArt).execute(track.getTrackArt());
		} else {
			trackArt.setImageResource(R.drawable.ic_stat_playing);
		}
	}

	
	@Override
	public void onPause(){
		super.onPause();
		if (receiverIsRegistered) {
		    unregisterReceiver(receiver);
		    receiverIsRegistered = false;
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if (!receiverIsRegistered) {
		    registerReceiver(receiver, filter);
		    receiverIsRegistered = true;
		}
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		static final String TAG = "DownloadImageTask";
	    ImageView view;
	    

	    public DownloadImageTask(ImageView view) {
	        this.view = view;
	        Log.i(TAG, "DownloadImageTask created");
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String imageURL = urls[0];
	        Bitmap bm = null;
	        try {
	        	Log.i(TAG, "retrieving image from: " + imageURL);
	            InputStream in = new java.net.URL(imageURL).openStream();
	            bm = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e(TAG, "Error downloading file: " + e.getMessage());
	        }
	        return bm;
	    }

	    protected void onPostExecute(Bitmap result) {
	        view.setImageBitmap(result);
	    }
	}
}