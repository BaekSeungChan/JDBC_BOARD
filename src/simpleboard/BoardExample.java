package ch20.oracle.sec12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BoardExample {
    // Field
    private Scanner scanner = new Scanner(System.in);
    private static Connection conn = null;
    private static PreparedStatement boardListPstmt = null;
    private static PreparedStatement boardInsertPstmt = null;
    private static PreparedStatement boardDetailPstmt = null;
    private static PreparedStatement boardDeletePstmt = null;
    private static PreparedStatement boardDeleteAllPstmt = null;

    static {
        try {
            //JDBC Driver 등록
            Class.forName("oracle.jdbc.OracleDriver");

            //연결하기
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.1.12:1521:xe", // 포트번호와 SID 사이에 콜론(:) 추가
                    "chanchan", // 계정이름
                    "1004" // 비밀번호
            );

            System.out.println("연결 성공");

            boardListPstmt = conn.prepareStatement("select * from boards");
            boardInsertPstmt = conn.prepareStatement("insert into boards (bno, btitle, bcontent, bwriter, bdate) values (seq_bno.nextval, ?, ?, ?, sysdate)");
            boardDetailPstmt = conn.prepareStatement("select * from boards where bno=?");
            boardDeletePstmt = conn.prepareStatement("delete from boards where bno=?");
            boardDeleteAllPstmt = conn.prepareStatement("delete from boards");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Constructor
    public BoardExample() {
    }

    // Method
    public void list() {
        System.out.println();
        System.out.println("[게시물 목록]");
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-6s%-12s%-16s%-40s\n", "no", "writer", "date", "title");
        System.out.println("-----------------------------------------------------------------------");
        // 아래 구문이 동작할 수 있게 기능 추가
        // select * from boards

        try {
            ResultSet rs = boardListPstmt.executeQuery();
            boolean bExistData = false;

            while (rs.next()) {
                bExistData = true;

                // 찾고자 하는 자료가 있음
                String bno = rs.getString("bno");
                String btitle = rs.getString("btitle");
                String bcontent = rs.getString("bcontent");
                String bwriter = rs.getString("bwriter");
                String bdate = rs.getString("bdate");

                System.out.println("bno : " + bno);
                System.out.println("btitle : " + btitle);
                System.out.println("bcontent : " + bcontent);
                System.out.println("bwriter : " + bwriter);
                System.out.println("bdate : " + bdate);
                System.out.println("====================\n");
            }

            if (!bExistData) {
                System.out.println("게시물의 자료가 존재하지 않습니다");
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        mainMenu();
    }

    public void mainMenu() {
        System.out.println();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Clear | 4.Exit");
        System.out.print("메뉴 선택: ");
        String menuNo = scanner.nextLine();
        System.out.println();

        switch (menuNo) {
            case "1" -> create();
            case "2" -> read();
            case "3" -> clear();
            case "4" -> exit();
        }
    }

    public void create() {
        System.out.println("[새 게시물 입력]");
        System.out.print("제목: ");
        // 할일 : 제목 입력
        String title = scanner.nextLine();
        System.out.print("내용: ");
        // 할일 : 내용 입력
        String content = scanner.nextLine();
        System.out.print("작성자: ");
        // 할일 : 작성자 입력
        String writer = scanner.nextLine();

        // 할일 : 보조 메뉴 출력
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
        System.out.print("메뉴 선택: ");
        String menuNo = scanner.nextLine();
        if (menuNo.equals("1")) {

            // 아래 구문이 동작할 수 있게 기능 추가
            // insert into boards (
            // bno, btitle, bcontent, bwriter, bdate
            // ) values (
            // seq_bno.nextval, ?, ?, ?, sysdate
            // )

            try {
                // 입력 값을 설정 한다
                boardInsertPstmt.setString(1, title);
                boardInsertPstmt.setString(2, content);
                boardInsertPstmt.setString(3, writer);

                int updated = boardInsertPstmt.executeUpdate();
                conn.commit();
                // 변경된 건 수
                System.out.println("변경 건수  : " + updated);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 게시물 목록 출력
        list();
    }

    public void read() {
        System.out.println("[게시물 읽기]");
        System.out.print("bno: ");
        // 게시물 번호 입력
        int bno = Integer.parseInt(scanner.nextLine());

        // 아래 구문이 동작할 수 있게 기능 추가
        // select * from boards where bno = ?

        try {
            // 입력 값을 설정 한다
            boardDetailPstmt.setInt(1, bno);

            ResultSet rs = boardDetailPstmt.executeQuery();
            if (rs.next()) {
                // 찾고자 하는 자료가 있음
                bno = rs.getInt(1);
                String btitle = rs.getString(2);
                String bcontent = rs.getString(3);
                String bwriter = rs.getString(4);
                String bdate = rs.getString(5);

                System.out.println("bno : " + bno);
                System.out.println("btitle : " + btitle);
                System.out.println("bcontent : " + bcontent);
                System.out.println("bwriter : " + bwriter);
                System.out.println("bdate : " + bdate);
                System.out.println("====================\n");

            } else {
                // 찾고자 하는 자료가 없음
                System.out.println("[" + bno + "] 에 대한 자료가 존재하지않습니다 ");
                bno = -1;
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (bno != -1) {
            // 보조 메뉴 출력
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

    public void update(int bno) {
        // 수정 내용 입력 받기
        System.out.println("[수정 내용 입력]");
        System.out.print("제목: ");
        // 할일 : 제목 입력
        String title = scanner.nextLine();
        System.out.print("내용: ");
        // 할일 : 내용 입력
        String content = scanner.nextLine();
        System.out.print("작성자: ");
        // 할일 : 작성자 입력
        String writer = scanner.nextLine();

        // 보조 메뉴 출력
        System.out.println("-------------------------------------------------------------------");
        System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
        System.out.print("메뉴 선택: ");
        String menuNo = scanner.nextLine();
        // 아래 구문이 동작할 수 있게 기능 추가
        // update 구문 완성해서 구현 해주세요

        // 게시물 목록 출력
        list();
    }

    public void delete(int bno) {
        // 아래 구문이 동작할 수 있게 기능 추가
        // delete 구문 완성해서 구현 해주세요
        try {
            // 입력 값을 설정 한다
            boardDeletePstmt.setInt(1, bno);

            int updated = boardDeletePstmt.executeUpdate();
            conn.commit();

            // 변경된 건 수
            System.out.println("삭제 건수  : " + updated);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 게시물 목록 출력
        list();
    }

    public void clear() {
        System.out.println("[게시물 전체 삭제]");
        // 아래 구문이 동작할 수 있게 기능 추가
        // delete 구문 완성해서 구현 해주세요
        try {
            int updated = boardDeleteAllPstmt.executeUpdate();
            conn.commit();

            // 변경된 건 수
            System.out.println("삭제 건수  : " + updated);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 게시물 목록 출력
        list();
    }

    public void exit() {

//      if (conn != null) {
//         try {
//            // 연결 끊기
//            conn.close();
//            System.out.println("연결 끊기");
//         } catch (SQLException e) {
//         }
//      }

        System.exit(0);
    }

    public static void main(String[] args) {
        BoardExample boardExample = new BoardExample();
        boardExample.list();
    }
}
