package com.Model;

import java.sql.Date;
import java.time.LocalDate;

public class Bill {
	private long BillID;
	 private long CustomerID;
	 private String ConnectionType;
	 private int ConsumedUnits;
	 private double DueAmount;
	 private double PaymentAmount;
	 private long PaymentID;
	 private String PaymentStatus;
	 private Date BillDate;
	 //only used when creating database return objects
	 private Date BillExpiryDate;
	 
	 public Bill(){super();}
	 public Bill(long billID, long customerID, double dueAmount, double paymentAmount, long paymentID,
			String paymentStatus, Date billDate) {
		super();
		BillID = billID;
		CustomerID = customerID;
		DueAmount = dueAmount;
		PaymentAmount = paymentAmount;
		PaymentID = paymentID;
		PaymentStatus = paymentStatus;
		BillDate = billDate;
	}
	//only used when creating database return objects
	public Bill(long billID, long customerID,int consumedUnits, double dueAmount, double paymentAmount, long paymentID,
			String paymentStatus, Date billDate, Date billExpiryDate) {
		super();
		BillID = billID;
		CustomerID = customerID;
		ConsumedUnits = consumedUnits;
		DueAmount = dueAmount;
		PaymentAmount = paymentAmount;
		PaymentID = paymentID;
		PaymentStatus = paymentStatus;
		BillDate = billDate;
		BillExpiryDate = billExpiryDate;
	}
	// Used when creating Bill
	public Bill(long billID, long customerID, String connectionType, int consumedUnits, double dueAmount,
			double paymentAmount, long paymentID, String paymentStatus, Date billDate, Date billExpiryDate) {
		super();
		BillID = billID;
		CustomerID = customerID;
		ConnectionType = connectionType;
		ConsumedUnits = consumedUnits;
		DueAmount = dueAmount;
		PaymentAmount = paymentAmount;
		PaymentID = paymentID;
		PaymentStatus = paymentStatus;
		BillDate = billDate;
		BillExpiryDate = billExpiryDate;
	}
	@Override
	public String toString() {
		return "bill [BillID=" + BillID + ", CustomerID=" + CustomerID + ", DueAmount=" + DueAmount + ", PaymentAmount="
				+ PaymentAmount + ", PaymentID=" + PaymentID + ", PaymentStatus=" + PaymentStatus + ", BillDate="
				+ BillDate + "]";
	}
	public long getBillID() {
		return BillID;
	}
	public void setBillID(long billID) {
		BillID = billID;
	}
	public long getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(long customerID) {
		CustomerID = customerID;
	}
	public double getDueAmount() {
		return DueAmount;
	}
	public void setDueAmount(double dueAmount) {
		DueAmount = dueAmount;
	}
	public double getPaymentAmount() {
		return PaymentAmount;
	}
	public void setPaymentAmount(double paymentAmount) {
		PaymentAmount = paymentAmount;
	}
	public long getPaymentID() {
		return PaymentID;
	}
	public void setPaymentID(long paymentID) {
		PaymentID = paymentID;
	}
	public String getPaymentStatus() {
		return PaymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		PaymentStatus = paymentStatus;
	}
	public Date getBillDate() {
		return BillDate;
	}
	public void setBillDate(Date date) {
		BillDate = date;
	}
	public Date getBillExpiryDate() {
		return BillExpiryDate;
	}
	public void setBillExpiryDate(Date billExpiryDate) {
		BillExpiryDate = billExpiryDate;
	}
	public String getConnectionType() {
		return ConnectionType;
	}
	public void setConnectionType(String connectionType) {
		ConnectionType = connectionType;
	}
	public int getConsumedUnits() {
		return ConsumedUnits;
	}
	public void setConsumedUnits(int consumedUnits) {
		ConsumedUnits = consumedUnits;
	}
	 
}
