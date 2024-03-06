ResultSet 구조
- SELECT 문에 기술된 컬럼으로 구성된 행(row)의 집합
```java
SELECT userid, username, userage FROM users
```
- 커서 cursor가 있는 행의 데이터만 읽을 수 있음
- first행을 읽으려면 next() 메소드로 커서 이동
```java
boolean result = rs.next();
```

1개의 데이터 행만 가져올 경우
```java
Result rs = pstmt.executeQuery();
if(rs.next()){
    //첫 번째 데이터 행 처리
} else {
    //afterLast 행으로 이동했을 경우
}
```

N개의 데이터 행을 가져올 경우
```java
Result rs = pstmt.executeQuery();
if(rs.next()){
    //첫 번째 데이터 행 처리
} else {
    //afterLast 행으로 이동했을 경우
}
```
주의할 점)

- SELECT 문에 따라 ResultSet에는 많은 데이터 행이 저장될 수 있기 때문에 ResultSeet을 더 이상 사용하지 않는다면 close() 메소드를 호출해서 ResultSet이 사용한 메모리를 해제하는 것이 좋다.



프로시저와 함수
- Oracle DB에 저장되는 PL/SQL 프로그램. 클라이언트 프로그램에서 매개값과 함께 프로시저 또는 함수를 호출하면 DB 내부애서 일련의 SQL 문을 실행하고, 실행 결과를 클라이언트 프로그램으로 돌려주는 역할을 한다.
- JDBC에서 프로시저와 함수를 호출할 때는 CallableStatement를 사용한다. 프로시저와 함수의 매개변수화된 호출문을 작성하고 Connection의 prepareCall() 메소드로부터 CallableStatement 객체를 얻을 수 있다.

```java
// 프로시저를 호출할 경우
String sql = "{ call 프로시저명(?,? ...) }";
CallableStatement csmt = conn.prepareCall(sql);

// 함수를 호출할 경우
String sql = "{ ? = call 함수명(?, ?, ....)}"
CallableStatement cstmt = conn.prepareCall(sql);
```





트랜잭션
- 기능 처리의 최소 단위. 하나의 기능은 여러 소작업들로 구성
- 트랜잭션은 소작업들이 모두 성공하거나 실패해야 함
- 커밋은 내부 작업을 모두 성공 처리하고, 롤백은 실행 전으로 돌아간다는 의미에서 모두 실패 처리
- JDBC에서 트랜잭션을 제어 시 Connection의 setAutoCommit() 메소드로 자동 커밋을 꺼야 함
```java
conn.setAutoCommit(false);
```



