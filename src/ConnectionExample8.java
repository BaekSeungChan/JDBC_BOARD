import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionExample8 {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            // JDBC Driver 등록
            Class.forName("oracle.jdbc.OracleDriver");

            // 연결하기
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.1.12:1521:xe", // 포트번호와 SID 사이에 콜론(:) 추가
                    "chanchan", // 계정이름
                    "1004" // 비밀번호
            );

            System.out.println("연결 성공");

            PreparedStatement pstmt1 = conn.prepareStatement("update ACCOUNT set BALANCE = BALANCE - ? where ano='111-11-1111'");
            PreparedStatement pstmt2 = conn.prepareStatement("update ACCOUNT set BALANCE = BALANCE + ? where ano='111-11-1112'");

            conn.setAutoCommit(false);

            // 출금 값을 설정한다
            pstmt1.setInt(1, 1000);
            // SQL 구문 실행
            pstmt1.executeUpdate();

            // 입력 값을 설정한다
            pstmt2.setInt(1, 1000);
            // SQL 구문 실행
            pstmt2.executeUpdate();

            // 작업을 완료한다
            conn.commit();

            System.out.println("트랜잭션 성공적으로 완료");

            pstmt1.close();
            pstmt2.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            // 오류 발생 시 롤백을 수행한다
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
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
