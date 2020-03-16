package io.domisum.lib.youtubeapilib.action.impl.actions.upload;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import io.domisum.lib.auxiliumlib.util.java.exceptions.ShouldNeverHappenError;
import io.domisum.lib.auxiliumlib.util.math.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Uploader
{

	protected final Logger logger = LoggerFactory.getLogger(getClass());


	// MEDIA HTTP UPLOADER
	protected void configureMediaHttpUploader(MediaHttpUploader mediaHttpUploader, long uploadLength)
	{
		mediaHttpUploader.setDirectUploadEnabled(false);
		mediaHttpUploader.setProgressListener(getUploadProgressListener(uploadLength));
	}

	private MediaHttpUploaderProgressListener getUploadProgressListener(long filesizeBytes)
	{
		return ul->logUploadProgress(filesizeBytes, ul);
	}

	private void logUploadProgress(long filesizeBytes, MediaHttpUploader ul)
	{
		switch(ul.getUploadState())
		{
			case INITIATION_STARTED:
				logger.info("Upload initiation started");
				break;
			case INITIATION_COMPLETE:
				logger.info("Upload initiation complete");
				logger.info("Upload in progress: {}", getProgress(ul.getNumBytesUploaded(), filesizeBytes));
				break;
			case MEDIA_IN_PROGRESS:
				logger.info("Upload in progress: {}", getProgress(ul.getNumBytesUploaded(), filesizeBytes));
				break;
			case MEDIA_COMPLETE:
				logger.info("Upload in progress: 100.0%");
				logger.info("Upload complete");
				break;
			case NOT_STARTED:
				logger.info("Upload not started");
				break;
			default: throw new ShouldNeverHappenError();
		}
	}

	private String getProgress(long bytesUploaded, long filesizeBytes)
	{
		double progressPercent = ((double) bytesUploaded/filesizeBytes)*100;
		return MathUtil.round(progressPercent, 1)+"%";
	}

}
