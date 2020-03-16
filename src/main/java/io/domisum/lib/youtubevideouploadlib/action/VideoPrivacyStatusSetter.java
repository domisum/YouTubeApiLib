package io.domisum.lib.youtubevideouploadlib.action;

import io.domisum.lib.youtubevideouploadlib.model.PrivacyStatus;

import java.io.IOException;

public interface VideoPrivacyStatusSetter
{

	void setPrivacyStatus(String videoId, PrivacyStatus privacyStatus) throws IOException;

}
