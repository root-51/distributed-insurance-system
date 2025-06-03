package main;


import main.User.User;

import java.sql.SQLException;

public class Main {

	private static User loginedUser;
	private static SystemManager menu;

	public static void main(String[] args) throws SQLException {
		LoginMenu loginMenu = new LoginMenu();
		loginedUser = loginMenu.login();

		if(loginedUser==null){
			System.out.println("일치하는 사용자가 없습니다.");
			System.exit(0);
		}

		menu = new SystemManager(loginedUser);
		while (true) {
			menu.printMainMenu();
			int selectedMenu = menu.getUserSelectInt();
			menu.excuteSelectedMenu(selectedMenu);
		}

	}
}
