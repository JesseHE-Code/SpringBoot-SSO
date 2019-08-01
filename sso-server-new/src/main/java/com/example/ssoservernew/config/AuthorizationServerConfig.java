package com.example.ssoservernew.config;

/*
认证服务器配置
 */
import com.example.ssoservernew.util.MyRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableAuthorizationServer   //作为认证中心
@EnableRedisHttpSession

public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * 配置ClientDetailsService
     * 注意，除非你在下面的configure(AuthorizationServerEndpointsConfigurer)中指定了一个AuthenticationManager，否则密码授权方式不可用。
     * 至少配置一个client，否则服务器将不会启动。
     * ClientDetailsServiceConfigurer:AuthorizationServerConfigurer 的一个回调配置项
     * 重要属性列表：
     * clientId：（必须的）用来标识客户的Id。
     * secret：（需要值得信任的客户端）客户端安全码，如果有的话。
     * scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     * authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
     * authorities：此客户端可以使用的权限（基于Spring Security authorities）。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // 定义了两个客户端应用的通行证
        clients.inMemory()
                .withClient("app1")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all")
                .autoApprove(true)
                .and()
                .withClient("app2")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all")
                .autoApprove(true);
    }

    /**
     * 该方法是用来配置Authorization Server endpoints的一些非安全特性的，比如token存储、token自定义、授权类型等等的
     * 默认情况下，你不需要做任何事情，除非你需要密码授权，那么在这种情况下你需要提供一个AuthenticationManager
     * AuthorizationEndpoint：用来作为请求者获得授权的服务，默认的URL是/oauth/authorize.
     * TokenEndpoint：用来作为请求者获得令牌（Token）的服务，默认的URL是/oauth/token.
     * 
     * AuthorizationServerTokenServices 接口定义了一些操作使得你可以对令牌进行一些必要的管理
     * JwtTokenStore：这个版本的全称是 JSON Web Token（JWT），它可以把令牌相关的数据进行编码（因此对于后端服务来说，
     * 它不需要进行存储，这将是一个重大优势），但是它有一个缺点，那就是撤销一个已经授权令牌将会非常困难，
     * 所以它通常用来处理一个生命周期较短的令牌以及撤销刷新令牌（refresh_token）。另外一个缺点就是这个令牌占用的空间会比较大，
     * 如果你加入了比较多用户凭证信息。JwtTokenStore 不会保存任何数据，但是它在转换令牌值以及授权信息方面与 
     * DefaultTokenServices 所扮演的角色是一样的。
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        //endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());

        endpoints.tokenStore(redisTokenStore());
        endpoints.authenticationManager(authenticationManager).accessTokenConverter(jwtAccessTokenConverter());

        DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();

        tokenServices.setTokenStore(endpoints.getTokenStore());

        tokenServices.setSupportRefreshToken(true);

        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());

        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());

        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(5)); // 一天有效期

        endpoints.tokenServices(tokenServices);
    }

    /**
     * 配置授权服务器的安全，意味着实际上是/oauth/token端点。
     * /oauth/authorize端点也应该是安全的
     * 默认的设置覆盖到了绝大多数需求，所以一般情况下你不需要做任何事情。
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //回到这里
        security.tokenKeyAccess("isAuthenticated()");
    }


    /**
     * 使用JWT令牌你需要在授权服务中使用 JwtTokenStore，资源服务器也需要一个解码的Token令牌的类 JwtAccessTokenConverter，
     * JwtTokenStore依赖这个类来进行编码以及解码，因此你的授权服务以及资源服务都需要使用这个转换类。
     * Token令牌默认是有签名的，并且资源服务需要验证这个签名，因此呢，你需要使用一个对称的Key值，
     * 用来参与签名计算，这个Key值存在于授权服务以及资源服务之中。或者你可以使用非对称加密算法来对Token进行签名，
     * Public Key公布在/oauth/token_key这个URL连接中，默认的访问安全规则是"denyAll()"，即在默认的情况下它是关闭的，
     * 你可以注入一个标准的 SpEL 表达式到 AuthorizationServerSecurityConfigurer 这个配置中来将它开启
     * （例如使用"permitAll()"来开启可能比较合适，因为它是一个公共密钥）。
     */
    
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("testKey");
        return converter;
    }

    @Bean
    public TokenStore redisTokenStore(){
        MyRedisTokenStore redisTokenStore = new MyRedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix("redis");
        return redisTokenStore;
    }

}
