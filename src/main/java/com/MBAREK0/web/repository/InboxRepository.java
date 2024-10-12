package com.MBAREK0.web.repository;

import com.MBAREK0.web.entity.Inbox;

import java.util.List;
import java.util.Optional;

public interface InboxRepository {
    List<Inbox> getAllInboxByUserId(Long userId);

    Inbox createInbox(Inbox inbox);

    Optional<Inbox> getInboxById(Long id);

    Inbox deleteInbox(Inbox inbox);
}
