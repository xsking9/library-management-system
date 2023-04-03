package com.southwind.controller;


import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.service.BookService;
import com.southwind.service.BorrowService;
import com.southwind.vo.AdminBorrowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-03-07
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookService bookService;

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url){
        return "/admin/"+url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @GetMapping("/borrowList")
    public String borrowList(Model model){
        List<AdminBorrowVO> adminBorrowVOList = this.borrowService.adminBorrowList();
        model.addAttribute("list", adminBorrowVOList);
        return "/admin/borrow";
    }

    @GetMapping("/allow")
    public String allow(Integer id){
        Borrow borrow = this.borrowService.getById(id);
        borrow.setStatus(1);
        this.borrowService.updateById(borrow);
        return "redirect:/admin/borrowList";
    }

    @GetMapping("/notAllow")
    public String notAllow(Integer id){
        Borrow borrow = this.borrowService.getById(id);
        borrow.setStatus(2);
        this.borrowService.updateById(borrow);
        Book book = this.bookService.getById(borrow.getBid());
        book.setNumber(book.getNumber()+1);
        this.bookService.updateById(book);
        return "redirect:/admin/borrowList";
    }

}

