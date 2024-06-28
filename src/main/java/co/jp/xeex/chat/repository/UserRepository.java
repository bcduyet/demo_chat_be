package co.jp.xeex.chat.repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto;
import co.jp.xeex.chat.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends RepositoryBase<User, String> {
    /**
     * Get user by login name or email
     * 
     * @param login login name or email
     * @return User
     */
    @Query(value = "select * from dfw_m050m where emp_cd = :login or email =:login limit 1 ", nativeQuery = true)
    User getLoginUser(@Param("login") String login);

    /**
     * Get user by user name
     * 
     * @param empCd user name
     * @return User
     */
    @Query(value = "select * from dfw_m050m where emp_cd = :empCd limit 1 ", nativeQuery = true)
    User findByUserName(@Param("empCd") String empCd);

    /**
     * Find users
     * 
     * @param id         user id. set null if you don't want to filter by id
     * @param userName   user name. set null if you don't want to filter by user
     *                   name
     * @param deptCd     department code. set null if you don't want to filter by
     * @param email      email. set null if you don't want to filter by email
     * @param fullName   full name. set null if you don't want to filter by full
     *                   name
     * @param activeFlag active flag. Set -1 if you want to get all users
     * 
     * @param useFlag    use flag. Set -1 if you want to get all users
     * @return List<User> list of users
     */
    @Query(value = "SELECT * FROM dfw_m050m u WHERE " +
            "(:id = '' OR u.id = :id) AND " +
            "(:userName IS NULL OR u.emp_cd = :userName) AND " +
            "(:deptCd IS NULL OR u.dept_Cd = :deptCd) AND " +
            "(:fullName IS NULL OR u.full_Name LIKE %:fullName%) AND " +
            "(:email IS NULL OR u.email = :email) AND " +
            "(:activeFlag = -1 OR u.active_flag = :activeFlag) AND " +
            "(:useFlag = -1 OR u.use_flag = :useFlag)", nativeQuery = true)
    List<User> findUsers(@Param("id") String id,
            @Param("userName") String userName,
            @Param("deptCd") String deptCd,
            @Param("fullName") String fullName,
            @Param("email") String email,
            @Param("activeFlag") Integer activeFlag,
            @Param("useFlag") Integer useFlag);

    /************************/
    // hard/soft delete user
    /************************/
    /**
     * Hard delete users
     * 
     * @param idList list of user id
     */
    @Modifying
    @Query(value = "delete from dfw_m050m where id in (:idList)", nativeQuery = true)
    void hardDeleteUsers(@Param("idList") String[] idList);

    /**
     * Soft delete users
     * 
     * @param idList list of user id
     * @param flag   flag value (get from User.U)
     */
    @Modifying
    @Query(value = "update dfw_m050m set active_flag = :flag where id in (:userIdList)", nativeQuery = true)
    void changeUserFlag(@Param("userIdList") String[] userIdList, @Param("flag") Integer flag);

    /**
     * Find user by dept
     * 
     * @param deptCd deptCd
     */
    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto(" +
            "   t.empCd, " +
            "   t.avatar, " +
            "   t.fullName) " +
            " FROM " +
            "   User t " +
            " WHERE " +
            "   :deptCd IS NULL OR t.deptCd = :deptCd ")
    List<MemberDto> findByDept(@Param("deptCd") String deptCd);

    /**
     * find member in group/friend
     * 
     * @param groupId
     * @return
     */
    @Query(value = "SELECT distinct new co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto(" +
            "   empCd, " +
            "   avatar, " +
            "   fullname) " +
            " FROM(" +
            "   SELECT " +
            "     t2.empCd AS empCd, " +
            "     t2.avatar AS avatar, " +
            "     t2.fullName AS fullname " +
            "   FROM " +
            "     ChatGroupMember t1, " +
            "     User t2 " +
            "   WHERE " +
            "     :groupId = '' OR (t1.memberEmpCd = t2.empCd AND t1.groupId = :groupId) " +
            "   UNION " +
            "   SELECT " +
            "     t2.empCd AS empCd, " +
            "     t2.avatar AS avatar, " +
            "     t2.fullName AS fullname " +
            "   FROM " +
            "     ChatFriend t1, " +
            "     User t2 " +
            "   WHERE " +
            "     :groupId = '' OR (t1.empCd1 = t2.empCd AND t1.groupId = :groupId)) t ")
    List<MemberDto> findByGroup(@Param("groupId") String groupId);

    /**
     * Find member none friend
     * 
     * @param empCd
     * @return
     */
    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto(" +
            "   t.empCd, " +
            "   t.avatar, " +
            "   t.fullName) " +
            " FROM " +
            "   User t " +
            " WHERE " +
            "   t.empCd <> :empCd " +
            "   AND t.empCd NOT IN (SELECT t.empCd2 FROM ChatFriend t WHERE t.empCd1 = :empCd) ")
    List<MemberDto> findNoneByFriend(@Param("empCd") String empCd);

    /**
     * Find avatar of user
     * 
     * @param empCd
     * @return
     */
    @Query(value = "SELECT " +
            "   avatar " +
            " FROM " +
            "   dfw_m050m " +
            " WHERE " +
            "   emp_cd = :empCd ", nativeQuery = true)
    String findAvatarByUser(@Param("empCd") String empCd);

    /**
     * v_long: get all users are in the relationship of the user (emp_cd).
     * Include: 1. list of friends ; 2. list of users that their are in the same
     * chat groups
     */
    @Query(value = // 1. friend
    "SELECT u.* " +
            "FROM dfw_m050m u " +
            "JOIN chat_friend f " +
            "ON u.emp_cd = f.emp_cd_1 OR u.emp_cd = f.emp_cd_2 " +
            "WHERE :empCd IN (f.emp_cd_1,f.emp_cd_2) " +
            "AND u.emp_cd != :empCd " + // exclusive current user
            "UNION " +
            // 2. list of users that their are in the same group with the user
            "SELECT u.* " +
            "FROM dfw_m050m u " +
            "JOIN chat_group_member gm " +
            "ON u.emp_cd = gm.member_emp_cd " +
            "WHERE EXISTS (" +
            "    SELECT 1 " +
            "    FROM chat_group_member " +
            "    WHERE member_emp_cd = :empCd " +
            "    AND group_id = gm.group_id " +
            ")" +
            "AND u.emp_cd != :empCd", nativeQuery = true) // exclusive current user
    List<User> findUsersOnRelationshipOf(@Param("empCd") String empCd);
}
