package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Feedback;
import com.javatiaocao.myblog.model.PrivateWord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: mybolg
 * @Title FeedbackMapper
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-29 18:11
 * @Version 1.0
 **/
@Mapper
@Repository
public interface FeedbackMapper {
    List<Feedback> getAllFeedback();

    List<PrivateWord> getAllPrivateWord();

//    List<String> getPublisherIdList();
}
