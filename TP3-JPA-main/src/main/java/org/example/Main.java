package org.example;

import entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{


        entityManager.getTransaction().begin();


        Cliente cliente = Cliente.builder()
                .nombre("Juan")
                .apellido("Martinez")
                .dni(20665232)
                .build();

        Factura factura1 = Factura.builder()
                .numero(11)
                .fecha("14/09/2020")
                .build();

        Domicilio dom = Domicilio.builder()
                .nombreCalle("Dr Posse")
                .numero(3151)
                .build();

        Categoria perecederos = Categoria.builder()
                .denominacion("Perecederos")
                .build();



        Categoria lacteos = Categoria.builder()
                .denominacion("lacteos")
                .build();

        Categoria caramelos = Categoria.builder()
                .denominacion("caramelos")
                .build();

        Articulo art1 = Articulo.builder()
                .cantidad(100)
                .denominacion("Yogurt Griego")
                .precio(30)
                .build();

        Articulo art2 = Articulo.builder()
                .cantidad(200)
                .denominacion("Pico Dulce")
                .precio(20)
                .build();

            cliente.setDomicilio(dom);
            dom.setCliente(cliente);
            factura1.setCliente(cliente);


            art1.getCategorias().add(perecederos);
            art1.getCategorias().add(lacteos);

            lacteos.getArticulos().add(art1);

            perecederos.getArticulos().add(art1);

            art2.getCategorias().add(caramelos);

            caramelos.getArticulos().add(art2);

            DetalleFactura det1 = DetalleFactura.builder().build();

            det1.setArticulo(art1);
            det1.setCantidad(100);
            det1.setSubtotal(3000);

            art1.getDetalleFacturas().add(det1);

            factura1.getDetalles().add(det1);

            det1.setFactura(factura1);

            DetalleFactura det2 = DetalleFactura.builder().build();

            det2.setArticulo(art2);
            det2.setCantidad(200);
            det2.setSubtotal(4000);



            art2.getDetalleFacturas().add(det2);


            factura1.getDetalles().add(det2);
            det2.setFactura(factura1);

            factura1.setTotal(7000);

        entityManager.persist(cliente);
        entityManager.persist(dom);
        entityManager.persist(factura1);


        entityManager.flush();
        entityManager.getTransaction().commit();

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("Algo ha fallado");
            System.out.println("Error : " + e.getMessage());
        }

        // Cerrar el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }
}
