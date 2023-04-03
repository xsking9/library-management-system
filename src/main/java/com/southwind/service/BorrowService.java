package com.southwind.service;

import com.southwind.entity.Borrow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.vo.AdminBorrowVO;
import com.southwind.vo.BorrowVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-03-07
 */
public interface BorrowService extends IService<Borrow> {
    public void add(Integer uid,Integer bid);
    public List<BorrowVO> borrowList(Integer uid);
    public List<BorrowVO> backList(Integer uid);
    public List<AdminBorrowVO> adminBorrowList();
}
