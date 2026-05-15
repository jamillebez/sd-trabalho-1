package br.ufc.quixada.reserva.model;

public class ReservaAgendada {
    private String data;
    private Usuario usuario;       
    private EspacoFisico espaco;   

    public ReservaAgendada(String data, Usuario usuario, EspacoFisico espaco) {
        this.data = data;
        this.usuario = usuario;
        this.espaco = espaco;
    }
    
    public String getData() { 
        return data; 
    }
    
    public Usuario getUsuario() { 
        return usuario; 
    }
    
    public EspacoFisico getEspaco() { 
        return espaco; 
    }
    
    public void setData(String data) { 
        this.data = data; 
    }
    
    public void setUsuario(Usuario usuario) { 
        this.usuario = usuario; 
    }
    
    public void setEspaco(EspacoFisico espaco) { 
        this.espaco = espaco; 
    }
}