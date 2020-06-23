package io.domisum.lib.youtubeapilib.playlist;

import io.domisum.lib.youtubeapilib.PrivacyStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class PlaylistSpecification
{
	
	@Getter
	private final String title;
	@Getter
	private final String description;
	@Getter
	private final PrivacyStatus privacyStatus;
	
}
