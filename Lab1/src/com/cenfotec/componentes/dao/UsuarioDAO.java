/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cenfotec.componentes.dao;
import com.cenfotec.componentes.database.Conexion;
import com.cenfotec.componentes.model.Usuario;
import com.cenfotec.componentes.model.Persona;
import com.cenfotec.componentes.model.Rol;
import java.sql.*;

/**
 *
 * @author moren
 */


public class UsuarioDAO {

    public void agregarUsuario(Usuario usuario) throws SQLException {

        Connection conn = Conexion.getConexion();
        conn.setAutoCommit(false);

        try {

            // Insertar Persona
            String sqlPersona = "INSERT INTO Personas(nombre) VALUES(?)";
            PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            stmtPersona.setString(1, usuario.getPersona().getNombre());
            stmtPersona.executeUpdate();

            ResultSet rsPersona = stmtPersona.getGeneratedKeys();
            int idPersona = 0;
            if (rsPersona.next()) {
                idPersona = rsPersona.getInt(1);
            }

            // Obtener idRol
            int idRol = obtenerIdRol(conn, usuario.getRol().getNombreRol());

            // Insertar Usuario
            String sqlUsuario = "INSERT INTO Usuarios(username, id_persona, id_rol) VALUES(?,?,?)";
            PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
            stmtUsuario.setString(1, usuario.getUsername());
            stmtUsuario.setInt(2, idPersona);
            stmtUsuario.setInt(3, idRol);
            stmtUsuario.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    public Usuario buscarUsuario(String username) throws SQLException {

        Connection conn = Conexion.getConexion();

        String sql = """
                SELECT u.id_usuario, p.id_persona, p.nombre, u.username,
                       r.id_rol, r.nombre_rol
                FROM Usuarios u
                JOIN Personas p ON u.id_persona = p.id_persona
                JOIN Roles r ON u.id_rol = r.id_rol
                WHERE u.username = ?
                """;

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        Usuario usuario = null;

        if (rs.next()) {

            Persona persona = new Persona(
                    rs.getInt("id_persona"),
                    rs.getString("nombre")
            );

            Rol rol = new Rol(
                    rs.getInt("id_rol"),
                    rs.getString("nombre_rol")
            );

            usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("username"),
                    persona,
                    rol
            );
        }

        conn.close();
        return usuario;
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {

        Connection conn = Conexion.getConexion();
        conn.setAutoCommit(false);

        try {

            // Actualizar Persona
            String sqlPersona = """
                    UPDATE Personas p
                    JOIN Usuarios u ON p.id_persona = u.id_persona
                    SET p.nombre = ?
                    WHERE u.id_usuario = ?
                    """;

            PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona);
            stmtPersona.setString(1, usuario.getPersona().getNombre());
            stmtPersona.setInt(2, usuario.getIdUsuario());
            stmtPersona.executeUpdate();

            // Obtener idRol
            int idRol = obtenerIdRol(conn, usuario.getRol().getNombreRol());

            // Actualizar Usuario
            String sqlUsuario = """
                    UPDATE Usuarios
                    SET username = ?, id_rol = ?
                    WHERE id_usuario = ?
                    """;

            PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
            stmtUsuario.setString(1, usuario.getUsername());
            stmtUsuario.setInt(2, idRol);
            stmtUsuario.setInt(3, usuario.getIdUsuario());
            stmtUsuario.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    public void eliminarUsuario(int idUsuario) throws SQLException {

        Connection conn = Conexion.getConexion();
        conn.setAutoCommit(false);

        try {

            // Primero obtener id_persona asociado
            String sqlBuscar = "SELECT id_persona FROM Usuarios WHERE id_usuario = ?";
            PreparedStatement stmtBuscar = conn.prepareStatement(sqlBuscar);
            stmtBuscar.setInt(1, idUsuario);
            ResultSet rs = stmtBuscar.executeQuery();

            int idPersona = 0;
            if (rs.next()) {
                idPersona = rs.getInt("id_persona");
            }

            // Eliminar Usuario
            String sqlUsuario = "DELETE FROM Usuarios WHERE id_usuario = ?";
            PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
            stmtUsuario.setInt(1, idUsuario);
            stmtUsuario.executeUpdate();

            // Eliminar Persona
            String sqlPersona = "DELETE FROM Personas WHERE id_persona = ?";
            PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona);
            stmtPersona.setInt(1, idPersona);
            stmtPersona.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    private int obtenerIdRol(Connection conn, String nombreRol) throws SQLException {

        String sqlRol = "SELECT id_rol FROM Roles WHERE nombre_rol = ?";
        PreparedStatement stmtRol = conn.prepareStatement(sqlRol);
        stmtRol.setString(1, nombreRol);

        ResultSet rsRol = stmtRol.executeQuery();

        if (rsRol.next()) {
            return rsRol.getInt("id_rol");
        }

        throw new SQLException("Rol no encontrado");
    }
}