package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.entity.Sort;
import com.southwind.entity.User;
import com.southwind.mapper.BookMapper;
import com.southwind.mapper.BorrowMapper;
import com.southwind.mapper.SortMapper;
import com.southwind.mapper.UserMapper;
import com.southwind.service.BorrowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.vo.AdminBorrowVO;
import com.southwind.vo.BorrowVO;
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
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements BorrowService {

    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private SortMapper sortMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(Integer uid, Integer bid) {
        Borrow borrow = new Borrow();
        borrow.setBid(bid);
        borrow.setUid(uid);
        this.borrowMapper.insert(borrow);
        Book book = this.bookMapper.selectById(bid);
        book.setNumber(book.getNumber()-1);
        this.bookMapper.updateById(book);
    }

    @Override
    public List<BorrowVO> borrowList(Integer uid) {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("uid", uid);
        List<Borrow> borrowList = this.borrowMapper.selectList(borrowQueryWrapper);
        List<BorrowVO> borrowVOList = new ArrayList<>();
        for (Borrow borrow : borrowList) {
            BorrowVO borrowVO = new BorrowVO();
            BeanUtils.copyProperties(borrow, borrowVO);
            Book book = this.bookMapper.selectById(borrow.getBid());
            BeanUtils.copyProperties(book, borrowVO);
            borrowVO.setBookName(book.getName());
            Sort sort = this.sortMapper.selectById(book.getSid());
            borrowVO.setSortName(sort.getName());
            borrowVOList.add(borrowVO);
        }
        return borrowVOList;
    }

    @Override
    public List<BorrowVO> backList(Integer uid) {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("uid", uid);
        borrowQueryWrapper.eq("status", 1);
        List<Borrow> borrowList = this.borrowMapper.selectList(borrowQueryWrapper);
        List<BorrowVO> borrowVOList = new ArrayList<>();
        for (Borrow borrow : borrowList) {
            BorrowVO borrowVO = new BorrowVO();
            BeanUtils.copyProperties(borrow, borrowVO);
            Book book = this.bookMapper.selectById(borrow.getBid());
            BeanUtils.copyProperties(book, borrowVO);
            borrowVO.setId(borrow.getId());
            borrowVO.setBookName(book.getName());
            borrowVOList.add(borrowVO);
        }
        return borrowVOList;
    }

    @Override
    public List<AdminBorrowVO> adminBorrowList() {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("status", 0);
        List<Borrow> borrowList = this.borrowMapper.selectList(borrowQueryWrapper);
        List<AdminBorrowVO> adminBorrowVOList = new ArrayList<>();
        for (Borrow borrow : borrowList) {
            AdminBorrowVO adminBorrowVO = new AdminBorrowVO();
            BeanUtils.copyProperties(borrow, adminBorrowVO);
            User user = this.userMapper.selectById(borrow.getUid());
            adminBorrowVO.setUserName(user.getUsername());
            Book book = this.bookMapper.selectById(borrow.getBid());
            adminBorrowVO.setBookName(book.getName());
            BeanUtils.copyProperties(book, adminBorrowVO);
            Sort sort = this.sortMapper.selectById(book.getSid());
            adminBorrowVO.setSortName(sort.getName());
            adminBorrowVO.setId(borrow.getId());
            adminBorrowVOList.add(adminBorrowVO);
        }
        return adminBorrowVOList;
    }
}
