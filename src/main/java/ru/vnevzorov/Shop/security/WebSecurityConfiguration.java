package ru.vnevzorov.Shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.vnevzorov.Shop.model.user.Role;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // обычной энкодер, кот из пароля делает хэш. То есть в базе пароли представлены как хэш
    }

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {  //HTTPSecurity - вся настройка HTTP безопасности: какие запросы куда могут идти

        http
            .csrf().disable()  //защита от межсайтовой подделки запроса.
            .authorizeRequests()  // очень важен порядок: от меньшего пермишена к большему(урлы с меньшим доступом ставим вначале)
                .antMatchers("/h2-console/**", "/style/**").permitAll()
                .antMatchers("/login*", "/registration*").permitAll()  // к данному урлу доступ есть у всех
                //.antMatchers("/shoppingcart").hasAnyAuthority(Role.ADMIN.name())
                .anyRequest().authenticated()   // все запросы, которые не попали выше, доступны только авторизованным юзерам
            .and()  //закончили конфигурить с одним, продолжаем конфигурировать главный объект
            .formLogin()    // начинаем конфиг процесс входа
                .loginPage("/login")    //урл, на кот будут перенаправляться все запросы не авторизованным пользовтелям
                .loginProcessingUrl("/login")   // урл, кот проверяет юзернейм и пассворд
                .defaultSuccessUrl("/mainpage", true) //урл, кот будет показан при успешной авторизации
                /*.failureUrl("/login?error=true")*/ //куда редирекнется при не успешной авторизации
            .and()
            .logout()
                .logoutUrl("/logout")  //куда отправить ПОСТ запрос чтобы выйти - почистится юзерская сессия
            .and()
            .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }


    /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // билдер, кот говорит спринг секьюрити как именно юзер будет авторизовываться в системе

        auth
            .inMemoryAuthentication() // объявили способ inMemory для аутентификации. Обычно используется, чтобы не создавать базу.
                .withUser("user").password(passwordEncoder().encode("password")).roles("USER"); // тут passwordEcoder захеширует пароль

    }*/
}
