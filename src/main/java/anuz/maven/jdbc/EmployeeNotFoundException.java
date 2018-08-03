package anuz.maven.jdbc;

@SuppressWarnings("serial")
public class EmployeeNotFoundException extends Exception{
	public EmployeeNotFoundException( String s ) {
		super(s);
	}
}
