package com.MBAREK0.web.repository.implementation;

import com.MBAREK0.web.entity.Tag;
import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.repository.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class TagRepositoryImpl implements TagRepository {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public TagRepositoryImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public Tag createTag(Tag tag) {
        TypedQuery<Tag> query = entityManager.createQuery("SELECT t FROM Tag t WHERE t.name = :name",Tag.class);
        query.setParameter("name",tag.getName());

        if (!query.getResultList().isEmpty()) {
            return null;
        }

        entityManager.getTransaction().begin();
        entityManager.merge(tag);
        entityManager.getTransaction().commit();

        return tag;
    }

    @Override
    public Optional<Tag> getTagById(long id){
        Tag tag = entityManager.find(Tag.class,id);
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> gatAllTags(){
        return entityManager.createQuery("SELECT t FROM Tag t",Tag.class).getResultList();
    }

    @Override
    public Tag updateTag(Tag tag){

        entityManager.getTransaction().begin();
        Tag tag1 = entityManager.merge(tag);
        entityManager.getTransaction().commit();

        return tag1;
    }

    @Override
    public Tag deleteTag(Tag tag){
        Optional<Tag> tagOptional = getTagById(tag.getId());
        if(tagOptional.isEmpty()) {
            return null;
        }
        entityManager.getTransaction().begin();
        entityManager.remove(tag);
        entityManager.getTransaction().commit();

        return tag;
    }



}
