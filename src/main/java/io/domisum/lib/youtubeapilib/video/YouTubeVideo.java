package io.domisum.lib.youtubeapilib.video;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString(includeFieldNames = false, exclude = {"videoStream"})
public class YouTubeVideo
{
	
	@Getter
	private final VideoStream videoStream;
	@Getter
	private final YouTubeVideoMetadata metadata;
	
}
