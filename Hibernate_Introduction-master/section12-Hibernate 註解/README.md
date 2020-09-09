## 十二、Hibenate Hibernate 註解
### 1. @Entity 註解將一個類宣告為實體Bean
### 2. @Id 和 @GeneratedValue 註解：
#### 每個實體bean將有一個主鍵，注釋在類的@Id注解。主鍵可以是單個字段或根據表結構的多個字段的組合。
### 3. @Table 註解:
#### @Table註解包含一個schema和一個catelog屬性,使用@UniqueConstraints 可以定義表的唯一約束。
	@Table(name="tbl_sky",uniqueConstraints ={@UniqueConstraint(columnNames={"month", "day"})} )
#### 上述程式碼在 "month" 和 "day" 兩個 field 上加上unique constrainst.
### 4. @Column 註解:
#### @Column 用於指定的列到一個字段或屬性將被映射的細節。可以使用列注釋以下最常用的屬性：

	name屬性：允許將顯式指定列的名稱。

	length 屬性：允許用於映射一個value尤其是對一個字符串值的列的大小。

	nullable 屬性：允許該列被標記為NOT NULL生成架構時。

	unique 屬性：允許被標記為隻包含唯一值的列。
### Example:
	@Entity
	@Table(name="user")
	public class User {
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="id")
		protected int id;
		
		@Column(name="name")
		protected String name;
		
		@Column(name="email")
		protected String email;
		
		@Column(name="country")
		protected String country;
		
		public User() {
		}
		
		public User(String name, String email, String country) {
			super();
			this.name = name;
			this.email = email;
			this.country = country;
		}

		public User(int id, String name, String email, String country) {
			super();
			this.id = id;
			this.name = name;
			this.email = email;
			this.country = country;
		}

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
	}
