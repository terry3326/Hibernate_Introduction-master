## 十三、Hibenate 查詢語言範例
### Hibernate語言查詢(Hibernate Query Language,HQL)
#### 它是完全物件導向的查詢語句，查詢功能非常強大；具備多型、關聯等特性，HQL查詢也是Hibernate官方推薦使用的查詢方法。
### 下面我們通過一個案例分析相關查詢方法
#### Classes.java：
	public class Classes {
	/*班級ID*/
	private int id;
	/*班級名稱*/
	private String name;
	/*班級和學生的關係*/
	private Set<Student> students;
	//省略setter和getter方法
	}

#### Student.java：
	public class Student {
	/*學生ID*/
	private int id;
	/*學生姓名*/
	private String name;
	/*學生和班級的關係*/
	private Classes classes;
	//省略setter和getter方法
	}
	
#### Classes.hbm.xml：
	<?xml version="1.0"?> 
	<!DOCTYPE hibernate-mapping PUBLIC  
	"-//Hibernate/Hibernate Mapping DTD 3.0//EN" 
	"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd"> 
	<hibernate-mapping package="com.lixue.bean"> 
	<!-- 設定lazy為false --> 
	<class name="Classes" table="t_classes" lazy="false"> 
	<id name="id"> 
	<generator class="native"/> 
	</id> 
	<property name="name"/> 
	<!-- 一對多對映 ,inverse="true"表示交給對端維護關係--> 
	<set name="students" inverse="true"> 
	<key column="classesid"/> 
	<one-to-many class="Student"/> 
	</set> 
	</class> 
	</hibernate-mapping> 
	
#### Student.hbm.xml：
	<?xml version="1.0"?> 
	<!DOCTYPE hibernate-mapping PUBLIC  
	"-//Hibernate/Hibernate Mapping DTD 3.0//EN" 
	"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd"> 
	<hibernate-mapping package="com.lixue.bean"> 
	<class name="Student" table="t_student"> 
	<id name="id"> 
	<generator class="native"/> 
	</id> 
	<!-- 對映普通屬性 --> 
	<property name="name"/> 
	<!-- 多對一 對映,在多的一端加上外來鍵--> 
	<many-to-one name="classes" column="classesid"/> 
	</class> 
	</hibernate-mapping> 
	
#### 1.查詢單一屬性：
	/*返回結果集屬性列表，元素型別和實體類中的屬性型別一致*/ 
	List<String> students = session.createQuery("select name from Student").list(); 
	/*遍歷*/ 
	for (Iterator<String> iter=students.iterator(); iter.hasNext();) { 
		String name = (String)iter.next(); 
		System.out.println(name); 
	} 
#### 注：查詢單一屬性的時候，返回的是一個集合，集合元素的型別是該屬性的型別。

#### 2.查詢多個屬性，返回物件陣列：
	/*查詢多個屬性，返回的是物件陣列*/ 
	List<Object[]> students = session.createQuery("select id, name from Student").list(); 
	/*遍歷*/ 
	for (Iterator<Object[]> iter=students.iterator(); iter.hasNext();) { 
		Object[] obj = (Object[])iter.next(); 
		System.out.println(obj[0]   ", "   obj[1]); 
	} 
#### 注：查詢多個屬性返回的是一個型別為物件陣列的集合，這個很好理解，當查詢單一屬性是返回的集合元素型別就是屬性的型別，但是多個型別呢？那必須是物件陣列來處理啊即Object[]。

#### 3.查詢多個屬性，返回物件型別的集合：
	/*我們給實體物件設定對應的建構函式，然後通過查詢物件的方式就可以返回一個實體物件型別的集合*/ 
	List students = session.createQuery("select new Student(id, name) from Student").list(); 
	/*遍歷*/ 
	for (Iterator iter=students.iterator(); iter.hasNext();) { 
	Student student = (Student)iter.next(); 
	System.out.println(student.getId()   ", "   student.getName()); 
	} 
#### 注：除了我們第二種方式返回的是一個物件陣列，我們還可以給實體物件設定對應的建構函式，然後通過查詢物件的方式進行查詢，然後返回的就是實體型別的集合。

#### 4. 使用別名進行查詢：
	/*可以使用別名*/ 
	List<Object[]> students = session.createQuery("select s.id, s.name from Student s").list(); 
	/*遍歷*/ 
	for (Iterator<Object[]> iter=students.iterator(); iter.hasNext();) { 
	Object[] obj = (Object[])iter.next(); 
		System.out.println(obj[0]   ", "   obj[1]); 
	} 

#### 5. 查詢實體物件：
	/*返回的是實體物件型別的集合*/ 
	List<Student> students = session.createQuery("from Student").list(); 
	/*遍歷*/ 
	for (Iterator<Student> iter=students.iterator(); iter.hasNext();) { 
		Student student = (Student)iter.next(); 
		System.out.println(student.getName()); 
	}
#### 注：查詢實體可以直接使用from 類名的形式。
	/*使用select就必須使用別名*/ 
	List<Student> students = session.createQuery("select s from Student s").list(); 
	/*遍歷*/ 
	for (Iterator<Student> iter=students.iterator(); iter.hasNext();) { 
	Student student = (Student)iter.next(); 
	System.out.println(student.getName()); 
	}
#### 注：如果要使用select關鍵字，那麼就必須使用別名。另外一點千萬要注意：hql不支select * 的形式。

#### 6. N 1問題：
#### 7. 條件查詢：
#### 8.佔位符的形式查詢：
#### 9.自定義引數的形式：
#### 10.查詢條件為in的形式：
#### 11.使用資料庫個性化函式：
#### 12.使用原生態的SQL語句：
#### 13.分頁查詢
#### 14.導航查詢

[參考網站](https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/298369/#outline__2)
