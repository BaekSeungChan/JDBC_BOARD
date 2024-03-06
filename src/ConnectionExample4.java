import java.sql.*;
import java.util.Scanner;

public class ConnectionExample4 {
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
            PreparedStatement pstmt = conn.prepareStatement("select userid, username, userpassword, userage, useremail from users where userid=?");
            Scanner scanner = new Scanner(System.in);
            while(true) {
                System.out.print("자료를 찾기 위한 아이디 입력 : ");
                String userid = scanner.nextLine();
                if (userid.equals("q")) break;

                //입력 값을 설정 한다
                pstmt.setString(1, userid);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    //찾고자 하는 자료가 있음
                    userid = rs.getString("userid");
                    String username = rs.getString("username");
                    String userpassword = rs.getString("userpassword");
                    int    userage = rs.getInt("userage");
                    String useremail = rs.getString("useremail");

                    System.out.println("userid : " + userid);
                    System.out.println("username : " + username);
                    System.out.println("userpassword : " + userpassword);
                    System.out.println("userage : " + userage);
                    System.out.println("useremail : " + useremail);

                } else {
                    //찾고자 하는 자료가 없음
                    System.out.println("userid : [" + userid + "] 에 대한 자료가 존재하지않습니다 ");
                }
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
