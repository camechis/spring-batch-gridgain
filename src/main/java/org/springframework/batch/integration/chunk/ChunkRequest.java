package org.springframework.batch.integration.chunk;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.batch.core.StepContribution;

/**
 * 
 * @author Alois Cochard
 */

public class ChunkRequest<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Collection<? extends T> items;
	private final Long jobId;
	private final StepContribution stepContribution;

	public ChunkRequest(Collection<? extends T> items, Long jobId,
			StepContribution stepContribution) {
		this.items = items;
		this.jobId = jobId;
		this.stepContribution = stepContribution;
	}

	public Collection<? extends T> getItems() {
		return items;
	}

	public Long getJobId() {
		return jobId;
	}

	/**
	 * @return the {@link StepContribution} for this chunk
	 */
	public StepContribution getStepContribution() {
		return stepContribution;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + ": jobId=" + jobId
		+ ", contribution=" + stepContribution + ", item count="
		+ items.size();
	}

}
