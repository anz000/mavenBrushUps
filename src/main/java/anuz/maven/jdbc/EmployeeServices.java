/**
 * @Author : Anuj Shrestha
 */
package anuz.maven.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EmployeeServices {
	/**
	 * This method will create a employee
	 * @param id
	 * @param name
	 * @param salary
	 * @param gender
	 * @return The employee with the given data
	 * @throws InvalidSalaryException if the salary is less than 5000
	 * @throws SQLException 
	 */
	public Employee createEmployee(int id, String name, double salary, String gender)
			throws InvalidSalaryException, SQLException;
	
	/**
	 * This method will delete the employee with the given id
	 * @param id
	 * @return the updated Employee list after removal
	 * @throws EmployeeNotFoundException if the employee id is not found
	 * @throws SQLException 
	 */
	public boolean deleteEmployee(int id) 
			throws EmployeeNotFoundException, SQLException;
	
	/**
	 * This method will update the Employee with the given ID with the given information
	 * @param id
	 * @param name
	 * @param salary
	 * @param gender
	 * @return The updated Employee List
	 * @throws EmployeeNotFoundException If the employee ID is not found
	 * @throws InvalidSalaryException If the salary is below 5000
	 * @throws SQLException 
	 */
	public boolean updateEmployee(int id, String name, double salary, String gender)
			throws EmployeeNotFoundException, InvalidSalaryException, SQLException;
	
	/**
	 * This method will return the employee with the given ID
	 * @param id
	 * @return The employee with the given ID
	 * @throws EmployeeNotFoundException If the employee ID is not found
	 * @throws SQLException 
	 */
	public Employee readEmployee(int id) throws EmployeeNotFoundException, SQLException;
	
	/**
	 * This method will calculate the gross salary of all the employees
	 * @return The gross salary value
	 * @throws SQLException 
	 */
	public double calculateGrossSalary() throws SQLException;
	
	/**
	 * This method will calculate the HRA
	 * @return The HRA value
	 * @throws SQLException 
	 */
	public double calculateHRA() throws SQLException;
	
	/**
	 * This method will group the employees into gender category
	 * @return List of List containing employees in each gender
	 * @throws SQLException 
	 */
	public Map<String, Integer> groupByGender() throws SQLException;
	
	/**
	 * This method will sort the employees by Age
	 * @return The list of employees sorted by age in ascending order
	 * @throws SQLException 
	 */
	public List<Employee> sortByName() throws SQLException;
	
	/**
	 * This method will sort the employees by Salary
	 * @return The list of employees sorted by salary in ascending order
	 * @throws SQLException 
	 */
	public List<Employee> sortBySalary() throws SQLException;
	
	/**
	 * This method will sort the employees by ID
	 * @return The list of employees sorted by ID in ascending order
	 * @throws SQLException 
	 */
	public List<Employee> sortById() throws SQLException;
	
	/**
	 * This method will group the employees into 2 category : above or below average salary
	 * @return List of List containing employees in each above/below av. salary
	 * @throws SQLException 
	 */
	public Map<String,Integer> groupByAvSalary() throws SQLException;

	/**
	 * This method will get all the employees
	 * @return
	 * @throws SQLException 
	 */
	public List<Employee> getAllEmployees() throws SQLException;
}
