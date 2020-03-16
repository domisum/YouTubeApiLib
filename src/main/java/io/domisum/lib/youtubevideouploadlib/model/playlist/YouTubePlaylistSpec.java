package io.domisum.lib.youtubevideouploadlib.model.playlist;

import io.domisum.lib.youtubevideouploadlib.model.PrivacyStatus;
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
