package com.Service;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.Dao.BillDao;
import com.Model.Bill;
import com.Util.MyDatabase;

public class BillService {
	// Admin Generates Bill Using this
	public static Bill insertValuesInBillTableByAdmin(Bill bill) {

		Random random = new Random();
		long maxBillID = 9999999999L, minBillID = 1000000000L;
		long GeneratedBIllID = minBillID + (long) (random.nextDouble() * (maxBillID - minBillID + 1));
		bill.setBillID(GeneratedBIllID);
		Date billDate = bill.getBillDate();
		LocalDate localBillDate = billDate.toLocalDate();
		LocalDate expiryLocalDate = localBillDate.plusDays(28);
		Date expiryDate = Date.valueOf(expiryLocalDate); // Convert LocalDate back to java.sql.Date
		bill.setBillExpiryDate(expiryDate);

		try {
			Connection con = MyDatabase.getConnection();
			boolean res = BillDao.createBillByAdmin(con, bill);
			System.out.println("BillService: " + bill.toString());
			con.close();
			if (res) {
				return bill;
			} else
				return null;
		} catch (Exception e) {
			System.out.println("BillNotInserted: Database Error");
			System.out.println(e.getMessage());
		}
		return null;

	}

	public int getCountOfBills(String status) {
		return BillDao.fetchCountOfBillsWithStatus(status);
	}

	public String getBillMonth(Date date) {
		LocalDate localDate = date.toLocalDate();
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		if (month == 1) {
			month = 12;
			year = year - 1;
		} else {
			month = month - 1;
		}
		return getMonthString(month) + "-" + year;
	}

	private String getMonthString(int month) {
		switch (month) {
		case 1:
			return "Jan";
		case 2:
			return "Feb";
		case 3:
			return "Mar";
		case 4:
			return "Apr";
		case 5:
			return "May";
		case 6:
			return "Jun";
		case 7:
			return "Jul";
		case 8:
			return "Aug";
		case 9:
			return "Sep";
		case 10:
			return "Oct";
		case 11:
			return "Nov";
		case 12:
			return "Dec";
		default:
			return "Invalid";
		}
	}

	public List<Bill> getBillsByCustomerID(long customerID) {
		return BillDao.getBillsByCustomerId(customerID);
	}

	public List<Bill> fetchFilteredBills(String statusFilter, String billIDString) {
		if (billIDString != null && !billIDString.isEmpty()) {
			try {
				long billID = Long.parseLong(billIDString);

				Bill bill = BillDao.getBillById(billID);
				return bill != null ? Arrays.asList(bill) : new ArrayList<>();
			} catch (NumberFormatException e) {
				return new ArrayList<>();
			}
		}
		return BillDao.getAllBillsByPaymentStatus(statusFilter);
	}
}
