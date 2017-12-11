package com.caej.batch.processor;

/**
 * @author lj on 2017/12/4.
 * @version 1.0
 * @company caej
 */

import com.caej.batch.model.BufAbonus;
import com.caej.batch.model.BufAccountConfirm;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 业务处理类。
 *
 * @author Wanggc
 */
@Component("abonusProcessor")
public class AbonusProcessor implements
        ItemProcessor<BufAbonus, BufAbonus> {

    /**
     * 对取到的数据进行简单的处理。
     *
     * @param student
     *            处理前的数据。
     * @return 处理后的数据。
     * @exception Exception
     *                处理是发生的任何异常。
     */
    public BufAbonus process(BufAbonus student) throws Exception {
//        /* 合并ID和名字 */
//        student.setName(student.getID() + "--" + student.getName());
//        /* 年龄加2 */
//        student.setAge(student.getAge() + 2);
//        /* 分数加10 */
//        student.setScore(student.getScore() + 10);
        /* 将处理后的结果传递给writer */
        return student;
    }}