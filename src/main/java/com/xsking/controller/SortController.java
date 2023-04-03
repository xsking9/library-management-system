package com.southwind.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.southwind.entity.Back;
import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.entity.Sort;
import com.southwind.service.BackService;
import com.southwind.service.BookService;
import com.southwind.service.BorrowService;
import com.southwind.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

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
@RequestMapping("/sort")
public class SortController {

    @Autowired
    private SortService sortService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BackService backService;

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("list", this.sortService.list());
        return "sysadmin/addBook";
    }

    @PostMapping("/search")
    public String search(String name,Model model){
        QueryWrapper<Sort> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        List<Sort> list = this.sortService.list(queryWrapper);
        model.addAttribute("list", list);
        return "/sysadmin/sort";
    }

    @PostMapping("/add")
    public String add(Sort sort){
        this.sortService.save(sort);
        return "redirect:/sysadmin/sortList";
    }

    @GetMapping("/findById/{id}")
    public String findById(@PathVariable("id") Integer id,Model model){
        Sort sort = this.sortService.getById(id);
        model.addAttribute("sort", sort);
        return "/sysadmin/updateSort";
    }

    @PostMapping("/update")
    public String update(Sort sort){
        this.sortService.updateById(sort);
        return "redirect:/sysadmin/sortList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq("sid", id);
        List<Book> bookList = this.bookService.list(bookQueryWrapper);
        for (Book book : bookList) {
            QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
            borrowQueryWrapper.eq("bid", book.getId());
            List<Borrow> borrowList = this.borrowService.list(borrowQueryWrapper);
            for (Borrow borrow : borrowList) {
                QueryWrapper<Back> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("brid", borrow.getId());
                this.backService.remove(queryWrapper);
                this.borrowService.removeById(borrow.getId());
            }
            this.bookService.removeById(book.getId());
        }
        this.sortService.removeById(id);
        return "redirect:/sysadmin/sortList";
    }
}

