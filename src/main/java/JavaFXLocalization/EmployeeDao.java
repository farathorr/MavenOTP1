package JavaFXLocalization;

import jakarta.persistence.EntityManager;

public class EmployeeDao {
    public void persist(Employee employee) {
        EntityManager em = DBConnection.getInstance();
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
    }

    public Employee update(Employee employee) {
        EntityManager em = DBConnection.getInstance();
        em.getTransaction().begin();
        em.merge(employee);
        em.getTransaction().commit();
        return employee;
    }

    public void delete(Employee employee) {
        EntityManager em = DBConnection.getInstance();
        em.getTransaction().begin();
        em.remove(employee);
        em.getTransaction().commit();
    }

    public Employee find(int id) {
        EntityManager em = DBConnection.getInstance();
        return em.find(Employee.class,  id);
    }
}
