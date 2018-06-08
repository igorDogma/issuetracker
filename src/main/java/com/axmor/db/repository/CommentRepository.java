package com.axmor.db.repository;

import com.axmor.db.entityes.Comment;

import java.util.List;

public class CommentRepository extends EntityRepository<Long, Comment>{

    public List<Comment> findByIssueId(Long id){
        List<Comment> entities =
                openSession().createQuery("from Comment where ( issue_id = '"+ id +"') ").list();
        closeCurrentSession();
        return entities;
    }

    @Override
    public String getEntityName() {
        return Comment.class.getSimpleName();
    }

    @Override
    public Class<Comment> getEntityType() {
        return Comment.class;
    }
}
