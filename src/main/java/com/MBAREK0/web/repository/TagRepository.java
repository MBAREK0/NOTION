package com.MBAREK0.web.repository;

import com.MBAREK0.web.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    Tag createTag(Tag tag);
    Optional<Tag> getTagById(long id);
    List<Tag> gatAllTags();
    Tag updateTag(Tag tag);
    Tag deleteTag(Tag tag);

}
