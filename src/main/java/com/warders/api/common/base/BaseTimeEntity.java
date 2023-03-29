package com.warders.api.common.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@SuperBuilder
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public abstract class BaseTimeEntity {

    @Comment("생성일시")
    @CreatedDate
    @Column(name = "register_dtm", nullable = false)
    private LocalDateTime registerDtm;

    @Comment("수정일시")
    @LastModifiedDate
    @Column(name = "modify_dtm")
    private LocalDateTime modifyDtm;

}
