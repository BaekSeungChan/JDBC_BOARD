package board.service;

import java.sql.SQLException;
import java.util.Scanner;

public class BoardMain {
    private BoardService boardService;

    public BoardMain(BoardService boardService) {
        this.boardService = boardService;
    }

    private Scanner scan = new Scanner(System.in);
    public void mainMenu() throws SQLException {
        System.out.println();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Clear | 4.Exit");
        System.out.print("메뉴 선택: ");
        String menuNo = scan.nextLine();
        System.out.println();

        switch(menuNo) {
            case "1" -> boardService.create();
            case "2" -> boardService.read();
            case "3" -> boardService.clear();
            case "4" -> boardService.exit();
        }
    }


    public static void main(String[] args) throws SQLException {
        BoardService boardService1 = new BoardServiceImpl();
        BoardMain boardMain = new BoardMain(boardService1);
        boardMain.mainMenu();


    }
}
