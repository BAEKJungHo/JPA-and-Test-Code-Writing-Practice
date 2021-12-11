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
