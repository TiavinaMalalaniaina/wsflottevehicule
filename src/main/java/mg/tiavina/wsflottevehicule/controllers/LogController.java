package mg.tiavina.wsflottevehicule.controllers;

import jakarta.servlet.http.HttpServletRequest;
import mg.tiavina.wsflottevehicule.models.User;
import mg.tiavina.wsflottevehicule.utils.JWebToken;
import mg.tiavina.wsflottevehicule.utils.Token;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class LogController {

    @PostMapping("login")
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            User user = User.log(username, password);
            JWebToken token = Token.createToken(user);
            return token.toString();
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @DeleteMapping("logout")
    public void logout(HttpServletRequest request) {
        try {
            JWebToken token = Token.checkToken(request);
            Token.delete(token.toString(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
