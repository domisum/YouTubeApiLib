package io.domisum.lib.youtubeapilib.model.analytics;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

@ToString
public class VideoMetricSnapshot
{
	
	// ATTRIBUTES
	private final Map<Metric,Long> metrics;
	@Getter
	private final boolean monetary;
	
	
	// INIT
	public VideoMetricSnapshot(Map<Metric,Long> metrics, boolean monetary)
	{
		for(var metric : Metric.values())
			Validate.isTrue(metrics.containsKey(metric), "metrics map missing metric "+metric);
		
		this.metrics = new HashMap<>(metrics);
		this.monetary = monetary;
	}
	
	
	// GETTERS
	public long getMetricValue(Metric metric)
	{
		return metrics.get(metric);
	}
	
}
