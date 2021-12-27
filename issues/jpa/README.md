# JPA Issues

### [#issue1] JPA Persistence.xml

- __JPA 를 사용하기 위한 설정파일__
- __Location__
  - src/main/resources/META-INF
  - 해당 classpath 경로에 있으면 별도의 설정 없이 JPA 인식
- __persistence version__
  - Ex. 2.2 버전을 사용한다고 명시
- __persistence-unit__
  - DB 를 생성한다고 보면 된다.
    - 연결할 DB 하나당 영속성 유닛 하나 등록
  - name 속성에는 DB 이름 작성
- __class__
  - `<class>entity</class>`
  - 엔티티 인식이 안되면 다음과 같은 에러가 발생한다.
    - `java.lang.IllegalArgumentException: Unknown entity`
  - 빌드 환경에 따라서 클래스 인식이 자동으로 안되는 경우가 있을때 아래 구문을 사용하면 된다.
  - ```xml
    <persistence-unit name="purejpa">
          <class>com.jtcwp.purejpa.domain.member.Member</class>
          <properties>
            // 생략
          </properties>
    </persistence-unit>
    ```

#### [#issue1-1] javax.persistence vs hibernate.xxx

- javax.persistence
  - JPA 가 ORM 기술에 대한 API 표준 명세
  - 따라서, JPA 를 구현한 구현체가 바뀌어도 상관 없음
- hibernate.xxx
  - JPA 의 구현체 중 hibernate 를 사용 

#### [#issue1-2] EntityManagerFactory 생성 과정
 
- __JPA 구동 과정__
  - persistence.xml 설정 정보를 조회하여 Persistence 를 생성하고 EntityManagerFactory 를 생성하여 EntityManager 들을 생성한다.
- __EntityManagerFactory 생성 과정__
  - PersistenceXmlParser 클래스의 locatePersistenceUnits 메서드를 보면 META-INF 아래에 있는 persistence.xml 설정 정보를 조회해서 접근가능한 모든 영속성 유닛들을 가져온다.
  - EntityManagerFactoryBuilderImpl 에서 Properties properties = persistenceUnit.getProperties(); 다음과 같은 코드로 xml 에 설정 되어 있는 속성들(DB 접속 정보, hibernate.format_sql 등)을 가져와서 HashMap 에 담는다.
  - 내부에서 몇가지 단계를 거쳐서 MergedSettings 객체에 HashMap 에 담아뒀던 정보들과 다른 추가적인 설정 값들(java.vender.url, java.version, PID 등)을 담고, 기타 등등 과정을 거쳐서 객체를 생성한다.
  - 만약, 데이터베이스 연결에 실패하게되면 아래와 같은 에러가 발생한다.
    - `Cannot get a connection as the driver manager is not properly initialized`

### [#issue3] EntityManagerFactory, EntityManager

- EntityManagerFactory : 하나만 생성하여 애플리케이션 전체에서 공유
- EntityManager : 쓰레드간 공유하면 안된다. 사용하고 버려야 함

#### [#issue3-1] EntityTransaction

- EntityTransaction : 영속성 컨텍스트에 있는 엔티티에 대한 트랜잭션 처리를 할 수있는 인터페이스
- 순수 JPA 사용 시 트랜잭션 처리 방법
  - ```java
    // 순수 JPA 사용 시 정석 코드
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
        Member member = new Member();
        member.setId(1L);
        member.setUserName("Jungho");
        em.persist(member);
        tx.commit();
    } catch (Exception e) {
        tx.rollback();
    } finally {
        em.close();
    }
    emf.close();
    ```
    
### [#issue4] 영속성 컨텍스트(Persistence Context)

- __영속성 컨텍스트(Persistence Context)__
  - 엔티티를 영구 저장하는 환경
  - 엔티티를 `식별자(javax.persistence.Id; @Id)`로 구분
    - 따라서, 엔티티에는 식별자 값이 필수임.
    - @Entity 어노테이션 적용 후 @Id 없으면 IDE 에서 미리 잡아준다.
    - persist() 로 영속 상태로 만들 때, id 에 대한 값을 지정하지 않으면 다음과 같은 에러 발생
      - `javax.persistence.PersistenceException: org.hibernate.id.IdentifierGenerationException: ids for this class must be manually assigned before calling save()`
  - 영속 상태의 엔티티는 `1차 캐시`에 저장된다.
    - 즉, 영속성 컨텍스트가 내부에 Map 으로된 캐시를 가지고 있고, `KEY : @Id, VALUE : entity` 를 가지고 있다.
- [JPA 는 1차 캐시를 통해서 Repeatable Read 를 애플리케이션 레벨에서 지원한다.](https://techvu.dev/116)
  
#### [#issue4-1] 플러시(flush)

- DB 에 값이 저장되기 위해선 영속성 컨텍스트에 있는 값이 트랜잭션에 의해서 `커밋(commit)` 되어야 함.
- persist(entity) 를 한다고 SQL 문이 실행되는 것이 아니다. 이때는 영속성 컨텍스트에 엔티티가 저장되는 순간이고, 트랜잭션이 커밋되는 시점에 JPA 가 SQL 문을 만들어서 실행.
- `플러시(flush)` : 트랜잭션에 의해서 커밋되는 순간 영속성 컨텍스트에 있는 값이 DB 에 반영되는 것을 말한다.
  - em.flush() : 직접 호출
  - 트랜잭션 커밋 시 플러시 자동 호출
  - JPQL 쿼리 실행 시 플러시 자동 호출

### [#issue5] 필드와 컬럼 매핑

- __@Column__
  - `@Column(name = "name")` : 테이블 컬럼의 이름을 필드에 매핑
  - `@Column(name = "name", nullable = false)` : DDL 생성 시 NOT NULL 제약조건 추가
  - `@Column(name = "name", columnDefinition = "varchar(100) default 'empty'")` : DDL 에 그대로 내용 추가
- __@Enumerated__
  - `@Enumerated(EnumType.ORDINAL)` : 기본값, enum 순서를 데이터베이스에 저장 EX. 1, 2 ...
  - `@Enumerated(EnumType.STRING)` : enum 이름을 데이터베이스에 저장. 이 방식이 더 좋다. 나중에 enum 이 추가되었을때 순서에 의한 문제가 없다.
- __날짜 관련__
  - 구 트렌드 : @Temporal 사용
    - `@Temporal(TemporalType.TIMESTAMP)`
      - TemporalType : DATE or TIME or TIMESTAMP
  - 최신 트렌드 : LocalDate or LocalDateTime 사용
    - 애노테이션이 없어도 하이버네이트가 타입을 보고 알아서 판단해준다.
    - private LocalDateTime createdAt;
