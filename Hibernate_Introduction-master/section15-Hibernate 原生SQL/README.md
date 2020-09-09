## 十五、Hibernate原生SQL
#### Hibernate可以使用原生SQL來表達數據庫查詢，如果想利用數據庫特有的功能，如查詢提示或者Oracle中的CONNECT關鍵字。 
#### Hibernate允許使用手寫SQL語句，包括存儲過程，所有的創建，更新，刪除和load操作。
#### Hibernate從session 創建一個原生SQL查詢 createSQLQuery()方法：
	public SQLQuery createSQLQuery(String sqlString) throws HibernateException
#### 當傳遞一個包含SQL查詢到createSQLQuery()方法，可以將SQL結果與任何現有的Hibernate實體聯接，
#### 或者一個標量結果使用addEntity()方法，addJoin()，和addScalar()方法關聯的字符串。
### 標量查詢：
#### 最基本的SQL查詢是從一個或多個表中得到標量（數值）的列表。以下是語法使用原生SQL標量的值：

	String sql = "SELECT first_name, salary FROM EMPLOYEE";
	SQLQuery query = session.createSQLQuery(sql);
	query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	List results = query.list();

### 實體的查詢：
#### 上麵的查詢都是返回標量值，也就是從resultset中返回的“裸”數據。
#### 以下是語法通過addEntity（）方法來從原生SQL查詢獲得實體對象作為一個整體。

	String sql = "SELECT * FROM EMPLOYEE";
	SQLQuery query = session.createSQLQuery(sql);
	query.addEntity(Employee.class);
	List results = query.list();

### 命名SQL查詢：
#### 以下是語法通過addEntity（）方法來從原生SQL查詢獲得實體對象和使用命名SQL查詢。

	String sql = "SELECT * FROM EMPLOYEE WHERE id = :employee_id";
	SQLQuery query = session.createSQLQuery(sql);
	query.addEntity(Employee.class);
	query.setParameter("employee_id", 10);
	List results = query.list();
	
### 範例練習:
#### POJO:
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
#### table schems
	create table EMPLOYEE (
	   id INT NOT NULL auto_increment,
	   first_name VARCHAR(20) default NULL,
	   last_name  VARCHAR(20) default NULL,
	   salary     INT  default NULL,
	   PRIMARY KEY (id)
	);
#### Employee.hbm.xml
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
#### 查詢方法
	/* Method to  READ all the employees using Scalar Query */
	public void listEmployeesScalar( ){
	  Session session = factory.openSession();
	  Transaction tx = null;
	  try{
		 tx = session.beginTransaction();
		 String sql = "SELECT first_name, salary FROM EMPLOYEE";
		 SQLQuery query = session.createSQLQuery(sql);
		 query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		 List data = query.list();

		 for(Object object : data)
		 {
			Map row = (Map)object;
			System.out.print("First Name: " + row.get("first_name")); 
			System.out.println(", Salary: " + row.get("salary")); 
		 }
		 tx.commit();
	  }catch (HibernateException e) {
		 if (tx!=null) tx.rollback();
		 e.printStackTrace(); 
	  }finally {
		 session.close(); 
	  }
	}

	/* Method to  READ all the employees using Entity Query */
	public void listEmployeesEntity( ){
	  Session session = factory.openSession();
	  Transaction tx = null;
	  try{
		 tx = session.beginTransaction();
		 String sql = "SELECT * FROM EMPLOYEE";
		 SQLQuery query = session.createSQLQuery(sql);
		 query.addEntity(Employee.class);
		 List employees = query.list();

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