# JPA and Test Code Writing Practice

- __목표__
    - JPA, Junit5 Test Code 작성 연습 및 REST API 적용
- __패키지 구성__
    - `pure` 
        - STEP 1 : 순수 JPA 연습
    - `advanced`
        - STEP 2 : QueryDSL, Spring Data JPA 연습
    - `issues`
        - `jpa`
        - `test`
        - 개발하면서 겪은 이슈들에 대한 내용들을 정리
- __기술 스택__
    - GitHub
    - Java 11
    - Spring Boot 2.6.1
    - JPA
    - QueryDSL
    - Thymeleaf
    - H2, MySQL 5.7 

### ✔️ [JPA issue 정리 내용](https://github.com/BAEKJungHo/jtcwp/tree/master/issues/jpa)

- [#issue1] JPA Persistence.xml
- [#issue1-1] javax.persistence vs hibernate.xxx
- [#issue1-2] EntityManagerFactory 생성 과정
- [#issue3] EntityManagerFactory, EntityManager
- [#issue3-1] EntityTransaction
- [#issue4] 영속성 컨텍스트(Persistence Context)
- [#issue4-1] 플러시(flush)
- [#issue5] 객체와 테이블 매핑
- [#issue6] 필드와 컬럼 매핑
- [#issue7] 기본키 매핑
- [#issue7-1] 권장하는 기본키 전략
- [#issue7-2] allocationSize 를 통한 성능 최적화
- [#issue8] JPA 와 데이터베이스 연결
- [#issue9] 연관관계 매핑
- [#issue9-1] 연관관계 주인
- [#issue9-2] 양방향 연관관계와 mappedBy
- [#issue9-3] 연관관계 편의 메서드
- [#issue9-4] 양방향 연관관계 매핑 시 무한루프 주의
- [#issue9-5] 양방향 연관관계 실무 팁
- [#issue9-6] 다양한 연관관계 매핑과 설정에 따른 트레이드 오프
- [#issue10] 상속관계 매핑
- [#issue10-1] @DiscriminatorColumn
- [#issue10-2] @MappedSuperclass
- [#issue11] em.find vs em.getReference
- [#issue12] 프록시 객체 초기화
- [#issue13] 프록시 특징
- [#issue14] LazyLoading vs EagerLoading
- [#issue14-1] 프록시와 즉시 로딩 주의
- [#issue15] 영속성 전이 : CASCASDE
- [#issue16] 고아 객체
- [#issue17] 값 타입
- [#issue17-1] 임베디드 타입
- [#issue17-2] 값 타입 비교
- [#issue18] 값 타입을 컬렉션에 넣어서 사용하기
- [#issue19] 값 타입을 엔티티로 승급
- [#issue20] JPQL
- [#issue21] QueryDSL
- [#issue22] TypeQuery 와 Query
- [#issue23] 프로젝션
- [#issue23-1] 조회 결과를 DTO 로 받기
- [#issue24] 페이징
- [#issue25] 조인
- [#issue26] 서브쿼리
- [#issue27] JPQL 타입 표현
- [#issue28] 조건식
- [#issue29] JPQL 기본 함수
- [#issue30] 경로 표현식
- [#issue31] Fetch Join
- [#issue31-1] Fetch Join 한계
- [#issue32] Named Query
- [#issue33] 벌크 연산

### ✔️ [TEST issue 정리 내용](https://github.com/BAEKJungHo/jtcwp/tree/master/issues/test)

- [#issue1] Given-When-Then
- [#issue1-1] Given-When-Then Templates
- [#issue2] @DisplayName

### Referecnes

- [JPA Contents](https://github.com/BAEKJungHo/JPA)
- [JPA Best Practice](https://github.com/cheese10yun/spring-jpa-best-practices)
- [Spring Guide](https://github.com/cheese10yun/spring-guide)
- [자바 ORM 표준 JPA Programming 요약 자료](https://ultrakain.gitbooks.io/jpa/content/)
- [인프런, 자바 ORM 표준 JPA 프로그래밍 - 기본편](https://www.inflearn.com/course/ORM-JPA-Basic/dashboard)
- [Junit 5 Docs](https://junit.org/junit5/docs/current/user-guide/)
- [Junit 5 Contents](https://github.com/BAEKJungHo/test-code-in-java/blob/main/doc/JUnit5.md)
