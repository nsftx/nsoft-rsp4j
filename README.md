# NSoft Security API

NSoft Security API is a Resource Server protection solution designed to work with **Chameleon Identity Management** (CIM) out-of-the-box.

## Installation

#### Maven

If your project is using Maven, please follow this [guide](https://help.github.com/en/articles/configuring-apache-maven-for-use-with-github-package-registry#installing-a-package).

#### Gradle

If your project is using Gradle, please follow the steps below:

1. Create a `gradle.properties` file in `~/.gradle/` and add the following properties:
```properties
mavenUser=GITHUB_USERNAME
mavenPassword=PERSONAL_ACCESS_TOKEN
```

To generate a personal access token, visit https://github.com/settings/tokens and create a new token with read privilages.

2. Add Maven repository
```groovy
repositories {
    maven {
        credentials {
            username "$mavenUser"
            password "$mavenPassword"
        }
        url 'https://maven.pkg.github.com/nsftx/nsoft-resource-server-java'
    }
}
```

3. Pull dependencies
```groovy
dependencies {
    ...
    compile("com.nsoft.security.api:security-spring-filter:1.2.0")
    compile("com.nsoft.security.api:security-spring-resolver:1.2.0")
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

