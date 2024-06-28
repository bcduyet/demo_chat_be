package co.jp.xeex.chat.repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.entity.User;
import org.springframework.stereotype.Repository;

/**
 * The repository for User management.
 * 
 * @author v_long
 */
@Repository
public interface AdminRepository extends RepositoryBase<User, String> {

}
