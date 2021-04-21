package org.launchcode.liftoffrecipetracker.Data;

import org.launchcode.liftoffrecipetracker.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
}
