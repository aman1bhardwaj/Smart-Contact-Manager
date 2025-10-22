package com.scm.scm20.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.scm20.entities.Contact;

@Repository
public interface ContactRepo extends JpaRepository<Contact , String>{

    @Query("Select c from Contact c where c.user.userId=:userId")
     List<Contact> findContactByUserId(@Param("userId") String userID);

     List<Contact> findByUser_UserIdAndIsFavouriteTrue(String userId);
}
