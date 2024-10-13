package com.MBAREK0.web.repository.implementation;



import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.entity.UserRole;
import com.MBAREK0.web.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User createUser(User user) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", user.getEmail());

        if (!query.getResultList().isEmpty()) {
            return null;
        }

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        return user;
    }


    @Override
    public Optional<User> getUserById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email){
        List<User> user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        if(user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(user.get(0));
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.role = :role", User.class)
                .setParameter("role", role)
                .getResultList();
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public List<User> getUsersWithEligibleDoubleTokens() {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.eligibleForDoubleTokens > 0", User.class).getResultList();
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
