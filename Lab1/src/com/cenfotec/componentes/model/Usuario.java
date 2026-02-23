/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenfotec.componentes.model;

/**
 *
 * @author moren
 */

public class Usuario {

    private int idUsuario;
    private String username;
    private Persona persona;
    private Rol rol;

    public Usuario() {}

    public Usuario(int idUsuario, String username, Persona persona, Rol rol) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.persona = persona;
        this.rol = rol;
    }

    public Usuario(String username, Persona persona, Rol rol) {
        this.username = username;
        this.persona = persona;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}