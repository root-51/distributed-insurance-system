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
        User user;
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

            try (DAO dao = new DAO()){
                user = dao.executeQuery("SELECT * from user where user_id = ? and user_pw = ?",id,pw).toUser();
            }catch (Exception e){
                System.out.println("로그인에 실패했습니다");
                return null;
            }
                System.out.println("로그인 되었습니다.");
                return user;

    }
}
