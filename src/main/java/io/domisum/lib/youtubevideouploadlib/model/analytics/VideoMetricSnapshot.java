package io.domisum.lib.youtubevideouploadlib.model.analytics;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

@ToString
public class VideoMetricSnapshot
{

	// ATTRIBUTES
	private final Map<Metric, Long> metrics;
	@Getter
	private final boolean monetized;


	// INIT
	public VideoMetricSnapshot(Map<Metric, Long> metrics, boolean monetized)
	{
		for(Metric metric : Metric.values())
			Validate.isTrue(metrics.containsKey(metric), "metrics map missing metric "+metric);

		this.metrics = new HashMap<>(metrics);
		this.monetized = monetized;
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
