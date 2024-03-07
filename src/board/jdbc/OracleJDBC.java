package board.jdbc;

import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Data
public class OracleJDBC {
    Connection conn = null;

    public void connection(){
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.1.12:1521:xe", // 포트번호와 SID 사이에 콜론(:) 추가
                    "chanchan", // 계정이름
                    "1004" // 비밀번호
            );

            System.out.println("연결 성공");

        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
