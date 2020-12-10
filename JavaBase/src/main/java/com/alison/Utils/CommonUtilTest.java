//package com.alison.Utils;
//
//import com.alibaba.fastjson.JSONObject;
//import jrx.anyest.engine.base.exception.DataException;
//import jrx.anyest.engine.base.model.field.ObjectField;
//import org.junit.Test;
//
//import java.beans.PropertyDescriptor;
//import java.util.Date;
//
///**
// * @author peidong.meng
// * @date 2019/4/12
// */
//public class CommonUtilTest {
//
//    @Test
//    public void setTest(){
//
////        ModelObjectInfoEntity entity = new ModelObjectInfoEntity();
//
////        ObjectField entity = new ObjectField();
//        try {
//            PropertyDescriptor updatePerson = new PropertyDescriptor("updatePerson", entity.getClass());
//            PropertyDescriptor contentCode = new PropertyDescriptor("contentCode", entity.getClass());
//            PropertyDescriptor updateTime = new PropertyDescriptor("updateTime", entity.getClass());
//
////            updatePerson.getWriteMethod().invoke(entity,"zhangsan");
////            contentCode.getWriteMethod().invoke(entity,"1");
////            updateTime.getWriteMethod().invoke(entity,new Date());
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new DataException("获取用户失败！");
//        }
//
//        System.out.println(JSONObject.toJSONString(entity,true));
//    }
//}
