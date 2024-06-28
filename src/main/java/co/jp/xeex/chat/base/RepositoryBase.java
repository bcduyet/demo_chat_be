package co.jp.xeex.chat.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository for all repositories<br>
 * Recommended to be used as a base repository for all repositories.
 * @author v_long
 */
@NoRepositoryBean
public interface RepositoryBase<T extends EntityBase, ID> extends JpaRepository<T, ID>{
    //add more common methods here...
}