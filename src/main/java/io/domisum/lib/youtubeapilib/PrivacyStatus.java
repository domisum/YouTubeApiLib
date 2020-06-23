package io.domisum.lib.youtubeapilib;

public enum PrivacyStatus
{
	
	PUBLIC,
	UNLISTED,
	PRIVATE;
	
	
	public static PrivacyStatus parse(String privacyStatusString)
	{
		for(var privacyStatus : values())
			if(privacyStatus.name().equalsIgnoreCase(privacyStatusString))
				return privacyStatus;
		
		throw new IllegalArgumentException("no privacy status equal to '"+privacyStatusString+"'");
	}
	
}
