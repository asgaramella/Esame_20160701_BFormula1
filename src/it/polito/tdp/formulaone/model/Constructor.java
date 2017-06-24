package it.polito.tdp.formulaone.model;

public class Constructor implements Comparable<Constructor> {

	private int constructorId;
	private String name;

	public Constructor(int constructorId, String name) {
		this.constructorId = constructorId;
		this.name = name;
	}

	public int getConstructorId() {
		return constructorId;
	}

	public void setConstructorId(int constructorId) {
		this.constructorId = constructorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + constructorId;
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
		Constructor other = (Constructor) obj;
		if (constructorId != other.constructorId)
			return false;
		return true;
	}

	@Override
	public int compareTo(Constructor other) {
		
		return this.constructorId-other.constructorId;
	}
	
	
}
