## 十、Hibenate 實例
#### 我們嘗試使用Hibernate提供的一個獨立應用程序Java持久化例子。通過使用Hibernate技術創建Java應用程序，步驟如下：
### 創建POJO 類:
#### 在創建應用程序的第一步是建立在Java POJO類或類，具體取決於將被持久化到數據庫的應用程序。
	public class Employee {
	   private int id;
	   private String firstName; 
	   private String lastName;   
	   private int salary;  

	   public Employee() {}
	   public Employee(String fname, String lname, int salary) {
		  this.firstName = fname;
		  this.lastName = lname;
		  this.salary = salary;
	   }
	   public int getId() {
		  return id;
	   }
	   public void setId( int id ) {
		  this.id = id;
	   }
	   public String getFirstName() {
		  return firstName;
	   }
	   public void setFirstName( String first_name ) {
		  this.firstName = first_name;
	   }
	   public String getLastName() {
		  return lastName;
	   }
	   public void setLastName( String last_name ) {
		  this.lastName = last_name;
	   }
	   public int getSalary() {
		  return salary;
	   }
	   public void setSalary( int salary ) {
		  this.salary = salary;
	   }
	}
### 創建數據庫表:
#### 第二步是在數據庫中創建表。會有一張表對應於每一個對象提供持久性。考慮上述目的需要存儲和檢索到下麵的RDBMS表：
	create table EMPLOYEE (
	   id INT NOT NULL auto_increment,
	   first_name VARCHAR(20) default NULL,
	   last_name  VARCHAR(20) default NULL,
	   salary     INT  default NULL,
	   PRIMARY KEY (id)
	);
### 創建映射配置文件：
#### 這一步是創建一個指示Hibernate如何定義的一個或多個類映射到數據庫表的映射文件。
	<?xml version="1.0" encoding="utf-8"?>
	<!DOCTYPE hibernate-mapping PUBLIC 
	 "-//Hibernate/Hibernate Mapping DTD//EN"
	 "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd"> 

	<hibernate-mapping>
	   <class name="Employee" table="EMPLOYEE">
		  <meta attribute="class-description">
			 This class contains the employee detail. 
		  </meta>
		  <id name="id" type="int" column="id">
			 <generator class="native"/>
		  </id>
		  <property name="firstName" column="first_name" type="string"/>
		  <property name="lastName" column="last_name" type="string"/>
		  <property name="salary" column="salary" type="int"/>
	   </class>
	</hibernate-mapping>

#### 應該保存的映射文件中的格式<classname>.hbm.xml。我們保存我們的映射文件中的文件Employee.hbm.xml。讓我們來看看有關映射文件的小細節：
	1. 映射文檔是具有<hibernate-mapping>為包含所有的<class>元素的根元素的XML文檔。

	2. <class>元素被用於定義數據庫表從一個Java類特定的映射。 Java類名指定使用class元素的name屬性和使用表屬性數據庫表名指定。

	3. <meta>元素是可選元素，可以用來創建類的描述。

	4. <id>元素映射在類中的唯一ID屬性到數據庫表的主鍵。 id 元素的 name 屬性是指屬性的類和 column 屬性是指在數據庫表中的列。 type屬性保存了Hibernate映射類型，這種類型的映射將會從Java轉換為SQL數據類型。

	5. id 元素內的 <generator> 元素被用來自動生成的主鍵值。將生成元素的class屬性設置為原產於讓Hibernate無論是identity，sequence或者hilo中的算法來創建主鍵根據底層數據庫的支持能力。

	6. <property> 元素用於一個Java類的屬性映射到數據庫表中的列。元素的name屬性是指屬性的類和column屬性是指在數據庫表中的列。 type屬性保存了Hibernate映射類型，這種類型的映射將會從Java轉換為SQL數據類型。

### 創建應用程序類：
#### 最後，我們將創建應用程序類 main() 方法來運行應用程序。我們將使用這個應用程序來保存一些雇員的記錄，然後我們將申請CRUD操作上的記錄。
	import java.util.List; 
	import java.util.Date;
	import java.util.Iterator; 
	 
	import org.hibernate.HibernateException; 
	import org.hibernate.Session; 
	import org.hibernate.Transaction;
	import org.hibernate.SessionFactory;
	import org.hibernate.cfg.Configuration;

	public class ManageEmployee {
	   private static SessionFactory factory; 
	   public static void main(String[] args) {
		  try{
			 factory = new Configuration().configure().buildSessionFactory();
		  }catch (Throwable ex) { 
			 System.err.println("Failed to create sessionFactory object." + ex);
			 throw new ExceptionInInitializerError(ex); 
		  }
		  ManageEmployee ME = new ManageEmployee();

		  /* Add few employee records in database */
		  Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
		  Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
		  Integer empID3 = ME.addEmployee("John", "Paul", 10000);

		  /* List down all the employees */
		  ME.listEmployees();

		  /* Update employee's records */
		  ME.updateEmployee(empID1, 5000);

		  /* Delete an employee from the database */
		  ME.deleteEmployee(empID2);

		  /* List down new list of the employees */
		  ME.listEmployees();
	   }
	   /* Method to CREATE an employee in the database */
	   public Integer addEmployee(String fname, String lname, int salary){
		  Session session = factory.openSession();
		  Transaction tx = null;
		  Integer employeeID = null;
		  try{
			 tx = session.beginTransaction();
			 Employee employee = new Employee(fname, lname, salary);
			 employeeID = (Integer) session.save(employee); 
			 tx.commit();
		  }catch (HibernateException e) {
			 if (tx!=null) tx.rollback();
			 e.printStackTrace(); 
		  }finally {
			 session.close(); 
		  }
		  return employeeID;
	   }
	   /* Method to  READ all the employees */
	   public void listEmployees( ){
		  Session session = factory.openSession();
		  Transaction tx = null;
		  try{
			 tx = session.beginTransaction();
			 List employees = session.createQuery("FROM Employee").list(); 
			 for (Iterator iterator = 
							   employees.iterator(); iterator.hasNext();){
				Employee employee = (Employee) iterator.next(); 
				System.out.print("First Name: " + employee.getFirstName()); 
				System.out.print("  Last Name: " + employee.getLastName()); 
				System.out.println("  Salary: " + employee.getSalary()); 
			 }
			 tx.commit();
		  }catch (HibernateException e) {
			 if (tx!=null) tx.rollback();
			 e.printStackTrace(); 
		  }finally {
			 session.close(); 
		  }
	   }
	   /* Method to UPDATE salary for an employee */
	   public void updateEmployee(Integer EmployeeID, int salary ){
		  Session session = factory.openSession();
		  Transaction tx = null;
		  try{
			 tx = session.beginTransaction();
			 Employee employee = 
						(Employee)session.get(Employee.class, EmployeeID); 
			 employee.setSalary( salary );
			 session.update(employee); 
			 tx.commit();
		  }catch (HibernateException e) {
			 if (tx!=null) tx.rollback();
			 e.printStackTrace(); 
		  }finally {
			 session.close(); 
		  }
	   }
	   /* Method to DELETE an employee from the records */
	   public void deleteEmployee(Integer EmployeeID){
		  Session session = factory.openSession();
		  Transaction tx = null;
		  try{
			 tx = session.beginTransaction();
			 Employee employee = 
					   (Employee)session.get(Employee.class, EmployeeID); 
			 session.delete(employee); 
			 tx.commit();
		  }catch (HibernateException e) {
			 if (tx!=null) tx.rollback();
			 e.printStackTrace(); 
		  }finally {
			 session.close(); 
		  }
	   }
	}

### 編譯和執行：
#### 下麵是編譯並運行上述應用程序的步驟。請確保已在進行編譯和執行之前，適當地設置PATH和CLASSPATH。

	1. 創建hibernate.cfg.xml配置文件。

	2. 創建Employee.hbm.xml映射文件。

	3. 創建Employee.java源文件。

	4. 創建ManageEmployee.java源文件。

	5. 執行ManageEmployee二進制文件來運行程序。