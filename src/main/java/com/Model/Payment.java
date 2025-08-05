package com.Model;

import java.time.LocalDate;
import java.util.List;

public class Payment {
	
	private long PaymentID; 
	private List<String> BillID; 
	private String paymentReturnBillsString;
	private long CustomerID; 
	private LocalDate PaymentDate; 
	private double PaymentAmount; 
	private String PaymentMode;
	private long indiBillID;
	
	public long getIndiBillID() {
		return indiBillID;
	}


	public void setIndiBillID(long indiBillID) {
		this.indiBillID = indiBillID;
	}


	public Payment(long paymentID, String paymentReturnBillsString, long customerID, LocalDate paymentDate,
			double paymentAmount, String paymentMode) {
		super();
		PaymentID = paymentID;
		this.paymentReturnBillsString = paymentReturnBillsString;
		CustomerID = customerID;
		PaymentDate = paymentDate;
		PaymentAmount = paymentAmount;
		PaymentMode = paymentMode;
	}


	public String getPaymentReturnBillsString() {
		return paymentReturnBillsString;
	}


	public void setPaymentReturnBillsString(String paymentReturnBillsString) {
		this.paymentReturnBillsString = paymentReturnBillsString;
	}


	public Payment(long paymentID, List<String> billID, long customerID, LocalDate paymentDate, double paymentAmount,
			String paymentMode) {
		super();
		PaymentID = paymentID;
		BillID = billID;
		CustomerID = customerID;
		PaymentDate = paymentDate;
		PaymentAmount = paymentAmount;
		PaymentMode = paymentMode;
	}


	public Payment() {
		super();
	}


	@Override
	public String toString() {
		return "Payment [PaymentID=" + PaymentID + ", BillID=" + BillID + ", CustomerID=" + CustomerID
				+ ", PaymentDate=" + PaymentDate + ", PaymentAmount=" + PaymentAmount + ", PaymentMode=" + PaymentMode
				+ "]";
	}


	public long getPaymentID() {
		return PaymentID;
	}


	public void setPaymentID(long paymentID) {
		PaymentID = paymentID;
	}


	public List<String> getBillID() {
		return BillID;
	}


	public void setBillID(List<String> billIDs) {
		BillID = billIDs;
	}


	public long getCustomerID() {
		return CustomerID;
	}


	public void setCustomerID(long customerID) {
		CustomerID = customerID;
	}


	public LocalDate getPaymentDate() {
		return PaymentDate;
	}


	public void setPaymentDate(LocalDate paymentDate) {
		PaymentDate = paymentDate;
	}


	public double getPaymentAmount() {
		return PaymentAmount;
	}


	public void setPaymentAmount(double paymentAmount) {
		PaymentAmount = paymentAmount;
	}


	public String getPaymentMode() {
		return PaymentMode;
	}


	public void setPaymentMode(String paymentMode) {
		PaymentMode = paymentMode;
	}
}




