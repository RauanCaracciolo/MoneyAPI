package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import model.connection.ConnectionFactory;
import model.entity.Transaction;

public class TransactionDao {

	private final SimpleDateFormat dF = new SimpleDateFormat("dd-MM-yyyy");
	private Connection c;
	private int limit = 10;

	public TransactionDao() throws Exception {
		try {
			c = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("Error in connecting with database: " + e.getMessage(), e);
		}
	}

	public List<Transaction> listPage(int p) throws Exception {
		List<Transaction> trs = new ArrayList<>();
		String sql = "SELECT * FROM transactions ORDER BY id DESC LIMIT ? OFFSET ?";
		try (PreparedStatement stmt = c.prepareStatement(sql)) {
			stmt.setInt(1, limit);
			stmt.setInt(2, limit * p);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					trs.add(new Transaction(rs.getInt("id"), rs.getDouble("value_"), rs.getString("description_"),
							rs.getString("type_"), rs.getString("category"), rs.getDate("date_").toString()));
				}
			}
		} catch (SQLException e) {
			throw new Exception("Error in list transactions page:  " + p + ": " + e.getMessage(), e);
		}
		return trs;
	}

	public void insert(Transaction tr) throws Exception {
		String sql = "INSERT INTO transactions (value_, description_, type_, category, date_) VALUES(?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = c.prepareStatement(sql)) {
			stmt.setDouble(1, tr.getValue());
			stmt.setString(2, tr.getDesc());
			stmt.setString(3, tr.getType());
			stmt.setString(4, tr.getCat());
			java.util.Date pd = dF.parse(tr.getTime());
			stmt.setDate(5, new Date(pd.getTime()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Error in insert Transaction: " + e.getMessage(), e);
		} catch (Exception e) {
			throw new Exception("Error in convert date format: " + tr.getTime(), e);
		}
	}

	public boolean delete(int id) throws Exception {
		String sql = "DELETE FROM transactions WHERE id = ?";
		try (PreparedStatement stmt = c.prepareStatement(sql)) {
			stmt.setInt(1, id);
			int lines = stmt.executeUpdate();
			return lines>0;
		} catch (SQLException e) {
			throw new Exception("Error in delete transaction with id: " + id + ": " + e.getMessage(), e);
		}
	}

	public boolean update(int id, Transaction tr) throws Exception {
		String sql = "UPDATE Transactions SET value_ = ?, description_,= ?, type_ = ?, category = ? WHERE id = ? ";
		try (PreparedStatement stmt = c.prepareStatement(sql)) {
			stmt.setDouble(1, tr.getValue());
			stmt.setString(2, tr.getDesc());
			stmt.setString(3, tr.getType());
			stmt.setString(4, tr.getCat());
			int lines = stmt.executeUpdate();
			return lines>0;
		} catch (Exception e) {
			throw new Exception("Error in update transaction id: " + id + e.getMessage(), e);
		}
	}

	public Transaction searchId(int id) throws Exception {
		Transaction tr = null;
		String sql = "SELECT * FROM transactions WHERE id = ?";
		try (PreparedStatement stmt = c.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					tr = new Transaction(rs.getInt("id"), rs.getDouble("value_"), rs.getString("description_"),
							rs.getString("type_"), rs.getString("category"), rs.getDate("date_").toString());
				}
			}
		} catch (Exception e) {
			throw new Exception("Error in search for id: " + id + e.getMessage(), e);
		}
		return tr;
	}
	
	public List<Transaction> searchForCategory(String cat) throws Exception{
		List<Transaction> trs = new ArrayList<>();
		String sql = "SELECT * FROM transactions where category = ? ORDER BY id DESC";
		try (PreparedStatement stmt = c.prepareStatement(sql)) {
			stmt.setString(1, cat);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					trs.add(new Transaction(rs.getInt("id"), rs.getDouble("value_"), rs.getString("description_"),
							rs.getString("type_"), rs.getString("category"), rs.getDate("date_").toString()));
				}
			}
		}catch(Exception e) {
			throw new Exception("Error in searching for category: " + cat + e.getMessage(), e);
		}
		return trs;
	}
	public List<Transaction> searchForType(String cat) throws Exception{
		List<Transaction> trs = new ArrayList<>();
		String sql = "SELECT * FROM transactions where type_ = ? ORDER BY id DESC";
		try (PreparedStatement stmt = c.prepareStatement(sql)) {
			stmt.setString(1, cat);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					trs.add(new Transaction(rs.getInt("id"), rs.getDouble("value_"), rs.getString("description_"),
							rs.getString("type_"), rs.getString("category"), rs.getDate("date_").toString()));
				}
			}
		}catch(Exception e) {
			throw new Exception("Error in searching for type: " + cat + e.getMessage(), e);
		}
		return trs;
	}
	public List<Transaction> searchForCategoryAndType(String cat, String type) throws Exception{
		List<Transaction> trs = new ArrayList<>();
		String sql = "SELECT * FROM transactions where category = ? AND type_ = ? ORDER BY id DESC";
		try (PreparedStatement stmt = c.prepareStatement(sql)) {
			stmt.setString(1, cat);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					trs.add(new Transaction(rs.getInt("id"), rs.getDouble("value_"), rs.getString("description_"),
							rs.getString("type_"), rs.getString("category"), rs.getDate("date_").toString()));
				}
			}
		}catch(Exception e) {
			throw new Exception("Error in searching for category: " + cat + e.getMessage(), e);
		}
		return trs;
	}
}
