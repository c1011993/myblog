package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;

/**
 * @program: mybolg
 * @Title FeedbackService
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-29 17:55
 * @Version 1.0
 **/
public interface FeedbackService {
    DataMap getAllFeedback(int rows, int pageNum);

    DataMap getAllPrivateWord();
}
