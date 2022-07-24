package com.example.practica_clase;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private String nombre;
    private String clave;
    private String email;
    private String rol;
    private float valoracion;

    public Usuario(String nombre, String clave, String email, String rol, float valoracion) {
        this.nombre = nombre;
        this.clave = clave;
        this.email = email;
        this.rol = rol;
        this.valoracion = valoracion;
    }

    protected Usuario(Parcel in) {
        nombre = in.readString();
        clave = in.readString();
        email = in.readString();
        rol = in.readString();
        valoracion = in.readFloat();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(clave);
        parcel.writeString(email);
        parcel.writeString(rol);
        parcel.writeFloat(valoracion);
    }
}
