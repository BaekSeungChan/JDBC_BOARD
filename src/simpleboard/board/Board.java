package simpleboard.board;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Board {
    private int bno;
    private String btitle;
    private String bcontent;
    private String bwriter;
    private String bdate;

    public Board(String title, String content, String writer) {
        this.btitle = title;
        this.bcontent = content;
        this.bwriter = writer;
    }

    public void print() {
        System.out.printf("%-6s%-12s%-16s%-40s \n"
                , bno
                , bwriter
                , bdate
                , btitle);
    }

    public void printDetail() {
        System.out.println("번호 : " + bno);
        System.out.println("작성자 : " + bwriter);
        System.out.println("작성일자 : " + bdate);
        System.out.println("제목 : " + btitle);
        System.out.println("내용 : " + bcontent);
    }





}