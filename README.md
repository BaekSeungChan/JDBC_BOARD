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


