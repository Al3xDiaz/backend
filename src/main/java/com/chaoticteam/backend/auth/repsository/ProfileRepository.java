package com.chaoticteam.backend.auth.repsository;

import com.chaoticteam.backend.auth.entities.ProfileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>{

}
