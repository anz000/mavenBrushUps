package anuz.maven.jdbc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@ToString(callSuper=false, includeFieldNames=true)
public class Employee {
	private int id;
	private String name;
	private double salary;
	private String gender;
	
	public final static float reimburseHRA = 5;
	
	public Employee() {	
	}

	public Employee(int id, String name, double salary, String gender) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.gender = gender;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(salary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(salary) != Double.doubleToLongBits(other.salary))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", gender=" + gender + "] \n";
	}
}
