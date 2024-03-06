import java.sql.*;
import java.util.Scanner;

public class ConnectionExample1 {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 드라이버 로드 (최신 JDBC 드라이버에서는 이 부분이 필요 없을 수 있습니다.)
            Class.forName("oracle.jdbc.OracleDriver");

            // 연결하기
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.1.12:1521:xe", // 포트번호와 SID 사이에 콜론(:) 추가
                    "chanchan", // 계정이름
                    "1004" // 비밀번호
            );

            System.out.println("연결 성공");
            //Statement stmt = conn.createStatement(); // 정적인 SQL 쿼리문 실행 고정된거를 사용할 때 values도 하드코딩으로 넣는다.
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (userid, username, userpassword, userage, useremail) VALUES (?,?,?,?,?)");
            Scanner scanner = new Scanner(System.in);
            while(true) {
                System.out.print("아이디 입력 : ");
                String userid = scanner.nextLine();
                if (userid.equals("q")) break;

                //입력 값을 설정 한다
                pstmt.setString(1, userid);
                pstmt.setString(2, "홍길동");
                pstmt.setString(3, "1004");
                pstmt.setInt(4, 20);
                pstmt.setString(5, "hong1@naver.com");

                System.out.println("입력된 아이디 : " + userid);

                int updated = pstmt.executeUpdate();
                //변경된 건 수
                System.out.println("변경 건수  : " + updated);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    // 연결 끊기
                    conn.close();
                    System.out.println("연결 끊기");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}