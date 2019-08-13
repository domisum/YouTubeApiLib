package de.domisum.youtubevideouploadlib.model.analytics;

import lombok.ToString;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

@ToString
public class VideoMetricSnapshot
{
	// ATTRIBUTES
	private final Map<Metric, Long> metrics;


	// INIT
	public VideoMetricSnapshot(Map<Metric, Long> metrics)
	{
		for(Metric metric : Metric.values())
			Validate.isTrue(metrics.containsKey(metric), "metrics missing metric "+metric);

		this.metrics = new HashMap<>(metrics);
	}


	// GETTERS
	public Map<Metric, Long> getMetrics()
	{
		return new HashMap<>(metrics);
	}

	public long getMetricValue(Metric metric)
	{
		return metrics.get(metric);
	}

}
