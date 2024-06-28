package co.jp.xeex.chat.entity;

import co.jp.xeex.chat.base.EntityBase;
import co.jp.xeex.chat.domains.file.enums.FileType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ChatFriend
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "file")
public class File extends EntityBase {
    @Column(name = "origin_name", length = 100, nullable = false)
    private String originName;

    @Column(name = "store_name", length = 100, nullable = false, unique = true)
    private String storeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", length = 5, nullable = false)
    private FileType fileType;
}
