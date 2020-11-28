package com.example.lab_login;

public class User {
    private Integer id,procontag;
    private String fullName, userName, eMail, password,sintomas;
    private Integer tipousuario,estado;

    public User() {

        //this.fullName = fullName;
        //this.userName = userName;
        //this.eMail = eMail;
        //this.password = password;
        //Gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTipoUsuario() {
        return tipousuario;
    }

    public void setTipoUsuario(Integer tipousuario) {
        this.tipousuario = tipousuario;
    }

    public Integer getProcontag() {
        return procontag;
    }

    public void setProcontag(Integer procontag) {
        this.procontag = procontag;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

}
