package com.techvortex.vortex.serviceimplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Cart;
import com.techvortex.vortex.repository.CartDao;
import com.techvortex.vortex.service.CartService;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    CartDao cartDao;

    @Override
    public void save(Cart cart) {
        cartDao.save(cart);
    }

    @Override
    public List<Cart> findAll(String username) {
        return cartDao.findAllByUsername(username);
    }

    @Override
    public Cart findCartByUsernameAndProductDetailId(String UserName, Integer productDetailId) {
        return cartDao.findByAccount_ProductDetailId(UserName, productDetailId);
    }

    @Override
    public List<Cart> findAllCart(String userName) {
        return cartDao.findAllCartByUserName(userName);
    }

    @Override
    public void deleteById(Integer cartId) {
        cartDao.deleteById(cartId);
    }

    @Override
    public Cart findById(Integer id) {
        return cartDao.findById(id).get();
    }

    @Override
    public Cart findByCartId(String userName, Integer id) {
        return cartDao.findCartById(userName, id);
    }

    @Override
    public void clearAllCart(String userName) {
        cartDao.deleteAllCart(userName);
    }

    @Override
    public Long displayqty(String userName) {
        return cartDao.countQty(userName);
    }

    @Override
    public void deleteCartById(Integer id) {
        cartDao.deleteById(id);
    }

    @Override
    public Cart findByProductDetailIdCartAndUserName(Integer detailId, String userName) {
        return cartDao.findByProductDetailIdCart(detailId, userName);
    }

    @Override
    public void deleteAllCartById(String username) {
        cartDao.deleteAllCart(username);
    }

    @Override
    public Integer countCart(String username) {
        return cartDao.getQtyCartThenCount(username);
    }
}
