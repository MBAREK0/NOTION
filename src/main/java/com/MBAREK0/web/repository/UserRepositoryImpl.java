package com.MBAREK0.web.repository;



import com.MBAREK0.web.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public UserRepositoryImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public User createUser(User user) {
        entityManager.getTransaction().begin();
        User user1 = entityManager.merge(user);
        entityManager.getTransaction().commit();
        return user1;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User updateUser(User user) {
        entityManager.getTransaction().begin();
        User user1 = entityManager.merge(user);
        entityManager.getTransaction().commit();
        return user1;
    }

    @Override
    public User deleteUser(Long id) {
        Optional<User> userOptional = getUserById(id);
        if(userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
        return user;

    }
}
