package com.casillega.llegaApi.repositories;

import com.casillega.llegaApi.entities.AssociationGroup;
import com.casillega.llegaApi.entities.AssociationGroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssociationGroupRepository extends JpaRepository<AssociationGroup, AssociationGroupId> {

    @Query("SELECT a.followedUser.fullName FROM AssociationGroup a WHERE a.followingUser.id = :userId")
    List<String> findFollowedUsersByUserId(@Param("userId") Long userId);

    @Query("SELECT a.followingUser.fullName FROM AssociationGroup a WHERE a.followedUser.id = :userId")
    List<String> findFollowersByUserId(@Param("userId") Long userId);

    void deleteByFollowingUserIdAndFollowedUserId(Long followingUserId, Long followedUserId);
}
