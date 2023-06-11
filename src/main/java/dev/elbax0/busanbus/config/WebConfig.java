package dev.elbax0.busanbus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${NCP_API_KEY_ID}")
    private String X_NCP_APIGW_API_KEY_ID;

    @Value("${NCP_API_KEY}")
    private String X_NCP_APIGW_API_KEY;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }


    @Bean(name = "busanBimsClient")
    public WebClient busanBimsClient() {
        return WebClient.builder()
                .baseUrl("https://apis.data.go.kr/6260000/BusanBIMS/")
                .exchangeStrategies(exchangeStrategies())
                .build();
    }

    @Bean(name = "naverMapClient")
    public WebClient naverMapClient() {
        return WebClient.builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-static/v2/raster")
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", X_NCP_APIGW_API_KEY_ID)
                .defaultHeader("X-NCP-APIGW-API-KEY", X_NCP_APIGW_API_KEY)
                .build();
    }

    // WebClient 사용 시 디폴트로 설정되어 있는 메모리 크기 제한 해제
    @Bean
    public ExchangeStrategies exchangeStrategies() {
        return ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(-1))
                .build();
    }

}
