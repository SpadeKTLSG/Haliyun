package xyz.spc.serve.guest.controller.users;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.spc.gate.remote.Guest.users.UsersApi;
import xyz.spc.gate.vo.Guest.users.UserVO;

@Tag(name = "Users", description = "用户合集")
@RequestMapping("/Guest/users/users")
@RestController
@RequiredArgsConstructor
public class UsersControl implements UsersApi {


    //! Client

    //! Func


    //! ADD

    //! DELETE
    @Override
    @PostMapping("/add")
    public void add() {

    }

    @Override
    @DeleteMapping("/delete")
    public void delete() {

    }

    @Override
    @PutMapping("/update")
    public void update() {

    }

    @Override
    @GetMapping("/get")
    public UserVO get() {
        return null;
    }
}
