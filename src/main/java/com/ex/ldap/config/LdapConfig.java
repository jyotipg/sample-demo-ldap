package com.hsbc.ldap.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {
	
  @Bean
  ContextSource contextSource() {
      LdapContextSource ldapContextSource = new LdapContextSource();
      ldapContextSource.setUserDn("uid=admin,ou=system");
      ldapContextSource.setUrl("ldap:localhost:10389");
      ldapContextSource.setPassword("secret");
      ldapContextSource.setBase("dc=example,dc=com");

      return ldapContextSource;
  }

  @Bean
  LdapTemplate ldapTemplate(ContextSource contextSource) {
      return new LdapTemplate(contextSource);
  }
}
