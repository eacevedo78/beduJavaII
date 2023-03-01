package org.bedu.servidores.model;

public class AppSrv {
    public Long id;
    public String nombre;
    public String version;
    public Long servidorId;
    public String servidorNombre;

    public AppSrv(Long id,String nombre,String version,Long servidorId,String servidorNombre){
        this.id=id;
        this.nombre=nombre;
        this.version=version;
        this.servidorId=servidorId;
        this.servidorNombre=servidorNombre;
    }
}
