package com.osc.lenus.LenusBackend.config.multipledbs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.osc.lenus.LenusBackend.repositories.central", mongoTemplateRef = "primaryMongoTemplate")
public class PrimaryMongoConfig {
}
