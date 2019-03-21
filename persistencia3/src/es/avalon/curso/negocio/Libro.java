package es.avalon.curso.negocio;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import es.avalon.utilidades.persistencia.DBHelper;

public class Libro {

	private String titulo;
	private String autor;
	private int paginas;

	public Libro() {
		super();
	}

	public Libro(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Libro(String titulo, String autor, int paginas) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.paginas = paginas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	public static List<Libro> buscarTodos() {

		List<Libro> libros = new ArrayList<Libro>();
		String sql = "select * from Libro";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet rs = sentencia.executeQuery(sql)) {

			while (rs.next()) {

				String titulo = rs.getString("titulo");
				String autor = rs.getString("autor");
				int paginas = rs.getInt("paginas");
				Libro li = new Libro(titulo, autor, paginas);

				libros.add(li);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e);
			// e.printStackTrace();
		}

		return libros;
	}

	public void insertar() {
		System.out.println(this.getAutor() + " " + this.getTitulo() + " " + this.getPaginas());
		String sql = " insert into libro (titulo, autor, paginas) values(?,?,?)";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, getTitulo());
			sentencia.setString(2, getAutor());
			sentencia.setDouble(3, getPaginas());
			sentencia.executeUpdate();

			System.out.println(this.getTitulo() + " fue insertado");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void deleteLibro() {

		String sql = "delete from libro where titulo = ?";
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

	public static Libro buscarUnLibroPorTitulo(String titulo) {

		String sql = "select * from Libro where titulo = ?";
		Libro libro = null;

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, titulo);
			ResultSet rs = sentencia.executeQuery();// No le pasamos la sql
			rs.next();

			libro = new Libro(rs.getString("titulo"), rs.getString("autor"), rs.getInt("paginas"));
			System.out.println("************* " + titulo + " fue encontrado");

		} catch (Exception e) {
			System.out.println("Error en clase: " + e);
			// e.printStackTrace();
		}
		return libro;
	}

	public void updateLibro() {

		String sql = "update libro set autor = ?, paginas = ? where titulo = ?";
		// String sql = "update ordenador set marca ='"+this.getMarca()+"',precio
		// ="+this.getPrecio()+" where modelo = '"+this.getModelo()+"'";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, this.getAutor());
			sentencia.setDouble(2, this.getPaginas());
			sentencia.setString(3, this.getTitulo());
			sentencia.executeUpdate();

			System.out.println(this.getTitulo() + " se actualizo correctamente");

		} catch (Exception e) {

			System.err.println(e.getMessage());
		}
	}

	public static List<Libro> filtrarPorCampo(String filtro) {

		System.out.println("filtrarPorCampo le llega: " + filtro);
		List<Libro> lista = new ArrayList<Libro>();
		String sql = "Select * from libro order by " + filtro;

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet rs = sentencia.executeQuery()) {

			while (rs.next()) {

				Libro lib = new Libro(rs.getString("titulo"), rs.getString("autor"),
						Integer.parseInt(rs.getString("paginas")));
				lista.add(lib);
			}

			System.out.println(" filtrado por " + filtro);

		} catch (Exception e) {
			System.out.println("Error al filtrarPorCampo: " + e);
		}
		return lista;

	}

	public static List<Libro> buscarPorTitulo(String titulo) {

		System.out.println("buscarPorTitulo le llega: " + titulo);
		List<Libro> lista = new ArrayList<Libro>();
		String sql = "Select * from libro where titulo= ? ";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, titulo);
			ResultSet rs = sentencia.executeQuery();

			while (rs.next()) {

				Libro lib = new Libro(rs.getString("titulo"), rs.getString("autor"),
						Integer.parseInt(rs.getString("paginas")));
				lista.add(lib);
			}

			System.out.println(" Encontrado " + titulo);

		} catch (Exception e) {
			System.out.println("Error al buscarPorTitulo: " + e);
		}
		return lista;

	}

}
