package persistencia;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import aritmetica.Fraccion;

public class ArtimeticaSQLite implements IAritmeticaDAO {

	String dbname = "data/aritmetica.db";

	@Override
	public void inicializar() {
		File tempFile = new File(dbname);

		if (!tempFile.exists()) {
			// CREAMOS LA BD Y LAS TABLAS
			try {
				Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
				Statement stmt = conn.createStatement();
				String sql = "CREATE TABLE OPERACIONES (" + " ID INTEGER PRIMARY KEY AUTOINCREMENT," + " FH   TEXT, "
						+ " TIPO VARCHAR(1) CHECK (TIPO IN ('*','+')), " + " NR   INT, " + " DR   INT, " + " N1   INT, "
						+ " D1   INT, " + " N2   INT, " + " D2   INT " + " )";
				// System.out.println(sql);
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE OPERANDOS (" + " F     TEXT NOT NULL, " + " IDOP  INT  NOT NULL, "
						+ " TIPO  INT  NOT NULL, " + " PRIMARY KEY (F, IDOP, TIPO) " + " )";
				System.out.println(sql);
				stmt.executeUpdate(sql);
				stmt.close();
				conn.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		} else {
			System.out.println(tempFile);
		}
	}

	@Override
	public int cmdRegistrarOperacion(String op, Fraccion f1, Fraccion f2, Fraccion fr) throws Exception {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "INSERT INTO OPERACIONES (FH,TIPO,NR,DR,N1,D1,N2,D2) VALUES (DATETIME('now', 'localtime'),?,?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// pstmt.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
			pstmt.setString(1, op);
			pstmt.setInt(2, fr.getNumerador());
			pstmt.setInt(3, fr.getDenominador());
			pstmt.setInt(4, f1.getNumerador());
			pstmt.setInt(5, f1.getDenominador());
			pstmt.setInt(6, f2.getNumerador());
			pstmt.setInt(7, f2.getDenominador());

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No rows affected.");
			}

			int id = -1;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			generatedKeys.next();
			id = generatedKeys.getInt(1);

			pstmt.close();
			conn.close();
			return id;
		} catch (Exception e) {
			// System.out.println(e.toString());
			throw e;
		}
	}

	@Override
	public void cmdRegistrarOperando(Fraccion f, int idOp, int tipo) throws Exception {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "INSERT INTO OPERANDOS (F,IDOP,TIPO) VALUES (?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, f.toString());
			pstmt.setInt(2, idOp);
			pstmt.setInt(3, tipo);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			// System.out.println(e.toString());
			throw e;
		}
	};

	private List<String> qryOperacionesWhere(String where) throws Exception {
		List<String> ops = new ArrayList<String>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "SELECT OPERACIONES.* FROM OPERACIONES ";
			if (where != "")
				sql += " WHERE " + where;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ops.add(rs.getString("FH") + "\t" + rs.getInt("N1") + "/" + rs.getInt("D1") + " " + rs.getString("TIPO")
						+ " " + rs.getInt("N2") + "/" + rs.getInt("D2") + " = " + rs.getInt("NR") + "/"
						+ rs.getInt("DR"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// System.out.println(e.toString());
			throw e;
		}
		return ops;
	}

	@Override
	public List<String> qryOperacionesPor(Fraccion f) throws Exception {
		List<String> ops = new ArrayList<String>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "SELECT OPERACIONES.* FROM OPERACIONES, OPERANDOS "
					+ " WHERE OPERACIONES.ID = OPERANDOS.IDOP AND OPERANDOS.F='" + f.toString() + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ops.add(rs.getString("FH") + "\t" + rs.getInt("N1") + "/" + rs.getInt("D1") + " " + rs.getString("TIPO")
						+ " " + rs.getInt("N2") + "/" + rs.getInt("D2") + " = " + rs.getInt("NR") + "/"
						+ rs.getInt("DR"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// System.out.println(e.toString());
			throw e;
		}
		return ops;
	}

	@Override
	public List<String> qryRanking() throws Exception {
		List<String> ops = new ArrayList<String>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "SELECT F,COUNT(*) AS PUNTOS FROM OPERANDOS" + " GROUP BY F" + " ORDER BY  PUNTOS DESC, F"
					+ " LIMIT 3";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ops.add(rs.getString(1) + "\t" + rs.getInt(2));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// System.out.println(e.toString());
			throw e;
		}
		return ops;
	}

	@Override
	public List<String> qryResultadosImpropios() throws Exception {
		return qryOperacionesWhere("NR>DR");
	}

	@Override
	public List<String> qryTodasLasOperaciones() throws Exception {
		return qryOperacionesWhere("");
	}
}
