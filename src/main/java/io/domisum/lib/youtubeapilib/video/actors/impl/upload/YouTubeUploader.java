package io.domisum.lib.youtubeapilib.video.actors.impl.upload;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import io.domisum.lib.auxiliumlib.exceptions.IncompleteCodeError;
import io.domisum.lib.auxiliumlib.util.math.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class YouTubeUploader
{
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	// MEDIA HTTP UPLOADER
	protected void configureMediaHttpUploader(MediaHttpUploader mediaHttpUploader, long uploadLength)
	{
		mediaHttpUploader.setDirectUploadEnabled(false);
		mediaHttpUploader.setProgressListener(getUploadProgressListener(uploadLength));
	}
	
	private MediaHttpUploaderProgressListener getUploadProgressListener(long uploadSizeBytes)
	{
		return ul->logUploadProgress(uploadSizeBytes, ul);
	}
	
	private void logUploadProgress(long uploadSizeBytes, MediaHttpUploader ul)
	{
		switch(ul.getUploadState())
		{
			case INITIATION_STARTED:
				logger.info("Upload initiation started");
				break;
			case INITIATION_COMPLETE:
				logger.info("Upload initiation complete");
				logger.info("Upload in progress: {}", getProgressDisplay(ul.getNumBytesUploaded(), uploadSizeBytes));
				break;
			case MEDIA_IN_PROGRESS:
				logger.info("Upload in progress: {}", getProgressDisplay(ul.getNumBytesUploaded(), uploadSizeBytes));
				break;
			case MEDIA_COMPLETE:
				logger.info("Upload in progress: 100.0%");
				logger.info("Upload complete");
				break;
			case NOT_STARTED:
				logger.info("Upload not started");
				break;
			
			default: throw new IncompleteCodeError();
		}
	}
	
	private String getProgressDisplay(long bytesUploaded, long uploadSizeBytes)
	{
		double progressPercent = ((double) bytesUploaded/uploadSizeBytes)*100;
		return MathUtil.round(progressPercent, 1)+"%";
	}
	
}
