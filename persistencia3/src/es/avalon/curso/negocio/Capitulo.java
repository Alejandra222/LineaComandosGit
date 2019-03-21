package es.avalon.curso.negocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import es.avalon.utilidades.persistencia.DBHelper;

public class Capitulo {

	private String titulo;
	private int paginas;
	private String libro_titulo;

	public Capitulo() {
		super();
	}

	public Capitulo(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Capitulo(String titulo, int paginas) {
		super();
		this.titulo = titulo;
		this.paginas = paginas;
	}

	public Capitulo(String titulo, int paginas, String libro_titulo) {
		super();
		this.titulo = titulo;
		this.paginas = paginas;
		this.libro_titulo = libro_titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public String getLibro_titulo() {
		return libro_titulo;
	}

	public void setLibro_titulo(String libro_titulo) {
		this.libro_titulo = libro_titulo;
	}

	public static List<Capitulo> buscarTodosLosCapitulos() {

		List<Capitulo> capitulos = new ArrayList<Capitulo>();
		String sql = "select * from capitulo";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet rs = sentencia.executeQuery(sql)) {

			while (rs.next()) {

				String titulo = rs.getString("titulo");
				int paginas = rs.getInt("paginas");
				String libro_titulo = rs.getString("libro_titulo");
				Capitulo cap = new Capitulo(titulo, paginas, libro_titulo);

				capitulos.add(cap);
			}

			System.out.println("Capitulos encontrados: " + capitulos.size());

		} catch (Exception e) {
			System.out.println("Error buscarTodosLosCapitulos: " + e);
			// e.printStackTrace();
		}

		return capitulos;
	}

	public static List<Capitulo> buscarCapituloPorLibro(String tituloLibro) {

		System.out.println("buscarCapituloPorLibro le llega: " + tituloLibro);
		List<Capitulo> lista = new ArrayList<Capitulo>();
		String sql = "Select * from capitulo where Libro_Titulo= ? ";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, tituloLibro);
			ResultSet rs = sentencia.executeQuery();

			while (rs.next()) {

				Capitulo lib = new Capitulo(rs.getString("titulo"), Integer.parseInt(rs.getString("paginas")));
				lista.add(lib);
			}

			System.out.println(" Encontrado " + tituloLibro);

		} catch (Exception e) {
			System.out.println("Error al buscarPorTitulo: " + e);
		}
		return lista;

	}

	public void insertar() {
		System.out.println(this.getLibro_titulo() + " " + this.getTitulo() + " " + this.getPaginas());
		String sql = " insert into capitulo (titulo, paginas, libro_titulo) values(?,?,?)";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, getTitulo());
			sentencia.setInt(2, getPaginas());
			sentencia.setString(3, getLibro_titulo());
			sentencia.executeUpdate();

			System.out.println(this.getTitulo() + " fue insertado Capitulo");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void deleteCapitulo() {

		String sql = "delete from capitulo where titulo = ?";
		// String sql = "delete from ordenador where modelo = '"+this.getModelo()+"'";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, this.getTitulo());
			sentencia.executeUpdate();
			System.out.println(this.getTitulo() + " fue eliminado");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public static Capitulo buscarCapituloPorTitulo(String titulo) {

		System.out.println("buscarCapituloPorTitulo le llega: " + titulo);

		String sql = "Select * from capitulo where titulo= ? ";

		Capitulo lib = null;
		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, titulo);
			ResultSet rs = sentencia.executeQuery();
			rs.next();

			lib = new Capitulo(rs.getString("titulo"), Integer.parseInt(rs.getString("paginas")));

			System.out.println(" Encontrado " + lib.getTitulo());

		} catch (Exception e) {
			System.out.println("Error al buscarPorTitulo: " + e);
		}
		return lib;

	}

	public void updateCapitulo() {

		String sql = "update capitulo set paginas = ?, libro_titulo = ? where titulo = ?";
		// String sql = "update ordenador set marca ='"+this.getMarca()+"',precio
		// ="+this.getPrecio()+" where modelo = '"+this.getModelo()+"'";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setInt(1, this.getPaginas());
			sentencia.setString(2, this.getLibro_titulo());
			sentencia.setString(3, this.getTitulo());
			sentencia.executeUpdate();

			System.out.println(this.getTitulo() + " se actualizo correctamente");

		} catch (Exception e) {

			System.err.println(e.getMessage());
		}
	}

}
