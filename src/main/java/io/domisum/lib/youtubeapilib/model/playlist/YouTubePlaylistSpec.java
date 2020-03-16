package io.domisum.lib.youtubeapilib.model.playlist;

import io.domisum.lib.youtubeapilib.model.PrivacyStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class YouTubePlaylistSpec
{

	@Getter
	private final String title;
	@Getter
	private final String description;
	@Getter
	private final PrivacyStatus privacyStatus;

}
