
package example.functions;

import classes.Copyleaks;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.response.CopyleaksAuthToken;
import java.util.concurrent.ExecutionException;

/**
 * Example for authenticating with the Copyleaks API.
 */
public class AuthenticationExample {

    /**
     * Authenticates with Copyleaks using the provided email and key.
     * Prints error details if authentication fails.
     *
     * @param email The Copyleaks account email address
     * @param key   The Copyleaks API key
     * @return      CopyleaksAuthToken if successful, null otherwise
     */
    public static CopyleaksAuthToken authenticate(String email, String key) {
        try {
            // Attempt to log in to Copyleaks
            CopyleaksAuthToken token= Copyleaks.login(email, key);

            System.out.println("Logged successfully!\nToken:");
            System.out.print(token);
            return token;
            
        } catch (ExecutionException | InterruptedException | UnderMaintenanceException | RateLimitException | CommandException e) {
            // Print error details if authentication fails
            System.out.println("Authentication failed: " + e.getMessage() + "\n");
            e.printStackTrace();
            return null;
        }
        
    }

}
