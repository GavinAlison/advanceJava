package com.third.Expression4j;

import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.collections.CollectionUtils;

import java.util.Map;
import java.util.Set;

@Slf4j
public class Expression4jUtil {

    public static double compute(String exp, Map<String, Object> env) {
        Set<String> envSet = env.keySet();
        Expression expression = new ExpressionBuilder(exp).variable(String.join(",", envSet)).build();
        if (CollectionUtils.isNotEmpty(expression.getVariableNames())) {
            env.entrySet().stream().forEach(it -> {
                expression.setVariable(it.getKey(), Double.valueOf(it.getValue().toString()));
            });
        } else {
            log.warn("compute aviator no param");
        }
        double result = expression.evaluate();
        if (log.isDebugEnabled()) {
            log.debug("aviator params: {}, result: {}", String.join(",", envSet), result);
        }
        return result;
    }

}
