package com.ronaldo.gestor.cines.api.rest.models.usuario;

import com.ronaldo.gestor.cines.api.rest.db.usuarios.UsuariosDB;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;

/**
 *
 * @author ronaldo
 */
public class Usuario implements Editable {

       private String id;
       private String nombre;
       private String correo;
       private String contraseña;
       private String telefono;
       private String InputStream;
       private boolean activo;

       public Usuario() {
       }

       public Usuario(String id, String nombre, String correo, String telefono) {
              this.id = id;
              this.nombre = nombre;
              this.correo = correo;
              this.telefono = telefono;
       }
       
       public String getId() {
              return id;
       }
       
       public void setId(String id) {
              this.id = id;
       }

       public String getNombre() {
              return nombre;
       }

       public void setNombre(String nombre) {
              this.nombre = nombre;
       }

       public String getCorreo() {
              return correo;
       }

       public void setCorreo(String correo) {
              this.correo = correo;
       }

       public String getContraseña() {
              return contraseña;
       }

       public void setContraseña(String contraseña) {
              this.contraseña = contraseña;
       }

       public String getTelefono() {
              return telefono;
       }

       public void setTelefono(String telefono) {
              this.telefono = telefono;
       }

       public String getInputStream() {
              return InputStream;
       }

       public void setInputStream(String InputStream) {
              this.InputStream = InputStream;
       }

       public boolean isActivo() {
              return activo;
       }

       public void setActivo(boolean activo) {
              this.activo = activo;
       }

       public boolean cuentaConSaldoSuficiente(double costoTransaccion) throws DataBaseException {
              UsuariosDB usuariosDB = new UsuariosDB();
              double saldoExistente = usuariosDB.obtenerCreditos(id);
              return saldoExistente >= costoTransaccion;
       }

       public double obtenerNuevoSaldo(double gasto) throws DataBaseException {
              UsuariosDB usuariosDB = new UsuariosDB();
              double saldoExistente = usuariosDB.obtenerCreditos(id);
              return saldoExistente-gasto;
       }

       public double calcularNuevoSaldo(double montoRecarga) throws DataBaseException {
              UsuariosDB usuariosDB = new UsuariosDB();
              return usuariosDB.obtenerCreditos(id)+montoRecarga;
       }
}
