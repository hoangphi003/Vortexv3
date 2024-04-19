package com.techvortex.vortex.service;

import java.util.List;

import com.techvortex.vortex.entity.Account;
import java.util.Optional;

public interface AccountService {
	// void UpdatePasswordByMail(String gmail);

	public List<Account> findAll(); // in ra lưu vào danh sách

	// public Page<Account> findAll(Pageable pageable); // phân trang
	// public Optional<Account> findById(String UserName);

	public Account findById(String UserName); // tìm kiếm theo id // tìm kiếm theo username

	public Account create(Account account); // thêm danh sách

	public void delete(Account account);

	void updateStatus(String UserName, Boolean Active);

	public List<Account> getAdministrators();
}
