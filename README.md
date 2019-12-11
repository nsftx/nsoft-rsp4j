# NSoft Security API

NSoft Security API is a Resource Server protection solution designed to work with **Chameleon Identity Management** (CIM) out-of-the-box.

## Repository installation

#### Maven

If your project is using Maven, please follow this [guide](https://help.github.com/en/github/managing-packages-with-github-packages/configuring-apache-maven-for-use-with-github-packages).

#### Gradle

If your project is using Gradle, please follow this [guide](https://help.github.com/en/github/managing-packages-with-github-packages/configuring-gradle-for-use-with-github-packages).

## Installation

#### Maven

```xml
<dependencies>
    ...
    <dependency>
      <groupId>com.nsoft.api.security</groupId>
      <artifactId>security-jwt-verifier</artifactId>
      <version>1.2.0</version>
    </dependency>
    ...
    <dependency>
      <groupId>com.nsoft.api.security</groupId>
      <artifactId>security-spring-resolver</artifactId>
      <version>1.2.0</version>
    </dependency>
    ...
<dependencies>
```

#### Gradle

```groovy
dependencies {
    ...
    implementation "com.nsoft.security.api:security-spring-filter:1.2.0"
    ...
    implementation "com.nsoft.security.api:security-spring-resolver:1.2.0"
    ...
}
```

## Usage

##### Filter
```java
package com.nsoft.api.security.example;

import com.nsoft.api.security.spring.filter.AbstractProtectedRouteFilter;
import com.nsoft.api.security.spring.filter.route.ProtectedRouteRegistry;
import org.springframework.stereotype.Component;

@Component
public class HttpRequestFilter extends AbstractProtectedRouteFilter {

    @Override
    public void registerProtectedRoutes(ProtectedRouteRegistry registry) {
        registry.registerRoute("/route1");
        registry.registerRoute("/route2/**");
    }
}
```

##### Resolver
```java
package com.nsoft.api.security.example;

import com.nsoft.api.security.spring.resolver.JWTClaimsSetArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class SpringAppConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new JWTClaimsSetArgumentResolver());
    }
}
```

