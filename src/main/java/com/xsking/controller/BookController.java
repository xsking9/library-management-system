package com.southwind.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.entity.Sort;
import com.southwind.mapper.BorrowMapper;
import com.southwind.service.BookService;
import com.southwind.service.BorrowService;
import com.southwind.service.SortService;
import com.southwind.vo.BookVO;
import com.southwind.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
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
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private SortService sortService;
    @Autowired
    private BorrowService borrowService;

    @GetMapping("/list/{current}")
    public String list(@PathVariable("current") Integer current, Model model){
        PageVO pageVO = this.bookService.pageList(current);
        model.addAttribute("page", pageVO);
        model.addAttribute("sortList", this.sortService.list());
        return "/user/list";
    }

    @PostMapping("/search")
    public String search(String keyWord,Integer current,Integer sid,Model model){
        PageVO pageVO = null;
        //类别检索
        if(!sid.equals(0)){
            pageVO = this.bookService.searchBySort(sid, current);
        } else {
            //关键字检索带分页
            pageVO = this.bookService.searchByKeyWord(keyWord, current);
        }
        model.addAttribute("page", pageVO);
        model.addAttribute("sortList", this.sortService.list());
        return "/user/list";
    }

    @PostMapping("/findByKey")
    public String findByKey(String key,Model model){
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(key), "name", key)
                .or()
                .like(StringUtils.isNotBlank(key), "author", key)
                .or()
                .like(StringUtils.isNotBlank(key), "publish", key);
        List<Book> list = this.bookService.list(queryWrapper);
        List<BookVO> bookVOList = new ArrayList<>();
        for (Book book : list) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            Sort sort = this.sortService.getById(book.getSid());
            bookVO.setSname(sort.getName());
            bookVOList.add(bookVO);
        }
        model.addAttribute("list", bookVOList);
        return "/sysadmin/book";
    }

    @PostMapping("/add")
    public String add(Book book){
        this.bookService.save(book);
        return "redirect:/sysadmin/bookList";
    }

    @GetMapping("/findById/{id}")
    public String findById(@PathVariable("id") Integer id,Model model){
        Book book = this.bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("list", this.sortService.list());
        return "/sysadmin/updateBook";
    }

    @PostMapping("/update")
    public String update(Book book){
        this.bookService.updateById(book);
        return "redirect:/sysadmin/bookList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bid", id);
        this.borrowService.remove(queryWrapper);
        this.bookService.removeById(id);
        return "redirect:/sysadmin/bookList";
    }
}

