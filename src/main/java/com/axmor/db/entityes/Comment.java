package com.axmor.db.entityes;

import com.axmor.db.entityes.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment extends PersistentEntity {
    @Column(name="description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(
            name = "account_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "account_comment_fk")
    )
    private Account account;

    @ManyToOne
    @JoinColumn(
            name = "issue_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "issue_comment_fk")
    )
    private Issue issue;

}
