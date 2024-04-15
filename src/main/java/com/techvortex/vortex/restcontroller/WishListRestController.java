package com.techvortex.vortex.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.WishList;
import com.techvortex.vortex.service.WishListService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
public class WishListRestController {

  @Autowired
  WishListService favoriteService;

  @GetMapping("/findallfavorite/{username}")
  public ResponseEntity<List<WishList>> getFindAllFavorite(@PathVariable("username") String username) {
    return ResponseEntity.ok(favoriteService.findAllProductByUsername(username));
  }

  @GetMapping("/deletefavorite/{id}")
  public void DeleteFavorite(@PathVariable("id") Integer id) {
    favoriteService.deleteFavorite(id);
  }

  @PostMapping("/addfavorite/{id}/{username}")
  public ResponseEntity<WishList> AddFavorite(@RequestBody WishList favorite, @PathVariable("id") Integer id,
      @PathVariable("username") String username) {
    WishList favoriteEntity = favoriteService.findFavoriteByProductId(id, username);
    if (favoriteEntity != null) {
      return ResponseEntity.ok(favoriteEntity);
    } else {
      favoriteService.save(favorite);
    }
    return ResponseEntity.ok().build();
  }

  @GetMapping("/countfavorite/{id}")
  public ResponseEntity<Integer> getCountFavorite(@PathVariable("id") Integer id) {
    return ResponseEntity.ok(favoriteService.countFavorite(id));
  }

}
