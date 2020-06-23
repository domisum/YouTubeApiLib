package io.domisum.lib.youtubeapilib.playlist;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode(of = {"playlistId"})
@ToString(includeFieldNames = false)
public class YouTubePlaylist
{
	
	@Getter
	private final PlaylistSpecification spec;
	@Getter
	private final String playlistId;
	
	
	// GETTERS
	public String getLink()
	{
		return "https://www.youtube.com/playlist?list="+playlistId;
	}
	
}