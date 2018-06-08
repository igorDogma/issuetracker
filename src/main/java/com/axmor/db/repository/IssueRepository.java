package com.axmor.db.repository;

import com.axmor.db.entityes.Issue;

public class IssueRepository extends EntityRepository<Long, Issue>{

    @Override
    public String getEntityName() {
        return Issue.class.getSimpleName();
    }

    @Override
    public Class<Issue> getEntityType() {
        return Issue.class;
    }


}
