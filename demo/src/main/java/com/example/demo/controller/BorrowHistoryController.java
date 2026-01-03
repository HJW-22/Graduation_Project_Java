package com.example.demo.controller;

import com.example.demo.entity.BooksDetailsRole;
import com.example.demo.entity.BorrowHistory;
import com.example.demo.entity.BooksDetails;
import com.example.demo.entity.User;
import com.example.demo.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bkHit")
public class BorrowHistoryController {

    @Autowired
    private BorrowHistoryMapper borrowHistoryMapper;

    @Autowired
    private BooksDetailsMapper booksDetailsMapper;

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/borrow/{userId}")
    public ResponseEntity<?> getUserId(@PathVariable String userId) {
        // 根据 userId 查询借阅记录
        List<BorrowHistoryWithDetailsDTO> borrowHistoryList = borrowHistoryMapper.selectListByUserId(userId);

        // 检查结果
        if (!borrowHistoryList.isEmpty()) {
            return ResponseEntity.ok(borrowHistoryList); // 返回查询结果
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("未找到书籍借阅");
        }
    }
    private String generateBookHistoryId() {
        String prefix = "HT"; // 使用不同的前缀以避免与其他 ID 冲突
        String date = new SimpleDateFormat("yyMMdd").format(new Date());
        String uniqueId = UUID.randomUUID().toString().substring(0, 4); // 使用 UUID 的前 4 位
        return prefix + date + uniqueId; // 合成最终的 detail_id
    }

    @Transactional // 确保借阅操作的原子性
    @PostMapping("/borrow")
    public String borrowBook(@RequestBody BorrowRequestDto borrowRequest) {
        String userId = borrowRequest.getUserId();
        String detailId = borrowRequest.getDetailId();
        String borrowDate = borrowRequest.getBorrowDate();

        // 打印接收到的参数以调试
        System.out.println("Received userId: " + userId);
        System.out.println("Received detailId: " + detailId);
        System.out.println("Received borrowDate: " + borrowDate);

        // 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            return "用户不存在。";
        }

        // 检查书籍详情是否存在
        BooksDetails bookDetail = booksDetailsMapper.selectById(detailId);
        if (bookDetail == null) {
            return "书籍详情不存在。";
        }

        // 创建新借阅记录
        BorrowHistory borrowHistory = new BorrowHistory();
        borrowHistory.setUserid(userId);
        borrowHistory.setDetailId(detailId);

        String HistoryTemp = generateBookHistoryId();
        borrowHistory.setHistoryId(HistoryTemp);

        // 格式化排除 borrowDate，使用当前日期作为借阅日期
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            borrowHistory.setBorrowAt(dateFormat.parse(borrowDate)); // 使用前端传入的借阅日期
        } catch (ParseException e) {
            return "借阅日期格式错误。";
        }

        borrowHistory.setCreateAt(new Date());

        // 插入借阅记录
        int insertResult = borrowHistoryMapper.insert(borrowHistory);
        if (insertResult <= 0) {
            return "借阅失败，请重试。";
        }

        // 更新书籍状态
        bookDetail.setStatus(BooksDetailsRole.borrowed); // 使用枚举更清晰
        int updateResult = booksDetailsMapper.updateById(bookDetail);
        if (updateResult <= 0) {
            return "借阅成功，但更新书本状态失败。";
        }

        return "借阅成功！";
    }

    @Transactional // 确保归还操作的原子性
    @PostMapping("/return")
    public String returnBook(@RequestBody ReturnRequestDto returnRequest) {
        String userId = returnRequest.getUserId();
        String historyId = returnRequest.getHistoryId();

        // 打印接收到的参数以调试
        System.out.println("Received userId: " + userId);
        System.out.println("Received historyId: " + historyId);

        // 根据 historyId 查找借阅记录
        BorrowHistory borrowHistory = borrowHistoryMapper.selectById(historyId);
        if (borrowHistory == null) {
            return "借阅记录不存在。";
        }

        // 更新书籍详情
        BooksDetails bookDetail = booksDetailsMapper.selectById(borrowHistory.getDetailId());
        if (bookDetail == null) {
            return "书籍详情不存在。";
        }

        // 更新书籍状态
        bookDetail.setStatus(BooksDetailsRole.available); // 设置状态为可借阅
        int updateResult = booksDetailsMapper.updateById(bookDetail);
        if (updateResult <= 0) {
            return "归还成功，但更新书本状态失败。";
        }

        // 删除借阅记录
        int deleteResult = borrowHistoryMapper.deleteById(historyId);
        if (deleteResult <= 0) {
            return "归还失败，请重试。";
        }

        return "归还成功！";
    }
    /*
    @Transactional
    @PostMapping("/return")
    public String returnBookDemo(@RequestBody ReturnRequestDto returnRequest) {
        String userId = returnRequest.getUserId();
        String historyId = returnRequest.getHistoryId();

        // 根据 historyId 查找借阅记录
        BorrowHistory borrowHistory = borrowHistoryMapper.selectById(historyId);
        if (borrowHistory == null) {return "借阅记录不存在。";}

        // 更新书籍详情
        BooksDetails bookDetail = booksDetailsMapper.selectById(borrowHistory.getDetailId());
        if (bookDetail == null) {return "书籍详情不存在。";}

        // 更新书籍状态
        bookDetail.setStatus(BooksDetailsRole.available); // 设置状态为可借阅
        if (booksDetailsMapper.updateById(bookDetail) <= 0) {return "归还成功，但更新书本状态失败。";}

        // 删除借阅记录
        if (borrowHistoryMapper.deleteById(historyId) <= 0) {return "归还失败，请重试。";}

        return "归还成功！";
    }

     */

    /*
    @Transactional
    @PostMapping("/borrow")
    public String borrowBookDemo(@RequestBody BorrowRequestDto borrowRequest) {
        String userId = borrowRequest.getUserId();
        String detailId = borrowRequest.getDetailId();

        // 检查书籍详情是否存在
        BooksDetails bookDetail = booksDetailsMapper.selectById(detailId);
        if (bookDetail == null) {return "书籍详情不存在。";}

        // 创建新借阅记录
        BorrowHistory borrowHistory = new BorrowHistory();
        borrowHistory.setUserid(userId);
        borrowHistory.setDetailId(detailId);
        borrowHistory.setHistoryId(generateBookHistoryId()); // 直接生成并设置历史记录 ID
        borrowHistory.setCreateAt(new Date());

        // 格式化并设置借阅日期，使用当前日期作为借阅日期
        try {
            borrowHistory.setBorrowAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(borrowRequest.getBorrowDate()));
        } catch (ParseException e) {return "借阅日期格式错误。";}

        // 插入借阅记录
        borrowHistoryMapper.insert(borrowHistory);

        // 更新书籍状态
        bookDetail.setStatus(BooksDetailsRole.borrowed); // 使用枚举更清晰
        booksDetailsMapper.updateById(bookDetail);

        return "借阅成功！";
    }
     */
}