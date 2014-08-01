package com.pmease.commons.git.command;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.pmease.commons.util.execution.Commandline;
import com.pmease.commons.util.execution.LineConsumer;
import com.pmease.commons.util.execution.StreamConsumer;

public class ShowForConsumeCommand extends GitCommand<Void> {

	private static final Logger logger = LoggerFactory.getLogger(ShowForConsumeCommand.class);
	
	private String revision;
	
	private String path;
	
	private StreamConsumer consumer;
	
	public ShowForConsumeCommand(final File repoDir) {
		super(repoDir);
	}

	public ShowForConsumeCommand revision(String revision) {
		this.revision = revision;
		return this;
	}
	
	public ShowForConsumeCommand path(String path) {
		this.path = path;
		return this;
	}
	
	public ShowForConsumeCommand consumer(StreamConsumer consumer) {
		this.consumer = consumer;
		return this;
	}
	
	@Override
	public Void call() {
		Preconditions.checkNotNull(revision, "revision has to be specified for browse.");
		Preconditions.checkNotNull(path, "path has to be specified.");
		Preconditions.checkNotNull(consumer, "consumer has to be specified.");
		
		Commandline cmd = cmd().addArgs("show", revision + ":" + path);
		cmd.execute(consumer, new LineConsumer() {

			@Override
			public void consume(String line) {
				logger.error(line);
			}
			
		}).checkReturnCode();
		
		return null;
	}

}
