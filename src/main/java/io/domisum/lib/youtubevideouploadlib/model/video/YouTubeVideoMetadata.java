package io.domisum.lib.youtubevideouploadlib.model.video;

import io.domisum.lib.auxiliumlib.util.PHR;
import io.domisum.lib.auxiliumlib.util.java.annotations.API;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
@ToString(of = {"title", "category"})
public class YouTubeVideoMetadata
{

	private static final Logger logger = LoggerFactory.getLogger(YouTubeVideoMetadata.class);


	// CONSTANTS
	@API
	public static final int MAX_TITLE_LENGTH = 100;
	@API
	public static final int MAX_DESCRIPTION_LENGTH = 5000;
	@API
	public static final int MAX_TAGS_LENGTH = 500;


	// ATTRIBUTES
	@Getter
	private final String title;
	@Getter
	private final String description;
	@Getter
	private final List<String> tags;
	@Getter
	private final VideoCategory category;


	// VALIDATION
	public void validate()
	{
		validateTitle(title);
		validateDescription(description);
		validateTags(tags);
	}

	@API
	public static void validateTitle(CharSequence title)
	{
		int titleLength = title.length();

		Validate.isTrue(
				titleLength <= MAX_TITLE_LENGTH,
				PHR.r("title length has to be smaller or equal to {}, was {}", MAX_TITLE_LENGTH, titleLength)
		);

		logger.info("title length: {} (max: {})", titleLength, MAX_TITLE_LENGTH);
	}

	@API
	public static void validateDescription(CharSequence description)
	{
		int descriptionLength = description.length();

		Validate.isTrue(
				descriptionLength <= MAX_DESCRIPTION_LENGTH,
				PHR.r("description length has to be smaller or equal to {}, was {}", MAX_DESCRIPTION_LENGTH, descriptionLength)
		);

		logger.info("description length: {} (max: {})", descriptionLength, MAX_DESCRIPTION_LENGTH);
	}

	@API
	public static void validateTags(Iterable<String> tags)
	{
		int tagsLength = 0;
		for(String tag : tags)
			tagsLength += tag.length()+1+(tag.contains(" ") ? 2 : 0);

		Validate.isTrue(
				tagsLength <= MAX_TAGS_LENGTH,
				PHR.r("tag length has to be smaller than or equal to {}, was {}", MAX_TAGS_LENGTH, tagsLength)
		);

		logger.info("tags length: {} (max: {})", tagsLength, MAX_TAGS_LENGTH);
	}

}
