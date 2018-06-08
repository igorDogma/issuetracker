package com.axmor.db.entityes;

import com.axmor.db.entityes.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "issue")
@Getter
@Setter
public class Issue extends PersistentEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(
            name = "account_id",
            nullable = false
//            foreignKey = @ForeignKey(name = "account_issue_fk")
    )
    private Account account;

}
