package io.domisum.lib.youtubeapilib.video;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@ToString(of = {"title", "category"})
public class YouTubeVideoMetadata
{
	
	@Getter
	private final String title;
	@Getter
	private final String description;
	@Getter
	private final List<String> tags;
	@Getter
	private final VideoCategory category;
	
}
