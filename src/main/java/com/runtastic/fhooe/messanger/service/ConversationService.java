package com.runtastic.fhooe.messanger.service;

import com.runtastic.fhooe.messanger.service.dto.ConversationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Conversation.
 */
public interface ConversationService {

    /**
     * Save a conversation.
     *
     * @param conversationDTO the entity to save
     * @return the persisted entity
     */
    ConversationDTO save(ConversationDTO conversationDTO);

    /**
     * Get all the conversations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConversationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" conversation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ConversationDTO> findOne(Long id);

    /**
     * Delete the "id" conversation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
