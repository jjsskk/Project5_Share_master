package com.example.book;

import java.util.List;

public interface BookService {
    int insertBook(BookVO vo);

    int updateBook(BookVO vo);

    int deleteBook(int sid);

    BookVO getBook(int sid);

    List<BookVO> getBookList();
}
