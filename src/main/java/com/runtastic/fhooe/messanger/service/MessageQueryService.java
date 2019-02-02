package com.runtastic.fhooe.messanger.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.runtastic.fhooe.messanger.domain.Message;
import com.runtastic.fhooe.messanger.domain.*; // for static metamodels
import com.runtastic.fhooe.messanger.repository.MessageRepository;
import com.runtastic.fhooe.messanger.service.dto.MessageCriteria;
import com.runtastic.fhooe.messanger.service.dto.MessageDTO;
import com.runtastic.fhooe.messanger.service.mapper.MessageMapper;

/**
 * Service for executing complex queries for Message entities in the database.
 * The main input is a {@link MessageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MessageDTO} or a {@link Page} of {@link MessageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MessageQueryService extends QueryService<Message> {

    private final Logger log = LoggerFactory.getLogger(MessageQueryService.class);

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    public MessageQueryService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    /**
     * Return a {@link List} of {@link MessageDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MessageDTO> findByCriteria(MessageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Message> specification = createSpecification(criteria);
        return messageMapper.toDto(messageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MessageDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageDTO> findByCriteria(MessageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Message> specification = createSpecification(criteria);
        return messageRepository.findAll(specification, page)
            .map(messageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MessageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Message> specification = createSpecification(criteria);
        return messageRepository.count(specification);
    }

    /**
     * Function to convert MessageCriteria to a {@link Specification}
     */
    private Specification<Message> createSpecification(MessageCriteria criteria) {
        Specification<Message> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Message_.id));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), Message_.content));
            }
            if (criteria.getSenderId() != null) {
                specification = specification.and(buildSpecification(criteria.getSenderId(),
                    root -> root.join(Message_.sender, JoinType.LEFT).get(Participant_.id)));
            }
            if (criteria.getReceiverId() != null) {
                specification = specification.and(buildSpecification(criteria.getReceiverId(),
                    root -> root.join(Message_.receiver, JoinType.LEFT).get(Participant_.id)));
            }
            if (criteria.getConversationId() != null) {
                specification = specification.and(buildSpecification(criteria.getConversationId(),
                    root -> root.join(Message_.conversation, JoinType.LEFT).get(Conversation_.id)));
            }
        }
        return specification;
    }
}
