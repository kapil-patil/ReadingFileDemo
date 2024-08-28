package com.example.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.filters.ChainFileListFilter;
import org.springframework.integration.file.filters.RegexPatternFileListFilter;
import org.springframework.messaging.MessageChannel;

import com.example.demo.JsonParser;
import com.example.model.BCInfo;
import com.example.model.FunctionalCutData;
import static com.example.model.FunctionalCutData.expectCount;

@Configuration
@IntegrationComponentScan
@EnableIntegration
public class IntegrationFlowConfig {

	ExecutorService executorService = Executors.newFixedThreadPool(10);

	@Bean
	public MessageChannel inputChannel() {
		return new DirectChannel();
	}

	public ChainFileListFilter fileListFilter() {
		ChainFileListFilter filter = new ChainFileListFilter();

		// Regex pattern filter to match files with a specific pattern
		RegexPatternFileListFilter regexFilter = new RegexPatternFileListFilter(".*\\.json");

		// AcceptOnce filter to ensure files are only processed once
		AcceptOnceFileListFilter acceptOnceFilter = new AcceptOnceFileListFilter();

		// Add filters to chain filter
		filter.addFilters(Arrays.asList(regexFilter, acceptOnceFilter));

		return filter;
	}

	@Bean
	public FileReadingMessageSource fileReadingMessageSource() {
		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setDirectory(new File("D:\\PSRE\\input"));
		source.setFilter(fileListFilter());
		return source;
	}

	@Bean
	public BCInfo bcInfo() {

		return new BCInfo();

	}

	public JsonParser jsonParser() {

		return new JsonParser();

	}

	@Bean
	public IntegrationFlow fileProcessingFlow() {
		return IntegrationFlow.from(fileReadingMessageSource(), e -> e.poller(p -> p.fixedDelay(5000)))
				.transform(new GenericTransformer<File, String>() {
					@Override
					public String transform(File file) {
						FunctionalCutData fcData = bcInfo().getLodInfo().ilMetaData.get("partner_br");
						if (fcData.getReceivedCount().equals(expectCount)) {

							executorService.execute(new Runnable() {
								public void run() {
									System.out.println("Asynchronous task executed by thread "+Thread.currentThread().getName());
									for (String files : fcData.getFileList()) {

										try {
											jsonParser().converttoEntity(jsonParser().loadFile(file.getPath()));
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
									
									fcData.setReceivedCount(0);
								}
							});
							
							executorService.shutdown();

						} else {
							System.out.println("skip the content");
							fcData.setReceivedCount(fcData.getReceivedCount() + 1);
							fcData.getFileList().add(file.getPath());
							System.out.println("Syncronus task executed by thread "+Thread.currentThread().getName());
						}

						return "Success: ";
					}
				})
				.enrichHeaders(headerEnricherSpec -> headerEnricherSpec.header("ProcessedTimestamp",
						System.currentTimeMillis()))
				.handle(System.out::println) // Handle and print the processed message
				.get();
	}

	
}
