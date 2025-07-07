package model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.connection.ConnectionFactory;
import model.entity.Transaction;

public class TransactionDao {

	private Connection c;
	private int limit = 10;
	
	public TransactionDao() throws Exception{
		c = ConnectionFactory.getConnection();
	}
	
	public List<Transaction> listPage(int p) throws Exception{
		List<Transaction> trs = new ArrayList<>();
		String sql = "SELECT * FROM Transactions ORDER BY id DESC LIMIT ? OFFSET ?";
		PreparedStatement stmt = c.prepareStatement(sql);
		stmt.setInt(1, limit);
		stmt.setInt(2, limit*p);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			trs.add(new Transaction(rs.getInt("id"), 
					rs.getDouble("value_"),
					rs.getString("description_"),
					rs.getString("type_"),
					rs.getString("category"),
					rs.getDate("date_").toString()));
		}
		return trs;
	}
}
