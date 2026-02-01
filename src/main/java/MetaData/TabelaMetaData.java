/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MetaData;
import java.sql.*;
import java.util.*;
/**
 *
 * @author Juan
 */
public class TabelaMetaData extends metadata{
    private int numColunas;
    private String[] nomeColunas;
    private String[] nomeTipoColunas;
    private int[] tipoColunas;
    private Map<String, String> FKs;

    public TabelaMetaData(Connection cn, String nomeTabela) {
        this.numColunas = metadata.numeroColunas(cn, nomeTabela);
        this.nomeColunas = metadata.nomeColunas(cn, nomeTabela);
        this.nomeTipoColunas = metadata.nomeTipoColunas(cn, nomeTabela);
        this.tipoColunas = metadata.tipoColunas(cn, nomeTabela);
        this.FKs = metadata.FKs(cn, nomeTabela);
    }
    
    @Override
    public String toString() {
        return "TabelaMetaData:" + "\nnumColunas = " + numColunas + "\nnomeColunas = " + Arrays.toString(nomeColunas) + "\nnomeTipoColunas = " + Arrays.toString(nomeTipoColunas) + "\ntipoColunas = " + Arrays.toString(tipoColunas) + "\nFKs = " + FKs;
    }

    public int getNumColunas() {
        return numColunas;
    }

    private void setNumColunas(int numColunas) {
        this.numColunas = numColunas;
    }

    public String[] getNomeColunas() {
        return nomeColunas;
    }

    private void setNomeColunas(String[] nomeColunas) {
        this.nomeColunas = nomeColunas;
    }

    public String[] getNomeTipoColunas() {
        return nomeTipoColunas;
    }

    private void setNomeTipoColunas(String[] nomeTipoColunas) {
        this.nomeTipoColunas = nomeTipoColunas;
    }

    public int[] getTipoColunas() {
        return tipoColunas;
    }

    private void setTipoColunas(int[] tipoColunas) {
        this.tipoColunas = tipoColunas;
    }

    public Map<String, String> getFKs() {
        return FKs;
    }

    private void setFKs(Map<String, String> FKs) {
        this.FKs = FKs;
    }
}
