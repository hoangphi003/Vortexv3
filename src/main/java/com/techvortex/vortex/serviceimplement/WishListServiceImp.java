package com.techvortex.vortex.serviceimplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.WishList;
import com.techvortex.vortex.repository.WishListDao;
import com.techvortex.vortex.service.WishListService;

@Service
public class WishListServiceImp implements WishListService {

    @Autowired
    WishListDao favoriteDao;

    @Override
    public List<WishList> findAllProductByUsername(String username) {
        return favoriteDao.getFindAllFavoriteByUsername(username);
    }

    @Override
    public void deleteFavorite(Integer id) {
        favoriteDao.deleteById(id);
    }

    @Override
    public WishList findFavoriteByProductId(Integer id, String username) {
        return favoriteDao.findByProductID(id, username);
    }

    @Override
    public void save(WishList favorite) {
        favoriteDao.save(favorite);
    }

    @Override
    public Integer countFavorite(Integer id) {
        return favoriteDao.countFavoriteByProductId(id);
    }

    @Override
    public Object findAll() {
        return favoriteDao.findAll();
    }
}
