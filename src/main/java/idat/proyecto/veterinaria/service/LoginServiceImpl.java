package idat.proyecto.veterinaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import idat.proyecto.veterinaria.entity.Usuario;
import idat.proyecto.veterinaria.response.Response;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
    private AuthenticationManager authenticationManager;

	@Override
	public ResponseEntity<?> Authentication(Usuario usuario) {	
		try {
	        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());
	        Authentication authentication = authenticationManager.authenticate(auth);
	        String jwtToken = (String) authentication.getPrincipal();
	        return new ResponseEntity<>(Response.createMapAuth("Successful Authentication!", jwtToken), HttpStatus.ACCEPTED);
	    } catch (BadCredentialsException e) {
	        return new ResponseEntity<>(Response.createMapAuth("Incorrect Credentials!", null), HttpStatus.UNAUTHORIZED);
	    }
	}

}
