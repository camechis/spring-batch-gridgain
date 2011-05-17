package org.springframework.batch.integration.chunk;

import gate.util.DocumentProcessor;

import java.io.Serializable;

import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.step.item.Chunk;
import org.springframework.batch.core.step.item.ChunkProcessor;
import org.springframework.batch.core.step.item.SimpleChunkProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 
 * @author Alois Cochard
 */
public class SerializableChunkProcessor<I, O> implements ChunkProcessor<I>, InitializingBean, Serializable {

	private static final long serialVersionUID = 1L;

	private transient ItemProcessor<? super I, ? extends O> itemProcessor;

	private transient ItemWriter<? super O> itemWriter;

	private transient SimpleChunkProcessor<I, O> delegate;

	private Serializable itemProcessorSerializable;

	private Serializable itemWriterSerializable;

	public SerializableChunkProcessor(ItemProcessor<? super I, ? extends O> itemProcessor, ItemWriter<? super O> itemWriter) {
		this.itemProcessor = itemProcessor;
		this.itemWriter = itemWriter;
		validate();
	}

	@SuppressWarnings("unused")
	private SerializableChunkProcessor() {

	}
	@Override
	public void afterPropertiesSet() throws Exception {
		validate();
	}

	@Override
	public void process(StepContribution contribution, Chunk<I> inputs) throws Exception {
		doInit();
		delegate.process(contribution, inputs);
	}

	public void setItemProcessor(ItemProcessor<? super I, ? extends O> itemProcessor) {
		this.itemProcessor = itemProcessor;
	}

	public void setItemWriter(ItemWriter<? super O> itemWriter) {
		this.itemWriter = itemWriter;
	}
	

	@SuppressWarnings("unchecked")
	private void doInit() throws JobExecutionException {
		if (delegate != null) {
			// Delegate is already initialized
			return;
		}

		// Initializing processor and writer
		if (itemWriter == null && itemWriterSerializable == null) {
			throw new JobExecutionException("No itemWriter defined");
		}
		if (itemProcessor == null && itemProcessorSerializable == null) {
			throw new JobExecutionException("No itemWriter defined");
		}
		if (itemWriter == null)
			itemWriter = (ItemWriter) itemWriterSerializable;
		if (itemProcessor == null)
			itemProcessor = (ItemProcessor) itemProcessorSerializable;

		// Initializing delegate
		delegate = new SimpleChunkProcessor<I, O>(itemProcessor,itemWriter);
	}
	
	private void validate() {
		Assert.notNull(itemProcessor, "itemProcessor is required");
		Assert.notNull(itemWriter, "itemWriter is required");
		Assert.isInstanceOf(Serializable.class, itemProcessor, "itemProcessor must implement Serializable");
		Assert.isInstanceOf(Serializable.class, itemWriter, "itemWriter must implement Serializable");
		itemProcessorSerializable = (Serializable) itemProcessor;
		itemWriterSerializable = (Serializable) itemWriter;
	}

}
