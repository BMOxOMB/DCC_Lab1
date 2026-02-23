/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenfotec.componentes.controller;
import com.cenfotec.componentes.dao.UsuarioDAO;
import com.cenfotec.componentes.model.*;

/**
 *
 * @author moren
 */


public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        usuarioDAO = new UsuarioDAO();
    }

    public void registrarUsuario(String nombre, String username, String nombreRol) {

        try {

            Persona persona = new Persona(nombre);
            Rol rol = new Rol(nombreRol);

            Usuario usuario = new Usuario(username, persona, rol);

            usuarioDAO.agregarUsuario(usuario);

            System.out.println("Usuario registrado correctamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}