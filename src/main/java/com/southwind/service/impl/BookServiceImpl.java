package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.Book;
import com.southwind.entity.Sort;
import com.southwind.mapper.BookMapper;
import com.southwind.mapper.SortMapper;
import com.southwind.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.vo.BookVO;
import com.southwind.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-03-07
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private SortMapper sortMapper;

    @Override
    public PageVO pageList(Integer currentPage) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.gt("number", 0);
        Page<Book> page = new Page<>(currentPage, 5);
        Page<Book> resultPage = this.bookMapper.selectPage(page, bookQueryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setCurrentPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        List<BookVO> result = new ArrayList<>();
        for (Book book : resultPage.getRecords()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            QueryWrapper<Sort> sortQueryWrapper = new QueryWrapper<>();
            sortQueryWrapper.eq("id", book.getSid());
            Sort sort = this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        return pageVO;
    }

    @Override
    public PageVO searchByKeyWord(String keyWord,Integer currentPage) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.gt("number", 0);
        bookQueryWrapper.like(StringUtils.isNotBlank(keyWord), "name", keyWord)
                .or()
                .like(StringUtils.isNotBlank(keyWord), "author", keyWord)
                .or()
                .like(StringUtils.isNotBlank(keyWord), "publish", keyWord);
        Page<Book> page = new Page<>(currentPage, 5);
        Page<Book> resultPage = this.bookMapper.selectPage(page, bookQueryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setCurrentPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        List<BookVO> result = new ArrayList<>();
        for (Book book : resultPage.getRecords()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            QueryWrapper<Sort> sortQueryWrapper = new QueryWrapper<>();
            sortQueryWrapper.eq("id", book.getSid());
            Sort sort = this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        return pageVO;
    }

    @Override
    public PageVO searchBySort(Integer sid, Integer currentPage) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.gt("number", 0);
        bookQueryWrapper.eq("sid",sid);
        Page<Book> page = new Page<>(currentPage, 5);
        Page<Book> resultPage = this.bookMapper.selectPage(page, bookQueryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setCurrentPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        List<BookVO> result = new ArrayList<>();
        for (Book book : resultPage.getRecords()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            QueryWrapper<Sort> sortQueryWrapper = new QueryWrapper<>();
            sortQueryWrapper.eq("id", book.getSid());
            Sort sort = this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        return pageVO;
    }
}
