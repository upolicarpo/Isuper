package model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import helper.ConfiguracaoFirebase;

public class Empresa  implements Serializable {
    private String idUsuario;
    private String urlImagem;
    private String nome;
    private String tempo;
    private String categoria;
    private Double precoEntrega;

    public Empresa() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public String getNome() {
        return nome;
    }

    public String getTempo() {
        return tempo;
    }

    public String getCategoria() {
        return categoria;
    }

    public Double getPrecoEntrega() {
        return precoEntrega;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecoEntrega(Double precoEntrega) {
        this.precoEntrega = precoEntrega;
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference empresaRef = firebaseRef.child("empresas")
                .child(getIdUsuario());
        empresaRef.setValue(this);
    }

}
