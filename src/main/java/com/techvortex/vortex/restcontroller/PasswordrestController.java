package com.techvortex.vortex.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.entity.ChangePasswordForm;
import com.techvortex.vortex.entity.PasswordChangeDTO;
import com.techvortex.vortex.service.DoiMatKhauService;
import com.techvortex.vortex.service.IUserService;


@RestController
@RequestMapping("/api/changepassword")
public class PasswordrestController {
    @Autowired
    IUserService user;
    // @Autowired
    // UserServiceImpl userService;
    // @Autowired
    // JwtProvider jwtProvider;
    // @Autowired
    // JwtTokenFilter jwtTokenFilter;
    //  @Autowired
    // PasswordEncoder passwordEncoder;

    //   @PutMapping("/change/password")
    // public ResponseEntity<?> changePassword(HttpServletRequest request, @Valid @RequestBody ChangePasswordForm changePasswordForm){
    //     String jwt = jwtTokenFilter.getJwt(request);
    //     String username = jwtProvider.getUerNameFromToken(jwt);
    //     Account user;
    //     try {
    //         user = userService.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found with -> username"+username));
    //         boolean matches = passwordEncoder.matches(changePasswordForm.getCurrentPassword(), user.getPassword());
    //         if(changePasswordForm.getNewPassword() != null){
    //             if(matches){
    //                 user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
    //                 userService.save(user);
    //             } else {
    //                 return new ResponseEntity<>(new ResponMessage("no"), HttpStatus.OK);
    //             }
    //         }
    //         return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
    //     } catch (UsernameNotFoundException exception){
    //         return new ResponseEntity<>(new ResponMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
    //     }
    // }
    @PutMapping("/{userName}")
    public ResponseEntity<Account> updateAccount(@PathVariable("userName") String userName,
            @RequestBody ChangePasswordForm updatedAccount) {
        Account existingAccount = user.findById(userName);
        System.out.println(userName);
        if (existingAccount == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Cập nhật các thông tin
        existingAccount.setPassword(updatedAccount.getNewPassword());
        Account savedAccount = user.save(existingAccount);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }
}