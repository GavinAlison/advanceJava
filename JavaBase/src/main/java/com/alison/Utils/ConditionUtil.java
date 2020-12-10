//package com.alison.Utils;
//
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Maps;
//import jrx.anyest.engine.base.enums.field.ValueType;
//import jrx.anyest.engine.base.enums.strategy.rule.RuleRelation;
//import jrx.anyest.engine.base.exception.DataException;
//import jrx.anyest.engine.base.model.condition.ConditionGroup;
//import jrx.anyest.engine.base.model.field.ObjectField;
//import jrx.anyest.meta.service.object.composite.excel.GroupData;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.Test;
//
//import java.util.Map;
//
///**
// * @author peidong.meng
// * @date 2020/1/8
// */
//public class ConditionUtil {
//
//    @Test
//    public void kuohao(){
//        String str = "(name = zhangsan and (age >= 12 or level in 1,2,3) and idcard notnull)";
//
//        Map<String, ObjectField> fields = Maps.newHashMap();
//        ObjectField f1 = new ObjectField();
//        f1.setFieldName("姓名");
//        f1.setFieldCode("name");
//        f1.setValueType(ValueType.STRING);
//        fields.put("name", f1);
//
//        ObjectField f2 = new ObjectField();
//        f2.setFieldName("年龄");
//        f2.setFieldCode("age");
//        f2.setValueType(ValueType.STRING);
//        fields.put("age", f2);
//
//        ObjectField f3 = new ObjectField();
//        f3.setFieldName("等级");
//        f3.setFieldCode("level");
//        f3.setValueType(ValueType.STRING);
//        fields.put("level", f3);
//
//        ObjectField f4 = new ObjectField();
//        f4.setFieldName("身份证");
//        f4.setFieldCode("idcard");
//        f4.setValueType(ValueType.STRING);
//        fields.put("idcard", f4);
//
//        char[] chars = str.toCharArray();
//
//        GroupData groupData = new GroupData();
//        groupData.setLeftIndex(0);
//        group(0, chars, groupData);
//
//
//        groupContent(groupData, str);
//
//
//        ConditionGroup conditionGroup = groupData.toConditionGroup(fields, 0);
//        System.out.println(JSONObject.toJSONString(conditionGroup, true));
//    }
//
//    private int group(int i, char[] chars, GroupData groupData){
//        while (i < chars.length - 1) {
//            i++;
//
//            // 含有子
//            if (String.valueOf(chars[i]).equals("(")) {
//                GroupData child = new GroupData();
//                child.setLeftIndex(i);
//                // 处理子
//                i = group(++i, chars, child);
//                groupData.addChild(child);
//            // 找到右侧则结束
//            } else if (String.valueOf(chars[i]).equals(")")) {
//                groupData.setRightIndex(i);
//
//                return i;
//            }
//        }
//
//        return i;
//    }
//
//    private void groupContent(GroupData groupData, String str){
//        groupData.setContent(str.substring(groupData.getLeftIndex() + 1, groupData.getRightIndex()));
//
//        if (CollectionUtils.isNotEmpty(groupData.getChilds())) {
//            groupData.getChilds().forEach(x-> {
//                groupContent(x, str);
//
//                String content = x.getContent();
//                content = content.replaceAll("\\(","\\\\(");
//                content = content.replaceAll("\\)","\\\\)");
//                String s = groupData.getContent().replaceFirst("\\(" + content + "\\)", "@" + x.getLeftIndex());
//                groupData.setRepContent(s);
//            });
//        }
//
//        RuleRelation ruleRelation;
//
//        // 如果是最后一层没有repContent
//        String relationContent = StringUtils.isNotEmpty(groupData.getRepContent()) ? groupData.getRepContent() : groupData.getContent();
//        if (relationContent.contains(RuleRelation.and.name()) && relationContent.contains(RuleRelation.or.name())) {
//            throw new DataException("同组条件不能同时拥有and 和 or!");
//        } else if (relationContent.contains(RuleRelation.or.name())){
//            ruleRelation = RuleRelation.or;
//            // 默认and处理
//        } else {
//            ruleRelation = RuleRelation.and;
//        }
//
//        groupData.setRuleRelation(ruleRelation);
//    }
//}
