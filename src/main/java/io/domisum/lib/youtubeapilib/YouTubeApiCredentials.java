package io.domisum.lib.youtubeapilib;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class YouTubeApiCredentials
{
	
	@Getter
	private final String clientId;
	@Getter
	private final String clientSecret;
	@Getter
	private final String refreshToken;
	
}
