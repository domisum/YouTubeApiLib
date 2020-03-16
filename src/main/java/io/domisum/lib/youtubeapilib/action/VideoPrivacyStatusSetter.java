package io.domisum.lib.youtubeapilib.action;

import io.domisum.lib.youtubeapilib.model.PrivacyStatus;

import java.io.IOException;

public interface VideoPrivacyStatusSetter
{

	void setPrivacyStatus(String videoId, PrivacyStatus privacyStatus) throws IOException;

}
