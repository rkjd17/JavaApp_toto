package com.epam;

public class Hit {
	private int numberOfHits;
	private long price;
	
	public Hit(int numberOfHits, long price) {
		super();
		this.numberOfHits = numberOfHits;
		this.price = price;
	}

	public int getNumberOfHits() {
		return numberOfHits;
	}

	public void setNumberOfHits(int numberOfHits) {
		this.numberOfHits = numberOfHits;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Hit [numberOfHits=" + numberOfHits + ", price=" + price + "]";
	}
	
	
	
	
	
	

}
