package anuz.maven.jdbc;

@SuppressWarnings("serial")
public class InvalidSalaryException extends Exception{
	public InvalidSalaryException( String s ) {
		super(s);
	}
}
