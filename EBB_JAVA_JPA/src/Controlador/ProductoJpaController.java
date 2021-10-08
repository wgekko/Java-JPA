/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidad.Fabricante;
import Entidad.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author WALTER GOMEZ
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController() {
    }
    
    

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("EBB_JAVA_JPAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fabricante codigoFabricante = producto.getCodigoFabricante();
            if (codigoFabricante != null) {
                codigoFabricante = em.getReference(codigoFabricante.getClass(), codigoFabricante.getCodigo());
                producto.setCodigoFabricante(codigoFabricante);
            }
            em.persist(producto);
            if (codigoFabricante != null) {
                codigoFabricante.getProductoList().add(producto);
                codigoFabricante = em.merge(codigoFabricante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getCodigo());
            Fabricante codigoFabricanteOld = persistentProducto.getCodigoFabricante();
            Fabricante codigoFabricanteNew = producto.getCodigoFabricante();
            if (codigoFabricanteNew != null) {
                codigoFabricanteNew = em.getReference(codigoFabricanteNew.getClass(), codigoFabricanteNew.getCodigo());
                producto.setCodigoFabricante(codigoFabricanteNew);
            }
            producto = em.merge(producto);
            if (codigoFabricanteOld != null && !codigoFabricanteOld.equals(codigoFabricanteNew)) {
                codigoFabricanteOld.getProductoList().remove(producto);
                codigoFabricanteOld = em.merge(codigoFabricanteOld);
            }
            if (codigoFabricanteNew != null && !codigoFabricanteNew.equals(codigoFabricanteOld)) {
                codigoFabricanteNew.getProductoList().add(producto);
                codigoFabricanteNew = em.merge(codigoFabricanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getCodigo();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Fabricante codigoFabricante = producto.getCodigoFabricante();
            if (codigoFabricante != null) {
                codigoFabricante.getProductoList().remove(producto);
                codigoFabricante = em.merge(codigoFabricante);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
