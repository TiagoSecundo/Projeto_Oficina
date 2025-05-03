package entities;

import java.util.ArrayList;

public class Veiculo {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private String placa;
    private String status;
    private Cliente proprietario;
    private  ArrayList<Servico> historicoServicos;
    //ainda preciso ver como relacionar as classes cliente, servicos e relatorio com o veiculo
    
    public Veiculo(){}
    
    public Veiculo(int id, String marca, String modelo, int ano, String placa, String status){
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.placa = placa;
        this.status = status;
    }
    /**
     * 
     * @return 
     */
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getPlaca(){
        return this.placa;
    }
    
    public void setPlaca (String placa){
        this.placa = placa;
    }
    
    public String getModelo(){
        return this.modelo;
    }
    
    public void setModelo(String modelo){
        this.modelo = modelo;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    @Override
    public String toString (){
        return String.format(
        "Veiculo [Id: %d Marca: %s, Modelo: %s, Ano: %d, Placa: %s, Status: %s]",
                id, marca, modelo, ano, placa, status);
    }
}
