package persistencia;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import aritmetica.Fraccion;
import aritmetica.Operacion;
import aritmetica.OperacionTipo;

public class OperacioneSQLite implements IOperacionesDAO {

	String dbname = "data/operaciones.db";

	@Override
	public void inicializar() {
		File miFile = new File(dbname);

		if (!miFile.exists()) {
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
			// System.out.println(miFile);
		}
	}
	
	@Override
	public int cmdRegistrarOperacion(Operacion op) throws Exception {
		int idOp = cmdInsertOperacion(op.tipo.toString(), op.f1, op.f2, op.resultado);
		cmdInsertOperando(op.resultado, idOp, 0);
		cmdInsertOperando(op.f1, idOp, 1);
		cmdInsertOperando(op.f2, idOp, 2);
		return idOp;
	}
	
	private int cmdInsertOperacion(String op, Fraccion f1, Fraccion f2, Fraccion fr) throws Exception {
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

	private void cmdInsertOperando(Fraccion f, int idOp, int tipo) throws Exception {
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

	private Operacion rsToOperacion(ResultSet rs) throws Exception {
//		System.out.println((rs.getString("FH") + "\t" + rs.getInt("N1") + "/" + rs.getInt("D1") + " " + rs.getString("TIPO")
//		+ " " + rs.getInt("N2") + "/" + rs.getInt("D2") + " = " + rs.getInt("NR") + "/"
//		+ rs.getInt("DR"));
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	java.util.Date fh= dateFormat.parse(rs.getString("FH"));
		return new Operacion(
				fh, 
				rs.getString("TIPO").equals("+")?OperacionTipo.SUMA:OperacionTipo.MULTIPLICACION,
				new Fraccion(rs.getInt("N1"),rs.getInt("D1")),
				new Fraccion(rs.getInt("N2"),rs.getInt("D2")),
				new Fraccion(rs.getInt("NR"),rs.getInt("DR"))
				);
	}
	
	private List<Operacion> qryOperacionesWhere(String where) throws Exception {
		List<Operacion> ops = new ArrayList<Operacion>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "SELECT OPERACIONES.* FROM OPERACIONES ";
			if (where != "")
				sql += " WHERE " + where;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Operacion op = rsToOperacion(rs);
				ops.add(op);
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
	public List<Operacion> qryOperacionesPor(Fraccion f) throws Exception {
		List<Operacion> ops = new ArrayList<Operacion>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
			String sql = "SELECT OPERACIONES.* FROM OPERACIONES, OPERANDOS "
					+ " WHERE OPERACIONES.ID = OPERANDOS.IDOP AND OPERANDOS.F='" + f.toString() + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Operacion op = rsToOperacion(rs);
				ops.add(op);
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
	public List<Operacion> qryResultadosImpropios() throws Exception {
		return qryOperacionesWhere("NR>DR");
	}

	@Override
	public List<Operacion> qryTodasLasOperaciones() throws Exception {
		return qryOperacionesWhere("");
	}

	@Override
	public void finalizar() {
		
	}


}
