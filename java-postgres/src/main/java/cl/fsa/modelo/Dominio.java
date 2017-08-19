/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.fsa.modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public @Data class Dominio {
    @Setter
    @Getter
    private String codigo;
    @Setter
    @Getter
    private String valor;
    @Setter
    @Getter
    private String descripcion;

    public Dominio() {
    }

    public Dominio(String codigo, String valor, String descripcion) {
        this.codigo = codigo;
        this.valor = valor;
        this.descripcion = descripcion;
    }
    
}
