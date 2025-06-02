package main;

import main.DAO.DAO;
import main.DAO.ResultSetWrapper;
import main.Employee.User;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginMenu {
    Scanner scanner;
    DAO dao;

    public User login() throws SQLException {
        dao = new DAO();
        System.out.println("==로그인==\n ID : ");
        scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        System.out.println("PW : ");
        String pw = scanner.nextLine();


            ResultSetWrapper wrapper = dao.executeQuery("SELECT * from user where user_id = ? and user_pw = ?",id,pw);
            User employee = wrapper.toUser();
            if(employee==null){
                System.out.println("일치하는 유저가 없습니다.");
                return null;
            }else{
                System.out.println("로그인 되었습니다.");
                return employee;
            }

    }
}
