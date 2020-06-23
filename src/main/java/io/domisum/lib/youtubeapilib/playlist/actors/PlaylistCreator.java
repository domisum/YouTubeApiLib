package io.domisum.lib.youtubeapilib.playlist.actors;

import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.playlist.PlaylistSpecification;

import java.io.IOException;

public interface PlaylistCreator
{
	
	String create(YouTubeApiCredentials credentials, PlaylistSpecification playlistSpecification)
			throws IOException;
	
}
