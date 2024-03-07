package simpleboard.user;

import simpleboard.board.Board;
import simpleboard.board.BoardDAO;
import simpleboard.board.BoardExample14;

import java.util.List;
import java.util.Scanner;

public class UserService {
    // Field
    private Scanner scanner = new Scanner(System.in);
    //참조 변수 선언
    UserDAO userDAO;

    // Constructor
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Method
    public void list() {

        System.out.println();
        System.out.println("[사용자 목록]");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-6s%-12s%-16s%-6s%-40s\n", "ID", "Name", "Password", "Age", "Email");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        List<User> list = userDAO.list();
        for (User user : list) {
            user.print();
        }

        if (list.size() == 0) {
            System.out.println("사용자의 자료가 존재하지 않습니다");
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
        System.out.println("[회원 가입]");
        System.out.print("아이디: ");
        String useid = scanner.nextLine();
        System.out.print("이름: ");
        String usename = scanner.nextLine();
        System.out.print("비밀번호: ");
        String usepassword = scanner.nextLine();
        System.out.print("나이: ");
        int userage = scanner.nextInt();
        scanner.nextLine();
        System.out.print("이메일: ");
        String usermail = scanner.nextLine();

        System.out.println("-----------------------------------------------------------------------");
        System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
        System.out.print("메뉴 선택: ");
        String menuNo = scanner.nextLine();
        if (menuNo.equals("1")) {
            int updated = userDAO.insert(new User(useid, usename, usepassword, userage, usermail));
            System.out.println("변경 건수  : " + updated);
        }

//     게시물 목록 출력
        list();
    }

    public void read() {
        System.out.println("[게시물 읽기]");
        System.out.print("USERID: ");
        // 게시물 번호 입력
        String userId = scanner.nextLine();

        // 아래 구문이 동작할 수 있게 기능 추가
        // select * from boards where bno = ?

        // 입력 값을 설정 한다
        User user = userDAO.read(userId);

        System.out.printf("%-6s%-12s%-16s%-6d%-40s \n"
                , user.getUserId()
                , user.getUserName()
                , user.getUserPassword()
                , user.getUserAge()
                , user.getUserEmail()
        );
        System.out.println("====================\n");


        if (user != null) {
            // 보조 메뉴 출력
            System.out.println("-------------------------------------------------------------------");
            System.out.println("보조 메뉴: 1.Update | 2.Delete | 3.List");
            System.out.print("메뉴 선택: ");
            String menuNo = scanner.nextLine();
            System.out.println();

            switch (menuNo) {
                case "1" -> update(userId);
                case "2" -> delete(userId);
                case "3" -> list();
            }
        }

    }

    public void update(String userId) {
        // 수정 내용 입력 받기
        System.out.print("이름: ");
        String usename = scanner.nextLine();
        System.out.print("비밀번호: ");
        String usepassword = scanner.nextLine();
        System.out.print("나이: ");
        int userage = scanner.nextInt();
        scanner.nextLine();
        System.out.print("이메일: ");
        String usermail = scanner.nextLine();

        User board = new User(usename, usepassword, userage, usermail);

        // 보조 메뉴 출력
        System.out.println("-------------------------------------------------------------------");
        System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
        System.out.print("메뉴 선택: ");
        String menuNo = scanner.nextLine();
        // 아래 구문이 동작할 수 있게 기능 추가
        // update 구문 완성해서 구현 해주세요

        // 게시물 목록 출력

        userDAO.update(board, userId);

        list();
    }



    public void delete(String name) {
        // 아래 구문이 동작할 수 있게 기능 추가
        // delete 구문 완성해서 구현 해주세요
        // 입력 값을 설정 한다

        int updated = userDAO.delete(name);

        System.out.println("삭제 건수  : " + updated);

        // 게시물 목록 출력
        list();
    }

    public void clear() {
        System.out.println("[정보 전체 삭제]");
        // 아래 구문이 동작할 수 있게 기능 추가
        // delete 구문 완성해서 구현 해주세요

        userDAO.clear();

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
        UserService userService = new UserService(new UserDAO());
        userService.list();
    }
}
