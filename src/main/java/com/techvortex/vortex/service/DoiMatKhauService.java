package com.techvortex.vortex.service;

import com.techvortex.vortex.entity.Account;

public interface DoiMatKhauService {
    // Nam đổi mật khẩu
	void changeUserPassword(Account user, String password);

	boolean checkIfValidOldPassword(Account user, String password);

	Account getCurrentUser();
}
