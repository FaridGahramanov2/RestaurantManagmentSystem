package restaurantManagementSystem;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


	public class Main {


		private static final String DB_USER = "root";
		private static final String DB_PASS = "123Zaraaa_";
		private static final String URL = "jdbc:mysql://localhost:3306/sor";
		private static Connection connection = null;

		public static void establishConnection() {
			try {
				connection = DriverManager.getConnection(URL, DB_USER, DB_PASS);
			} catch (SQLException e) {
				throw new RuntimeException("unhandled", e);
			}
		}
		static Scanner scanner = new Scanner(System.in);
		static Restaurant restaurant = new Restaurant();

		public static void main(String[] args) throws IOException, SQLException {

			System.out.println("Welcome to OZU Restaurant!\n");
			while(true){
				System.out.println("-------------------------Main Menu-------------------------");
				System.out.println("1: Create Order");
				System.out.println("2: Manage Restaurant");
				System.out.println("3: Exit Program");
				System.out.println("-----------------------------------------------------------");
				System.out.print(">> ");
				String option = scanner.nextLine();
				if(option.equals("1")){
				    createOrder();
				}
				else if(option.equals("2")){
					manageRestaurant();
				}
				else if(option.equals("3")){
				    System.out.println("Bye");
				    break;
				}
				else{
				    System.out.println("Try Again...");
				}
			}
		}
		
		private static void createOrder() throws SQLException {
			establishConnection();
			Waiter w = restaurant.assignWaiter();
			Statement stmt = connection.createStatement();
			String sql = "SELECT name FROM Waiter";
			ResultSet rs2 = stmt.executeQuery(sql);
			
			System.out.println("Hi, I am " + rs2.getString("name") + ".");
			System.out.println("I will be our waiter today.");
			System.out.println("What would you like to get today?");
			
			Order order = new Order();
			while(true){
				System.out.println("-----------------------Create Order-----------------------");
				System.out.println("1: List Order");
				System.out.println("2: Add Product");
				System.out.println("3: Complete Order");
				System.out.println("4: Give the Bill");
				System.out.println("5: Go back to Main Menu");

				System.out.println("-----------------------------------------------------------");
				System.out.print(">> ");
				String option = scanner.nextLine();
				if(option.equals("1")){
				    order.listOrder();
				}
				else if(option.equals("2")){
					ArrayList<Product> products = restaurant.getProducts();//product getter function need to be implemented
					while(true){
						System.out.println("-----------------------Add Product-----------------------");
						for (int i = 0; i < products.size(); i++) {
							System.out.println((i + 1) + ": " + products.get(i).getName());
						}
						System.out.println("-----------------------------------------------------------");
						System.out.print(">> ");
						option = scanner.nextLine();
						int productNumber = Integer.parseInt(option);
						if (productNumber <= products.size() && productNumber > 0) {
						    order.addProduct(products.get(productNumber - 1));//addProduct
						    order.listOrder();
						    break;
						}
						else  {
							System.out.println("Try Again...");
						}
					}
				}
				else if(option.equals("3")){
					w.createOrder(order);
					System.out.println("Your order is complete!");
					System.out.println("Returning to Main Menu");
				    break;
				}
				else if(option.equals("4")){
					w.createOrder(order);
					order.giveBill();
				    break;
				}
				else if(option.equals("5")){
				    System.out.println("Returning to Main Menu");
				}
				else{
					System.out.println("Try Again");
				}
			}
		}
		
		private static void manageRestaurant() throws IOException, SQLException {
			
			while(true){
				System.out.println("---------------------Manage Restaurant---------------------");
				System.out.println("1: List Employees");
				System.out.println("2: Add Employee");
				System.out.println("3: Calculate Expenses");
				System.out.println("4: Calculate Revenue");
				System.out.println("5: Go back to Main Menu");
				System.out.println("-----------------------------------------------------------");
				System.out.print(">> ");
				String option = scanner.nextLine();
				if(option.equals("1")){
				    restaurant.listEmployees();
				}
				else if(option.equals("2")){
					addEmployee();
				}
				else if(option.equals("3")){
					System.out.println("Employee Expenses: " + restaurant.calculateEmployeeExpenses());
					System.out.println("Order Expenses: " + restaurant.calculateOrderExpenses());
					System.out.println("Total Expense: " + restaurant.calculateExpenses());
				}
				else if(option.equals("4")){
					System.out.println("Total Revenue: " + restaurant.calculateRevenue());
				}
				else if(option.equals("5")){
					System.out.println("Returning to Main Menu");
				    break;
				}
				else{
				    System.out.println("Try Again...");
				}
			}
		}

		private static void addEmployee() throws IOException, SQLException {
			
			while(true){
				System.out.println("---------------------Add Employee---------------------");
				System.out.println("1: Add Cook");
				System.out.println("2: Add Waiter");
				System.out.println("3: Go back to Manage Restaurant Menu");
				System.out.println("-----------------------------------------------------------");
				System.out.print(">> ");
				String option = scanner.nextLine();
				if(option.equals("1")){
					System.out.println("Name of the Cook:");
					System.out.print(">> ");
					String name = scanner.nextLine();
					System.out.println("Salary of the Cook:");
					System.out.print(">> ");
					String salary = scanner.nextLine();
					restaurant.addCook(name, Double.parseDouble(salary));
				}
				else if(option.equals("2")){
					System.out.println("Name of the Waiter:");
					System.out.print(">> ");
					String name = scanner.nextLine();
					System.out.println("Salary of the Cook:");
					System.out.print(">> ");
					int salary = scanner.nextInt();
					restaurant.addWaiter(name, salary);
					System.out.println("Returning to Main Menu");
				}
				else if(option.equals("3")){
					System.out.println("Returning to Manage Restaurant Menu");
					break;
				}
				else{
				    System.out.println("Try Again...");
				}
			}
		}
  }


