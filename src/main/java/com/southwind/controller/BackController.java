package com.southwind.controller;


import com.southwind.entity.Back;
import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.entity.User;
import com.southwind.service.BackService;
import com.southwind.service.BookService;
import com.southwind.service.BorrowService;
import com.southwind.service.SortService;
import com.southwind.vo.BackVO;
import com.southwind.vo.BorrowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/back")
public class BackController {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BackService backService;
    @Autowired
    private BookService bookService;
    @Autowired
    private SortService sortService;

    @GetMapping("/list")
    public String list(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        List<BorrowVO> backList = this.borrowService.backList(user.getId());
        model.addAttribute("list", backList);
        return "/user/back";
    }

    @GetMapping("/add")
    public String add(Integer id){
        Back back = new Back();
        back.setBrid(id);
        this.backService.save(back);
        Borrow borrow = this.borrowService.getById(id);
        borrow.setStatus(3);
        this.borrowService.updateById(borrow);
        return "redirect:/back/list";
    }

    @GetMapping("/adminList")
    public String adminList(Model model){
        List<BackVO> backVOList = this.backService.backList();
        model.addAttribute("list", backVOList);
        return "/admin/back";
    }

    @GetMapping("/allow")
    public String allow(Integer id){
        Back back = this.backService.getById(id);
        back.setStatus(1);
        this.backService.updateById(back);
        Borrow borrow = this.borrowService.getById(back.getBrid());
        Book book = this.bookService.getById(borrow.getBid());
        book.setNumber(book.getNumber()+1);
        this.bookService.updateById(book);
        return "redirect:/back/adminList";
    }



}

