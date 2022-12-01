package acc.br.quarkus.swagger.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        tags = {
                @Tag(name = "user", description = "User operations."),
                @Tag(name = "contacorrente", description = "Conta corrente operations.")
        },
        info = @Info(
                title = "User API with Quarkus",
                version = "0.0.1",
                contact = @Contact(
                        name = "",
                        url = "",
                        email = ""),
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/licenses/MIT"))
        
        
)
public class SwaggerConfig extends Application {

}

