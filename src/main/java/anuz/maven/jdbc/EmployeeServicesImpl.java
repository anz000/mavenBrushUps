package anuz.maven.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * @author anz
 *
 */
public class EmployeeServicesImpl implements EmployeeServices {

	private EmployeeDAO eDAO = new EmployeeDAO(); // employee Data Access Object
	
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
	@Override
	public Employee createEmployee(int id, String name, double salary, String gender) throws InvalidSalaryException, SQLException {
		// if the salary is less than 5000, then throw invalid salary exception
		if (salary < 5000)
			throw new InvalidSalaryException("Salary has to be greater than 5000.");

		// if no errors then return the new employee
		Employee newEmp = new Employee(id, name, salary, gender);
		
		eDAO.save(newEmp);
		return newEmp;
	}

	/**
	 * This method will delete the employee with the given id
	 * @param id
	 * @return the updated Employee list after removal
	 * @throws EmployeeNotFoundException if the employee id is not found
	 * @throws SQLException 
	 */
	@Override
	public boolean deleteEmployee(int id) throws EmployeeNotFoundException, SQLException {
		boolean dFlag = false;
		if(eDAO.remove(id))
			dFlag = true;

		if (!dFlag) {
			throw new EmployeeNotFoundException("Given id is Invalid. Please provide valid employee id");
		}

		return dFlag;
	}

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
	@Override
	public boolean updateEmployee(int id, String name, double salary, String gender)
			throws EmployeeNotFoundException, InvalidSalaryException, SQLException {

		boolean uFlag = false;
		if(eDAO.update(id,name,salary,gender))
			uFlag = true;

		if (!uFlag)
			throw new EmployeeNotFoundException("Given id is Invalid. Please provide valid employee id");

		return uFlag;
	}

	/**
	 * This method will return the employee with the given ID
	 * @param id
	 * @return The employee with the given ID
	 * @throws EmployeeNotFoundException If the employee ID is not found
	 * @throws SQLException 
	 */
	@Override
	public Employee readEmployee(int id) throws EmployeeNotFoundException, SQLException {
		int rIndexer = -1;
		Employee e = eDAO.read(id);
		if(e != null)
			rIndexer = 1;

		if (rIndexer < 0)
			throw new EmployeeNotFoundException("Given id is Invalid. Please provide valid employee id");

		return e;
	}

	/**
	 * This method will calculate the gross salary of all the employees
	 * @return The gross salary value
	 * @throws SQLException 
	 */
	@Override
	public double calculateGrossSalary() throws SQLException {
		double grossSalary = 0;
		grossSalary = eDAO.calcGrossSalary();
		return grossSalary;
	}

	/**
	 * This method will calculate the HRA
	 * @return The HRA value
	 * @throws SQLException 
	 */
	@Override
	public double calculateHRA() throws SQLException {
		double grossHRA = 0;
		List<Double> salaries = eDAO.getSalary();
		for (Double sal : salaries)
			grossHRA += sal * 0.01 * Employee.reimburseHRA * 12;

		return grossHRA;
	}

	/**
	 * This method will group the employees into gender category
	 * @return List of List containing employees in each gender
	 * @throws SQLException 
	 */
	@Override
	public Map<String, Integer> groupByGender() throws SQLException {
		return eDAO.groupByGender();
	}

	/**
	 * This method will sort the employees by Age
	 * @return The list of employees sorted by age in ascending order
	 * @throws SQLException 
	 */
	@Override
	public List<Employee> sortByName() throws SQLException {
		return eDAO.sortByName();
	}

	/**
	 * This method will sort the employees by Salary
	 * @return The list of employees sorted by salary in ascending order
	 * @throws SQLException 
	 */
	@Override
	public List<Employee> sortBySalary() throws SQLException {
		return eDAO.sortBySalary();
	}

	/**
	 * This method will sort the employees by ID
	 * @return The list of employees sorted by ID in ascending order
	 * @throws SQLException 
	 */
	@Override
	public List<Employee> sortById() throws SQLException {
		return eDAO.sortById();
	}

	/**
	 * This method will group the employees into 2 category : above or below average salary
	 * @return List of List containing employees in each above/below av. salary
	 * @throws SQLException 
	 */
	@Override
	public Map<String, Integer> groupByAvSalary() throws SQLException {
		return eDAO.groupByAvSalary();
	}

	/**
	 * This method will get all the employees
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public List<Employee> getAllEmployees() throws SQLException {
		return eDAO.getAllEmployees();
	}


}
