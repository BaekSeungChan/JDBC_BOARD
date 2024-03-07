package simpleboard.user;

import simpleboard.board.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDAO {

    static Connection conn = null;
    private static PreparedStatement userListPstmt = null;
    private static PreparedStatement userInsertPstmt = null;
    private static PreparedStatement userUpdatePstmt = null;
    private static PreparedStatement userDetailPstmt = null;
    private static PreparedStatement userDeletePstmt = null;
    private static PreparedStatement userDeleteAllPstmt = null;

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

            userListPstmt = conn.prepareStatement("select * from users");
            userInsertPstmt = conn.prepareStatement("insert into users (USERID, USERNAME, USERPASSWORD, USERAGE, USEREMAIL) values (?, ?, ?, ?, ?)");
            userUpdatePstmt = conn.prepareStatement("UPDATE users SET USERNAME = ?, USERPASSWORD = ?, USERAGE = ?, USEREMAIL = ? WHERE USERID = ?");
            userDetailPstmt = conn.prepareStatement("select * from users where USERID=?");
            userDeletePstmt = conn.prepareStatement("delete from users where USERID=?");
            userDeleteAllPstmt = conn.prepareStatement("delete from users");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method
    public List<User> list() {
        // 아래 구문이 동작할 수 있게 기능 추가
        // select * from boards
        List<User> list = new ArrayList<>();
        try {
            ResultSet rs = userListPstmt.executeQuery();
            while (rs.next()) {
                // 찾고자 하는 자료가 있음
                User user = new User(
                        rs.getString("USERID"),
                        rs.getString("USERNAME"),
                        rs.getString("USERPASSWORD"),
                        rs.getInt("USERAGE"),
                        rs.getString("USEREMAIL"));

                //배열에 객체를 추가한다
                list.add(user);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(User user) {
        int updated = 0;
        try {
            // 입력 값을 설정 한다
            userInsertPstmt.setString(1, user.getUserId());
            userInsertPstmt.setString(2, user.getUserName());
            userInsertPstmt.setString(3, user.getUserPassword());
            userInsertPstmt.setInt(4, user.getUserAge());
            userInsertPstmt.setString(5, user.getUserEmail());

            updated = userInsertPstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    public User read(String userId) {
        // 아래 구문이 동작할 수 있게 기능 추가
        // select * from boards where bno = ?
        User user = null;
        try {
            // 입력 값을 설정 한다


            userDetailPstmt.setString(1, userId);
            ResultSet rs = userDetailPstmt.executeQuery();

            if (rs.next()) {
                // 찾고자 하는 자료가 있음
                user = new User(
                        rs.getString("USERID"),
                        rs.getString("USERNAME"),
                        rs.getString("USERPASSWORD"),
                        rs.getInt("USERAGE"),
                        rs.getString("USEREMAIL"));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void update(User user, String name) {
        try{
            userUpdatePstmt.setString(1, user.getUserName());
            userUpdatePstmt.setString(2, user.getUserPassword());
            userUpdatePstmt.setInt(3, user.getUserAge());
            userUpdatePstmt.setString(4, user.getUserEmail());
            userUpdatePstmt.setString(5, name);

            userUpdatePstmt.executeUpdate();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int delete(String userId) {
        int updated = 0;
        try {
            userDeletePstmt.setString(1, userId);
            updated = userDeletePstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    public int clear() {
        // 아래 구문이 동작할 수 있게 기능 추가
        // delete 구문 완성해서 구현 해주세요
        int updated = 0;
        try {
            updated = userDeleteAllPstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updated;
    }
}