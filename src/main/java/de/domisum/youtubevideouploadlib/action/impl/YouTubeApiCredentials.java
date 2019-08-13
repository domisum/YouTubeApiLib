package de.domisum.youtubevideouploadlib.action.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class YouTubeApiCredentials
{

	@Getter private final String channelId;

	@Getter private final String clientId;
	@Getter private final String clientSecret;
	@Getter private final String refreshToken;

}
