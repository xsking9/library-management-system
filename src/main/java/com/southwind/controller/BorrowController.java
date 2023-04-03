package com.southwind.controller;


import com.southwind.entity.User;
import com.southwind.service.BorrowService;
import com.southwind.vo.BorrowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @GetMapping("/add")
    public String add(Integer bookId, HttpSession session){
        User user = (User) session.getAttribute("user");
        this.borrowService.add(user.getId(), bookId);
        return "redirect:/borrow/list";
    }

    @GetMapping("/list")
    public String list(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        List<BorrowVO> borrowVOList = this.borrowService.borrowList(user.getId());
        model.addAttribute("list", borrowVOList);
        return "/user/borrow";
    }

}

