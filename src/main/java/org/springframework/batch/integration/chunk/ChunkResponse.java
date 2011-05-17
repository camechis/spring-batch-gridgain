package org.springframework.batch.integration.chunk;

import java.io.Serializable;

import org.springframework.batch.core.StepContribution;

/**
 * 
 * @author Alois Cochard
 *
 */
public class ChunkResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Long jobId;
	private final String message;
	private final boolean status;
	private final StepContribution stepContribution;

	public ChunkResponse(boolean status, Long jobId,
			StepContribution stepContribution) {
		this(status, jobId, stepContribution, null);
	}

	public ChunkResponse(boolean status, Long jobId,
			StepContribution stepContribution, String message) {
		this.status = status;
		this.jobId = jobId;
		this.stepContribution = stepContribution;
		this.message = message;
	}

	public ChunkResponse(Long jobId, StepContribution stepContribution) {
		this(true, jobId, stepContribution, null);
	}

	public Long getJobId() {
		return jobId;
	}

	public String getMessage() {
		return message;
	}

	public StepContribution getStepContribution() {
		return stepContribution;
	}

	public boolean isSuccessful() {
		return status;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + ": jobId=" + jobId
		+ ", stepContribution=" + stepContribution + ", successful="
		+ status;
	}

}
