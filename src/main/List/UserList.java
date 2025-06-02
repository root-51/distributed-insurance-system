package main.List;


import main.Employee.User;

public interface UserList {
		/**
		 *
		 * @param employeeID
		 */
		public boolean delete(String employeeID);

		/**
		 *
		 * @param employee
		 */
		public boolean insert(User employee);

		/**
		 *
		 * @param employeeID
		 */
		public User search(String employeeID);

		/**
		 *
		 * @param employee
		 */
		public boolean update(User employee);

	}