## 十七、Hibenate 批量處理
### 當需要使用Hibernate上傳大量的記錄到數據庫中。以下是實現這一使用之Hibernate代碼片段：
	Session session = SessionFactory.openSession();
	Transaction tx = session.beginTransaction();
	for ( int i=0; i<100000; i++ ) {
		Employee employee = new Employee(.....);
		session.save(employee);
	}
	tx.commit();
	session.close();
#### 預設情況下，Hibernate 在session 級別會緩存所有的持久對象，最終應用程序在50,000條記錄左右會失敗並發生OutOfMemoryException。
#### Hibernate使用的是批量處理解決這個問題。
#### 要使用批量處理功能，首先根據對象的大小設置hibernate.jdbc.batch_size為20或50批量大小，這將在每 X 行插入hibernate的批次容器。
#### 為了實現這個在代碼中，我們需要做一點修改如下：
	Session session = SessionFactory.openSession();
	Transaction tx = session.beginTransaction();
	for ( int i=0; i<100000; i++ ) {
		Employee employee = new Employee(.....);
		session.save(employee);
		if( i % 50 == 0 ) { // Same as the JDBC batch size
			//flush a batch of inserts and release memory:
			session.flush();
			session.clear();
		}
	}
	tx.commit();
	session.close();
#### 上麵的代碼將正常完成INSERT操作，如果要做UPDATE操作，那麼可以使用下面的代碼實現：
	Session session = sessionFactory.openSession();
	Transaction tx = session.beginTransaction();

	ScrollableResults employeeCursor = session.createQuery("FROM EMPLOYEE").scroll();
	int count = 0;

	while ( employeeCursor.next() ) {
	   Employee employee = (Employee) employeeCursor.get(0);
	   employee.updateEmployee();
	   seession.update(employee); 
	   if ( ++count % 50 == 0 ) {
		  session.flush();
		  session.clear();
	   }
	}
	tx.commit();
	session.close();
	
#### 批量處理範例：
#### 讓我們修改配置文件 - hibernate.cfg.xml ，補充hibernate.jdbc.batch_size屬性：
	<?xml version="1.0" encoding="utf-8"?>
	<!DOCTYPE hibernate-configuration SYSTEM 
	"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

	<hibernate-configuration>
	   <session-factory>
	   <property name="hibernate.dialect">
		  org.hibernate.dialect.MySQLDialect
	   </property>
	   <property name="hibernate.connection.driver_class">
		  com.mysql.jdbc.Driver
	   </property>

	   <!-- Assume students is the database name -->
	   <property name="hibernate.connection.url">
		  jdbc:mysql://localhost/test
	   </property>
	   <property name="hibernate.connection.username">
		  root
	   </property>
	   <property name="hibernate.connection.password">
		  1234
	   </property>
	   <property name="hibernate.jdbc.batch_size">
		  50
	   </property>

	   <!-- List of XML mapping files -->
	   <mapping resource="Employee.hbm.xml"/>

	</session-factory>
	</hibernate-configuration>
#### POJO Employee類：
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
####　EMPLOYEE Table Schema
	create table EMPLOYEE (
	   id INT NOT NULL auto_increment,
	   first_name VARCHAR(20) default NULL,
	   last_name  VARCHAR(20) default NULL,
	   salary     INT  default NULL,
	   PRIMARY KEY (id)
	);
#### EMPLOYEE.hbm.xml 映射文件
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
#### 最後，我們將使用flush() 和 clear() 等Session物件方法，使 Hibernate 將這些記錄繼續寫到數據庫中，而不是將它們緩存在應用內存。
	/* Method to create employee records in batches */
	public void addEmployees( ){
	  Session session = factory.openSession();
	  Transaction tx = null;
	  Integer employeeID = null;
	  try{
		 tx = session.beginTransaction();
		 for ( int i=0; i<100000; i++ ) {
			String fname = "First Name " + i;
			String lname = "Last Name " + i;
			Integer salary = i;
			Employee employee = new Employee(fname, lname, salary);
			session.save(employee);
			if( i % 50 == 0 ) {
			   session.flush();
			   session.clear();
			}
		 }
		 tx.commit();
	  }catch (HibernateException e) {
		 if (tx!=null) tx.rollback();
		 e.printStackTrace(); 
	  }finally {
		 session.close(); 
	  }
	  return ;
	}
#### 步驟：
	1. 創建 hibernate.cfg.xml配置文件。

	2. 創建Employee.hbm.xml映射文件。

	3. 創建Employee.java 文件。

	4. 創建 EmployeeDao.java 文件，在EMPLOYEE表中創建100000條記錄程序。
