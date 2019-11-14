package com.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.util.Date;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    private JobLauncher jobLauncher;

    @Value("${spring.mail.username}")
    private String sender;

    private String attachment;



    @Bean
    public JdbcCursorItemReader<Pret> reader(){
        JdbcCursorItemReader<Pret> reader = new JdbcCursorItemReader<Pret>();
        reader.setDataSource(dataSource);
        reader.setSql("select a.id ,a.date_pret,a.date_retour,a.nom_livre,a.utilisateur, b.mail from " +
                "pret  a inner join utilisateur  b on a.utilisateur=b.identifiant where date_retour < CURDATE() and rendu=false;");
        reader.setRowMapper(new PretRowMapper());

        return reader;
    }


    @Bean
    public PretItemProcessor processor(){
        return new PretItemProcessor(sender,attachment);
    }

    @Bean
    public MailBatchItemWriter writer() {
        MailBatchItemWriter writer = new MailBatchItemWriter();
        return writer;
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1").<Pret,MimeMessage> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job retardTagUpdateJob() {
        return jobBuilderFactory.get("retardTagUpdate")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    public void perform() throws Exception {

        System.out.println("Job Started at :" + new Date());

        JobParameters param = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        JobExecution execution = jobLauncher.run(retardTagUpdateJob(), param);

        System.out.println("Job finished with status :" + execution.getStatus());
    }



}
