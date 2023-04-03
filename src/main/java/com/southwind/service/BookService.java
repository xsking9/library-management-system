package com.southwind.service;

import com.southwind.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-03-07
 */
public interface BookService extends IService<Book> {
    public PageVO pageList(Integer currentPage);
    public PageVO searchByKeyWord(String keyWord,Integer currentPage);
    public PageVO searchBySort(Integer sid,Integer currentPage);
}
