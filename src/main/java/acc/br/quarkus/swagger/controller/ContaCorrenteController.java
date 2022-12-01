package acc.br.quarkus.swagger.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import acc.br.quarkus.swagger.entity.ContaCorrente;
import acc.br.quarkus.swagger.entity.User;
import acc.br.quarkus.swagger.exception.ContaCorrenteNotFoundException;
import acc.br.quarkus.swagger.exceptionhandler.ExceptionHandler;
import acc.br.quarkus.swagger.service.ContaCorrenteService;

@RequestScoped
@Path("/v1/contacorrente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "Basic Auth", type = SecuritySchemeType.HTTP, scheme = "basic")

public class ContaCorrenteController {

    private final ContaCorrenteService contaCorrenteService;

    @Inject
    public ContaCorrenteController(ContaCorrenteService contaCorrenteService) {
        this.contaCorrenteService = contaCorrenteService;
    }

    @GET
//    @RolesAllowed({"USER", "ADMIN"})
    @Operation(summary = "Recupera contas-correntes", description = "Lista todos as conta correntes exitentes.")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContaCorrente.class))))
    public List<ContaCorrente> getContaCorrente() {
        return contaCorrenteService.getAllContaCorrente();
    }

    @GET
//    @RolesAllowed({"USER", "ADMIN"})
    @Path("/{id}")
    @Operation(summary = "Recupera uma conta corrente", description = "Recupera conta corrente pelo id.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContaCorrente.class))),
            @APIResponse(responseCode = "404", description="Conta não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class)))
    })
    public ContaCorrente getContaCorrente(@PathParam("id") int id) throws ContaCorrenteNotFoundException {
        return contaCorrenteService.getContaCorrenteById(id);
    }

    @POST
    @PermitAll
    @Operation(summary = "Adiciona uma conta corrente", description = "Cria uma conta e persiste no banco de dados")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContaCorrente.class))))
    public ContaCorrente createContaCorrente(@Valid ContaCorrenteDTO contaCorrenteDTO) {
        return contaCorrenteService.saveContaCorrente(contaCorrenteDTO.toContaCorrente());
    }

    @PUT
//    @RolesAllowed("ADMIN")
    @Path("/{id}")
    @Operation(summary = "Atualiza uma conta corrente", description = "Atualiza uma conta existente pelo id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "404", description="Conta não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class)))
    })
    public ContaCorrente updateContaCorrente(@PathParam("id") int id, @Valid ContaCorrenteDTO contaCorrenteDTO) throws ContaCorrenteNotFoundException {
        return contaCorrenteService.updateContaCorrente(id, contaCorrenteDTO.toContaCorrente());
    }

    @DELETE
//    @RolesAllowed("ADMIN")
    @Path("/{id}")
    @Operation(summary = "Deleta uma conta", description = "Deleta uma conta pelo id")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Sucesso"),
            @APIResponse(responseCode = "404", description="Conta não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class)))
    })
    public Response deleteContaCorrente(@PathParam("id") int id) throws ContaCorrenteNotFoundException {
        contaCorrenteService.deleteContaCorrente(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Schema(name="ContaCorrenteDTO", description="Representacao da conta corrente a ser criada")
    public static class ContaCorrenteDTO {

		@NotBlank
        @Schema(title = "Agencia", required = true)
        private String agencia;

        @NotBlank
        @Schema(title = "Numero", required = true)
        private String numero;
        
        @NotBlank
        @Schema(title = "Saldo", required = true)
        private BigDecimal saldo;
        
        @NotBlank
        @Schema(title = "IdCliente", required = true)
        private Integer idCliente;

//        @Schema(title = "Papel do usuario, ADMIN ou USER. USER e default")
//        private String role;


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


		public ContaCorrente toContaCorrente() {
			ContaCorrente contaCorrente = new ContaCorrente();
			contaCorrente.setAgencia(agencia);
			contaCorrente.setIdCliente(idCliente);
			contaCorrente.setNumero(numero);
			contaCorrente.setSaldo(saldo);
			return contaCorrente;
		}
    }
}

