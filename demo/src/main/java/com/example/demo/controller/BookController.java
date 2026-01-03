package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    @Resource
    private BookMapper bookMapper;


    private String generateBooKId() {
        String prefix = "BK";
        String date = new SimpleDateFormat("yyMMdd").format(new Date());
        String uniqueId = UUID.randomUUID().toString().substring(0, 4); // 使用 UUID 的前 4 位
        return prefix + date + uniqueId; // 合成最终的 userid
    }

    // 添加新书籍
    @PostMapping
    public ResponseEntity<String> add(@RequestBody Book book) {
        book.setBookId(generateBooKId()); // 生成并设置新的 userid
        int result = bookMapper.insert(book);
        return result > 0
                ? ResponseEntity.status(HttpStatus.CREATED).body("添加成功")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加失败");
    }



//    // 根据 bookId 查询书籍
//    @GetMapping("/{bookId}")
//    public ResponseEntity<Book> get(@PathVariable String bookId) {
//        Book book = bookMapper.selectById(bookId);
//        return book != null
//                ? ResponseEntity.ok(book)
//                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }


    // 删除书籍
    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> delete(@PathVariable String bookId) {
        int result = bookMapper.deleteById(bookId);
        return result > 0
                ? ResponseEntity.ok("删除成功")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("删除失败");
    }

    // 更新书籍信息
    @PutMapping("/{bookId}")
    public ResponseEntity<String> update(@PathVariable String bookId, @RequestBody Book book) {
        // 设置传入的 bookId
        book.setBookId(bookId);
        int result = bookMapper.updateById(book); // 更新书籍信息
        return result > 0
                ? ResponseEntity.ok("更新成功")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败，书籍未找到或数据未改变");
    }

    //查询书籍
    @GetMapping
    public ResponseEntity<List<Book>> list(@RequestParam String search) {
        List<Book> books;

        if (search != null && !search.isEmpty()) {        //是否提供了搜索关键字
            books = bookMapper.selectBySearch(search);    //调用部分查询语句
        } else {
            books = bookMapper.selectList(null); //调用查询全部
        }
        return ResponseEntity.ok(books);
    }
}