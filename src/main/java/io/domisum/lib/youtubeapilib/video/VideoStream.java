package io.domisum.lib.youtubeapilib.video;

import io.domisum.lib.auxiliumlib.annotations.API;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UncheckedIOException;

@RequiredArgsConstructor
public class VideoStream
{
	
	@Getter
	private final InputStream inputStream;
	@Getter
	private final long length;
	
	
	// INIT
	@API
	public static VideoStream ofFile(File file)
	{
		try
		{
			return new VideoStream(new FileInputStream(file), file.length());
		}
		catch(FileNotFoundException e)
		{
			throw new UncheckedIOException(e);
		}
	}
	
}
