package com.fdc.in.payment;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.fdc.in.payment.aggregate.InboundPayment;
import com.fdc.in.payment.integration.file.processor.InboundPaymentItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public MongoTemplate mongoTemplate;

    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<InboundPayment> reader() {
        FlatFileItemReader<InboundPayment> reader = new FlatFileItemReader<InboundPayment>();
        reader.setResource(new ClassPathResource("payment.csv"));
        reader.setLineMapper(new DefaultLineMapper<InboundPayment>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "cvr", "cpr", "ovkAgreementNumber", "amount" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<InboundPayment>() {{
                setTargetType(InboundPayment.class);
            }});
        }});
        return reader;
    }

    @Bean
    public InboundPaymentItemProcessor processor() {
        return new InboundPaymentItemProcessor();
    }

    @Bean
    public MongoItemWriter<InboundPayment> writer() {
    	MongoItemWriter<InboundPayment> writer = new MongoItemWriter<InboundPayment>();
    	writer.setTemplate(mongoTemplate);
    	writer.setCollection("inboundPayment");
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job processPaymentJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("processPaymentJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<InboundPayment, InboundPayment> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    // end::jobstep[]
}
