package com.Dao;

import com.Model.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.time.LocalDate;
public class PaymentDao {

	
	private long generateUniquePaymentID(Connection connection) {
		Random random = new Random();
		long maxPaymentID = 9999999999L, minPaymentID = 1000000000L;
		long paymentID;
		
		do {
			paymentID = minPaymentID + (long) (random.nextDouble() * (maxPaymentID - minPaymentID + 1));
		} while (isPaymentIDExists(connection, paymentID));
		
		return paymentID;
	}
	
	private boolean isPaymentIDExists(Connection connection, long paymentID) {
		String checkQuery = "SELECT COUNT(*) FROM Payment WHERE PaymentID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(checkQuery)) {
			stmt.setLong(1, paymentID);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error checking payment ID: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	public Payment addPayment(Connection connection, Payment payment) {
	    String query = "INSERT INTO Payment (PaymentID, BillID, CustomerID, PaymentDate, PaymentAmount, PaymentMode) VALUES (?, ?, ?, ?, ?, ?)";
	    long paymentID = generateUniquePaymentID(connection);
	    payment.setPaymentID(paymentID);
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setLong(1, paymentID);
	        stmt.setString(2, payment.getBillID().toString());
	        stmt.setLong(3, payment.getCustomerID());
	        stmt.setDate(4, Date.valueOf(payment.getPaymentDate()));
	        stmt.setDouble(5, payment.getPaymentAmount());
	        stmt.setString(6, payment.getPaymentMode());
	        List<Long> insertedBills = new ArrayList<>();
	        boolean flag =  false;
	        if( stmt.executeUpdate() > 0){
	        	for(String l:payment.getBillID()){
	        		payment.setIndiBillID(Long.parseLong(l));
	        		Bill reBill = BillDao.updateBillValueUponPayment(payment);
	        		
	        		if(reBill!=null){
	        			System.out.println("Bill inserted w.r.t payment table entry.");
	        			insertedBills.add(Long.parseLong(l));
	        		}
	        		else {
	        				flag = true;        			
	        		}
	        	}
	        	if (flag){
	        		boolean flag2=false;
	        		for(Long ll: insertedBills){
	        			payment.setIndiBillID(ll);
	        			Bill deleteOPOfBills= BillDao.updateBillValueUponPaymentFailed(payment);
	        			if(deleteOPOfBills!=null){
	        				System.out.println("Appended bill delete,bill id = "+ll);
	        			}else{
	        				flag2=true;
	        				System.out.println("Appended bill delete error= "+ll);
	        				System.out.println("Database con error");
	        			}
	        		}
	        		
	        		if(!flag){
        				boolean paymentDeleted = deletePaymentByPaymentID(connection, payment.getPaymentID());
        				if(paymentDeleted) {System.out.println("Payment table and Bill Table Modified for Bill Failure");}
        			}else{System.out.println("Payment table not updated with Delete cmd: Database Error");}
	        	}
	        }
	        return payment;
	    } catch (SQLException e) {
	        System.err.println("Error inserting payment: " + e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	    
	}


    public Payment getPaymentById(Connection connection,long paymentId) {
        String query = "SELECT * FROM Payment WHERE PaymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, paymentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Payment(
                    rs.getLong("PaymentID"),
                    rs.getString("BillID"),
                    rs.getLong("CustomerID"),
                    rs.getDate("PaymentDate").toLocalDate(),
                    rs.getDouble("PaymentAmount"),
                    rs.getString("PaymentMode")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Payment> getPaymentsByCustomerId(Connection connection,long customerId) {
        String query = "SELECT * FROM Payment WHERE CustomerID = ?";
        List<Payment> payments = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                payments.add(new Payment(
                    rs.getLong("PaymentID"),
                    rs.getString("BillID"),
                    rs.getLong("CustomerID"),
                    rs.getDate("PaymentDate").toLocalDate(),
                    rs.getDouble("PaymentAmount"),
                    rs.getString("PaymentMode")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public List<Payment> getPaymentsByDateRange(Connection connection,LocalDate startDate, LocalDate endDate) {
        String query = "SELECT * FROM Payment WHERE PaymentDate BETWEEN ? AND ?";
        List<Payment> payments = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                payments.add(new Payment(
                    rs.getLong("PaymentID"),
                    rs.getString("BillID"),
                    rs.getLong("CustomerID"),
                    rs.getDate("PaymentDate").toLocalDate(),
                    rs.getDouble("PaymentAmount"),
                    rs.getString("PaymentMode")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public boolean updatePaymentAmount(Connection connection,long paymentId, double newAmount) {
        String query = "UPDATE Payment SET PaymentAmount = ? WHERE PaymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, newAmount);
            stmt.setLong(2, paymentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


  
    public boolean linkPaymentToBill(Connection connection,long paymentId, long billId) {
        String query = "UPDATE Payment SET BillID = ? WHERE PaymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, billId);
            stmt.setLong(2, paymentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean isPaymentExist(Connection connection,long paymentId) {
        String query = "SELECT COUNT(*) FROM Payment WHERE PaymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, paymentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Payment> getListOfAllPayments(Connection connection) {
        String query = "SELECT * FROM Payment";
        List<Payment> payments = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                payments.add(new Payment(
                    rs.getLong("PaymentID"),
                    rs.getString("BillID"),
                    rs.getLong("CustomerID"),
                    rs.getDate("PaymentDate").toLocalDate(),
                    rs.getDouble("PaymentAmount"),
                    rs.getString("PaymentMode")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }


	public static boolean deletePaymentByPaymentID(Connection con, long paymntID) {
		String query = "DELETE FROM Payment WHERE PaymentID = ?";
		try (PreparedStatement pst = con.prepareStatement(query)) {
	
			pst.setLong(1, paymntID);
	
			int rowsAffected = pst.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
