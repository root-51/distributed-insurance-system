package test;

import main.MenuPrinter;
import main.Employee.Employee.EmployeeType;

public class Main {
	public static void main(String[] args) {
		MenuPrinter menuPrinter = new MenuPrinter();
		
		menuPrinter.printMainMenu(EmployeeType.Sales);
	}
}
