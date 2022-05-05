package guc.bttsBtngan.authentication.dao;

import guc.bttsBtngan.authentication.model.DAOUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRespository extends CrudRepository<DAOUser,Long> {

    DAOUser findByUsername(String username);

}
