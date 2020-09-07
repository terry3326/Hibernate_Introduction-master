## 七、Hibenate 持久化類
### 什麼是持久化類呢？在Hibernate中持久化類的英文名稱是Persistent Object(簡稱PO)。
### 持久化：將內存中的一個類持久化到數據庫中的過程。
### 持久化類：一個Java類與數據庫中的表建立了關系映射，那麽這個類在hibernate中就可以稱之為持久化類。
### 對於Hibernate中的PO，有如下編寫規則：
	1. 必須提供一個無引數的public構造方法。
	2. 所有屬性要用private修飾，對外提供public的get/set方法。
	3. 在PO類必須提供一個標識屬性，讓它與資料庫中的主鍵對應，我們管這個屬性叫OID。
	4. PO類中的屬性儘量使用基本資料型別的包裝類。
	5. PO類不能使用final修飾符。
### 對於第1、2點，勿須多言，下面著重解釋一下後面3點。
### 為何PO類必須提供一個標識屬性OID，讓它與資料庫中的主鍵對應呢？
	OID指的是與資料庫中表的主鍵對應的屬性。
	Hibernate框架是通過OID來區分不同的PO物件，如果在記憶體中有兩個相同的OID物件，那麼Hibernate認為它們是同一個物件。
	大家理解起來不是很好理解，它涉及到關於Hibernate快取的概念，因為Hibernate是對資料庫直接操作，那麼我們為了優化它呢，肯定提供一些快取的策略。
	那麼在快取裡面我們怎麼知道這個物件重不重複呢？我們是通過OID來區分的。 
### 為何PO類中的屬性應儘量使用基本資料型別的包裝類？
	使用基本資料型別是沒有辦法去描述不存在的概念的，如果使用包裝型別，它就是一個物件，對於物件它的預設值是null，
	我們知道如果它為null，就代表不存在，那麼它就可以幫助我們去描述不存在的概念。
### 為何PO類不能使用final修飾符？
#### 要回答這個問題，必須要知道Hibernate中的get/load方法的區別。這也是Hibernate中常考的面試題。我先給出答案：
	雖然get/load方法它們都是根據id去查詢物件，但他倆的區別還是蠻大的：
	1. get方法直接得到一個持久化型別物件，它就是立即查詢操作，也即我要什麼就查到什麼。
	load方法它得到的是持久化類的代理型別物件（子類物件）。
	它採用了一種延遲策略來查詢資料。這時如果PO類使用final修飾符，就會報錯，因為final修飾的類不可以被繼承。
	2. get方法在查詢時，如果不存在返回null；load方法在查詢時，如果不存在，會產生異常——org.hibernate.ObjectNotFoundException。
### 一個簡單的POJO例子：
#### 基於上麵提到的一些規則，我們可以如下定義一個POJO類：
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

