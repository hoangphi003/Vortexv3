package com.techvortex.vortex.service;

import java.util.List;

import com.techvortex.vortex.entity.WishList;

public interface WishListService {

    List<WishList> findAllProductByUsername(String username);

    void deleteFavorite(Integer id);

    WishList findFavoriteByProductId(Integer id,String username);

    void save(WishList favorite);

    Integer countFavorite(Integer id);

    Object findAll();

}
