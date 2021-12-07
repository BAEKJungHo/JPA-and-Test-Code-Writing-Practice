# JTCWP(JPA and Test Code Writing Practice)

- __목표__
    - JPA, Junit5 Test Code 작성 연습 및 REST API 적용
        - JPA 연습을 위한 단계별 게시판 기능 구현
- __패키지 구성__
    - `pure` 
        - STEP 1 : QueryDSL, Spring Data JPA 없이 순수 JPA 로 구현
    - `advanced`
        - STEP 2 : pure 에서 구현한 내용을 가지고 QueryDSL, Spring Data JPA 적용
    - `issues`
        - `jpa`
        - `test`
        - 개발하면서 겪은 이슈들에 대한 내용들을 정리
- __초기 구현(간단하게 시작)__
    - 회원 가입과 로그인
        - ArgumentResolver 를 활용하여 어노테이션 기반으로 회원 정보 받기
    - 게시판 CRUD
    - 페이징
    - 파일 다운로드와 업로드
- __필요 테이블__
    - 회원 테이블(MEMBER)
    - 게시판 테이블(BOARD)
    - 게시글 테이블(ARTICLE)
    - 파일 테이블(FILES)
- __브랜치 구성__
    - master
    - feature/pure-xxx
    - feature/advanced-xxx
- __기술 스택__
    - GitHub
    - Java 11
    - Spring Boot 2.6.1
    - JPA
    - QueryDSL
    - Thymeleaf
    - H2 

### ✔️ [JPA issue 정리 내용](https://github.com/BAEKJungHo/jtcwp/tree/master/issues/jpa)

- [#issue1] JPA Persistence.xml
- [#issue1-1] javax.persistence vs hibernate.xxx

### ✔️ [TEST issue 정리 내용](https://github.com/BAEKJungHo/jtcwp/tree/master/issues/test)

### Referecnes

- [JPA Contents](https://github.com/BAEKJungHo/JPA)
- [JPA Best Practice](https://github.com/cheese10yun/spring-jpa-best-practices)
- [Spring Guide](https://github.com/cheese10yun/spring-guide)
- [자바 ORM 표준 JPA Programming 요약 자료](https://ultrakain.gitbooks.io/jpa/content/)
- [Junit 5 Docs](https://junit.org/junit5/docs/current/user-guide/)
- [Junit 5 Contents](https://github.com/BAEKJungHo/test-code-in-java/blob/main/doc/JUnit5.md)
