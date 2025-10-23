package com.scm.scm20.config;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.scm20.entities.Social;

@Configuration
public class SecurityConfig {

  @Autowired
  private OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;

  Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  /*
   * We have created below method for testing purpose and understanding purpose.
   * As this method created the user present in inMemory or we can say manually
   * created , we dont need this for our project. For out project , user we do
   * signup will be stored in DB and we want to do the login from those users. So
   * ,this method is not for us just for learning.
   */
  // @Bean
  // public UserDetailsService userDetailService() {

  // UserDetails userDetails1 = User
  // .withDefaultPasswordEncoder()
  // .username("admin123")
  // .password("admin123")
  // .build(); // if we dont use passwrod encoder , we will not be able to login ,
  // thats why for testing purpose , we are using the deprecated method

  // UserDetails userDetails2 =
  // User.withUsername("userr").password("userr123").build(); // If we login with
  // this user , we will not be able to do login

  // var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(userDetails1,
  // userDetails2);

  // return inMemoryUserDetailsManager;

  // }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {

    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    // IN below statement , we are configuring teh URLS which we need to have a
    // public access and which we want to have a private access
    httpSecurity.authorizeHttpRequests(authorize -> {
      // authorize.requestMatchers("/home" , "/register","/services").permitAll();
      authorize.requestMatchers("/user/**").authenticated();
      authorize.requestMatchers("/contact/**").authenticated();
      authorize.anyRequest().permitAll();
    });

    // Form default login method
    // IF we want to change something related to form login , we will come here and
    // change the below statement.
    httpSecurity.formLogin(formLogin -> {

      formLogin.loginPage("/login").loginProcessingUrl("/authenticate").successForwardUrl("/user/dashboard") // This
                                                                                                             // will
                                                                                                             // forward
                                                                                                             // as a
                                                                                                             // POST
                                                                                                             // request,
                                                                                                             // so the
                                                                                                             // target
                                                                                                             // URL must
                                                                                                             // be
                                                                                                             // mapped
                                                                                                             // using
                                                                                                             // @PostMapping.
          // .failureForwardUrl("/login?error=true") //This will forward a post requets as
          // well and we need to either configure config as post but will get issue for
          // GET mapping..recommend is to create anotehr postmapping as well in
          // controller.
          .usernameParameter("email").passwordParameter("password");

    });

    httpSecurity.csrf(AbstractHttpConfigurer::disable); // Not recommeneded disabling the csrf for security purposes go
                                                        // to READ_ME for more details

    // Below configurations is for Oauth of google and GIthub which we have
    // configured while doing login.

    // httpSecurity.oauth2Login(Customizer.withDefaults());

    httpSecurity.oauth2Login(oauth -> {
      oauth.loginPage("/login");
      oauth.successHandler(oAuthAuthenticationSuccessHandler);
    });

    httpSecurity.logout(logout -> {
      logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true");
    });

    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ModelMapper modelmapper() {

    ModelMapper modelMapper = new ModelMapper();
    // String → Enum mapping
    modelMapper.addConverter(ctx -> ctx.getSource() == null ? null : Social.valueOf(ctx.getSource().toUpperCase()),
        String.class, Social.class);

    return modelMapper;
  }

}