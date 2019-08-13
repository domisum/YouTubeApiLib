package de.domisum.youtubevideouploadlib.action.impl.actions.upload;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube.Thumbnails.Set;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.action.upload.VideoThumbnailUploader;
import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class VideoThumbnailUploaderUsingApi extends Uploader implements VideoThumbnailUploader
{

	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// UPLOAD
	@Override
	public void uploadThumbnail(String videoId, BufferedImage thumbnail) throws IOException
	{
		logger.info("Preparing to upload thumbnail for video '{}'", videoId);

		BufferedImage modifiedThumbnail = removeImageAlphaChannel(thumbnail);

		// convert to byte array to be able to measure length, which is required for upload
		byte[] imageRaw = imageToByteArray(modifiedThumbnail, "jpg");
		try(InputStream inputStream = new ByteArrayInputStream(imageRaw))
		{
			InputStreamContent imageContent = new InputStreamContent("image/jpeg", inputStream);
			imageContent.setLength(imageRaw.length);

			logger.info("Starting thumbnail upload...");
			createApiRequest(videoId, imageContent).execute();
			logger.info("Thumbnail upload complete");
		}
	}

	private BufferedImage removeImageAlphaChannel(BufferedImage originalImage)
	{
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] rgb = originalImage.getRGB(0, 0, width, height, null, 0, width);
		newImage.setRGB(0, 0, width, height, rgb, 0, width);

		return newImage;
	}


	// REQUEST
	private Set createApiRequest(String videoId, InputStreamContent imageContent) throws IOException
	{
		Set thumbnailSet = authorizedYouTubeApiClient.getYouTubeApiClient().thumbnails().set(videoId, imageContent);
		configureMediaHttpUploader(thumbnailSet.getMediaHttpUploader(), imageContent.getLength());

		return thumbnailSet;
	}


	// UTIL
	private byte[] imageToByteArray(RenderedImage image, String imageFormat) throws IOException
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, imageFormat, outputStream);

		return outputStream.toByteArray();
	}

}
