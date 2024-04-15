package com.techvortex.vortex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Color;

@Service
public interface ColorService {

    public List<Color> findAll(); // in ra lưu vào danh sách

    public Color findById(Integer colorId); // tìm kiếm theo id

    public Color create(Color color); // thêm danh sách

    public Color update(Color color); // sửa danh sách

    public void delete(Color color);

    public boolean existsByNameIgnoreCase(String colorName);

}
