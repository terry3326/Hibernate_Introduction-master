package net.javaguides.usermanagement.model;

public class Character {
	private Integer id;
	private String charName;
	private String email;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCharName() {
		return charName;
	}
	public void setCharName(String charName) {
		this.charName = charName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((charName == null) ? 0 : charName.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Character other = (Character) obj;
		if (charName == null) {
			if (other.charName != null)
				return false;
		} else if (!charName.equals(other.charName))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Character [id=" + id + ", charName=" + charName + ", email=" + email + "]";
	}
	public Character() {
		super();
		// TODO 自動產生的建構子 Stub
	}
	public Character(Integer id, String charName, String email) {
		super();
		this.id = id;
		this.charName = charName;
		this.email = email;
	}
	
	

}
