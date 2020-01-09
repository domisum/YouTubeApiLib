package de.domisum.youtubevideouploadlib.model.playlist;

import de.domisum.youtubevideouploadlib.model.PrivacyStatus;
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
