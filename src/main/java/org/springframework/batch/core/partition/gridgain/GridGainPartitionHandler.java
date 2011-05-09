package org.springframework.batch.core.partition.gridgain;

import java.util.Collection;

import org.gridgain.grid.Grid;
import org.gridgain.grid.GridFactory;
import org.gridgain.grid.GridTaskFuture;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.StepExecutionSplitter;

public class GridGainPartitionHandler implements PartitionHandler {

	private Step step;
	
	public Collection<StepExecution> handle(StepExecutionSplitter stepSplitter, StepExecution stepExecution) throws Exception {
		Grid grid = GridFactory.grid();
		PartitionProvider partitionProvider = new PartitionProvider(stepSplitter, stepExecution);
		GridTaskFuture<Collection<StepExecution>> future = grid.execute(GridGainPartitionTask.class, partitionProvider );
		return future.get();
	}
	
	public void setStep( Step step ) {
		this.step = step;
	}

}
