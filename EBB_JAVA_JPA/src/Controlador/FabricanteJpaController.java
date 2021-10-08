/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Entidad.Fabricante;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidad.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author WALTER GOMEZ
 */
public class FabricanteJpaController implements Serializable {

    public FabricanteJpaController() {
    }

    
    
    public FabricanteJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("EBB_JAVA_JPAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fabricante fabricante) {
        if (fabricante.getProductoList() == null) {
            fabricante.setProductoList(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : fabricante.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getCodigo());
                attachedProductoList.add(productoListProductoToAttach);
            }
            fabricante.setProductoList(attachedProductoList);
            em.persist(fabricante);
            for (Producto productoListProducto : fabricante.getProductoList()) {
                Fabricante oldCodigoFabricanteOfProductoListProducto = productoListProducto.getCodigoFabricante();
                productoListProducto.setCodigoFabricante(fabricante);
                productoListProducto = em.merge(productoListProducto);
                if (oldCodigoFabricanteOfProductoListProducto != null) {
                    oldCodigoFabricanteOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldCodigoFabricanteOfProductoListProducto = em.merge(oldCodigoFabricanteOfProductoListProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fabricante fabricante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fabricante persistentFabricante = em.find(Fabricante.class, fabricante.getCodigo());
            List<Producto> productoListOld = persistentFabricante.getProductoList();
            List<Producto> productoListNew = fabricante.getProductoList();
            List<String> illegalOrphanMessages = null;
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoListOldProducto + " since its codigoFabricante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getCodigo());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            fabricante.setProductoList(productoListNew);
            fabricante = em.merge(fabricante);
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    Fabricante oldCodigoFabricanteOfProductoListNewProducto = productoListNewProducto.getCodigoFabricante();
                    productoListNewProducto.setCodigoFabricante(fabricante);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldCodigoFabricanteOfProductoListNewProducto != null && !oldCodigoFabricanteOfProductoListNewProducto.equals(fabricante)) {
                        oldCodigoFabricanteOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldCodigoFabricanteOfProductoListNewProducto = em.merge(oldCodigoFabricanteOfProductoListNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fabricante.getCodigo();
                if (findFabricante(id) == null) {
                    throw new NonexistentEntityException("The fabricante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fabricante fabricante;
            try {
                fabricante = em.getReference(Fabricante.class, id);
                fabricante.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fabricante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Producto> productoListOrphanCheck = fabricante.getProductoList();
            for (Producto productoListOrphanCheckProducto : productoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fabricante (" + fabricante + ") cannot be destroyed since the Producto " + productoListOrphanCheckProducto + " in its productoList field has a non-nullable codigoFabricante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(fabricante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fabricante> findFabricanteEntities() {
        return findFabricanteEntities(true, -1, -1);
    }

    public List<Fabricante> findFabricanteEntities(int maxResults, int firstResult) {
        return findFabricanteEntities(false, maxResults, firstResult);
    }

    private List<Fabricante> findFabricanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fabricante.class));
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

    public Fabricante findFabricante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fabricante.class, id);
        } finally {
            em.close();
        }
    }

    public int getFabricanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fabricante> rt = cq.from(Fabricante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
