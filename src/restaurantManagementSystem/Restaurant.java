package restaurantManagementSystem;

import java.io.IOException;
import java.sql.*;
import java.util.*;


public class Restaurant
{

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
	private ArrayList<Employee> employees = new ArrayList<>();
	private ArrayList<Product> products = new ArrayList<>();
	
	public Restaurant() 
	{
		establishConnection();
	}
	
		public void listEmployees() throws SQLException {
		Statement stmt = connection.createStatement();
		String sql2 = "SELECT name FROM Cook";
		ResultSet rs = stmt.executeQuery(sql2);
		while (rs.next()) {
			System.out.println(rs.getString("name"));
		}
		String sql = "SELECT name FROM Waiter";
		ResultSet rs2 = stmt.executeQuery(sql);
		while (rs2.next()) {
			System.out.println(rs2.getString("name"));
		}
	}


	public void addCook(String name, double salary) throws IOException, SQLException {

		Statement stmt = connection.createStatement();
		String sql =  "INSERT INTO Cook (name, salary) " +
				"VALUES ('" + name + "', " + salary + ")";
		stmt.executeUpdate(sql);

	}
	public void addWaiter(String name, int salary) throws IOException, SQLException {

		Statement stmt = connection.createStatement();
		String sql =  "INSERT INTO Waiter (name, salary) " +
				"VALUES ('" + name + "', " + salary + ")";
		stmt.executeUpdate(sql);
	}
	public Waiter assignWaiter()
	{
		ArrayList<Waiter> waiters = new ArrayList<>();
		for(Employee employee : employees)
		{
			if(employee instanceof Waiter)
			{
				waiters.add((Waiter)employee);
			}
		}
		Random rand = new Random();
		int randomIndex = rand.nextInt(waiters.size());
		return waiters.get(randomIndex);
	}
	public double calculateEmployeeExpenses()
	{
		double employeesExpense = 0.0;
		for(Employee employee : employees) 
		{
			employeesExpense += employee.calculateExpense();
		}
		return employeesExpense;
	}
	public double calculateOrderExpenses()
	{
		ArrayList<Order> orderReceivedList = new ArrayList<>();
		double ordersExpense = 0.0;
		for(Employee employee : employees)
		{
			if(employee instanceof Waiter)
	    	{
	    		orderReceivedList = ((Waiter) employee).getOrdersReceived();
	    		for(Order order : orderReceivedList) 
	    		{
	    			ordersExpense += order.calculateOrderExpenses();
	    		}
	    	}
		}
		return ordersExpense;
	}
	public double calculateExpenses()
	{
		return this.calculateEmployeeExpenses() + this.calculateOrderExpenses();
	}
	public double calculateRevenue()
	{
		ArrayList<Order> orderReceivedList = new ArrayList<>();
		double totalRevenue = 0.0;
	    for(Employee employee : employees)
	    {
	    	if(employee instanceof Waiter)
	    	{
	    		orderReceivedList = ((Waiter) employee).getOrdersReceived();
	    		for(Order order : orderReceivedList) 
	    		{
	    			totalRevenue += order.calculateTotalPrice();
	    		}
	    	}
	    }
		return totalRevenue;
	}
	public ArrayList<Product> getProducts()
	{
		return products;
	}
	
}



// Implement the rest of the class




