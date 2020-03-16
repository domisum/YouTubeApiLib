package io.domisum.lib.youtubevideouploadlib.action.videometadata;

import java.io.IOException;
import java.time.Duration;

public interface VideoDurationFetcher
{

	Duration fetch(String videoId) throws IOException;

}
