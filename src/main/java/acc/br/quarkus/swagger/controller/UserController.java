package acc.br.quarkus.swagger.controller;

import acc.br.quarkus.swagger.entity.User;
import acc.br.quarkus.swagger.exception.UserNotFoundException;
import acc.br.quarkus.swagger.exceptionhandler.ExceptionHandler;
import acc.br.quarkus.swagger.service.UserService;
import org.apache.commons.lang3.StringUtils;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "Basic Auth", type = SecuritySchemeType.HTTP, scheme = "basic")
public class UserController {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Operation(summary = "Recupera usuarios", description = "Lista todos os usuários exitentes.")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))))
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/{id}")
    @Operation(summary = "Recupera um usuario", description = "Recupera usuário pelo id.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "404", description="Usuario não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class)))
    })
    public User getUser(@PathParam("id") int id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @POST
    @PermitAll
    @Operation(summary = "Adiciona um usuario", description = "Cria um usuário e presiste no banco de dados")
    @APIResponses(value = @APIResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))))
    public User createUser(@Valid UserDto userDto) {
        return userService.saveUser(userDto.toUser());
    }

    @PUT
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    @Operation(summary = "Atualiza um usuario", description = "Atualiza um usuario existente pelo id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "404", description="Usuario não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class)))
    })
    public User updateUser(@PathParam("id") int id, @Valid UserDto userDto) throws UserNotFoundException {
        return userService.updateUser(id, userDto.toUser());
    }

    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    @Operation(summary = "Deleta um usuario", description = "Deleta um usuario pelo id")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Sucesso"),
            @APIResponse(responseCode = "404", description="Usuario não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionHandler.ErrorResponseBody.class)))
    })
    public Response deleteUser(@PathParam("id") int id) throws UserNotFoundException {
        userService.deleteUser(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Schema(name="UserDTO", description="Representacao do usuario a ser criada")
    public static class UserDto {

        @NotBlank
        @Schema(title = "Usuario", required = true)
        private String username;

        @NotBlank
        @Schema(title = "Senha", required = true)
        private String password;

        @Schema(title = "Papel do usuario, ADMIN ou USER. USER e default")
        private String role;

        @NotBlank
        @Schema(title="Nome do usuario", required = true)
        private String firstName;

        @NotBlank
        @Schema(title="Ultimo nome do usuario", required = true)
        private String lastName;

        @Min(value = 1, message = "Este valor tem que ser maior que 0")
        @Max(value = 200, message = "Este valor tem que ser menor que 200")
        @Schema(title="Idade do usuário deve estar entre 1 e 200", required = true)
        private int age;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public User toUser() {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(StringUtils.isBlank(role) ? "USER" : StringUtils.upperCase(role));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAge(age);
            return user;
        }
    }
}
