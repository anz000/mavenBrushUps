package anuz.maven.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDAO {

	Connection con; // this holds the connection to the MariaDB databASe
	
	// Default Constructor
	public EmployeeDAO() {
		// get the DB Connection
		this.con = DBConnection.getConnection();
	}

	/**
	 * This method adds a given employee
	 * @param newEmp
	 * @throws SQLException
	 */
	public void save(Employee newEmp) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("INSERT into employees VALUES(?,?,?,?)");
		stmt.setInt(1, newEmp.getId());
		stmt.setString(2, newEmp.getName());
		stmt.setDouble(3, newEmp.getSalary());
		stmt.setString(4, newEmp.getGender());

		int i = stmt.executeUpdate();
		System.out.println(i + " records inserted");
	}

	/**
	 * This method removes the employee with the given id
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean remove(int id) throws SQLException {
		boolean dFlag = false;
		PreparedStatement stmt = con.prepareStatement("DELETE FROM employees WHERE id = ?");
		stmt.setInt(1, id);

		int rs = stmt.executeUpdate();
		if (rs > 0) {
			dFlag = true;
			con.commit(); // Added
			con.setAutoCommit(true); // Added	
		}
		return dFlag;
	}

	/**
	 * This method updates the employee info with the given id
	 * @param id
	 * @param name
	 * @param salary
	 * @param gender
	 * @return
	 * @throws SQLException
	 */
	public boolean update(int id, String name, double salary, String gender) throws SQLException {
		boolean uFlag = false;

		PreparedStatement stmt = con
				.prepareStatement("UPDATE employees SET name=?, gender=?, salary=? where id = ?");
		stmt.setString(1, name);
		stmt.setString(2, gender);
		stmt.setDouble(3, salary);
		stmt.setInt(4, id);

		int rs = stmt.executeUpdate();
		if (rs > 0) {
			uFlag = true;
			con.commit(); // Added
			con.setAutoCommit(true); // Added
		}

		return uFlag;
	}

	/**
	 * This emthod returns the employee with the given id
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Employee read(int id) throws SQLException {
		Employee e = null;
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM employees WHERE id = ?");
		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			e = new Employee(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
		}
		return e;
	}

	/**
	 * This method retuns the gross Salary 
	 * @return
	 * @throws SQLException
	 */
	public double calcGrossSalary() throws SQLException {
		double gSalary = 0.0;
		PreparedStatement stmt = con.prepareStatement("SELECT SUM(salary) FROM employees");
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			gSalary = rs.getDouble(1);
		}
		return gSalary;
	}

	/**
	 * This method gets the Salary of all employees
	 * @return
	 * @throws SQLException
	 */
	public List<Double> getSalary() throws SQLException {
		List<Double> salaries = new ArrayList<>();
		PreparedStatement stmt = con.prepareStatement("SELECT salary FROM employees");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			salaries.add(rs.getDouble(1));
		}
		return salaries;
	}

	/**
	 * This method sorts the employees by name
	 * @return
	 * @throws SQLException
	 */
	public List<Employee> sortByName() throws SQLException {
		List<Employee> eList = new ArrayList<>();

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM employees ORDER BY name");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Employee temp = new Employee(rs.getInt("id"), rs.getString("name"), rs.getDouble("salary"),
					rs.getString("gender"));

			eList.add(temp);
		}
		return eList;
	}

	/**
	 * This method sorts the employees by Salary
	 * @return
	 * @throws SQLException
	 */
	public List<Employee> sortBySalary() throws SQLException {
		List<Employee> eList = new ArrayList<>();

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM employees ORDER BY salary");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Employee temp = new Employee(rs.getInt("id"), rs.getString("name"), rs.getDouble("salary"),
					rs.getString("gender"));

			eList.add(temp);
		}
		return eList;
	}

	/**
	 * This method sorts the employees by ID
	 * @return
	 * @throws SQLException
	 */
	public List<Employee> sortById() throws SQLException {
		List<Employee> eList = new ArrayList<>();

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM employees ORDER BY id");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Employee temp = new Employee(rs.getInt("id"), rs.getString("name"), rs.getDouble("salary"),
					rs.getString("gender"));

			eList.add(temp);
		}
		return eList;
	}

	/**
	 * This method queries and finds the gender and counts the number of employees ASsociated with that gender
	 * @return Map of gender -> # of employees
	 * @throws SQLException
	 */
	public Map<String, Integer> groupByGender() throws SQLException {
		Map<String, Integer> genderMap= new HashMap<>();
		// you could use nested loop to complete this.
		PreparedStatement stmt = con.prepareStatement("SELECT gender,COUNT(*) AS count FROM employees GROUP BY gender");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			genderMap.put(rs.getString("gender"),rs.getInt("count"));
		}
		return genderMap;
	}

	public Map<String, Integer> groupByAvSalary() throws SQLException {
		Map<String, Integer> salaryMap= new HashMap<>();
		double avgSalary = 0.0; 
		
		/**
		 * Calculating Average Salary
		 */
		PreparedStatement stmt = con.prepareStatement("SELECT AVG(salary) AS avgSalary FROM employees");
		ResultSet avgRs = stmt.executeQuery();
		while (avgRs.next()) {
			avgSalary = avgRs.getDouble("avgSalary");
		}
		stmt.close();
		avgRs.close();
		//System.out.println(avgSalary);
		
		/**
		 * Calculating below average salary count
		 */
		PreparedStatement stmt2 = con.prepareStatement("SELECT COUNT(*) AS low FROM employees WHERE salary < ?");
		stmt2.setDouble(1, avgSalary);
		ResultSet resultSet2 = stmt2.executeQuery();
		while (resultSet2.next()) 
			salaryMap.put("low", (int)resultSet2.getInt("low"));
		
		stmt2.close();
		resultSet2.close();
		//printResultSet(resultSet);
		
		/**
		 * Calculating above average salary count
		 */
		PreparedStatement stmt3 = con.prepareStatement("SELECT COUNT(*) FROM employees WHERE salary > ?");
		stmt3.setDouble(1, avgSalary);
		ResultSet resultSet3 = stmt3.executeQuery();
		while (resultSet3.next()) 
			salaryMap.put("high", resultSet3.getInt(1));
		
		stmt3.close();
		resultSet3.close();
		//printResultSet(resultSet2);
		return salaryMap;
	}
	
	/**
	 * This method queries the data bASe and fetches all info FROM the Employee table
	 * @return List of employees
	 * @throws SQLException
	 */
	public List<Employee> getAllEmployees() throws SQLException {
		List<Employee> eList = new ArrayList<>();

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM employees");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Employee temp = new Employee(rs.getInt("id"), rs.getString("name"), rs.getDouble("salary"),
					rs.getString("gender"));

			eList.add(temp);
		}
		return eList;
	}
	
	/**
	 * This is for debugging
	 * @param rs
	 * @throws SQLException
	 */
	final public static void printResultSet(ResultSet rs) throws SQLException
	{
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columnsNumber = rsmd.getColumnCount();
	    while (rs.next()) {
	        for (int i = 1; i <= columnsNumber; i++) {
	            if (i > 1) System.out.print(" | ");
	            System.out.print(rs.getString(i));
	        }
	        System.out.println("");
	    }
	}	
}
