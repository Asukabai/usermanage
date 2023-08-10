package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    // 新增用户
    @PostMapping
    public Result add(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    // 修改用户
    @PutMapping
    public Result update(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    // 根据id查询用户
    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable Long id) {
        return Result.success(userService.findById(id));
    }

    // 查询所有用户
    @GetMapping
    public Result<List<User>> findAll() {
        return Result.success(userService.findAll());
    }

    // 分页查询用户
    @GetMapping("/page")
    public Result<Page<User>> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "8") Integer pageSize,
                                       @RequestParam(required = false) String name) {
        return Result.success(userService.findPage(pageNum, pageSize, name));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAvatar(@RequestParam("avatar") MultipartFile avatar) {
        try {
            // 获取原始文件名
            String originalFilename = avatar.getOriginalFilename();

            // 在数据库中查找已有记录
            UserService existingAvatar = userService.findByOriginalFilename(originalFilename);

            if (existingAvatar != null) {
                // 如果已有记录，则更新文件内容
                // 执行更新逻辑，例如更新文件内容、更新其他字段等
                // 更新其他字段...
            } else {
                // 如果没有记录，则插入新的文件记录
                // 设置其他字段...
                // newAvatar.setSomeField("Value");
            }

            return ResponseEntity.ok("上传成功！");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败：" + e.getMessage());
        }
    }

}
