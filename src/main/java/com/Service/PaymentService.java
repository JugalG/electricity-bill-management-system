package com.Service;

import com.Dao.PaymentDao;
import com.Model.Payment;
import com.Util.MyDatabase;

public class PaymentService {

	public PaymentService() {
		super();
	}

	public Payment processPayment(Payment payment) {
		PaymentDao paymentDao = new PaymentDao();
		Payment insertedPayment = paymentDao.addPayment(MyDatabase.getConnection(), payment);
		return insertedPayment;
	}
}
