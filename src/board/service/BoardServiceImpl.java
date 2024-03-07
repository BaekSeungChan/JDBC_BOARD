package board.service;

import board.domain.Board;
import board.jdbc.OracleJDBC;
import oracle.jdbc.OracleDriver;

import java.sql.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class BoardServiceImpl extends OracleDriver implements BoardService {

    OracleJDBC oracleJDBC = new OracleJDBC();
    Scanner scanner = new Scanner(System.in);



    public void list() throws SQLException {


        oracleJDBC.connection();
        Connection conn = oracleJDBC.getConnection();

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM boards");
        ResultSet rs = pstmt.executeQuery();

        System.out.println();
        System.out.println("[게시물 목록]");
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-6s%-12s%-16s%-40s\n", "no", "writer", "date", "title");
        System.out.println("-----------------------------------------------------------------------");

        while (rs.next()) {

            int bno = rs.getInt("bno");
            String btitle = rs.getString("btitle");
            String bcontent = rs.getString("bcontent");
            String bwriter = rs.getString("bwriter");

            System.out.printf("%-6s%-12s%-16s%-40s \n"
                    , bno
                    , btitle
                    , bcontent
                    , bwriter);

        }

    }


    public void create() throws SQLException {

        oracleJDBC.connection();
        Connection conn = oracleJDBC.getConnection();
        conn.setAutoCommit(false);
        System.out.println("[새 게시물 입력]");
        System.out.print("제목: ");
        //할일 : 제목 입력
        String title = scanner.nextLine();
        System.out.print("내용: ");
        //할일 : 내용 입력
        String content = scanner.nextLine();
        System.out.print("작성자: ");
        //할일 : 작성자 입력
        String writer = scanner.nextLine();

        //할일 : 보조 메뉴 출력
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
        System.out.print("메뉴 선택: ");
        String menuNo = scanner.nextLine();
        if (menuNo.equals("1")) {
            //할일 : 입력된 정보를 기준으로 객체를 생성하여 배열에 저장 하는 기능을 구현한다
            Board board = new Board(title, content, writer);
            try {
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO boards (bno, btitle, bcontent, bwriter, bdate) VALUES (seq_bno.nextval, ?, ?, ?, sysdate)");


                pstmt.setString(1, board.getBtitle());
                pstmt.setString(2, board.getBcontent());
                pstmt.setString(3, board.getBwriter());
                int updated = pstmt.executeUpdate();

                System.out.println("변경 건수  : " + updated);

                board.setBno(board.getBno());

                conn.commit();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        list();
    }


    @Override
    public void read() throws SQLException {
        oracleJDBC.connection();
        Connection conn = oracleJDBC.getConnection();
        System.out.println("[게시물 읽기]");
        System.out.print("bno: ");
        //게시물 번호 입력
        int bno = Integer.parseInt(scanner.nextLine());

        PreparedStatement pstmt = conn.prepareStatement("SELECT bno, btitle, bcontent, bwriter, bdate FROM boards WHERE bno = ?");

        // 아래 구문이 동작할 수 있게 기능 추가

        pstmt.setInt(1, bno);

        ResultSet rs = pstmt.executeQuery();

        //할일 : 입력된 게시물 번호를 이용하여 게시물 목록에서 자료를 찾아 출력한다

        if (rs.next()) {
            bno = rs.getInt("bno");
            String btitle = rs.getString("btitle");
            String bcontent = rs.getString("bcontent");
            String bwriter = rs.getString("bwriter");

            System.out.printf("%-6s%-12s%-16s%-40s \n"
                    , bno
                    , btitle
                    , bcontent
                    , bwriter);

            //보조 메뉴 출력
            System.out.println("-------------------------------------------------------------------");
            System.out.println("보조 메뉴: 1.Update | 2.Delete | 3.List");
            System.out.print("메뉴 선택: ");
            String menuNo = scanner.nextLine();
            System.out.println();

            switch (menuNo) {
                case "1" -> update(bno);
                case "2" -> delete(bno);
                case "3" -> list();
            }

        }
    }



    public void update(int num) throws SQLException {
        oracleJDBC.connection();
        Connection conn = oracleJDBC.getConnection();
        conn.setAutoCommit(false);
        //수정 내용 입력 받기
        System.out.println("[수정 내용 입력]");
        System.out.print("제목: ");
        //할일 : 제목 입력
        String title = scanner.nextLine();
        System.out.print("내용: ");
        //할일 : 내용 입력
        String content = scanner.nextLine();
        System.out.print("작성자: ");
        //할일 : 작성자 입력
        String writer = scanner.nextLine();

        //보조 메뉴 출력
        System.out.println("-------------------------------------------------------------------");
        System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
        System.out.print("메뉴 선택: ");
        String menuNo = scanner.nextLine();

        PreparedStatement pstmt = conn.prepareStatement("UPDATE boards SET btitle = ?, bcontent = ?, bwriter = ?, bdate = sysdate WHERE bno = ?");
        if ("1".equals(menuNo)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, writer);
            pstmt.setInt(4, num);  // 게시물 번호를 파라미터로 설정
        }

        int updated = pstmt.executeUpdate();
        System.out.println("변경 건수: " + updated);

        conn.commit();

        //게시물 목록 출력
        list();
    }


    public void delete(int num) throws SQLException {
        oracleJDBC.connection();
        Connection conn = oracleJDBC.getConnection();

        conn.setAutoCommit(false);
        // 아래 구문이 동작할 수 있게 기능 추가
        // delete 구문 완성해서 구현 해주세요

        PreparedStatement pstmt = conn.prepareStatement("delete from boards where bno=?");
        pstmt.setInt(1, num);

        int updated = pstmt.executeUpdate();

        System.out.println("변경된 건 수 : " + updated);

        conn.commit();

        list();
    }


    public void clear() throws SQLException {

        oracleJDBC.connection();
        Connection conn = oracleJDBC.getConnection();
        conn.setAutoCommit(false);
        System.out.println("[게시물 전체 삭제]");
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM boards");
            int deleted = pstmt.executeUpdate();
            System.out.println("삭제된 행의 수: " + deleted);

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 게시물 목록 출력
        list();
    }


    @Override
    public void exit () {
        System.exit(0);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
