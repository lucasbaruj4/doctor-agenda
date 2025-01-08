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

import com.example.models.Area;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AreaService {
    
    private final DataSource dataSource;


    //Obtener Areas Servicio
    public List<Area> obtenerAreas(){
        List<Area> listaArea = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM areas;");

    try (
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sb.toString());
        ResultSet rs = ps.executeQuery()
    ){
        while(rs.next()){
            listaArea.add(new Area(
                rs.getLong("id"), rs.getString("nombre"), rs.getString("descripcion"))
            );

        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error al obtener las areas: " + e.getMessage());
    } return listaArea;
    };

    public List<Area> obtenerAreasPorId(int id) {
        List<Area> listaAreaID = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM areas WHERE id = ?");

        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sb.toString());
            ResultSet rs = ps.executeQuery();
        ){
            ps.setInt(1, id);
            if(rs.next()){
                listaAreaID.add(new Area(
                    rs.getLong("id"), rs.getString("nombre"), rs.getString("descripcion")));
            } else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al obtener el area.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el area: " + e.getMessage());
        } 
        return listaAreaID;
    }

    public String eliminarArea(int id){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM public.areas WHERE id = ?;");
        String respuesta = "La Area no ha sido eliminada";
        try (
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sb.toString());)
        {   
            ps.setInt(1, id);
            ps.execute();
            respuesta = "La Area ha sido eliminada";
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la Area: "+ e.getMessage());
        }

        return respuesta;
    }

    public String crearArea(Area Area) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO public.areas(nombre, descripcion) VALUES (?, ?);");
        String respuesta = "Area no grabada";
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sb.toString());
        ){ 
            ps.setString(1, Area.getNombre());
            ps.setString(2, Area.getDescripcion());
            ps.execute();
            respuesta = "Registro grabado";
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al anhadir la Area: " + e.getMessage());
        }
        return respuesta;
    }


    
}
