
package example.functions;

import classes.Copyleaks;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.response.CopyleaksAuthToken;
import java.util.concurrent.ExecutionException;

public class AuthenticationExample {

    /** @return the token, or null when the login fails. */
    public static CopyleaksAuthToken authenticate(String email, String key) {
        try {
            CopyleaksAuthToken token= Copyleaks.login(email, key);

            System.out.println("Logged successfully!\nToken:");
            System.out.print(token);
            return token;
            
        } catch (ExecutionException | InterruptedException | UnderMaintenanceException | RateLimitException | CommandException e) {
            System.out.println("Authentication failed: " + e.getMessage() + "\n");
            e.printStackTrace();
            return null;
        }
        
    }

}
