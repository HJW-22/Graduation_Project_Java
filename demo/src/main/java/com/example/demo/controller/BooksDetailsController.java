package com.example.demo.controller;

import com.example.demo.entity.BooksDetails;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.BooksDetailsMapper;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bkDet")
@CrossOrigin(origins = "http://localhost:5173")
public class BooksDetailsController {

    @Resource
    private BooksDetailsMapper booksDetailsMapper;
    private BookMapper bookMapper;

    private String generateBookDetailsId() {
        String prefix = "DT"; // 使用不同的前缀以避免与其他 ID 冲突
        String date = new SimpleDateFormat("yyMMdd").format(new Date());
        String uniqueId = UUID.randomUUID().toString().substring(0, 4); // 使用 UUID 的前 4 位
        return prefix + date + uniqueId; // 合成最终的 detail_id
    }

    // 添加新书籍详情
    @PostMapping
    public ResponseEntity<String> add(@RequestBody BooksDetails booksDetails) {
        booksDetails.setDetailId(generateBookDetailsId()); // 生成并设置新的 detail_id
        int result = booksDetailsMapper.insert(booksDetails);
        return result > 0
                ? ResponseEntity.status(HttpStatus.CREATED).body("添加成功")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加失败");
    }


    @GetMapping("/{bookId}")
    public ResponseEntity<?> getByBookId(@PathVariable String bookId) {
        List<BooksDetails> bookDetailsList = booksDetailsMapper.selectListByBookId(bookId); // 返回多条记录

        // 检查结果
        if (!bookDetailsList.isEmpty()) {
            return ResponseEntity.ok(bookDetailsList); // 返回查询结果
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("未找到书籍详情");
        }
    }


    // 删除书籍详情
    @DeleteMapping("/{detailId}")
    public ResponseEntity<String> delete(@PathVariable String detailId) {
        int result = booksDetailsMapper.deleteById(detailId);
        return result > 0
                ? ResponseEntity.ok("删除成功")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("删除失败");
    }



}