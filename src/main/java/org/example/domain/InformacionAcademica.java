package org.example.domain;

public class InformacionAcademica {
    private int idInfoAcademica;
    private int idFuncionario;
    private String universidad;
    private String nivelEstudio;
    private String titulo;

    public InformacionAcademica() {}

    public InformacionAcademica(int idInfoAcademica, int idFuncionario, String universidad, String nivelEstudio, String titulo) {
        this.idInfoAcademica = idInfoAcademica;
        this.idFuncionario = idFuncionario;
        this.universidad = universidad;
        this.nivelEstudio = nivelEstudio;
        this.titulo = titulo;
    }

    // Getters y Setters
    public int getIdInfoAcademica() {
        return idInfoAcademica;
    }

    public void setIdInfoAcademica(int idInfoAcademica) {
        this.idInfoAcademica = idInfoAcademica;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getNivelEstudio() {
        return nivelEstudio;
    }

    public void setNivelEstudio(String nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

