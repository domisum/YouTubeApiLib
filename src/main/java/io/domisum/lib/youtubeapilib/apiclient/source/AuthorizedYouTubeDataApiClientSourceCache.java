package io.domisum.lib.youtubeapilib.apiclient.source;

import io.domisum.lib.auxiliumlib.datastructures.LazyCache;
import io.domisum.lib.youtubeapilib.apiclient.AuthorizedYouTubeDataApiClient;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorizedYouTubeDataApiClientSourceCache
		implements AuthorizedYouTubeDataApiClientSource
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeDataApiClientSource backingSource;
	
	// STATUS
	private final LazyCache<YouTubeApiCredentials,AuthorizedYouTubeDataApiClient> cache = LazyCache.neverExpire();
	
	
	// SOURCE
	@Override
	public synchronized AuthorizedYouTubeDataApiClient getFor(YouTubeApiCredentials credentials)
	{
		var clientFromCacheOptional = cache.get(credentials);
		if(clientFromCacheOptional.isPresent())
			return clientFromCacheOptional.get();
		
		var clientFromBackingSource = backingSource.getFor(credentials);
		cache.set(credentials, clientFromBackingSource);
		
		return clientFromBackingSource;
	}
	
}
