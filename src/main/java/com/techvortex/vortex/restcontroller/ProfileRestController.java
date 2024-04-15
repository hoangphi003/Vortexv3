package com.techvortex.vortex.restcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.service.FileStorageService;
import com.techvortex.vortex.service.LoginService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {
    @Autowired
    LoginService user;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/session")
    public ResponseEntity<Map<String, String>> getSessionId(HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> response = new HashMap<>();
        if (authentication != null && authentication.isAuthenticated()) {
            String loggedInUserName = authentication.getName();
            if (loggedInUserName != null) {
                response.put("sessionId", loggedInUserName);
                return ResponseEntity.ok(response);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{userName}")
    public ResponseEntity<Account> updateAccount(@PathVariable("userName") String userName,
            @RequestBody Account updatedAccount) {
        Account existingAccount = user.findById(userName);
        System.out.println(userName);
        if (existingAccount == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Cập nhật các thông tin
        existingAccount.setFullName(updatedAccount.getFullName());
        // existingAccount.setPassword(updatedAccount.getPassword());
        existingAccount.setPhone(updatedAccount.getPhone());
        existingAccount.setEmail(updatedAccount.getEmail());
         existingAccount.setBirth(updatedAccount.getBirth());
        existingAccount.setGender(updatedAccount.getGender());
        existingAccount.setAvatar(updatedAccount.getAvatar());
        // Tiếp tục cập nhật các thông tin khác nếu cần

        System.out.println(existingAccount.getFullName());
        Account savedAccount = user.save(existingAccount);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    @PostMapping("/{userName}/avatar")
    public ResponseEntity<Account> updateAvatar(@PathVariable String userName,
            @RequestParam("file") MultipartFile file) {

        Account account = user.findById(userName);

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Lưu trữ file và lấy đường dẫn
        String filePath = fileStorageService.storeFile(file);

        // Cập nhật đường dẫn file vào tài khoản
        account.setAvatar(filePath);
        Account updatedAccount = user.save(account);

        System.out.println(filePath);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
}
