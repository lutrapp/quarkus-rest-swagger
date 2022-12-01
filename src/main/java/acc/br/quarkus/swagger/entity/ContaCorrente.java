package acc.br.quarkus.swagger.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.quarkus.security.jpa.UserDefinition;

@Entity
@Table(name = "contacorrente")
public class ContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "agencia", nullable = false)
    @NotBlank
    @Size(max = 3)
    private String agencia;
    
    @Column(name = "numero", nullable = false)
    @NotBlank
    @Size(max = 45)
    private String numero;
    
    @Column(name = "saldo", nullable = false)
    @NotBlank
    @Digits(integer=5, fraction=2)
    private BigDecimal saldo;
    
    @Column(name = "idCliente", nullable = false)
    @NotBlank
    private Integer idCliente;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	
   
}
