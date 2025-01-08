package com.example.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.models.Usuario;
import com.example.utils.Utils;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final DataSource dataSource;
    private final Utils utils;

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> listaUsuario = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM usuarios");

        try (
                Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sb.toString()); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                listaUsuario.add(new Usuario(
                        rs.getLong("id"), rs.getString("nombre"), rs.getString("correo"), rs.getString("contraseña")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener los usuarios: " + e.getMessage());
        }
        return listaUsuario;
    }

    public List<Usuario> obtenerUsuariosPorId(int id) {
        List<Usuario> listaUsuarioID = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM usuarios WHERE id = ?");

        try (
                Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sb.toString()); ResultSet rs = ps.executeQuery();) {
            ps.setInt(1, id);
            if (rs.next()) {
                listaUsuarioID.add(new Usuario(
                        rs.getLong("id"), rs.getString("nombre"), rs.getString("correo"), rs.getString("contraseña")
                ));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener las reservas: " + e.getMessage());
        }
        return listaUsuarioID;
    }

    public String eliminarUsuario(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM public.usuarios WHERE id = ?;");
        String respuesta = "El usuario no ha sido eliminado";
        try (
                Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sb.toString());) {
            ps.setInt(1, id);
            ps.execute();
            respuesta = "El usuario ha sido eliminada";
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage());
        }

        return respuesta;
    }

    public String crearUsuario(Usuario usuario) {
        String respuesta = "Usuario registrado";
        try (
            Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuarios WHERE nombre = ?;"); ) {
            ps.setString(1, usuario.getNombre());
            @Cleanup ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                respuesta = "El usuario ya existe";
                return respuesta;
            } else {
            try (
                PreparedStatement ps2 = connection.prepareStatement("INSERT INTO public.usuarios(nombre, correo, contraseña) VALUES (?, ?, ?);");
            ){
                    String nuevaContra = utils.Encriptar(usuario.getContraseña());
                    ps2.setString(1, usuario.getNombre());
                    ps2.setString(2, usuario.getCorreo());
                    ps2.setString(3, nuevaContra);
                    ps2.execute();
                    respuesta = "Usuario grabado";
                } catch (SQLException e) {
                    throw new RuntimeException("Error al anhadir al usuario: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al anhadir al usuario: " + e.getMessage());
        }

        return respuesta;
    }

    public Object loginUsuario(Usuario usuario){
        try 
        ( 
        Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuarios WHERE nombre = ?"); 
        ){
            ps.setString(1, usuario.getNombre());
            @Cleanup ResultSet rs = ps.executeQuery(); 
            if(rs.next()){
                try (
                PreparedStatement ps2 = connection.prepareStatement("SELECT contraseña FROM usuarios WHERE nombre = ?");
                ){
                    ps2.setString(1, usuario.getNombre());
                    @Cleanup ResultSet rs2 = ps2.executeQuery();
                    // String passInput = usuario.getContraseña();
                    String pass = utils.Desencriptar(rs2.getString("contraseña"));
                    System.out.println(pass);
                    if(usuario.getContraseña().equals(pass)){
                        return usuario;
                    }else{
                        throw new RuntimeException("La contrasena es incorrecta");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("PIPI");
                }
            }


        } catch (Exception e) {
            throw new RuntimeException("Error al validar al usuario: " + e.getMessage());
        }
        return usuario;
    }

}

// public Object loginUsuario(Usuario usuario){
//         String input = utils.Desencriptar(usuario.getContraseña());
//         System.out.println(input);
//         return usuario;
//     }
// }

// public String crearUsuario(Usuario usuario) {
//     StringBuilder sb = new StringBuilder();
//     sb.append("INSERT INTO public.usuarios(nombre, correo, contraseña) VALUES (?, ?, ?);");
//     String respuesta = "Usuario no grabado";
//     try (
//         Connection connection = dataSource.getConnection();
//         PreparedStatement ps = connection.prepareStatement(sb.toString());
//     ){ 
//         ps.setString(1, usuario.getNombre());
//         ps.setString(2, usuario.getCorreo());
//         ps.setString(3, usuario.getContraseña());
//         ps.execute();
//         respuesta = "Usuario grabado";
//     } catch (SQLException e) {
//         throw new RuntimeException("Error al anhadir al usuario: " + e.getMessage());
//     }
//     return respuesta;
// }


// }

