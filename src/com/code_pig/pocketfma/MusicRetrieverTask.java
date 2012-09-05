package com.code_pig.pocketfma;

import android.os.AsyncTask;

/**
 *  Asynchronous task to prepare {@link MusicRetriever} and report completion 
 *  to {@MusicRetrieverPreparedListener}.
 **/
public class MusicRetrieverTask extends AsyncTask<Void,Void,Void> {
	MusicRetriever retriever;
	MusicRetrieverPreparedListener listener;

	public MusicRetrieverTask(MusicRetriever retriever, MusicRetrieverPreparedListener listener) {
		this.retriever = retriever;
		this.listener = listener;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		retriever.onInit();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		listener.onMusicRetrieverPrepared();
	}
	
    public interface MusicRetrieverPreparedListener {
        public void onMusicRetrieverPrepared();
    }
}