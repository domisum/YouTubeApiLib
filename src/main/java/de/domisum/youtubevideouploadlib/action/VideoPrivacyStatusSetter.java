package de.domisum.youtubevideouploadlib.action;

import de.domisum.youtubevideouploadlib.model.PrivacyStatus;

import java.io.IOException;

public interface VideoPrivacyStatusSetter
{

	void setPrivacyStatus(String videoId, PrivacyStatus privacyStatus) throws IOException;

}
