# rental

This application was generated using JHipster 7.9.3, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v7.9.3](https://www.jhipster.tech/documentation-archive/v7.9.3).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.
This application is configured for Service Discovery and Configuration with the JHipster-Registry. On launch, it will refuse to start if it is not able to connect to the JHipster-Registry at [http://localhost:8761](http://localhost:8761). For more information, read our documentation on [Service Discovery and Configuration with the JHipster-Registry][].

## Project Structure

Node is required for generation and recommended for development. `package.json` is always generated for a better development experience with prettier, commit hooks, scripts and so on.

In the project root, JHipster generates configuration files for tools like git, prettier, eslint, husky, and others that are well known and you can find references in the web.

`/src/*` structure follows default Java structure.

- `.yo-rc.json` - Yeoman configuration file
  JHipster configuration is stored in this file at `generator-jhipster` key. You may find `generator-jhipster-*` for specific blueprints configuration.
- `.yo-resolve` (optional) - Yeoman conflict resolver
  Allows to use a specific action when conflicts are found skipping prompts for files that matches a pattern. Each line should match `[pattern] [action]` with pattern been a [Minimatch](https://github.com/isaacs/minimatch#minimatch) pattern and action been one of skip (default if ommited) or force. Lines starting with `#` are considered comments and are ignored.
- `.jhipster/*.json` - JHipster entity configuration files
- `/src/main/docker` - Docker configurations for the application and services that the application depends on

## Development

To start your application in the dev profile, run:

```
./mvnw
```

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### JHipster Control Center

JHipster Control Center can help you manage and control your application(s). You can start a local control center server (accessible on http://localhost:7419) with:

```
docker-compose -f src/main/docker/jhipster-control-center.yml up
```

## Building for production

### Packaging as jar

To build the final jar and optimize the rental application for production, run:

```
./mvnw -Pprod clean verify
```

To ensure everything worked, run:

```
java -jar target/*.jar
```

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off authentication in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mariadb database in a docker container, run:

```
docker-compose -f src/main/docker/mariadb.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/mariadb.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
npm run java:docker
```

Or build a arm64 docker image when using an arm64 processor os like MacOS with M1 processor family running:

```
npm run java:docker:arm64
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

When running Docker Desktop on MacOS Big Sur or later, consider enabling experimental `Use the new Virtualization framework` for better processing performance ([disk access performance is worse](https://github.com/docker/roadmap/issues/7)).

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 7.9.3 archive]: https://www.jhipster.tech/documentation-archive/v7.9.3
[doing microservices with jhipster]: https://www.jhipster.tech/documentation-archive/v7.9.3/microservices-architecture/
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v7.9.3/development/
[service discovery and configuration with the jhipster-registry]: https://www.jhipster.tech/documentation-archive/v7.9.3/microservices-architecture/#jhipster-registry
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v7.9.3/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v7.9.3/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v7.9.3/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v7.9.3/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v7.9.3/setting-up-ci/
[node.js]: https://nodejs.org/
[npm]: https://www.npmjs.com/



```java
/**
 * A Rental.
 */
@Entity
@Table(name = "rental")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rental implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rental_status")
    private RentalStatus rentalStatus;

    // 연체료
    @Column(name = "late_free")
    private Long lateFree;

    // 대출 아이템
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RentedItem> rentedItems = new HashSet<>();

    // 연체 아이템
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OverdueItem> overdueItems = new HashSet<>();

    // 반납 아이템
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReturnedItem> returnedItems = new HashSet<>();
```


- ``@Id``: 해당 필드가 테이블의 PK임을 나타냄
- ``@GeneratedValue``: PK의 생성 규칙을 나타냄. 스프링 부트 2.0 에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 됨
- ``@Column``: 테이블의 컬럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됨. 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용함. 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나(ex: title), 타입을 TEXT로 변경하고 싶거나(ex: content) 등의 경우에 사용됨
- ``@Enumerated(EnumType.STRING)``: JPA로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지를 결정함. 기본적으로 int로 된 숫자가 저장되지만, 숫자로 저장되면 데이터베이스로 확인할 때 그 값이 무슨 코드를 의미하는지 알 수가 없음. 그래서 문자열(EnumType.STRING)로 저장될 수 있도록 선언함. 숫자로 저장하고 싶으면 그냥 EnumType.ORDINAL을 사용하면 됨
- ``@OneToMany``: 1:N 관계를 나타냄. Rental : RentedItem = 1 : N 관계임
- ``@JoinColumn``: 외래 키를 나타냄. Rental 테이블의 rental_id 컬럼이 외래 키가 됨
- ``@ManyToOne``: N:1 관계를 나타냄. RentedItem : Rental = N : 1 관계임
- ``Set<RentedItem> rentedItems = new HashSet<>()``: 1:N 관계에서 N에 해당하는 데이터를 담을 컬렉션을 선언함. 여기서는 List 대신 Set을 사용하는데, 이유는 같은 데이터가 중복해서 저장되는 것을 막기 위함임. List를 사용하면 같은 데이터가 중복해서 저장될 수 있음
- ``@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)``: JPA에서 1차 캐시를 사용하도록 설정함. 1차 캐시란, JPA를 통해 DB에서 데이터를 가져오면, 해당 데이터를 캐시에 저장해두고, 다음번에 같은 데이터를 요청하면 캐시에서 가져오는 것을 말함. 이렇게 하면, DB에 접근하는 횟수를 줄일 수 있어서 성능이 향상됨. 여기서는 NONSTRICT_READ_WRITE 옵션을 사용함. 이 옵션은 엔티티를 수정할 때, 캐시와 DB를 동기화하는 옵션임. 이 옵션을 사용하면, 엔티티를 수정할 때, 캐시와 DB를 동기화하지 않고, 캐시를 우선적으로 수정함. 이 옵션을 사용하면, 성능이 향상되지만, 데이터의 정합성이 보장되지 않음. 만약, 데이터의 정합성이 중요하다면, READ_WRITE 옵션을 사용하면 됨. 이 옵션은 엔티티를 수정할 때, 캐시와 DB를 동기화함. 이 옵션을 사용하면, 성능이 떨어지지만, 데이터의 정합성이 보장됨. 이 옵션을 사용하면, 성능이 향상되지만, 데이터의 정합성이 보장되지 않음. 만약, 데이터의 정합성이 중요하다면, READ_WRITE 옵션을 사용하면 됨. 
- ``cascade = CascadeType.ALL``: RentedItem 엔티티를 저장할 때, Rental 엔티티도 함께 저장하도록 설정함. 이 옵션을 사용하면, RentedItem 엔티티를 저장할 때, Rental 엔티티도 함께 저장하도록 설정할 수 있음. 
- ``orphanRemoval = true``: 연관된 엔티티를 삭제할 때, orphanRemoval 옵션을 사용하면, 연관된 엔티티도 함께 삭제함



<br/>
<br/>

----

<br/>
<br/>


```java

@Entity
@Getter
@Setter
@ToString
@Table(name = "overdue_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@RequiredArgsConstructor
public class OverdueItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "book_title")
    private String bookTitle;

    @ManyToOne
    @JsonIgnoreProperties("overdueItems")
    private Rental rental;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    //생성 메소드
    public static OverdueItem createOverdueItem(Long bookId, String bookTitle, LocalDate dueDate){
        OverdueItem overdueItem = new OverdueItem();
        overdueItem.setBookId(bookId);
        overdueItem.setBookTitle(bookTitle);
        overdueItem.setDueDate(dueDate);

        return overdueItem;
    }

    public OverdueItem bookId(Long bookId) {
        this.bookId = bookId;
        return this;
    }

    public OverdueItem dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public OverdueItem bookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    public OverdueItem rental(Rental rental) {
        this.rental = rental;
        return this;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OverdueItem)) {
            return false;
        }
        return id != null && id.equals(((OverdueItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
```

- ``@Entity``: JPA의 Entity임을 나타냄
- ``@Table(name = "overdue_item")``: Entity가 매핑할 테이블을 지정함. 기본적으로 클래스 이름을 테이블 이름으로 사용함
- ``@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)``: 캐시 설정. 여기서는 캐시를 사용하고, 쓰기 지연을 사용함
- ``@RequiredArgsConstructor``: 생성자를 생성함. 여기서는 파라미터가 없는 생성자만 생성함
- ``@Id``: Entity의 PK를 나타냄
- ``@GeneratedValue(strategy = GenerationType.IDENTITY)``: PK의 생성 규칙을 나타냄. 여기서는 auto_increment를 사용함
- ``@Column(name = "book_id")``: Entity의 컬럼을 나타냄. 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용함
- ``@ManyToOne``: N:1 관계를 나타냄. OverdueItem : Rental = N : 1 관계임
- ``@JsonIgnoreProperties("overdueItems")``: Rental에 있는 OverdueItem의 데이터를 가져오지 않음. Rental에 있는 OverdueItem의 데이터를 가져오면 무한루프에 빠질 수 있기 때문임  




<br/>
<br/>

----

<br/>
<br/>

```java

/**
 * A ReturnedItem.
 */
@Entity
@Table(name = "returned_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@ToString
public class ReturnedItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "returned_date")
    private LocalDate returnedDate;

    @Column(name = "book_title")
    private String bookTitle;

    @ManyToOne
    @JsonIgnoreProperties("returnedItems")
    private Rental rental;

    public static ReturnedItem createReturnedItem(Long bookId, String bookTitle, LocalDate now) {
        ReturnedItem returnedItem = new ReturnedItem();
        returnedItem.setBookId(bookId);
        returnedItem.setBookTitle(bookTitle);
        returnedItem.setReturnedDate(now);
        return returnedItem;
    }

    public ReturnedItem bookId(Long bookId) {
        this.bookId = bookId;
        return this;

    }

    public ReturnedItem returnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
        return this;
    }

    public ReturnedItem bookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public ReturnedItem rental(Rental rental) {
        this.rental = rental;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReturnedItem)) {
            return false;
        }
        return id != null && id.equals(((ReturnedItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
```

- ``@Entity``: JPA의 Entity임을 나타냄
- ``@Table(name = "returned_item")``: Entity가 매핑할 테이블을 지정함. 기본적으로 클래스 이름을 테이블 이름으로 사용함
- ``@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)``: 캐시 설정. 여기서는 캐시를 사용하고, 쓰기 지연을 사용함
- ``@RequiredArgsConstructor``: 생성자를 생성함. 여기서는 파라미터가 없는 생성자만 생성함
- ``@Id``: Entity의 PK를 나타냄
- ``@GeneratedValue(strategy = GenerationType.IDENTITY)``: PK의 생성 규칙을 나타냄. 여기서는 auto_increment를 사용함
- ``@Column(name = "book_id")``: Entity의 컬럼을 나타냄. 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용함
- ``@ManyToOne``: N:1 관계를 나타냄. ReturnedItem : Rental = N : 1 관계임
- ``@JsonIgnoreProperties("returnedItems")``: Rental에 있는 ReturnedItem의 데이터를 가져오지 않음. Rental에 있는 ReturnedItem의 데이터를 가져오면 무한루프에 빠질 수 있기 때문임















