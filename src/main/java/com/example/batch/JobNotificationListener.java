package com.example.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.batch.data.Person;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobNotificationListener extends JobExecutionListenerSupport {
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
		
		if(jobExecution.getStatus() == BatchStatus.STARTED) {
			
			log.info("*** JOB STARTED! >>>>");
		}		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("*** JOB FINISHED! <<< Time to verify the results");

			jdbcTemplate.query("SELECT first_name, last_name FROM people",
				(rs, row) -> new Person(
					rs.getString(1),
					rs.getString(2))
			).forEach(person -> log.info("Found <" + person + "> in the database."));
		}
	}	

}
