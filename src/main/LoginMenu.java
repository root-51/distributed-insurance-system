package main;

import main.DAO.DAO;
import main.DAO.ResultSetWrapper;
import main.User.User;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginMenu {
    Scanner scanner;
    DAO dao;

    public User login() throws SQLException {
        dao = new DAO();
        System.out.print("==로그인==\n ID : ");
        scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        while(id.equals("")){
            System.out.println("id를 입력해주세요.");
            id = scanner.nextLine();
        }
        System.out.print("PW : ");
        String pw = scanner.nextLine();
        while(pw.equals("")){
            System.out.println("id를 입력해주세요.");
            pw = scanner.nextLine();
        }


            ResultSetWrapper wrapper = dao.executeQuery("SELECT * from user where user_id = ? and user_pw = ?",id,pw);

            User user = wrapper.toUser();
            if(user==null){
                return null;
            }else{
                System.out.println("로그인 되었습니다.");
                return user;
            }
    }
}
