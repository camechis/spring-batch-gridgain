package org.springframework.batch.core.partition.gridgain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.gridgain.grid.GridException;
import org.gridgain.grid.GridJob;
import org.gridgain.grid.GridJobAdapter;
import org.gridgain.grid.GridJobResult;
import org.gridgain.grid.GridTaskSplitAdapter;
import org.gridgain.grid.logger.GridLogger;
import org.gridgain.grid.resources.GridLoggerResource;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.StepExecution;

public class GridGainPartitionTask extends
		GridTaskSplitAdapter<PartitionProvider, Collection<StepExecution>> {

	@GridLoggerResource
	private GridLogger log = null;

	@Override
	protected Collection<? extends GridJob> split(int gridSize, PartitionProvider stepSplit) throws GridException {

		log.info("Executing steps for grid size=" + gridSize);
		System.out.println("Executing steps for grid size=" + gridSize );
		List<GridJob> jobs = new ArrayList<GridJob>(gridSize);

		//final String stepName = stepSplit.getStepName();
		final String stepName = "step";
		System.out.println("STEP NAME IS = " + stepName);
		try {
			System.out.println("SPLITTING JOB");
			for (final StepExecution stepExecution : stepSplit.getStepExecutions(gridSize)) {
				System.out.println("SPLIT JOB ONCE");
				RemoteStepExecutor stepExecutor = new RemoteStepExecutor("META-INF/spring/applicationContext.xml", stepName, stepExecution);
				jobs.add(new GridJobAdapter<RemoteStepExecutor>(stepExecutor) {
					public Serializable execute() {
						RemoteStepExecutor stepExecutor = getArgument();
						log.info("Executing step '" + stepName + "' on this node.");
						return stepExecutor.execute();
					}
				});

			}
		}
		catch (JobExecutionException e) {
			throw new GridException("Could not execute split step", e);
		}

		return jobs;
	}

	public Collection<StepExecution> reduce(List<GridJobResult> results)
			throws GridException {
		Collection<StepExecution> total = new ArrayList<StepExecution>();
		for (GridJobResult res : results) {
			StepExecution status = res.getData();
			total.add(status);
		}
		return total;
	}

}
