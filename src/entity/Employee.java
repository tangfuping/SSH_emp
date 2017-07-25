package entity;

public class Employee {
	private int t_id;
	private String empname;
	private int empage;
	private double empsalary;
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(int t_id, String empname, int empage, double empsalary) {
		super();
		this.t_id = t_id;
		this.empname = empname;
		this.empage = empage;
		this.empsalary = empsalary;
	}
	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public int getEmpage() {
		return empage;
	}
	public void setEmpage(int empage) {
		this.empage = empage;
	}
	public double getEmpsalary() {
		return empsalary;
	}
	public void setEmpsalary(double empsalary) {
		this.empsalary = empsalary;
	}
	@Override
	public String toString() {
		return "Employee [t_id=" + t_id + ", empname=" + empname + ", empage="
				+ empage + ", empsalary=" + empsalary + "]";
	}

}
