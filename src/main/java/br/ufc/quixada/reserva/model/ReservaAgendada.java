package br.ufc.quixada.reserva.model;

public class ReservaAgendada implements java.io.Serializable {
    private Integer id;
    private String data;
    private Usuario usuario;       
    private EspacoFisico espaco;   

    public ReservaAgendada(String data, Usuario usuario, EspacoFisico espaco, Integer id)  {
        this.id = id;
        this.data = data;
        this.usuario = usuario;
        this.espaco = espaco;
    }

    public Integer getId() {
        return id;
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

    public void setId(Integer id) { this.id = id; }
    
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