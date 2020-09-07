## 六、Hibenate Sessions
### Hibernate在對資料庫進行操作之前，必須先取得Session實例，相當於JDBC在對資料庫操作之前，必須先取得Connection實例， 
### Session是Hibernate操作的基礎，它不是設計為執行緒安全（Thread-safe），一個Session由一個執行緒來使用。
### 1. 開啟Session:
#### Session實例由SessionFactory開啟獲得，例如：
	Configuration config = new Configuration().configure();
	SessionFactory sessionFactory = config.buildSessionFactory();
	Session session = sessionFactory.openSession();
	Transaction tx = session.beginTransaction();
	....
	tx.commit();
	session.close();
#### 開啟Session不會馬上取得Connection，而是在最後真正需要連接資料庫進行更新或查詢時才會取 得Connection，
#### 如果有設定Connection pool，則從Connection pool中取得Connection，而關閉Session時，如果有設定Connection pool，則是將Connection歸還給Connection pool，而不是直接關閉Connection。
#### 在Hibernate中，開啟一個Session會建立一個Persistence context，它可以進行快取管理、dirty check等，而所有的讀取、更新、插入等動作，則是在Transaction中完成。
### 2. 儲存資料:
#### 透過Session，可以對資料庫進行新增、刪除、更新，例如使用save()新增一筆資料：
	User user = new User();
	user.setName("momor");
	user.setAge(new Integer(26));

	Session session = sessionFactory.openSession();
	Transaction tx = session.beginTransaction();
	session.save(user);
	tx.commit();
	session.close();
#### save()之後，不會馬上對資料庫進行更新，而是在Transaction的commit()之後才會對資料庫進行更新，在Transaction之間 的操作要就全部成功，要就全部失敗，如果更新失敗，則在資料庫層面會撤消所有更新操作，然而記憶體中的持久物件是不會回復原有狀態的，事實上，當 Transaction失敗，這一次的Session就要馬上跟著失效，放棄所有記憶體中的物件，而不是嘗試以原物件再進行更新的動作。
### 3. 取得資料:
#### 使用get()或load()方法取得id為1的資料：
	Session session = sessionFactory.openSession();
	Transaction tx = session.beginTransaction();
	User user = (User) session.get(User.class, new Integer(1));
	tx.commit();
	session.close();
#### 如果未能發現相符合的資料，則get()方法會返回null，而load()方 法會丟出ObjectNotFoundException，在進階的應用中，load()方法可以返回代理（proxy）物件，在必要時才真正查詢資料庫取得對應資料，並可充分利用快取機制。
### 4. 刪除資料:
#### 接下來看看使用Session刪除資料，可使用delete()刪除資料：
	Session session = sessionFactory.openSession();
	Transaction tx = session.beginTransaction();
	User user = (User) session.get(User.class, new Integer(1));
	session.delete(user);
	tx.commit();
	session.close();
### 5. 更新資料:
#### 當您在同一個Session中取出資料並封裝為Persistence Object，接著更新物件狀態，而後將Transaction commit，則物件上的狀態更新將反應至資料庫中，您無需額外進行任何的更新操作。
#### 如果開啟了一個Session，從資料表中取出資料顯示到使用者介面上，之後關閉Session，當使用者在介面上操作完畢並按下儲存時，這時要重新開啟一個Session，使用update()方法將物件中的資料更新至對應的資料表中：
	Session session = sessionFactory.openSession();
	Transaction tx = session.beginTransaction();
	User user = (User) session.get(User.class, new Integer(2));
	tx.commit();
	session.close();
	....
	user.setAge(new Integer(27));
	session = sessionFactory.openSession();   
	tx= session.beginTransaction();
	session.update(user);
	tx.commit();
	session.close();	
#### Session提供了一個saveOrUpdate()方法，為資料的儲存或更新提供了一個統一的操作介面，藉由定義映射文件時，設定< id>標籤的unsaved-value來決定什麼是新的值必需，什麼是已有的值必須更新：
	<id name="id" column="id" type="java.lang.Integer" unsaved-value="null">
		<generator class="native"/>
	</id>
#### unsaved-value可以設定的值包括：
	(1)any：總是儲存
	(2)none：總是更新
	(3)null：id為null時儲存（預設）
	(4)valid：id為null或是指定值時儲存
#### 這樣設定之後，就可以使用Session的saveOrUpdate()方法來取代update()方法。
