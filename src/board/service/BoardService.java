package board.service;

import java.sql.SQLException;

public interface BoardService {

    //목록
    void list() throws SQLException;

    // 생성
    void create() throws SQLException;

    // 읽기
    void read() throws SQLException;

    // 게시물 전체 삭제
    void clear() throws SQLException;

    // 프로그램 종료
    void exit();


}
