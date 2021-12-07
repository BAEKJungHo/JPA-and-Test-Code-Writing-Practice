# Test Issues

### [#issue1] Given-When-Then

- BDD(Behavior-Driven Development) 중 하나로 Daniel Terhorst-North 와 Chris Matts 에 의해 개발되었다. 
- 모든 종류의 테스트에서 `Given-When-Then` 스타일을 사용할 수 있다.
- Give-When-Then을 단위 테스트 내 비공식 블록을 표시하기 위해 주석으로 넣을 수 있다.
  - ```java
    // given
    // when
    // then
    ```
- `given` : 테스트에서 구체화하고자 하는 행동을 시작하기 전에 테스트 상태를 설명하는 부분이다.
- `when` : 구체화하고자 하는 그 행동이 된다.
- `then` : 어떤 특정한 행동 때문에 발생할거라고 예상되는 변화에 대해 설명하는 부분이다.

> [Martinfowler - GivenWhenThen](https://martinfowler.com/bliki/GivenWhenThen.html)

#### [#issue1-1] Given-When-Then Templates

```java
@DisplayName("한글명으로 작성")
@Test
void $NAME$() throws Exception {
    // given
    $END$
    // when
    
    // then
}
```

> IntelliJ > Settings > Editor > Live Templates > Custom 추가 후 `tdd` 라는 명칭으로 등록 가능. 사용 방법은 `tdd` 입력 후 엔터.

### [#issue2] @DisplayName

- 현재 나는 `Given-When-Then Templates` 을 따르고 있다.
- @Displayname 은 Test Results 에서 보여질 이름을 설정한다.
  - 따로 설정하지 않으면 메서드 이름으로 동작된다.
- @Displayname 을 클래스와 메서드에 각각 설정하면 다음과 같은 계층 구조로 Test Results 에 보이게된다.

```java
@DisplayName("EntityManagerFactory 와 EntityManager 생성 테스트")
@SpringBootTest
class EntityManagerFactoryTest {

    @DisplayName("EntityManagerFactory 생성")
    @Test
    void createEntityManagerFactory() {
    }

    @DisplayName("EntityManager 생성")
    @Test
    void createEntityManager() {
    }
}
```

```
- EntityManagerFactory 와 EntityManager 생성 테스트
   - EntityManagerFactory 생성
   - EntityManager 생성
```

