# Spring Batch GridGain Integration #

This project provides some implementations of Spring Batch idioms and SPIs using [GridGain](http://www.gridgain.org).

## Getting Started ##

To build it clone and then use Maven:

    $ git clone ...
    $ cd spring-batch-gridgain
    $ mvn install

To use it you would create a project depend on both Spring Batch and this project.  If you have the SpringSource Tool Suite (STS) you can use  a template project (File->New->Spring Template Project) to get a starting point.

Using Maven: in your `pom.xml`

		<dependency>
			<groupId>org.springframework.batch</groupId>
			<artifactId>spring-batch-gridgain</artifactId>
			<version>1.0.0.BUILD-SNAPSHOT</version>
		</dependency>

and then you will be able to use all the features provided by this project.

## Features ##

`GridGainPartitionHandler` can be used to send Step execution work out into a GridGain cluster.  There is an example in the spring-batch-gridgain unit tests:

    <job id="job" xmlns="http://www.springframework.org/schema/batch">
       <step id="step-master">
          <partition handler="partitionHandler" step="step"
             partitioner="partitioner" />
       </step>
    </job>
    <bean id="partitionHandler"
       class="org.springframework.batch.core.partition.gridgain.GridGainPartitionHandler" />
