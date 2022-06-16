# Spring Security Authentication Strategies

This repository is created to test different strategies to implement authentication and
authorization with [Spring Security](https://spring.io/projects/spring-security).

This project will introduce different authentication methods
using [Gradle multi module](https://spring.io/guides/gs/multi-module/).

Some methods I plan to add are:

- [x] Session Login
- [x] JWT Login
- [ ] OAuth 2.0 Login
- [ ] API Gateway

## Modules

- **springsec**: The root module.
    - **springsec-session**: Implements session login. 
    - **springsec-jwt**: Implements [JWT](https://jwt.io/introduction) ([Bearer](https://oauth.net/2/bearer-tokens/)) login.
    - **springsec-oauth**: Implements [OAuth 2.0](https://oauth.net/2/) login with different OAuth providers.
