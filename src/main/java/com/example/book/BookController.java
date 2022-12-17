package com.example.book;

import com.example.common.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BookController {


    @Autowired
    BookService bookService;

    @Autowired
    FileUpload upload;
    @Autowired
    BookDAO bookDAO;

    @RequestMapping("/")
    public String home() {
        return "login";
    }

    @RequestMapping(value = "/book/list", method = RequestMethod.GET)
    public String booklist(Model model) {
        model.addAttribute("list", bookService.getBookList());
        return "posts";
    }

    @RequestMapping(value = "/book/add", method = RequestMethod.GET)
    public String addPost() {
        return "addpostform";
    }

    @RequestMapping(value = "/book/addok", method = RequestMethod.POST)
    public String addPostOK(HttpServletRequest request) {

        BookVO vo =upload.uploadPhoto(request);
        int i = bookService.insertBook(vo);
        if (i == 0)
            System.out.println("데이터 추가에 실패했습니다.");
        else
            System.out.println("데이터 추가에 성공했습니다.");
        return "redirect:list";
    }

    @RequestMapping(value = "/book/editform/{id}", method = RequestMethod.GET)
    public String editPost(@PathVariable("id") int id, Model model) {
        BookVO bookVO = bookService.getBook(id);
        model.addAttribute("bookVO", bookVO);
        return "editform";
    }

    @RequestMapping(value = "/book/editok", method = RequestMethod.POST)
    public String editPostOK(HttpServletRequest request) {

        BookVO vo =upload.uploadPhoto(request);
        int i = bookService.updateBook(vo);
        if (i == 0)
            System.out.println("데이터 수정에 실패했습니다.");
        else
            System.out.println("데이터 수정에 성공했습니다.");
        return "redirect:list";
    }

    @RequestMapping(value = "/book/deleteok/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") int id,HttpServletRequest request) {
        String filename = bookDAO.getPhotoFilename(id); //ddddd
        if(filename != null)
            FileUpload.deleteFile(request,filename);

        int i = bookService.deleteBook(id);
        if (i == 0)
            System.out.println("데이터 삭제에 실패했습니다.");
        else
            System.out.println("데이터 삭제에 성공했습니다.");
        return "redirect:../list";
    }

    @RequestMapping(value = "/book/view/{id}", method = RequestMethod.GET)
    public String viewPost(@PathVariable("id") int id, Model model) {
        BookVO bookVO = bookService.getBook(id);
        model.addAttribute("book", bookVO);
        return "view";
    }
}
