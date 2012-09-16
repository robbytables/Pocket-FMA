package com.code_pig.pocketfma;

import android.os.Parcel;
import android.os.Parcelable;

class TrackItem implements Parcelable{
	private String trackName;
	private String artistName;
	private String albumName;
	private String trackArt;
	
	public static final Parcelable.Creator<TrackItem> CREATOR = new Parcelable.Creator<TrackItem>() {
		 public TrackItem createFromParcel(Parcel in) {
			 return new TrackItem(in);
		 }

		@Override
		public TrackItem[] newArray(int size) {
			return new TrackItem[size];
		}
	 };
	
	public TrackItem() {
		trackName = null;
		artistName = null;
		albumName = null;
		trackArt = null;
	}
	
	
	public TrackItem(String trackName, String artistName, String albumName, String trackArt) {
		this.trackName = trackName;
		this.artistName = artistName;
		this.albumName = albumName;
		this.trackArt = trackArt;
	}
	
	public TrackItem(Parcel in) {
		trackName = in.readString();
		artistName = in.readString();
		albumName = in.readString();
		trackArt = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p, int flags) {
		p.writeString(trackName);
		p.writeString(artistName);
		p.writeString(albumName);
		p.writeString(trackArt);
	}
	
	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getTrackArt() {
		return trackArt;
	}
	public void setTrackArt(String trackArt) {
		this.trackArt = trackArt;
	}
}