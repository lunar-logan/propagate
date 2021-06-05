package org.propagate.persistence.mongo.dao;

import lombok.AllArgsConstructor;
import org.propagate.common.dao.UserDao;
import org.propagate.common.domain.User;
import org.propagate.persistence.mongo.helper.ConversionHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class UserDaoImpl implements UserDao {
    private final UserMongoDao userMongoDao;

    @Override
    public User save(User entity) {
        return Optional.of(entity)
                .map(ConversionHelper::convert)
                .map(userMongoDao::save)
                .map(ConversionHelper::convert)
                .get();
    }

    @Override
    public Optional<User> findById(String id) {
        return userMongoDao.findById(id)
                .map(ConversionHelper::convert);
    }

    @Override
    public List<User> findAll() {
        return userMongoDao.findAll().stream()
                .map(ConversionHelper::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAll(int page, int size) {
        return userMongoDao.findAll(PageRequest.of(page, size))
                .map(ConversionHelper::convert)
                .toList();
    }

    @Override
    public void delete(User entity) {
        userMongoDao.deleteById(entity.getId());
    }

    @Override
    public void deleteById(String id) {
        userMongoDao.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userMongoDao.findByEmail(email)
                .map(ConversionHelper::convert);
    }
}
