//package com.MBAREK0.web.scheduler;
//
//import com.MBAREK0.web.config.PersistenceManager;
//import com.MBAREK0.web.entity.Tag;
//import com.MBAREK0.web.service.TagService;
//import com.MBAREK0.web.service.TaskService;
//import com.MBAREK0.web.service.UserService;
//import jakarta.ejb.Schedule;
//import jakarta.ejb.Singleton;
//import jakarta.ejb.Startup;
//import jakarta.inject.Inject;
//import jakarta.persistence.EntityManager;
//
//@Startup
//@Singleton
//public class TagScheduler {
//
//
//    private TagService tagService; // Assuming you have a TagService to manage tags
//    public TagScheduler() {
//        EntityManager entityManager = PersistenceManager.getEntityManager();
//        // Initialize the UserService
//        this.tagService = new TagService(entityManager);
//    }
//    // Method to add a new unique tag every minute
//    @Schedule(minute = "*", hour = "*", persistent = false)
//    public void addUniqueTag() {
//        String tagName = "Tag_" + System.currentTimeMillis(); // Create a unique tag name
//
//        Tag newTag = new Tag();
//        newTag.setName(tagName);
//
//        tagService.createTag(newTag); // Add the new tag to the system
//
//        System.out.println("Added new tag: " + tagName); // Log the action (optional)
//    }
//}
