package com.packt.webstore.domain;

public class RenamedCustomer {
	
	private String customerId;
	private String name;
	private String address;
	private String itemName;
	private int noOfOrdersMade;
		

	public RenamedCustomer()
	{
		
	}
	
	public RenamedCustomer(String customerId, String name, String address, String itemName, int noOfOrdersMade) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.address = address;
		this.itemName = itemName;
		this.noOfOrdersMade = noOfOrdersMade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + noOfOrdersMade;
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
		RenamedCustomer other = (RenamedCustomer) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (noOfOrdersMade != other.noOfOrdersMade)
			return false;
		return true;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	

	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public int getNoOfOrdersMade() {
		return noOfOrdersMade;
	}


	public void setNoOfOrdersMade(int noOfOrdersMade) {
		this.noOfOrdersMade = noOfOrdersMade;
	}
	
	
	
	

}
