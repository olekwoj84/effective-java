package com.pivovarit.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AccountConfiguration {

    @Bean
    AccountFacade accountFacade() {
        return new AccountFacade();
    }
}
