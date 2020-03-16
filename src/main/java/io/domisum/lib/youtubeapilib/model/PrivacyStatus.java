package io.domisum.lib.youtubeapilib.model;

public enum PrivacyStatus
{

	PUBLIC,
	UNLISTED,
	PRIVATE;


	public static PrivacyStatus parse(String privacyStatusString)
	{
		for(PrivacyStatus privacyStatus : values())
			if(privacyStatus.name().equalsIgnoreCase(privacyStatusString))
				return privacyStatus;

		throw new IllegalArgumentException("no privacy status equal to '"+privacyStatusString+"'");
	}

}
