package com.xbhy.workorder.config;

//@Component
//@Configuration
//@ConfigurationProperties(prefix = "spring.elasticsearch.rest")
//public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
//    private String uris = "192.168.1.xxx:8080";
//    private String username = "elastic";
//    private String password = "xxxxxxx";
//    @Override
//    @Bean(destroyMethod = "close")
//    public RestHighLevelClient elasticsearchClient() {
//        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo(uris)
//                .withBasicAuth(username, password)
//                .build();
//        return RestClients.create(clientConfiguration).rest();
//    }
//}