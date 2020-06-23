package io.domisum.lib.youtubeapilib.apiclient.source;

import io.domisum.lib.auxiliumlib.datastructures.LazyCache;
import io.domisum.lib.youtubeapilib.apiclient.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorizedYouTubeApiClientSourceCache
		implements AuthorizedYouTubeApiClientSource
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClientSource backingSource;
	
	// STATUS
	private final LazyCache<YouTubeApiCredentials,AuthorizedYouTubeApiClient> cache = LazyCache.neverExpire();
	
	
	// SOURCE
	@Override
	public synchronized AuthorizedYouTubeApiClient getFor(YouTubeApiCredentials credentials)
	{
		var clientFromCacheOptional = cache.get(credentials);
		if(clientFromCacheOptional.isPresent())
			return clientFromCacheOptional.get();
		
		var clientFromBackingSource = backingSource.getFor(credentials);
		cache.set(credentials, clientFromBackingSource);
		
		return clientFromBackingSource;
	}
	
}
