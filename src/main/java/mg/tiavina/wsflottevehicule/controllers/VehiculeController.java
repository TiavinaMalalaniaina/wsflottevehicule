package mg.tiavina.wsflottevehicule.controllers;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import mg.tiavina.wsflottevehicule.models.Vehicule;
import mg.tiavina.wsflottevehicule.utils.JWebToken;
import mg.tiavina.wsflottevehicule.utils.Token;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class VehiculeController {

    @GetMapping("vehicules")
    public List<Vehicule> findAll(HttpServletRequest request) throws Exception {
        try {
            JWebToken c = Token.checkToken(request);
            return Vehicule.findAll(null);
        }  catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("vehicules/{matricule}")
    public Vehicule findByMatricule(HttpServletRequest request, @PathVariable String matricule) {
        try {
            JWebToken c = Token.checkToken(request);
            Vehicule model = Vehicule.findByMatricule(matricule, null);
            return model;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("vehicules")
    public Vehicule save(HttpServletRequest request, @RequestParam String matricule, @RequestParam String marque, @RequestParam String name) {
        Vehicule vehicule = new Vehicule(matricule, marque, name);
        try {
            JWebToken c = Token.checkToken(request);
            vehicule.save(null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicule;
    }

    @PutMapping("vehicules")
    public Vehicule update(HttpServletRequest request, @RequestParam String matricule, @RequestParam String marque, @RequestParam String name) {
        Vehicule vehicule = new Vehicule(matricule, marque, name);
        try {
            JWebToken c = Token.checkToken(request);
            vehicule.update(null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicule;
    }

    @DeleteMapping("vehicules/{matricule}")
    public void delete(HttpServletRequest request, @PathVariable String matricule) {
        try {
            JWebToken c = Token.checkToken(request);
            Vehicule.delete(matricule, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
