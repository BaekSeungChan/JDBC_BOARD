package simpleboard.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
public class User {


    private String userId;
    private String userName;
    private String userPassword;
    private int userAge;
    private String userEmail;

    public User(String userName, String userPassword){
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User(String useid, String usename, String usepassword, int userage, String userEmail) {
        this.userId = useid;
        this.userName = usename;
        this.userPassword = usepassword;
        this.userAge = userage;
        this.userEmail = userEmail;
    }

    public User(String usename, String userpassword, int userage, String usermail) {
        this.userName = usename;
        this.userPassword = userpassword;
        this.userAge = userage;
        this.userEmail = usermail;
    }

    public void print() {
        System.out.printf("%-6s%-12s%-16s%-6d%-40s \n"
                , userId
                , userName
                , userPassword
                , userAge
                , userEmail
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
