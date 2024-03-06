import java.sql.*;
import java.util.Scanner;

public class ConnectionExample2 {
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
            PreparedStatement pstmt = conn.prepareStatement("update users set username=?, userpassword=?, userage=? where userid=?");
            Scanner scanner = new Scanner(System.in);
            while(true) {
                System.out.print("변경 자료를 찾기 위한 아이디 입력 : ");
                String userid = scanner.nextLine();
                if (userid.equals("q")) break;

                System.out.print("이름 : ");
                String username = scanner.nextLine();
                System.out.print("비밀번호 : ");
                String userpassword = scanner.nextLine();
                System.out.print("나이 : ");
                int userage = scanner.nextInt();
                //enter key를 입력 받아 버리기 위함
                scanner.nextLine();

                //입력 값을 설정 한다
                pstmt.setString(1, username);
                pstmt.setString(2, userpassword);
                pstmt.setInt(3, userage);
                pstmt.setString(4, userid);

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
