YouTubeApiLib
==================

YouTubeApiLib is a high level wrapper of the YouTube API, 
enabling the user to create and edit videos, playlists and more.

Its features simply wrap the official API.

### Features
* Video upload
* Thumbnail upload
* Set video privacy status
* Fetch video duration
* Fetch/set video metadata
* Create/delete playlist
* Fetch playlist id by name
* Fetch playlist videos
* Add video to playlist

### Example usages

#### Upload video
'clientId' and 'clientSecret' can be acquired from the Google API dashboard after you enabled the YouTube Data API for your account.
To generate a refresh token, use this utility: [YouTubeAuthenticator](https://github.com/domisum/YouTubeAuthenticator)

```
YouTubeApiCredentials credentials = new YouTubeApiCredentials("channelId", "clientId", "clientSecret", "refreshToken");
AuthorizedYouTubeApiClient apiClient = new AuthorizedYouTubeApiClient(credentials);
VideoUploader videoUploader = new VideoUploaderUsingApi(apiClient);


VideoStream videoStream = VideoStream.ofFile(new File("myFile.flv"));
YouTubeVideoMetadata videoMetaData = new YouTubeVideoMetadata("Title",
		"description",
		Arrays.asList("tags", "moreTags"),
		VideoCategory.COMEDY
);
YouTubeVideo youTubeVideo = new YouTubeVideo(videoStream, videoMetaData);

String videoId = videoUploader.upload(youTubeVideo, PrivacyStatus.PUBLIC);
```
