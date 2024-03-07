package board.domain;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Data
public class Board {
    private static int nextNo = 1;
    private int bno;
    private String btitle;
    private String bcontent;
    private String bwriter;
    private Date bdate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

    public Board(String title, String content, String writer) {
        this.bno = nextNo++;
        this.btitle = title;
        this.bcontent = content;
        this.bwriter = writer;
        this.bdate = new Date();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Board other = (Board) obj;
        return bno == other.bno;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bno);
    }

    public void print() {
        System.out.printf("%-6s%-12s%-16s%-40s \n"
                , bno
                , bwriter
                , sdf.format(bdate)
                , btitle);
    }

    public void printDetail() {
        System.out.println("번호 : " + bno);
        System.out.println("작성자 : " + bwriter);
        System.out.println("작성일자 : " + sdf.format(bdate));
        System.out.println("제목 : " + btitle);
        System.out.println("내용 : " + bcontent);
    }


}
