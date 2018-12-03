package ca.homedepot.relevancy.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ca.homedepot.relevancy.config.properties.ReportProperties;

@Configuration
@Component
public class AppConfig {

	private ReportProperties reportProperties;

	@Autowired
	public AppConfig(ReportProperties reportProperties) {
		this.reportProperties = reportProperties;
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory httpRequestFactory() {
		return new HttpComponentsClientHttpRequestFactory(httpClient());
	}

	@Bean
	public HttpClient httpClient() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setDefaultMaxPerRoute(reportProperties.getHttpPoolSize());
		connectionManager.setMaxTotal(reportProperties.getHttpPoolSize());
		return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
	}
}
