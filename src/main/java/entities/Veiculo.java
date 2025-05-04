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
    /**
     * 
     * @param id 
     */
    public void setId(int id){
        this.id = id;
    }
    /**
     * 
     * @return 
     */
    public String getPlaca(){
        return this.placa;
    }
    /**
     * 
     * @param placa 
     */
    public void setPlaca (String placa){
        this.placa = placa;
    }
    /**
     * 
     * @return 
     */
    public String getModelo(){
        return this.modelo;
    }
    /**
     * 
     * @param modelo 
     */
    public void setModelo(String modelo){
        this.modelo = modelo;
    }
    /**
     * 
     * @return 
     */
    public String getStatus(){
        return this.status;
    }
    /**
     * 
     * @param status 
     */
    public void setStatus(String status){
        this.status = status;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String toString (){
        return String.format(
        "Veiculo [Id: %d Marca: %s, Modelo: %s, Ano: %d, Placa: %s, Status: %s]",
                id, marca, modelo, ano, placa, status);
    }
}
